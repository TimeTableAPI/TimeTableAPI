package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClassroomControllerHandler {

    //region HANDLERS

    /**
     * fetchAllClassRooms endpoint handler.
     * @param response
     * @param request
     * @return
     */
    public static final List<Classroom> getAllClassRoomsHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            return context.getClassrooms();
        }

        return new ArrayList<Classroom>();
    }

    //endregion

}
