package pt.iscte.asd.projectn3.group11.services.controllerhandlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import pt.iscte.asd.projectn3.group11.services.Context;
import pt.iscte.asd.projectn3.group11.services.CookieHandlerService;
import pt.iscte.asd.projectn3.group11.services.LogService;
import pt.iscte.asd.projectn3.group11.services.SessionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public final class AlgorithmControllerHandler {

    /**
     * Handler for Algorithm Name requests
     * @param response
     * @param request
     * @return
     */
    public static final String getAlgorithmNameHandler(HttpServletResponse response, HttpServletRequest request) {
        String result = "";
        UUID uuid = CookieHandlerService.getUUID(request, response);
        SessionsService sessionServiceInstance = SessionsService.getInstance();

        if (sessionServiceInstance.containsSession(uuid)) {
            Context context = sessionServiceInstance.getContext(uuid);
            try {
                result = context.getAlgorithm().getName();
            } catch (NullPointerException e) {
                LogService.getInstance().trace("getAlgorithmNameHandler::No algorithm in context "+e.getMessage());
                result = "";
            }

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
        SessionsService sessionServiceInstance = SessionsService.getInstance();

        if (sessionServiceInstance.containsSession(uuid)) {
            Context context = sessionServiceInstance.getContext(uuid);
            try {
                result = context.getAlgorithm().getProgress();
            } catch (NullPointerException e) {
                LogService.getInstance().trace("getAlgorithmProgressHandler::No algorithm in context "+e.getMessage());
                result = 0.0;
            }

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
        SessionsService sessionServiceInstance = SessionsService.getInstance();

        if (sessionServiceInstance.containsSession(uuid)) {
            Context context = sessionServiceInstance.getContext(uuid);
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
        SessionsService sessionServiceInstance = SessionsService.getInstance();

        if (sessionServiceInstance.containsSession(uuid)) {
            Context context = sessionServiceInstance.getContext(uuid);

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

    /**
     * Handler for stopping the algorithm
     * @param response
     * @param request
     * @return
     */
    public static final ResponseEntity stopAlgorithmHandler(HttpServletResponse response, HttpServletRequest request ) {
        ResponseEntity<Object> result;
        UUID uuid = CookieHandlerService.getUUID(request, response);
        SessionsService sessionServiceInstance = SessionsService.getInstance();

        if (sessionServiceInstance.containsSession(uuid)) {
            Context context = sessionServiceInstance.getContext(uuid);

            try {
                context.getAlgorithm().stop();
            } catch (NullPointerException e) {
                LogService.getInstance().trace("getAlgorithmProgressHandler::No algorithm in context "+e.getMessage());
                return ResponseEntity.noContent().build();
            }

            result = ResponseEntity.ok().build();
        } else {
            result = ResponseEntity.noContent().build();
        }

        return result;
    }

}
