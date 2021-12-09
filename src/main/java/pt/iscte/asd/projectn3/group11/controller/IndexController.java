package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.iscte.asd.projectn3.group11.loaders.ClassLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassRoomLoader;
import pt.iscte.asd.projectn3.group11.models.Class;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.FormResponse;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;
import java.util.LinkedList;

@Controller
public class IndexController {


    public static final String CLASSROOMPATH = "/classrooms";
    public static final String TIMETABLEPATH = "/timetable";
    public static final String FORMPATH = "/form";

//region home

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

    //endregion
//region classrooms
    @GetMapping(value = CLASSROOMPATH)
    public String fetchAllClassRooms(Model model) {
        model.addAttribute("classrooms", ClassRoomLoader.classrooms);
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
//region timetable
    @GetMapping(value = TIMETABLEPATH)
    public String fetchTimeTable(Model model) {
        model.addAttribute("timetable", ClassLoader.classes);
        return "timetable";
    }

    @PostMapping(value = TIMETABLEPATH + "/upload")
    public String timeTableUpload(@RequestParam("file_classes") MultipartFile file_classes, @RequestParam("file_classrooms") MultipartFile file_classrooms, RedirectAttributes attributes, Model model) {
        // check if file is empty
        if (file_classes.isEmpty() || file_classrooms.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/" + TIMETABLEPATH;
        }

        // normalize the file path
        try {
            //FileUploadService.uploadFile(file_classes);
            //FileUploadService.uploadFile(file_classrooms);
            ClassLoader.clear();
            ClassRoomLoader.clear();

            LinkedList<Class> loadedClasses = ClassLoader.load(file_classes);
            LinkedList<Classroom> loadedClassRooms = ClassRoomLoader.load(file_classrooms);
            attributes.addFlashAttribute("message", "You successfully uploaded\n" + file_classes.getOriginalFilename() + "and" + file_classrooms.getOriginalFilename() + '!');

            model.addAttribute("timetable", loadedClasses);

            // return success response
            return "timetable";
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "Something went wrong with the upload or the files...\n" + file_classes.getOriginalFilename() + "and" + file_classrooms.getOriginalFilename() + '!');
            e.printStackTrace();
            model.addAttribute("timetable", ClassLoader.classes);
            return "redirect:/" + TIMETABLEPATH;
        }


    }

    //endregion
//region form
    @GetMapping(value = FORMPATH)
    public String getForm(Model model) {
        FormResponse formResponse = new FormResponse();
        model.addAttribute("response", formResponse);
        return "form";
    }

    @PostMapping(value = FORMPATH)
    public String submitForm(@ModelAttribute(name = "response") FormResponse formResponse, Model model) {
        String classCourse = formResponse.getClassCourse();
        String classRoom = formResponse.getClassRoom();
        model.addAttribute("classCourse", classCourse);
        model.addAttribute("classRoom", classRoom);
        return "form";
    }

    @PostMapping(value = FORMPATH + "/upload")
    public String submitFileForm(@RequestParam("file") MultipartFile file, RedirectAttributes attributes, Model model) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/form";
        }

        // normalize the file path
        try {
            FileUploadService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + '!');

        return "redirect:/form";
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