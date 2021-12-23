package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pt.iscte.asd.projectn3.group11.services.LoggerService;


@ComponentScan({"pt.iscte.asd.projectn3.group11.controllers"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LoggerService.LOGGER.info("Main App Started with SPRING");
    }

}
