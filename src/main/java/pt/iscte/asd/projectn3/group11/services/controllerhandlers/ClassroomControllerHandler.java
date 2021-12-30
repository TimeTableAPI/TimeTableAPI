package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class ClassroomControllerHandler {
    private ClassroomControllerHandler(){}

    private static Logger LOGGER = LogManager.getLogger(ClassroomControllerHandler.class);

    //region HANDLERS

    /**
     * fetchAllClassRooms endpoint handler.
     * @param response
     * @param request
     * @param model
     * @return
     */
    public static final String fetchAllClassRoomsHandler(HttpServletResponse response, HttpServletRequest request, Model model)
    {
        LOGGER.info("Entering FetchAllClassRooms Endpoint Handler");

        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            model.addAttribute("classrooms", context.getClassrooms());
        }

        LOGGER.info("Exiting FetchAllClassRooms Endpoint Handler");
        return "classrooms";
    }

    //endregion

}
