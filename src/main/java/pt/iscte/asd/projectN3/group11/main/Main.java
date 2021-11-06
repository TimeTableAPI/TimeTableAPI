package pt.iscte.asd.projectN3.group11.main;

import pt.iscte.asd.projectN3.group11.loaders.ClassLoader;
import pt.iscte.asd.projectN3.group11.loaders.ClassRoomLoader;
import pt.iscte.asd.projectN3.group11.models.Class;

public class Main {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";
    private static final String SAMPLE_CSV_FILE_CLASSROOM_PATH = "./src/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
        ClassLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
        ClassRoomLoader.load(SAMPLE_CSV_FILE_CLASSROOM_PATH);
    }
}
