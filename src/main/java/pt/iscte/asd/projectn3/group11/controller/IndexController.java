package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.iscte.asd.projectn3.group11.models.Classroom;

@Controller
public class IndexController {


    public static final String CLASSROOMPATH = "/classroom";

    /**
     * Handles the root (/)endpoint and return astart page.
     *
     * @return "start"
     */
    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("projectTitle", "Software and Design Arquitecture");
        model.addAttribute("teamMembers", new String[]{"Afonso Costa Vale", "João ALmeida", "Saroj Duwadi"});
        return "index";
    }

    @GetMapping(value = CLASSROOMPATH)
    public ResponseEntity fetchAllClassRooms() {
        return new ResponseEntity(HttpStatus.OK);
        //return "ClassRooms";
    }

    @GetMapping(value = CLASSROOMPATH + "/{id}/")
    public ResponseEntity fetchClassRoomById(@PathVariable("id") String id) {
        return new ResponseEntity(HttpStatus.OK);
        //return "ClassRoom with id" + id;
    }

    @PostMapping(value = CLASSROOMPATH, consumes = "application/json", produces = "application/json")
    public Classroom createClassRoom(@RequestBody Classroom classroom) {
        return classroom;
    }

    @DeleteMapping(CLASSROOMPATH + "/{id}/")
    public ResponseEntity deleteClassRoom(@PathVariable("id") String id) {
        return new ResponseEntity(HttpStatus.OK);
    }
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