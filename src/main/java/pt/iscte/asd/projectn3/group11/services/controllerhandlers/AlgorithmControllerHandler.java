package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AlgorithmControllerHandler {
    public static final String algorithmRequestHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);
            return context.getAlgorithm().getName();

        }
        return "";

    }
    public static final Double algorithmProgressRequestHandler(HttpServletResponse response, HttpServletRequest request)
    {
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if(SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);
            return context.getAlgorithm().getProgress();

        }
        return (double) 0;

    }
}
