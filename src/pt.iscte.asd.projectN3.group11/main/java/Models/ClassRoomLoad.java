package Models;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class ClassRoomLoad {


    private static final String SAMPLE_CSV_FILE_PATH = "./src/pt.iscte.asd.projectN3.group11/main/resources/ADS - Caracterizacao das salas.csv";

    public static void main(String[] args) {
//https://www.callicoder.com/java-read-write-csv-file-opencsv/
        try (
                final Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                final CSVReader csvReader = new CSVReader(reader)
        ) {
            final LinkedList<ClassRoom> classRooms = new LinkedList<>();
            final String[] headers = csvReader.readNext();

            String[] nextRecord;
            if (Arrays.equals(headers, ClassRoom.CHARACTERISTICS_LIST)) {
                System.out.println("ClassRoomLoad.main::Arrays are equal, loading...");

                while ((nextRecord = csvReader.readNext()) != null) {
                    final String building = nextRecord[0];
                    final String classroomName = nextRecord[1];
                    final int normal_capacity = Integer.parseInt(nextRecord[2]);
                    final int exam_capacity = Integer.parseInt(nextRecord[3]);
                    final int number_characteristics = Integer.parseInt(nextRecord[4]);
                    final LinkedList<Boolean> characteristics = new LinkedList<>();

                    for (int index = 5; index < nextRecord.length; index++) {
                        if (nextRecord[index].equals("X")) {
                            characteristics.add(true);
                        } else {
                            characteristics.add(false);
                        }
                    }
                    ClassRoom classN = new ClassRoom(characteristics, building, classroomName, normal_capacity, exam_capacity, number_characteristics);
                    classRooms.add(classN);
                }
                System.out.println("ClassRoomLoad.main::classRooms = " + classRooms);
            } else {
                System.out.println("ClassRoomLoad.main::Arrays are NOT equal, exiting...");

            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
