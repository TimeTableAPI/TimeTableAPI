package pt.iscte.asd.projectn3.group11.services.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jetbrains.annotations.NotNull;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.LogService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public final class ClassroomLoaderService extends LoaderService<Classroom> {

    private static ClassroomLoaderService INSTANCE = null;

    private ClassroomLoaderService(){}
    public static synchronized ClassroomLoaderService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ClassroomLoaderService();
        }
        return INSTANCE;
    }

    @Override
    protected void extract(@NotNull CSVReader csvReader, @NotNull List<Classroom> classrooms) throws IOException, CsvValidationException {
        csvReader.readNext();
        String[] nextRecord;

        while ((nextRecord = csvReader.readNext()) != null) {
            final String building = nextRecord[0];
            final String classroomName = nextRecord[1];
            final int normal_capacity = Integer.parseInt(nextRecord[2]);
            final int exam_capacity = Integer.parseInt(nextRecord[3]);
            final int number_characteristics = Integer.parseInt(nextRecord[4]);
            final LinkedList<Boolean> characteristics = new LinkedList<>();

            for (int index = 5; index < nextRecord.length; index++) {
                characteristics.add (nextRecord[index].equals("X") || nextRecord[index].equals("x"));
            }
            Classroom classN = new Classroom.Builder().
                    building(building).
                    classroomName(classroomName).
                    normalCapacity(normal_capacity).
                    examCapacity(exam_capacity).
                    numberCharacteristics(number_characteristics).
                    characteristics(characteristics).
                    build();
            classrooms.add(classN);
        }
        LogService.getInstance().info(classrooms);
    }

}
