package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class Controller {

    @GetMapping(value = "/api/user")
    public String user() {
        return "user";
    }
}
