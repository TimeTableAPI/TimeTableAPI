package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.UUID;

import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.services.CookiesHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassCourseLoaderService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ClassCourseController {
    public static final String TIMETABLEPATH = "/timetable";

    //region timetable
    @GetMapping(value = ClassCourseController.TIMETABLEPATH)
    public String fetchTimeTable(HttpServletResponse response, HttpServletRequest request, Model model) {

        UUID uuid = CookiesHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            model.addAttribute("timetable", context.getClassCourses());
        }

        return "timetable";
    }

    @GetMapping(value = ClassCourseController.TIMETABLEPATH + "/download")
    public ResponseEntity<Resource> downloadTimeTable(HttpServletResponse response, HttpServletRequest request, Model model) {

        UUID uuid = CookiesHandlerService.getUUID(request, response);
        if(!SessionsService.containsSession(uuid)) return (ResponseEntity<Resource>) ResponseEntity.notFound();

        Context context = SessionsService.getContext(uuid);
        model.addAttribute("timetable", context.getClassCourses());

        try {
            File file = ClassCourseLoaderService.export(context.getClassCourses());

            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ExportedClasses.csv");
                header.add("Cache-Control", "no-cache, no-store, must-revalidate");
                header.add("Pragma", "no-cache");
                header.add("Expires", "0");

                return ResponseEntity.ok()
                        .headers(header)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return (ResponseEntity<Resource>) ResponseEntity.notFound();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }
    }

    @PostMapping(value = ClassCourseController.TIMETABLEPATH + "/upload")
    public String timeTableUpload(HttpServletResponse response, HttpServletRequest request, @RequestParam("file_classes") MultipartFile file_classes, @RequestParam("file_classrooms") MultipartFile file_classrooms, RedirectAttributes attributes, Model model) {
        // check if file is empty
        if (file_classes.isEmpty() || file_classrooms.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/" + ClassCourseController.TIMETABLEPATH;
        }

        // normalize the file path
        try {
            attributes.addFlashAttribute("message", "You successfully uploaded\n" + file_classes.getOriginalFilename() + "and" + file_classrooms.getOriginalFilename() + '!');

            //FileUploadService.uploadFile(file_classes);
            //FileUploadService.uploadFile(file_classrooms);

            LinkedList<ClassCourse> loadedClassCourses = ClassCourseLoaderService.load(file_classes, false);
            LinkedList<Classroom> loadedClassRooms = ClassroomLoaderService.load(file_classrooms, false);
            Context context = new Context(loadedClassCourses, loadedClassRooms, new BasicAlgorithmService());
            context.computeSolutionWithAlgorithm();

            UUID uuid = CookiesHandlerService.getUUID(request, response);
            SessionsService.putSession(uuid, context);

            model.addAttribute("timetable", loadedClassCourses);

            // return success response
            return "timetable";
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "Something went wrong with the upload or the files...\n" + file_classes.getOriginalFilename() + "and" + file_classrooms.getOriginalFilename() + '!');
            e.printStackTrace();
            //model.addAttribute("timetable", ClassCourseLoaderService.CLASS_COURSES);
            return "redirect:/" + ClassCourseController.TIMETABLEPATH;
        }


    }

    //endregion


}
