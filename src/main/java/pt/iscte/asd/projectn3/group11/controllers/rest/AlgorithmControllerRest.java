package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.AlgorithmControllerHandler.*;

@RestController
public class AlgorithmControllerRest {
    //region PATH_CONSTANTS
    public static final String ALGORITHM_REST_PATH = "/rest/algorithm";
    //endregion

    //region GETTERS

    /**
     * Gets Algorithm Name.
     * @param response response
     * @param request request
     * @return String, name of the algorithm
     */
    @GetMapping(value = ALGORITHM_REST_PATH)
    public String getAlgorithmName(HttpServletResponse response, HttpServletRequest request) {
        return getAlgorithmNameHandler(response,request);
    }

    /**
     * Gets Algorithm Progress.
     * @param response response
     * @param request request
     * @return
     */
    @GetMapping(value = ALGORITHM_REST_PATH + "/progress")
    public Double getAlgorithmProgress(HttpServletResponse response, HttpServletRequest request) {
        return getAlgorithmProgressHandler(response,request);
    }
    //endregion

    //region POST

    /**
     * Set Algorithm and his name.
     * @param response response
     * @param request request
     * @return
     */
    @PostMapping(value = ALGORITHM_REST_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity changeAlgorithm(HttpServletResponse response, HttpServletRequest request, @RequestPart(value = "algorithm") String algorithm) {
        return changeAlgorithmRequestHandler(response,request, algorithm);
    }
    /**
     * Starts running Algorithm  and his name.
     * @param response response
     * @param request request
     * @return
     */
    @PostMapping(value = ALGORITHM_REST_PATH + "/run")
    public ResponseEntity runAlgorithm(HttpServletResponse response, HttpServletRequest request) {
        return runAlgorithmHandler(response,request);
    }
    /**
     * Starts running Algorithm  and his name.
     * @param response response
     * @param request request
     * @return
     */
    @PostMapping(value = ALGORITHM_REST_PATH + "/stop")
    public ResponseEntity stopAlgorithm(HttpServletResponse response, HttpServletRequest request) {
        return stopAlgorithmHandler(response,request);
    }

    //endregion

}
