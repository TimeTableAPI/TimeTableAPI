package pt.iscte.asd.projectn3.group11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;

@RestController
public class FileUploadController {

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadService.uploadFile(file);

    }
}
