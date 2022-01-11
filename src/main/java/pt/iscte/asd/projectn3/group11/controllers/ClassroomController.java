package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.getClassroomsHandler;


@Controller
public class ClassroomController {

    //region PATH_CONSTANTS
    public static final String CLASSROOM_PATH = "/classrooms";
    //endregion

    //region CLASSROOM

    /**
     * Fetched Classrooms.
     * @param response response
     * @param request request
     * @param model model
     * @return html filled with the variables
     */
    @GetMapping(value = CLASSROOM_PATH)
    public String fetchAllClassRooms(HttpServletResponse response, HttpServletRequest request, Model model) {
        model.addAttribute("classrooms", getClassroomsHandler(response, request));
        return "classrooms";
    }

    //endregion
}
