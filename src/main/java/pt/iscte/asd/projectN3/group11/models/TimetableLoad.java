package pt.iscte.asd.projectN3.group11.models;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class TimetableLoad {

    private static final String SAMPLE_CSV_FILE_PATH = "./src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv";

    public static void main(final String[] args)
    {
        try (
                final Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                final CSVReader csvReader = new CSVReader(reader)
        ) {
            final LinkedList<Class> aClasses = new LinkedList<>();;

            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {

                final String course = nextRecord[0];
                final String unit = nextRecord[1];
                final String shift = nextRecord[2];
                final String classOfCourse = nextRecord[3];
                final String studentNum = nextRecord[4];
                final String shiftsWithFreeSpots = nextRecord[5];
                final String shiftsWithMoreThanTheCapacity = nextRecord[6];
                final String weekday = nextRecord[7];
                final String beginningHour = nextRecord[8];
                final String endHour = nextRecord[9];
                final String monthDay = nextRecord[10];
                final String askedCharacteristics = nextRecord[11];
                final String classroom = nextRecord[12];
                final String capacity = nextRecord[13];
                final String realCharacteristics = nextRecord[14];

                Class aClass = new Class(
                        course,
                        unit,
                        shift,
                        classOfCourse,
                        studentNum,
                        shiftsWithFreeSpots,
                        shiftsWithMoreThanTheCapacity,
                        weekday,
                        beginningHour,
                        endHour,
                        monthDay,
                        askedCharacteristics,
                        classroom,
                        capacity,
                        realCharacteristics);

                System.out.println(aClass);
                aClasses.add(aClass);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
