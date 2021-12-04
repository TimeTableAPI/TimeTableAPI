package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pt.iscte.asd.projectn3.group11.loaders.ClassRoomLoader;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.UploadedFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    public static final String CLASSROOMPATH = "/classroom";

    /**
     * Handles the root (/)endpoint and return start page.
     *
     * @return "start"
     */
    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("teamMembers", new String[]{"Afonso Costa Vale", "João ALmeida", "Saroj Duwadi"});
        return "index";
    }

    @GetMapping(value = "classrooms")
    public String fetchAllClassRooms(Model model) {
        //return new ResponseEntity(HttpStatus.OK);
        model.addAttribute("classrooms", ClassRoomLoader.classrooms);
        return "classrooms";
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

    @GetMapping(value = "/form")
    public String getForm(Model model) {
        UploadedFile uploadedFile = new UploadedFile();
        model.addAttribute("file", uploadedFile);
        return "form";
    }

    @PostMapping(value = "/form")
    public String submitForm(@ModelAttribute(name = "file") UploadedFile uploadedFile, Model model) {
        String classCourse = uploadedFile.getClassCourse();
        String classRoom = uploadedFile.getClassRoom();
        model.addAttribute("classCourse", classCourse);
        model.addAttribute("classRoom", classRoom);
        return "form";
    }
/*
    @PostMapping(value = "/form")
    public String submitForm(HttpServletRequest request, Model model) {
        String classCourse = request.getParameter("classCourse");
        String classRoom = request.getParameter("classRoom");
        if (classCourse == null) {
            classCourse = "";
        }
        if (classRoom == null) {
            classRoom = "";
        }
        model.addAttribute("classCourse", classCourse);
        model.addAttribute("classRoom", classRoom);
        return "form";
    }
    */
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