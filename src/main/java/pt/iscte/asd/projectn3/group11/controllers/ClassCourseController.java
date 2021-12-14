package pt.iscte.asd.projectn3.group11.controllers;

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
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
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

    //region PATH_CONSTANTS
    public static final String TIMETABLE_PATH = "/timetable";
    //endregion

    //region TIMETABLE

    /**
     * Fetches the timetable.
     * @param response response
     * @param request request
     * @param model model
     * @return html filled with the variables
     */
    @GetMapping(value = ClassCourseController.TIMETABLE_PATH)
    public String fetchTimeTable(HttpServletResponse response, HttpServletRequest request, Model model) {

        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            model.addAttribute("timetable", context.getClassCourses());
        }

        return "timetable";
    }
    //endregion

    //region TIMETABLE_DOWNLOAD

    /**
     * Downloads timetable.
     * @param response response
     * @param request request
     * @param model model
     * @return File
     */
    @GetMapping(value = ClassCourseController.TIMETABLE_PATH + "/download")
    public ResponseEntity<Resource> downloadTimeTable(HttpServletResponse response, HttpServletRequest request, Model model) {

        UUID uuid = CookieHandlerService.getUUID(request, response);
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
    //endregion

    //region TIMETABLE_UPDATE

    /**
     * Updates timetable.
     * @param response response
     * @param request request
     * @param fileClasses file of the classes
     * @param fileClassrooms file classrooms
     * @param attributes redirect
     * @param model model
     * @return html filled with the variables
     */
    @PostMapping(value = ClassCourseController.TIMETABLE_PATH + "/upload")
    public String timeTableUpload(HttpServletResponse response, HttpServletRequest request, @RequestParam("file_classes") MultipartFile fileClasses, @RequestParam("file_classrooms") MultipartFile fileClassrooms, RedirectAttributes attributes, Model model) {
        // check if file is empty
        if (fileClasses.isEmpty() || fileClassrooms.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/" + ClassCourseController.TIMETABLE_PATH;
        }

        // normalize the file path
        try {
            attributes.addFlashAttribute("message", "You successfully uploaded\n" + fileClasses.getOriginalFilename() + "and" + fileClassrooms.getOriginalFilename() + '!');

            LinkedList<ClassCourse> loadedClassCourses = ClassCourseLoaderService.load(fileClasses, false);
            LinkedList<Classroom> loadedClassRooms = ClassroomLoaderService.load(fileClassrooms, false);
            Context context = new Context(loadedClassCourses, loadedClassRooms, new BasicAlgorithmService());
            context.computeSolutionWithAlgorithm();

            UUID uuid = CookieHandlerService.getUUID(request, response);
            SessionsService.putSession(uuid, context);

            model.addAttribute("timetable", loadedClassCourses);

            return "timetable";
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "Something went wrong with the upload or the files...\n" + fileClasses.getOriginalFilename() + "and" + fileClassrooms.getOriginalFilename() + '!');
            e.printStackTrace();
            return "redirect:/" + ClassCourseController.TIMETABLE_PATH;
        }
    }

    //endregion

}
