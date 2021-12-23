package pt.iscte.asd.projectn3.group11.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerService {
    private LoggerService(){}

    public static final Logger LOGGER = LogManager.getLogger(LoggerService.class);

}
