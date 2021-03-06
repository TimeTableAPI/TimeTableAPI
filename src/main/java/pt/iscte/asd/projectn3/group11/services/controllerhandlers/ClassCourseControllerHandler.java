package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.Context;
import pt.iscte.asd.projectn3.group11.controllers.rest.ClassCourseControllerRest;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.services.*;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassCourseLoaderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ClassCourseControllerHandler {


    private static final Logger LOGGER  = LogManager.getLogger(ClassCourseControllerHandler.class);

    //region HANDLERS

    /**
     * Handler for {@link ClassCourseControllerRest#getClasses}
     * @param response
     * @param request
     * @return List of class courses.
     */
    public static final List<ClassCourse.ClassCourseJson> getClassesHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            List<ClassCourse.ClassCourseJson> loadedClassCoursesJSON = new LinkedList<>();
            try {
                context.getClassCourses().stream().map(ClassCourse::toJsonType).forEach(loadedClassCoursesJSON::add);
            } catch (NullPointerException e) {
                LOGGER.trace("getAlgorithmProgressHandler::No algorithm in context "+e.getMessage());
            }
            return loadedClassCoursesJSON;
        }
        return new LinkedList<>();
    }

    /**
     * Handler for {@link ClassCourseControllerRest#getClassesOfClass}
     * @param response
     * @param request
     * @param className
     * @return List of class courses.
     */
    public static final HashMap<Date, HashMap<Integer, HashSet<ClassCourse>>> getClassesOfClassHandler(HttpServletResponse response,
                                                                                                       HttpServletRequest request,
                                                                                                       String className)
    {
        LOGGER.info(className);
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            List<ClassCourse.ClassCourseJson> loadedClassCoursesJSON = new LinkedList<>();
            final TreeMap<String, HashMap<Date, HashMap<Integer, HashSet<ClassCourse>>>> classesByStudents = context.getClassesByStudents();
            return classesByStudents.get(className);
        }
        return new HashMap<>();
    }

    /**
     * Gets the metric results handler.
     * @param response
     * @param request
     * @return List of metric results.
     */
    public static final List<MetricResult> getMetricResultsHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            return context.getMetricResults();
        }

        return new LinkedList<>();
    }

    /**
     * downloadTimeTable endpoint handler.
     * @param response
     * @param request
     * @return
     */
    public static final ResponseEntity<Resource> downloadClassesHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(!SessionsService.containsSession(uuid)) return (ResponseEntity<Resource>) ResponseEntity.notFound();

        Context context = SessionsService.getContext(uuid);

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
     * setClasses endpoint handler.
     * @param response
     * @param request
     * @param classesFile
     * @return
     */
    public static final ResponseEntity setClassesHandler(HttpServletResponse response, HttpServletRequest request, MultipartFile classesFile)
    {
        LOGGER.info("In set classes handler");
        LinkedList<ClassCourse> loadedClassCourses;
        try {
            loadedClassCourses = ClassCourseLoaderService.load(classesFile, false);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        UUID uuid = CookieHandlerService.getUUID(request, response);

        if (SessionsService.containsSession(uuid)) {
            LOGGER.info("Context found setting new classcourses");
            Context context = SessionsService.getContext(uuid);
            context.setClassCourses(loadedClassCourses);
        } else {
            LOGGER.info("Context not found, creating empty and setting new classcourses");
            Context context = new Context.Builder().classCourses(loadedClassCourses).build();
            SessionsService.putSession(uuid, context);
        }
        return ResponseEntity.ok().build();
    }
    //endregion
}
