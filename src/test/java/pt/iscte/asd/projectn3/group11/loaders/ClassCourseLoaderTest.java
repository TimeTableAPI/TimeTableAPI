package pt.iscte.asd.projectn3.group11.loaders;

import org.junit.jupiter.api.Test;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.*;

public class ClassCourseLoaderTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classTest.csv";
    private static ClassCourse aClassCourse;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        final List<String> courses = Arrays.asList("ISCTE-IUL", "LCP", "LHMC", "LP", "LS", "LS-PL", "MIA");
        final List<String> units = Collections.singletonList("Língua Espanhola");
        final String shift = "00670TP02";
        final List<String> classesOfCourse = Collections.singletonList("CI-CT1");
        final int numberOfStudentsInClass = 23;
        final int shiftsWithFreeSpots = 0;
        final int shiftsWithMoreThanTheCapacity = 0;
        final String weekday = "Seg";
        final String beginningHour = "11:00:00";
        final String endHour = "13:00:00";
        final String monthDay = "23-11-2015";
        final String askedCharacteristics = "Sala de Aulas normal";
        final String classroom = "";
        final String capacity = "50";
        final List<String> realCharacteristics = Arrays.asList("Sala Aulas Mestrado Plus", "Horário sala visível portal público", "Sala Aulas Mestrado", "Sala de Aulas normal");
        aClassCourse = new ClassCourse.Builder().
                courses(courses).
                units(units).
                shift(shift).
                classesOfCourse(classesOfCourse).
                numberOfStudentsInClass(numberOfStudentsInClass).
                shiftsWithFreeSpots(shiftsWithFreeSpots).
                shiftsWithMoreThanTheCapacity(shiftsWithMoreThanTheCapacity).
                weekday(weekday).
                beginningHour(beginningHour).
                endHour(endHour).
                monthDay(monthDay).
                askedCharacteristics(askedCharacteristics).
                classroom(classroom).
                capacity(capacity).
                realCharacteristics(realCharacteristics).
                build();
    }

    @org.junit.jupiter.api.Test
    void load() {
        ClassCourse classCourseTest = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH).get(0);
        assertEquals(classCourseTest.getCourses(), aClassCourse.getCourses());
        assertEquals(classCourseTest.getUnits(), aClassCourse.getUnits());
        assertEquals(classCourseTest.getClassesOfCourse(), aClassCourse.getClassesOfCourse());
        assertEquals(classCourseTest.getNumberOfStudentsInClass(), aClassCourse.getNumberOfStudentsInClass());
        assertEquals(classCourseTest.getShiftsWithFreeSpots(), aClassCourse.getShiftsWithFreeSpots());
        assertEquals(classCourseTest.getShiftsWithMoreThanTheCapacity(), aClassCourse.getShiftsWithMoreThanTheCapacity());
        assertEquals(classCourseTest.getWeekday(), aClassCourse.getWeekday());
        assertEquals(classCourseTest.getBeginningHour(), aClassCourse.getBeginningHour());
        assertEquals(classCourseTest.getEndHour(), aClassCourse.getEndHour());
        assertEquals(classCourseTest.getMonthDay(), aClassCourse.getMonthDay());
        assertEquals(classCourseTest.getAskedCharacteristics(), aClassCourse.getAskedCharacteristics());
        assertEquals(classCourseTest.getClassroom(), aClassCourse.getClassroom());
        assertEquals(classCourseTest.getCapacity(), aClassCourse.getCapacity());
        assertEquals(classCourseTest.getRealCharacteristics(), aClassCourse.getRealCharacteristics());
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
