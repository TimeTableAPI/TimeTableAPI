package pt.iscte.asd.projectn3.group11.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;

public class LogService {

    private static LogService INSTANCE = null;

    public static synchronized LogService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new LogService();
        }
        return INSTANCE;
    }

    public void info(String log)
    {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).info(log);
    }

    public void info(Object log)
    {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).info(log);
    }

    public void info(Marker var1, String... var2)
    {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).info(var1, var2);
    }

    public void error(String log)
    {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).error(log);
    }

    public void trace(String log)
    {
        LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName()).trace(log);
    }
}
