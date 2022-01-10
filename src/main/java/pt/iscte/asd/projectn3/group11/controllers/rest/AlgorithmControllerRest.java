package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.AlgorithmControllerHandler.*;

@RestController
public class AlgorithmControllerRest {
    //region PATH_CONSTANTS
    public static final String CLASSCOURSE_REST_PATH = "/rest/algorithm";
    //endregion

    //region GETTERS

    /**
     * Gets Algorithm Name.
     * @param response response
     * @param request request
     * @return String, name of the algorithm
     */
    @GetMapping(value = CLASSCOURSE_REST_PATH)
    public String getClassCourseAlgorithmName(HttpServletResponse response, HttpServletRequest request) {
        return algorithmRequestHandler(response,request);
    }

    /**
     * Gets Algorithm Progress.
     * @param response response
     * @param request request
     * @return Classrooms list
     */
    @GetMapping(value = CLASSCOURSE_REST_PATH+ "/progress")
    public Double getClassCourseAlgorithmProgress(HttpServletResponse response, HttpServletRequest request) {
        return algorithmProgressRequestHandler(response,request);
    }
    //endregion
    //region POST

    /**
     * Changes Algorithm  and his name.
     * @param response response
     * @param request request
     * @return Classrooms list
     */
  @PostMapping(value = CLASSCOURSE_REST_PATH)
    public ResponseEntity changeClassCourseAlgorithm(HttpServletResponse response, HttpServletRequest request, String newAlgorithmName) {
        return algorithmChangeRequestHandler(response,request,newAlgorithmName);
    }

    //endregion

}
