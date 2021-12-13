package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.Context;

import java.util.HashMap;
import java.util.UUID;

public class SessionsService {

    private static final HashMap<UUID, Context> SESSIONS = new HashMap<>();
    private static int SESSION_NUM = 0;

    public synchronized static void putSession(UUID uuid, Context context)
    {
        SESSIONS.put(uuid, context);
    }

    public synchronized static void removeSession(UUID uuid)
    {
        SESSIONS.remove(uuid);
    }

    public synchronized static Context getContext(UUID uuid)
    {
        return SESSIONS.get(uuid);
    }

    public synchronized static boolean containsSession(UUID uuid)
    {
        return SESSIONS.containsKey(uuid);
    }

    public synchronized static void increaseSessionNum()
    {
        SESSION_NUM++;
    }

    public synchronized static void removeSessionNum()
    {
        SESSION_NUM--;
    }

    public synchronized static int getSessionNum()
    {
        return SESSION_NUM;
    }
}
