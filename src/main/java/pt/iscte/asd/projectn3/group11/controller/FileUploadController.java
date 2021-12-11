package pt.iscte.asd.projectn3.group11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;

@RestController
public class FileUploadController {
    final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    public static final String UPLOADRESTPATH = "/uploadrest";


    @PostMapping(value = UPLOADRESTPATH)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info(UPLOADRESTPATH+"::POST::Accessed");

        FileUploadService.uploadFile(file);


    }


}
