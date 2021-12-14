package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

import pt.iscte.asd.projectn3.group11.models.FormResponse;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

@Deprecated
@Controller
public class FormController {

    //region PATH_CONSTANTS
    public static final String FORM_PATH = "/form";
    //endregion

    //region FORM

    /**
     * Gets the form
     * @param model model
     * @return html
     */
    @GetMapping(value = FORM_PATH)
    public String getForm(Model model) {
        FormResponse formResponse = new FormResponse();
        model.addAttribute("response", formResponse);
        return "form";
    }
    //endregion

    //region FORM_UPLOAD
    /**
     * Submits form
     * @param formResponse form response
     * @param model model
     * @return html
     */
    @PostMapping(value = FORM_PATH)
    public String submitForm(@ModelAttribute(name = "response") FormResponse formResponse, Model model) {
        String classCourse = formResponse.getClassCourse();
        String classRoom = formResponse.getClassroom();
        model.addAttribute("classCourse", classCourse);
        model.addAttribute("classRoom", classRoom);
        return "form";
    }

    /**
     * Submits file form
     * @param file file
     * @param attributes attributes
     * @param model model
     * @return html
     */
    @PostMapping(value = FORM_PATH + "/upload")
    public String submitFileForm(@RequestParam("file") MultipartFile file, RedirectAttributes attributes, Model model) {
        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:" + FORM_PATH;
        }

        // normalize the file path
        try {
            FileUploadService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + '!');

        return "redirect:" + FORM_PATH;
    }
    //endregion

}
