package pt.iscte.asd.projectn3.group11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    //region home

    /**
     * Handles the root (/)endpoint and return start page.
     *
     * @return "start"
     */
    @RequestMapping("/")
    public String index(Model model) {
        logger.info("Home::GET::Accessed");
        model.addAttribute("teamMembers", new String[]{"Afonso Costa Vale", "João ALmeida", "Saroj Duwadi"});
        return "index";
    }
    //endregion

}

/*
 JSON FOR POSTMAN TESTS

 {
"building":"Ala Autónoma (ISCTE-IUL)",
"classroomName":"Auditório Afonso de Barros",
"normalCapacity":80,
"examCapacity":39,
"numberCharacteristics":4,
"characteristics":[false,false,false]
}
* */