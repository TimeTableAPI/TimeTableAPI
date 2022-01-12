package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.Context;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ClassroomControllerHandler {

    private static final Logger LOGGER  = LogManager.getLogger(ClassroomControllerHandler.class);

    //region HANDLERS

    /**
     * getClassRooms endpoint handler.
     * @param response
     * @param request
     * @return
     */
    public static final List<Classroom> getClassroomsHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid))
        {
            Context context = SessionsService.getContext(uuid);
            return context.getClassrooms();
        }

        return new ArrayList<>();
    }

    /**
     * setClassrooms endpoint handler.
     * @param response
     * @param request
     * @param classesFile
     * @return
     */
    public static final ResponseEntity setClassroomsHandler(HttpServletResponse response, HttpServletRequest request, MultipartFile classesFile)
    {
        LOGGER.info("In set classrooms handler");
        LinkedList<Classroom> loadedClassrooms;
        try {
            loadedClassrooms = ClassroomLoaderService.load(classesFile, false);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        UUID uuid = CookieHandlerService.getUUID(request, response);

        if (SessionsService.containsSession(uuid)) {
            LOGGER.info("Context found setting new classrooms");
            Context context = SessionsService.getContext(uuid);
            context.setClassrooms(loadedClassrooms);
        } else {
            LOGGER.info("Context not found, creating empty and setting new classrooms");
            Context context = new Context.Builder().classrooms(loadedClassrooms).build();
            SessionsService.putSession(uuid, context);
        }
        return ResponseEntity.ok().build();
    }
    //endregion

}
