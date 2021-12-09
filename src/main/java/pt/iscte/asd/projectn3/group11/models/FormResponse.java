package pt.iscte.asd.projectn3.group11.models;

public class FormResponse {

    private String classCourse;
    private String classRoom;

    public FormResponse() {
        super();
    }

    public FormResponse(String classcourse, String classRoom) {
        this.classCourse = classcourse;
        this.classRoom = classRoom;
    }

    public String getClassCourse() {
        return classCourse;
    }

    public void setClassCourse(String classCourse) {
        this.classCourse = classCourse;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }
}
