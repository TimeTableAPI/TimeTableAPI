package pt.iscte.asd.projectn3.group11.main;

import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.loaders.ClassCourseLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassroomLoader;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import java.io.*;

@ComponentScan({"pt.iscte.asd.projectn3.group11.controller"})
@SpringBootApplication
public class Main {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";
    private static final String SAMPLE_CSV_FILE_CLASSROOM_PATH = "./src/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
        List<ClassCourse> classCourses = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
        List<Classroom> classrooms = ClassroomLoader.load(SAMPLE_CSV_FILE_CLASSROOM_PATH);

        Context context = new Context(classCourses, classrooms, new BasicAlgorithmService());
        context.resolve();
        System.out.println(classesWithRoomPercentage(classCourses));

        System.out.println(TimetableEvaluationService.evaluateTimetable(classCourses));
        try {
            File exportedClasses = ClassCourseLoader.export();
            //output.write(exportedClasses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Main.class, args);
        System.out.println("Main App Started with SPRING");

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

/*
    public static void copy() {
        File infile = new File("C:\\MyInputFile.txt");
        File outfile = new File("C:\\MyOutputFile.txt");
        try (
                FileInputStream instream = new FileInputStream(infile);
                FileOutputStream outstream = new FileOutputStream(outfile);
        ) {

            byte[] buffer = new byte[1024];

            int length;
            //copying the contents from input stream to
            //output stream using read and write methods

            while (instream.read(buffer)) >0){
                outstream.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
