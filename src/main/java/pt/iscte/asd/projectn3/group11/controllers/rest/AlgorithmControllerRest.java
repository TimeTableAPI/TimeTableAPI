package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.AlgorithmControllerHandler.algorithmProgressRequestHandler;

@RestController
public class AlgorithmControllerRest {
    //region PATH_CONSTANTS
    public static final String CLASSCOURSE_REST_PATH = "/rest/algorithm";
    //endregion

    //region CLASSCOURSE

    /**
     * Gets all Classrooms.
     * @param response response
     * @param request request
     * @return Classrooms list
     */
    @GetMapping(value = CLASSCOURSE_REST_PATH)
    public Double getClassCourseAlgorithmProgress(HttpServletResponse response, HttpServletRequest request) {
        return algorithmProgressRequestHandler(response,request);
    }


}
