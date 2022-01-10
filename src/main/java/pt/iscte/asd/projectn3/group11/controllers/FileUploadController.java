package pt.iscte.asd.projectn3.group11.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.IOException;

/**
 * @deprecated
 */
@Deprecated
@RestController
public class FileUploadController {

    //region PATH_CONSTANTS
    public static final String FILE_UPLOAD_PATH = "/uploadrest";
    //endregion

    //region FILE_UPLOAD

    /**
     * Uploads a file.
     * @param file file
     * @throws IOException
     */
    @PostMapping(value = FILE_UPLOAD_PATH)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadService.uploadFile(file);
    }

    //endregion

}
