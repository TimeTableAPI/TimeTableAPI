package pt.iscte.asd.projectn3.group11.main;

import pt.iscte.asd.projectn3.group11.loaders.ClassLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassRoomLoader;

public class Main {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";
    private static final String SAMPLE_CSV_FILE_CLASSROOM_PATH = "./src/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
        ClassLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
        ClassRoomLoader.load(SAMPLE_CSV_FILE_CLASSROOM_PATH);
    }
}
