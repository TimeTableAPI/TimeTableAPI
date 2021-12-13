package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class ClassroomController {

    //region PATH_CONSTANTS
    public static final String CLASSROOM_PATH = "/classrooms";
    //endregion

    //region CLASSROOM
    @GetMapping(value = CLASSROOM_PATH)
    public String fetchAllClassRooms(HttpServletResponse response, HttpServletRequest request, Model model) {

        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            model.addAttribute("classrooms", context.getClassrooms());
        }

        return "classrooms";
    }
    //endregion
}
