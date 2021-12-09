package pt.iscte.asd.projectn3.group11.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pt.iscte.asd.projectn3.group11.loaders.ClassCourseLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassRoomLoader;

import java.io.*;

@ComponentScan({"pt.iscte.asd.projectn3.group11.controller"})
@SpringBootApplication
public class Main {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";
    private static final String SAMPLE_CSV_FILE_CLASSROOM_PATH = "./src/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
        ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
        ClassRoomLoader.load(SAMPLE_CSV_FILE_CLASSROOM_PATH);
        try {
            File exportedClasses = ClassCourseLoader.export();
            //output.write(exportedClasses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Main.class, args);
        System.out.println("Main App Started with SPRING");

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
