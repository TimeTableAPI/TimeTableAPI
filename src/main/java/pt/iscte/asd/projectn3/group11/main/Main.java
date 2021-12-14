package pt.iscte.asd.projectn3.group11.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"pt.iscte.asd.projectn3.group11.controller"})
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Main App Started with SPRING");
    }

}
