package pt.iscte.asd.projectn3.group11.models;

@Deprecated
public class FormResponse {

    private String classCourse;
    private String classroom;

    public FormResponse() {
        super();
    }

    public FormResponse(String classCourse, String classroom) {
        this.classCourse = classCourse;
        this.classroom = classroom;
    }

    /**
     * Gets class course String
     * @return class course String
     */
    public String getClassCourse() {
        return classCourse;
    }

    /**
     * Sets class course String
     * @param classCourse Class course String to set
     */
    public void setClassCourse(String classCourse) {
        this.classCourse = classCourse;
    }

    /**
     * Gets classroom String
     * @return classroom String
     */
    public String getClassroom() {
        return classroom;
    }

    /**
     * Sets classroom String
     * @param classroom Classroom string to set
     */
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
