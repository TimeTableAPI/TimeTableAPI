package pt.iscte.asd.projectn3.group11.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"pt.iscte.asd.projectn3.group11.controllers"})
@SpringBootApplication
public class Application {

    private static final Logger LOGGER  = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("Main App Started with SPRING");
    }

}
