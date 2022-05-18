package pt.iscte.asd.projectn3.group11.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieHandlerService {

    private static final String ID_NAME = "id";

    //region PUBLIC_HANDLER
    /**
     * Gets or Generates a UUID for a session
     * @param request request
     * @param response response
     * @return UUID of the session
     */
    public static UUID getUUID(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie cookie = CookieHandlerService.getIdCookie(request);
        if(cookie == null || cookie.getValue() == null) cookie = CookieHandlerService.addIdCookie(response);
        return UUID.fromString(cookie.getValue());
    }
    //endregion

    //region INTERNAL_ID_COOKIE
    /**
     * Adds or sets the Id cookie
     * @param  response response
     * @return id Cookie
     */
    private static Cookie addIdCookie(HttpServletResponse response)
    {
        UUID id = UUID.randomUUID();
        SessionsService sessionsService = SessionsService.getInstance();
        while(sessionsService.containsSession(id))
        {
            id = UUID.randomUUID();
        }
        final Cookie cookie = addCookie(response, ID_NAME, id.toString());
        return cookie;
    }

    /**
     * Removes the Id cookie
     * @param response response
     */
    private static void removeIdCookie(HttpServletResponse response)
    {
        removeCookie(response, ID_NAME);
    }

    /**
     * Gets the Id cookie
     * @param request request
     * @return id Cookie
     */
    private static Cookie getIdCookie(HttpServletRequest request)
    {
        return getCookie(request, ID_NAME);
    }
    //endregion

    //region INTERNAL_GENERAL
    /**
     * Adds a generic cookie
     * @param response response
     * @param name name of the cookie
     * @param value value of the cookie
     * @return the cookie
     */
    private static Cookie addCookie(HttpServletResponse response, final String name, final String value)
    {
        final Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");

        response.addCookie(cookie);
        return cookie;
    }

    /**
     * Removes a generic cookie
     * @param response response
     * @param name name of the cookie to remove
     */
    private static void removeCookie(HttpServletResponse response, final String name)
    {
        final Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    /**
     * Gets a cookie from a given name
     * @param request request
     * @param name name of the cookie
     * @return the cookie
     */
    private static Cookie getCookie(HttpServletRequest request, final String name)
    {
        final Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;
        for (Cookie cookie: cookies)
        {
            if(cookie.getName().equals(name))
                if(cookie.getValue() == null) throw new NullPointerException("ID_IS_NULL");
                else return cookie;
        }
        return null;
    }
    //endregion
}
