package pt.iscte.asd.projectn3.group11.loaders;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import org.junit.jupiter.api.Test;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassCourseLoaderTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classTest.csv";
    private static ClassCourse classCourse1;
    private static ClassCourse classCourse2;
    private static LinkedList<ClassCourse> loadedClassCourses ;
    private static ClassCourse aClassCourse;

    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        loadedClassCourses = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);

        final List<String> courses = Arrays.asList("ISCTE-IUL", "LCP", "LHMC", "LP", "LS", "LS-PL", "MIA");
        final List<String> units = Collections.singletonList("Língua Espanhola");
        final String shift = "00670TP02";
        final List<String> classesOfCourse = Collections.singletonList("CI-CT1");
        final int numberOfStudentsInClass = 23;
        final int shiftsWithFreeSpots = 0;
        final int shiftsWithMoreThanTheCapacity = 0;
        final String weekday = "Seg";
        final Date date = new Date("23-11-2015");
        final List<String> askedCharacteristics = Collections.singletonList("Sala de Aulas normal");
        final String classroom = "";
        final int capacity = 50;
        final List<String> realCharacteristics = Arrays.asList("Sala Aulas Mestrado Plus", "Horário sala visível portal público", "Sala Aulas Mestrado", "Sala de Aulas normal");
        final TimeShift shift1 = TimeShift.stringToClassTime("11:00:00");
        final TimeShift shift2 = TimeShift.stringToClassTime("11:30:00");
        final TimeShift shift3 = TimeShift.stringToClassTime("12:00:00");
        final String beginningHour = "11:00:00";
        final String endHour = "13:00:00";
        final String monthDay = "23-11-2015";
        classCourse1 = new ClassCourse.Builder().
                courses(courses).
                units(units).
                shift(shift).
                classesOfCourse(classesOfCourse).
                numberOfStudentsInClass(numberOfStudentsInClass).
                shiftsWithFreeSpots(shiftsWithFreeSpots).
                shiftsWithMoreThanTheCapacity(shiftsWithMoreThanTheCapacity).
                weekday(weekday).
                beginningHour(shift1).
                endHour(shift2).
                date(date).
                askedCharacteristics(askedCharacteristics).
                capacity(capacity).
                realCharacteristics(realCharacteristics).
                build();
        classCourse2 = new ClassCourse.Builder().
                courses(courses).
                units(units).
                shift(shift).
                classesOfCourse(classesOfCourse).
                numberOfStudentsInClass(numberOfStudentsInClass).
                shiftsWithFreeSpots(shiftsWithFreeSpots).
                shiftsWithMoreThanTheCapacity(shiftsWithMoreThanTheCapacity).
                weekday(weekday).
                beginningHour(shift2).
                endHour(shift3).
                date(date).
                askedCharacteristics(askedCharacteristics).
                capacity(capacity).
                realCharacteristics(realCharacteristics).
                build();
    }

    @org.junit.jupiter.api.Test
    void load()
    {
        ClassCourse classCourseTest = loadedClassCourses.get(0);
        assertEquals(classCourseTest.getCourses(), classCourse1.getCourses());
        assertEquals(classCourseTest.getUnits(), classCourse1.getUnits());
        assertEquals(classCourseTest.getClassesOfCourse(), classCourse1.getClassesOfCourse());
        assertEquals(classCourseTest.getNumberOfStudentsInClass(), classCourse1.getNumberOfStudentsInClass());
        assertEquals(classCourseTest.getShiftsWithFreeSpots(), classCourse1.getShiftsWithFreeSpots());
        assertEquals(classCourseTest.getShiftsWithMoreThanTheCapacity(), classCourse1.getShiftsWithMoreThanTheCapacity());
        assertEquals(classCourseTest.getWeekday(), classCourse1.getWeekday());
        assertEquals(classCourseTest.getDate(), classCourse1.getDate());
        assertEquals(classCourseTest.getAskedCharacteristics(), classCourse1.getAskedCharacteristics());
        assertEquals(classCourseTest.getClassroom(), classCourse1.getClassroom());
        assertEquals(classCourseTest.getCapacity(), classCourse1.getCapacity());
        assertEquals(classCourseTest.getRealCharacteristics(), classCourse1.getRealCharacteristics());
    }

    @org.junit.jupiter.api.Test
    void testLoadedTimeShifts(){
        assertEquals(classCourse1.getBeginningHour(),loadedClassCourses.get(0).getBeginningHour() );
        assertEquals(classCourse1.getEndHour(),loadedClassCourses.get(0).getEndHour() );

        assertEquals(classCourse2.getBeginningHour(),loadedClassCourses.get(1).getBeginningHour() );
        assertEquals(classCourse2.getEndHour(),loadedClassCourses.get(1).getEndHour() );

    }

    @Test
    void clear() {

    }

    @Test
    void export() {
        try {
            LinkedList<ClassCourse> classCourses = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
            File file = ClassCourseLoader.export();
            LinkedList<ClassCourse> classesExported = ClassCourseLoader.load(file);
            for (int i = 0; i < min(classCourses.size(), classesExported.size()); i++) {
                assertEquals(classCourses.get(i), classesExported.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
