package pt.iscte.asd.projectn3.group11.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;
import java.io.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class ClassCourseLoader {
    //region LOADERS
    public final static LinkedList<ClassCourse> CLASS_COURSES = new LinkedList<>();

    /**
     * Loads a Class csv file from given path.
     *
     * @param path path to the classroom csv.
     * @return List of classrooms
     */
    public static final LinkedList<ClassCourse> load(final String path) {
        try (
                final Reader reader = Files.newBufferedReader(Paths.get(path));
                final CSVReader csvReader = new CSVReader(reader)
        ) {
            extractClass(csvReader);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return CLASS_COURSES;
    }

    //endregion

    /**
     * Loads a Class csv file.
     *
     * @param file object containing the csv for the Classes.
     * @return List of classrooms
     */
    public static final LinkedList<ClassCourse> load(final File file) {
        try (
                final Reader reader = new FileReader(file);
                final CSVReader csvReader = new CSVReader(reader)
        ) {
            extractClass(csvReader);

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return CLASS_COURSES;
        }
        return CLASS_COURSES;
    }

    /**
     * Loads a Class csv file.
     *
     * @param multipartFile object containing the csv for the Classes.
     * @return List of classrooms
     */
    public static final LinkedList<ClassCourse> load(final MultipartFile multipartFile, boolean toDisk) throws IOException {
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
        ClassCourseLoader.load(file);
        return CLASS_COURSES;
    }

    private static void extractClass(CSVReader csvReader) throws IOException, CsvValidationException {
        csvReader.readNext();
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
            final String beginningHourString = nextRecord[8];
            final String endHourString = nextRecord[9];
            final String dateString = nextRecord[10];
            final String askedCharacteristics = nextRecord[11];
            final String classroom = nextRecord[12];
            final String capacityString = nextRecord[13];
            final String realCharacteristics = nextRecord[14];

            final int capacity = capacityString.isEmpty() ? -1 : Integer.parseInt(capacityString);
            final TimeShift beginningHour = TimeShift.stringToClassTime(beginningHourString);
            final TimeShift endHour = TimeShift.stringToClassTime(endHourString);
            final Date date = new Date(dateString);

            for( int i =beginningHour.getId() ; i< endHour.getId();i++){
                TimeShift begginingTime = TimeShift.getById(i);
                TimeShift endTime = TimeShift.getById(i+1);

                ClassCourse aclassCourse = new ClassCourse.Builder().
                        courses(Arrays.asList(course.split(", "))).
                        units(Arrays.asList(unit.split(", "))).
                        shift(shift).
                        classesOfCourse(Arrays.asList(classOfCourse.split(", "))).
                        numberOfStudentsInClass(Integer.parseInt(studentNum)).
                        shiftsWithFreeSpots(Integer.parseInt(shiftsWithFreeSpots)).
                        shiftsWithMoreThanTheCapacity(Integer.parseInt(shiftsWithMoreThanTheCapacity)).
                        weekday(weekday).
                        beginningHour(begginingTime).
                        endHour(endTime).
                        date(date).
                        askedCharacteristics(Arrays.asList((askedCharacteristics).split(","))).
                        capacity(capacity).
                        realCharacteristics(Arrays.asList((realCharacteristics.split(", ")))).
                        build();
                System.out.println(aclassCourse);
                CLASS_COURSES.add(aclassCourse);
            }
        }
    }

    public static void clear() {
        ClassCourseLoader.CLASS_COURSES.clear();
    }
    //endregion

    //region Exporter
    public static File export() throws IOException {
        try {
            Path temp = Files.createTempFile("tempExportedClasses", ".csv");
            File myObj = new File(temp.toUri());
            //if (myObj.createNewFile()) {
            try (FileWriter writer = new FileWriter(myObj)) {
                writer.write(String.join(",", ClassCourse.HEADER));
                for (ClassCourse classCourse : ClassCourseLoader.CLASS_COURSES) {
                    writer.write(classCourse.toCSVString());
                    writer.write("\n");
                }

            }
            System.out.println("File created in:" + myObj.getAbsolutePath());
            //} else {
            //    System.out.println("File already exists.");
            //}
            return myObj;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            throw e;
        }
    }

//endregion
}
