package pt.iscte.asd.projectn3.group11.models;

public class UploadedFile {

    private String classCourse;
    private String classRoom;

    public UploadedFile() {
        super();
    }

    public UploadedFile(String classcourse, String classRoom) {
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
