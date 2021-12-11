package pt.iscte.asd.projectn3.group11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pt.iscte.asd.projectn3.group11.loaders.ClassroomLoader;

@Controller
public class ClassroomController {
    final static Logger logger = LoggerFactory.getLogger(ClassroomController.class);
    public static final String CLASSROOMPATH = "/classrooms";

    //region classrooms
    @GetMapping(value = CLASSROOMPATH)
    public String fetchAllClassRooms(Model model) {
        logger.info(CLASSROOMPATH+"::GET::Accessed");

        model.addAttribute("classrooms", ClassroomLoader.classrooms);
        return "classrooms";
    }
/*
    @PostMapping(value = CLASSROOMPATH, consumes = "application/json", produces = "application/json")
    public Classroom createClassRoom(@RequestBody Classroom classroom) {
        return classroom;
    }

    @DeleteMapping(CLASSROOMPATH + "/{id}/")
    public ResponseEntity deleteClassRoom(@PathVariable("id") String id) {
        return new ResponseEntity(HttpStatus.OK);
    }*/

    //endregion

}
