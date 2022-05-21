package pt.iscte.asd.projectn3.group11.services;

import java.util.HashMap;
import java.util.UUID;

public final class SessionsService {

    private final HashMap<UUID, Context> sessions;
    private int sessionNum;

    private SessionsService (){
        sessions = new HashMap<>();
        sessionNum = 0;
    }

    private static SessionsService INSTANCE = null;

    public static synchronized SessionsService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SessionsService();
        }
        return INSTANCE;
    }

    //region SESSION_CONTEXT
    /**
     * Puts a session on a UUID
     * @param uuid UUID of the session
     * @param context context of the session
     */
    public synchronized void putSession(UUID uuid, Context context)
    {
        sessions.put(uuid, context);
    }

    /**
     * Removes the session of a UUID
     * @param uuid UUID of the session
     */
    public synchronized void removeSession(UUID uuid)
    {
        sessions.remove(uuid);
    }

    /**
     * Gets the context of a UUID
     * @param uuid UUID of the session
     * @return the context of the session
     */
    public synchronized Context getContext(UUID uuid)
    {
        return sessions.get(uuid);
    }

    /**
     * Returns if exists a context for that session
     * @param uuid UUID of the session
     * @return if the session has context
     */
    public synchronized boolean containsSession(UUID uuid)
    {
        return sessions.containsKey(uuid);
    }
    //endregion

    //region SESSION_NUM
    /**
     * Increases the number of sessions
     */
    public synchronized void increaseSessionNum()
    {
        sessionNum++;
    }

    /**
     * Decreases the number of sessions
     */
    public synchronized void removeSessionNum()
    {
        sessionNum--;
    }

    /**
     * Gets the session number
     * @return Session number
     */
    public synchronized int getSessionNum()
    {
        return sessionNum;
    }
    //endregion
}
