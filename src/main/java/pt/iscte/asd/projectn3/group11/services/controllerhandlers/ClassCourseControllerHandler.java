package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.controllers.ClassCourseController;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.services.*;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.CustomAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
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

    public static final String TIMETABLE_HTML = "timetable";
    public static final String MESSAGE_HTML = "message";
    public static final String REDIRECT = "redirect:";

    private ClassCourseControllerHandler (){}

    private static final String BASIC = "basic";
    private static final String OWL = "owl";
    private static final int MAX_EVALUATION = 3;
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
        LoggerService.LOGGER.info("Entering FetchTimeTable Endpoint Handler");

        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);LinkedList<ClassCourse.ClassCourseJson> loadedClassCoursesJSON = new LinkedList<>();
            context.getClassCourses().stream().map(ClassCourse::toJsonType).forEach(loadedClassCoursesJSON::add);

            model.addAttribute(TIMETABLE_HTML, loadedClassCoursesJSON);

            final Map<String, Float> stringFloatHashtable =  TimetableEvaluationService.evaluateTimetable(context.getClassCourses(), context.getClassrooms());
            final List<MetricResult> metricResultList = new LinkedList<>();
            for(Map.Entry<String,Float> resultEntry : stringFloatHashtable.entrySet()){
                metricResultList.add(new MetricResult(resultEntry.getKey(),resultEntry.getValue()));
            }
            model.addAttribute("timetablestats",metricResultList);
        }

        LoggerService.LOGGER.info("Exiting FetchTimeTable Endpoint Handler");
        return TIMETABLE_HTML;
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
        LoggerService.LOGGER.info("Entering DownloadTimeTable Endpoint Handler");

        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(!SessionsService.containsSession(uuid))
        {
            LoggerService.LOGGER.info("Exiting DownloadTimeTable Endpoint Handler - No session");
            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }

        Context context = SessionsService.getContext(uuid);
        model.addAttribute(TIMETABLE_HTML, context.getClassCourses());

        try {
            File file = ClassCourseLoaderService.export(context.getClassCourses());

            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ExportedClasses.csv");
                header.add("Cache-Control", "no-cache, no-store, must-revalidate");
                header.add("Pragma", "no-cache");
                header.add("Expires", "0");

                LoggerService.LOGGER.info("Exiting DownloadTimeTable Endpoint Handler");
                return ResponseEntity.ok()
                        .headers(header)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);

            } catch (FileNotFoundException e) {
                LoggerService.LOGGER.error("Exiting DownloadTimeTable Endpoint Handler - " + e.getMessage());
                return (ResponseEntity<Resource>) ResponseEntity.notFound();
            }
        } catch (IOException e) {
            LoggerService.LOGGER.error("Exiting DownloadTimeTable Endpoint Handler - " + e.getMessage());
            return (ResponseEntity<Resource>) ResponseEntity.notFound();
        }
    }

    /**
     * timeTableUpload endpoint handler.
     * @param response
     * @param request
     * @param fileClasses
     * @param fileClassrooms
     * @param algorithm
     * @param attributes
     * @return
     */
    public static final String timeTableRequestHandler(HttpServletResponse response,
                                                       HttpServletRequest request,
                                                       MultipartFile fileClasses,
                                                       MultipartFile fileClassrooms,
                                                       RedirectAttributes attributes,
                                                       String algorithm)
    {
        LoggerService.LOGGER.info("Entering TimeTableUpload Endpoint Handler");

        // check if file is empty
        if (fileClasses.isEmpty() || fileClassrooms.isEmpty()) {
            attributes.addFlashAttribute(MESSAGE_HTML, "Please select a file to upload.");
            LoggerService.LOGGER.info("Exiting TimeTableUpload Endpoint Handler - No file selected");
            return REDIRECT + ClassCourseController.TIMETABLE_PATH;
        }

        // normalize the file path
        try {
            if(algorithm == null || algorithm.isEmpty()){
                algorithm = BASIC;
            }
            attributes.addFlashAttribute(MESSAGE_HTML, "You successfully uploaded\n" + fileClasses.getOriginalFilename() + "and" + fileClassrooms.getOriginalFilename() + '!');

            List<ClassCourse> loadedClassCourses = ClassCourseLoaderService.load(fileClasses, false);
            List<Classroom> loadedClassRooms = ClassroomLoaderService.load(fileClassrooms, false);

            IAlgorithmService iAlgorithmService;

            if(algorithm.equals(BASIC)) {
                iAlgorithmService = new BasicAlgorithmService();
            }
            else if (algorithm.equals(OWL)) {
                List<String> querryResult = SwrlService.querry(TimetableEvaluationService.METRICSLIST.size());
                if(querryResult == null || querryResult.isEmpty()) {
                    iAlgorithmService = new BasicAlgorithmService();
                }else {
                    iAlgorithmService = new CustomAlgorithmService(querryResult.get(0), MAX_EVALUATION);
                }
            }
            else {
                iAlgorithmService = new CustomAlgorithmService(algorithm, MAX_EVALUATION);
            }

            Context context = new Context(loadedClassCourses, loadedClassRooms, iAlgorithmService);
            context.computeSolutionWithAlgorithm();

            UUID uuid = CookieHandlerService.getUUID(request, response);
            SessionsService.putSession(uuid, context);

            LoggerService.LOGGER.info("Exiting TimeTableUpload Endpoint Handler");
            return REDIRECT + ClassCourseController.TIMETABLE_PATH;
        } catch (IOException e) {
            attributes.addFlashAttribute(MESSAGE_HTML, "Something went wrong with the upload or the files...\n" + fileClasses.getOriginalFilename() + "and" + fileClassrooms.getOriginalFilename() + '!');
            LoggerService.LOGGER.error("Exiting TimeTableUpload Endpoint Handler - " + e.getMessage());
            return REDIRECT + ClassCourseController.TIMETABLE_PATH;
        }
    }

    //endregion
}
