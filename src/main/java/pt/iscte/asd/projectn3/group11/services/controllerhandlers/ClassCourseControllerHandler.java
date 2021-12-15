package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.controllers.ClassCourseController;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.MetricResult;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassCourseLoaderService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ClassCourseControllerHandler {

    //region HANDLERS

    /**
     * fetchTimeTable endpoint handler.
     * @param response
     * @param request
     * @param model
     * @return
     */
    public static final String fetchTimeTableHandler(HttpServletResponse response, HttpServletRequest request, Model model)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            model.addAttribute("timetable", context.getClassCourses());

            final Hashtable<String, Float> stringFloatHashtable =  TimetableEvaluationService.evaluateTimetable(context.getClassCourses(), context.getClassrooms());
            final List<MetricResult> metricResultList = new LinkedList<>();
            for(Map.Entry<String,Float> resultEntry : stringFloatHashtable.entrySet()){
                metricResultList.add(new MetricResult(resultEntry.getKey(),resultEntry.getValue()));
            }
            model.addAttribute("timetablestats",metricResultList);
        }

        return "timetable";
    }

    /**
     * downloadTimeTable endpoint handler.
     * @param response
     * @param request
     * @param model
     * @return
     */
    public static final ResponseEntity<Resource> downloadTimeTableHandler(HttpServletResponse response, HttpServletRequest request, Model model)
    {
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

    /**
     * timeTableUpload endpoint handler.
     * @param response
     * @param request
     * @param fileClasses
     * @param fileClassrooms
     * @param attributes
     * @param model
     * @return
     */
    public static final String timeTableUploadHandler(HttpServletResponse response, HttpServletRequest request, MultipartFile fileClasses, MultipartFile fileClassrooms, RedirectAttributes attributes, Model model)
    {
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

            final Hashtable<String, Float> stringFloatHashtable =  TimetableEvaluationService.evaluateTimetable(context.getClassCourses(), context.getClassrooms());
            final List<MetricResult> metricResultList = new LinkedList<>();
            for(Map.Entry<String,Float> resultEntry : stringFloatHashtable.entrySet()){
                metricResultList.add(new MetricResult(resultEntry.getKey(),resultEntry.getValue()));
            }
            model.addAttribute("timetablestats",metricResultList);

            return "timetable";
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "Something went wrong with the upload or the files...\n" + fileClasses.getOriginalFilename() + "and" + fileClassrooms.getOriginalFilename() + '!');
            e.printStackTrace();
            return "redirect:/" + ClassCourseController.TIMETABLE_PATH;
        }
    }

    //endregion
}
