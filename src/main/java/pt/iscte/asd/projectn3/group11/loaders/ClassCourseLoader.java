package pt.iscte.asd.projectn3.group11.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class ClassCourseLoader {
    //region LOADERS

    /**
     * Loads a Class csv file.
     * @param path path to the classroom csv.
     * @return List of classrooms
     */
    public static final LinkedList<ClassCourse> load(final String path)
    {
        final LinkedList<ClassCourse> classCourses = new LinkedList<>();
        try (
                final Reader reader = Files.newBufferedReader(Paths.get(path));
                final CSVReader csvReader = new CSVReader(reader)
        ) {
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

                    ClassCourse classCourse = new ClassCourse.Builder().
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
                    System.out.println(classCourse);
                    classCourses.add(classCourse);
                }

            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return classCourses;
    }

    //endregion
}
