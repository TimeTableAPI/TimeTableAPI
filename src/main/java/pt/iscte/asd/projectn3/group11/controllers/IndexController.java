package pt.iscte.asd.projectn3.group11.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    //region home

    /**
     * Handles the root (/)endpoint and return start page.
     *
     * @return "index"
     */
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("teamMembers", new String[]{"Afonso Costa Vale", "João ALmeida", "Saroj Duwadi"});
        return "index";
    }
    //endregion


}