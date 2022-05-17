package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pt.iscte.asd.projectn3.group11.services.controllerhandlers.ClassroomControllerHandler.getClassroomsHandler;

@Controller
@ApiIgnore
public final class WeekController {

	//region PATH_CONSTANTS
	public static final String WEEK_PATH = "/week";
	//endregion

	//region WEEK

	/**
	 * Week Page
	 * @param response response
	 * @param request request
	 * @return html filled with the variables
	 */
	@GetMapping(value = WEEK_PATH)
	public String getWeek(HttpServletResponse response, HttpServletRequest request) {
		return "week";
	}

	//endregion


}
