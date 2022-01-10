package pt.iscte.asd.projectn3.group11.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	private static final Logger LOGGER  = LogManager.getLogger(RestController.class);

	/**
	 * Handles the (/log4shell) endpoint and returns a simple string.
	 *
	 * @return "start"
	 */
	@RequestMapping("/log4shell")
	public String log4shell(@RequestHeader("X-Api-Version") String apiversion) {
		LOGGER.info("Received a request for apiversion" + apiversion );
		return "sucess";
	}
}
