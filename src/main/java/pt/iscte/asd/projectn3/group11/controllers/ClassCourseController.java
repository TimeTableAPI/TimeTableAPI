package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler;

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
        return ClassCourseControllerHandler.fetchTimeTableHandler(response, request, model);
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
        return ClassCourseControllerHandler.downloadTimeTableHandler(response, request, model);
    }
    //endregion

    //region TIMETABLE_REQUEST

    /**
     * Requests timetable.
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
        return ClassCourseControllerHandler.timeTableRequestHandler(response, request, fileClasses, fileClassrooms, attributes, model, "basic");
    }

    //endregion

}
