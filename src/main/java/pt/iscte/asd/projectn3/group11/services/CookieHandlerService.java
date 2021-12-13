package pt.iscte.asd.projectn3.group11.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieHandlerService {

    private static final String ID_NAME = "id";

    public static UUID getUUID(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie cookie = CookieHandlerService.getIdCookie(request);
        if(cookie == null || cookie.getValue() == null) cookie = CookieHandlerService.addIdCookie(response);
        return UUID.fromString(cookie.getValue());
    }

    private static Cookie addIdCookie(HttpServletResponse response)
    {
        UUID id = UUID.randomUUID();
        while(SessionsService.containsSession(id))
        {
            id = UUID.randomUUID();
        }
        final Cookie cookie = addCookie(response, ID_NAME, id.toString());
        return cookie;
    }

    private static void removeIdCookie(HttpServletResponse response)
    {
        removeCookie(response, ID_NAME);
    }

    private static Cookie getIdCookie(HttpServletRequest request)
    {
        return getCookie(request, ID_NAME);
    }

    private static Cookie addCookie(HttpServletResponse response, final String name, final String value)
    {
        final Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");

        response.addCookie(cookie);
        return cookie;
    }

    private static void removeCookie(HttpServletResponse response, final String name)
    {
        final Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

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

}
