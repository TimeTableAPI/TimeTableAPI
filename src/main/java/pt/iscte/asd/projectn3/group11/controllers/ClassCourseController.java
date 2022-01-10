package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler.getMetricResultsHandler;
import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler.getClassesHandler;

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
        model.addAttribute("timetable", getClassesHandler(response, request));
        model.addAttribute("metrics", getMetricResultsHandler(response, request));
        return "timetable";
    }
    //endregion

    //region TIMETABLE_REQUEST

    /**
     * Requests timetable.
     * @param response response
     * @param request request
     * @param fileClasses file of the classes
     * @param fileClassrooms file classrooms
     * @param algorithm formResponse
     * @param attributes redirect
     * @return html filled with the variables
     */
    @PostMapping(value = ClassCourseController.TIMETABLE_PATH + "/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String timeTableUpload(HttpServletResponse response,
                                  HttpServletRequest request,
                                  @RequestPart(value = "file_classes" ) MultipartFile fileClasses,
                                  @RequestPart(value = "file_classrooms" ) MultipartFile fileClassrooms,
                                  @RequestPart(value = "algorithm") String algorithm,
                                  RedirectAttributes attributes) {
        return ClassCourseControllerHandler.timeTableRequestHandler(response,
                request,
                fileClasses,
                fileClassrooms,
                attributes,
                algorithm
        );
    }

    //endregion

}
