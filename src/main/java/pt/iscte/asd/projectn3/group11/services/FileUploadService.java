package pt.iscte.asd.projectn3.group11.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @deprecated
 */
@Deprecated
@Service
public class FileUploadService {
    private FileUploadService(){}

    public static final String UPLOADED_FILES_LOCATION = "./";

    public static void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File(UPLOADED_FILES_LOCATION + file.getOriginalFilename()));
    }

}
