package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.Context;

import java.util.HashMap;
import java.util.UUID;

public class SessionsService {
    private SessionsService(){}

    private static final HashMap<UUID, Context> SESSIONS = new HashMap<>();
    private static int SESSION_NUM = 0;

    //region SESSION_CONTEXT
    /**
     * Puts a session on a UUID
     * @param uuid UUID of the session
     * @param context context of the session
     */
    public static synchronized void putSession(UUID uuid, Context context)
    {
        SESSIONS.put(uuid, context);
    }

    /**
     * Removes the session of a UUID
     * @param uuid UUID of the session
     */
    public static synchronized void removeSession(UUID uuid)
    {
        SESSIONS.remove(uuid);
    }

    /**
     * Gets the context of a UUID
     * @param uuid UUID of the session
     * @return the context of the session
     */
    public static synchronized Context getContext(UUID uuid)
    {
        return SESSIONS.get(uuid);
    }

    /**
     * Returns if exists a context for that session
     * @param uuid UUID of the session
     * @return if the session has context
     */
    public static synchronized boolean containsSession(UUID uuid)
    {
        return SESSIONS.containsKey(uuid);
    }
    //endregion

    //region SESSION_NUM
    /**
     * Increases the number of sessions
     */
    public static synchronized void increaseSessionNum()
    {
        SESSION_NUM++;
    }

    /**
     * Decreases the number of sessions
     */
    public static synchronized void removeSessionNum()
    {
        SESSION_NUM--;
    }

    /**
     * Gets the session number
     * @return Session number
     */
    public static synchronized int getSessionNum()
    {
        return SESSION_NUM;
    }
    //endregion
}
