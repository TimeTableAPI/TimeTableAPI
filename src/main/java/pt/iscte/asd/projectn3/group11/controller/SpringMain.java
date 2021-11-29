package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
        System.out.println("Main App executed with SPRING");
    }
}
