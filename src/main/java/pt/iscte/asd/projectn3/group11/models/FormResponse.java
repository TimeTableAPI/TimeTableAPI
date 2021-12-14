package pt.iscte.asd.projectn3.group11.models;

@Deprecated
public class FormResponse {

    //region MEMBERS
    private String classCourse;
    private String classroom;
    //endregion

    //region CONSTRUCTORS
    public FormResponse() {
        super();
    }

    public FormResponse(String classCourse, String classroom) {
        this.classCourse = classCourse;
        this.classroom = classroom;
    }
    //endregion

    //region SETTERS
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
    //endregion

    //region GETTERS
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
    //endregion
}
