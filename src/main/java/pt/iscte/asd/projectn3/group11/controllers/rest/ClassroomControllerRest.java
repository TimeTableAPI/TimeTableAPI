package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.getAllClassRoomsHandler;

@RestController
public class ClassroomControllerRest {

    //region PATH_CONSTANTS
    public static final String CLASSROOM_PATH = "/rest/classrooms";
    //endregion

    //region CLASSROOM

    /**
     * Gets all Classrooms.
     * @param response response
     * @param request request
     * @return Classrooms list
     */
    @GetMapping(value = CLASSROOM_PATH)
    public List<Classroom> getAllClassRooms(HttpServletResponse response, HttpServletRequest request) {
        return getAllClassRoomsHandler(response, request);
    }

    //endregion
}
