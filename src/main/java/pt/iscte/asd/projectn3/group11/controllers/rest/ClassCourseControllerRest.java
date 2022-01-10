package pt.iscte.asd.projectn3.group11.controllers.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.iscte.asd.projectn3.group11.controllers.ClassCourseController;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassCourseControllerHandler.*;

@RestController
public class ClassCourseControllerRest {

    //region PATH_CONSTANTS
    public static final String TIMETABLE_PATH = "/rest/timetable";
    //endregion

    //region TIMETABLE

    /**
     * Gets the timetable.
     * @param response response
     * @param request request
     * @return The timetable.
     */
    @GetMapping(value = TIMETABLE_PATH)
    public List<ClassCourse.ClassCourseJson> getTimeTable(HttpServletResponse response, HttpServletRequest request) {
        return getTimeTableHandler(response, request);
    }
    //endregion

    //region METRIC_RESULTS
    /**
     * Gets the timetable.
     * @param response response
     * @param request request
     * @return The timetable.
     */
    @GetMapping(value = TIMETABLE_PATH + "/metrics")
    public List<MetricResult> getMetricResults(HttpServletResponse response, HttpServletRequest request) {
        return getMetricResultsHandler(response, request);
    }

    //endregion

    //region TIMETABLE_DOWNLOAD

    /**
     * Downloads timetable.
     * @param response response
     * @param request request
     * @return File
     */
    @GetMapping(value = TIMETABLE_PATH + "/download")
    public ResponseEntity<Resource> downloadTimeTable(HttpServletResponse response, HttpServletRequest request) {
        return downloadTimeTableHandler(response, request);
    }

    //endregion
}
