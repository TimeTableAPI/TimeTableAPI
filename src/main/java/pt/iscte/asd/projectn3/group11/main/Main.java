package pt.iscte.asd.projectn3.group11.main;

import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.loaders.ClassCourseLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassroomLoader;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;

import java.util.List;

public class Main {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";
    private static final String SAMPLE_CSV_FILE_CLASSROOM_PATH = "./src/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
        List<ClassCourse> classCourses = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
        List<Classroom> classrooms = ClassroomLoader.load(SAMPLE_CSV_FILE_CLASSROOM_PATH);
        Context context = new Context(classCourses, classrooms, new BasicAlgorithmService());
        context.resolve();
        System.out.println(classesWithRoomPercentage(classCourses));
    }

    static double classesWithRoomPercentage(List<ClassCourse> classes) {
        if (classes.size() > 0) {
            double counter = 0;
            for (ClassCourse classCourse : classes) {
                if (classCourse.getClassroom() != null) {
                    counter++;
                }
            }
            final double percentage = counter / classes.size();
            return percentage;
        } else {
            return 0;
        }
    }

}
