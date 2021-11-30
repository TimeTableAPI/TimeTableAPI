package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.iscte.asd.projectn3.group11.models.Classroom;

@org.springframework.web.bind.annotation.RestController
public class Controller {


    public static final String CLASSROOMPATH = "/classroom";

    /**
     * Handles the root (/)endpoint and return astart page.
     *
     * @return "start"
     */
    @RequestMapping("/")
    public String index() {
        return "start";
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
