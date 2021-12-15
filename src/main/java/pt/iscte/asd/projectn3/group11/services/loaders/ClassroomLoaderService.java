package pt.iscte.asd.projectn3.group11.services.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ClassroomLoaderService {

    /**
     * Loads a Classroom List from a file in the given path.
     *
     * @param path path to the classroom csv.
     * @return List of classrooms
     */
    public static final LinkedList<Classroom> load(final String path) {
        LinkedList<Classroom> classrooms = new LinkedList<>();
        try (
                final Reader reader = Files.newBufferedReader(Paths.get(path));
                final CSVReader csvReader = new CSVReader(reader)
        ) {

            extract(csvReader, classrooms);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    /**
     * Loads a Classroom List from a file.
     *
     * @param file object containing the classroom csv.
     * @return List of classrooms
     */
    public static final LinkedList<Classroom> load(final File file) {
        LinkedList<Classroom> classrooms = new LinkedList<>();
        try (
                final Reader reader = new FileReader(file);
                final CSVReader csvReader = new CSVReader(reader)
        ) {

            extract(csvReader, classrooms);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return classrooms;
        }
        return classrooms;
    }

    /**
     * Loads a Classroom List from a MultipartFile.
     *
     * @param multipartFile MultipartFile containing the classroom csv.
     * @param toDisk
     * @return List of classrooms
     */
    public static final LinkedList<Classroom> load(final MultipartFile multipartFile, boolean toDisk) throws IOException {
        File file;
        if (toDisk) {
            file = new File(FileUploadService.UPLOADED_FILES_LOCATION + multipartFile.getOriginalFilename());
        } else {
            Path temp = Files.createTempFile(multipartFile.getOriginalFilename(), ".csv");
            file = new File(temp.toUri());
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        LinkedList<Classroom> classrooms = load(file);
        return classrooms;
    }

    /**
     * Loads a ClassRoom List from a CSVreader given as a param
     *
     * @param csvReader  reader to extract the data
     * @param classrooms list where the extracted classRooms will be appended
     */
    private static void extract(CSVReader csvReader, List<Classroom> classrooms) throws IOException, CsvValidationException {
        final String[] headers = csvReader.readNext();

        String[] nextRecord;
        System.out.println("ClassRoomLoad.main::Arrays are equal, loading...");

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
        System.out.println("ClassRoomLoad.main::classRooms = " + classrooms);
    }

}
