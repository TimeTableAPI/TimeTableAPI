package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;

@RestController
public class FileUploadController {

    public static final String FILE_UPLOAD_PATH = "/uploadrest";

    @PostMapping(value = FILE_UPLOAD_PATH)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadService.uploadFile(file);


    }


}
