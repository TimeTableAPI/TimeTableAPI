package pt.iscte.asd.projectn3.group11.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;
@EntityScan
public final class FormResponse {

    //region MEMBERS
    private MultipartFile classCourse;
    private MultipartFile classroom;
    private String algorithm;
    //endregion

    //region CONSTRUCTORS
    public FormResponse() {
        super();
    }

    public FormResponse(MultipartFile classCourse, MultipartFile classroom, String algorithm) {
        this.classCourse = classCourse;
        this.classroom = classroom;
        this.algorithm = algorithm;
    }
    //endregion

    //region SETTERS
    /**
     * Gets class course MultipartFile
     * @return class course MultipartFile
     */
    public MultipartFile getClassCourse() {
        return classCourse;
    }

    /**
     * Sets class course MultipartFile
     * @param classCourse Class course MultipartFile to set
     */
    public void setClassCourse(MultipartFile classCourse) {
        this.classCourse = classCourse;
    }
    //endregion

    //region GETTERS
    /**
     * Gets classroom MultipartFile
     * @return classroom MultipartFile
     */
    public MultipartFile getClassroom() {
        return classroom;
    }

    /**
     * Sets classroom MultipartFile
     * @param classroom Classroom string to set
     */
    public void setClassroom(MultipartFile classroom) {
        this.classroom = classroom;
    }

    /**
     *
     * @return
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     *
     * @param algorithm String
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }


    //endregion
}
