package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.springframework.http.ResponseEntity;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AlgorithmControllerHandler {

    /**
     * Handler for Algorithm Name requests
     * @param response
     * @param request
     * @return
     */
    public static final String getAlgorithmNameHandler(HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if (SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);
            result = context.getAlgorithm().getName();

        }

        return result;
    }

    /**
     * Handler for Algorithm Progress requests
     *
     * @param response
     * @param request
     * @return
     */
    public static final Double getAlgorithmProgressHandler(HttpServletResponse response, HttpServletRequest request) {
        Double result;
        UUID uuid = CookieHandlerService.getUUID(request, response);
        if (SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);
            result = context.getAlgorithm().getProgress();

        } else {
            result = (double) 0;
        }

        return result;
    }
    /**
     * Handler for Algorithm Progress requests
     *
     * @param response
     * @param request
     * @return
     */
    public static final ResponseEntity changeAlgorithmRequestHandler(HttpServletResponse response, HttpServletRequest request, String newAlgorithmName) {

        ResponseEntity<Object> result;
        UUID uuid = CookieHandlerService.getUUID(request, response);

        if (SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);
            context.changeAlgorithm(newAlgorithmName);
            result = ResponseEntity.ok().build();
        } else {
            result = ResponseEntity.noContent().build();
        }

        return result;
    }

    /**
     * Handler for running the algorithm
     * @param response
     * @param request
     * @return
     */
    public static final ResponseEntity runAlgorithmHandler(HttpServletResponse response, HttpServletRequest request ) {
        ResponseEntity<Object> result;
        UUID uuid = CookieHandlerService.getUUID(request, response);

        if (SessionsService.containsSession(uuid)) {
            Context context = SessionsService.getContext(uuid);

            Thread computingThread = new Thread(() -> {
                context.computeSolutionWithAlgorithm();
                context.calculateMetrics();
            });
            computingThread.start();

            result = ResponseEntity.ok().build();
        } else {
            result = ResponseEntity.noContent().build();
        }

        return result;
    }



}
