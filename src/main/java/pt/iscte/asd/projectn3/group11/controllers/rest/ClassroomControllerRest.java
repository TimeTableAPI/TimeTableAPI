package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.getClassroomsHandler;
import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.setClassroomsHandler;

@RestController
public class ClassroomControllerRest {

    //region PATH_CONSTANTS
    public static final String CLASSROOM_REST_PATH = "/rest/classrooms";
    //endregion

    //region CLASSROOM

    /**
     * Gets all Classrooms.
     * @param response response
     * @param request request
     * @return Classrooms list
     */
    @GetMapping(value = CLASSROOM_REST_PATH)
    public List<Classroom> getClassrooms(HttpServletResponse response, HttpServletRequest request) {
        return getClassroomsHandler(response, request);
    }

    /**
     * Set Classrooms.
     * @param response response
     * @param request request
     */
    @PostMapping(value = CLASSROOM_REST_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity setClassrooms(HttpServletResponse response, HttpServletRequest request, @RequestPart(value = "file_classrooms") MultipartFile classroomsFile) {
        return setClassroomsHandler(response, request, classroomsFile);
    }

    //endregion
}
