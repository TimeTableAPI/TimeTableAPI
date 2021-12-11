package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping(value = "/uploadrest")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadService.uploadFile(file);


    }


}
