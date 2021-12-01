package pt.iscte.asd.projectn3.group11.loaders;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseDate;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClassCourseLoaderTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classTest.csv";
    private static ClassCourse classCourse;

    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        final List<String> courses = Arrays.asList("ISCTE-IUL", "LCP", "LHMC", "LP", "LS", "LS-PL", "MIA");
        final List<String> units = Collections.singletonList("Língua Espanhola");
        final String shift = "00670TP02";
        final List<String> classesOfCourse = Collections.singletonList("CI-CT1");
        final int numberOfStudentsInClass = 23;
        final int shiftsWithFreeSpots = 0;
        final int shiftsWithMoreThanTheCapacity = 0;
        final String weekday = "Seg";
        final ClassCourseTime beginningHour = ClassCourseTime.stringToClassTime("11:00:00");
        final ClassCourseTime endHour = ClassCourseTime.stringToClassTime("13:00:00");
        final ClassCourseDate date = new ClassCourseDate("23-11-2015");
        final String askedCharacteristics = "Sala de Aulas normal";
        final String classroom = "";
        final int capacity = 50;
        final List<String> realCharacteristics = Arrays.asList("Sala Aulas Mestrado Plus", "Horário sala visível portal público", "Sala Aulas Mestrado", "Sala de Aulas normal");
        classCourse = new ClassCourse.Builder().
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
                date(date).
                askedCharacteristics(askedCharacteristics).
                classroom(classroom).
                capacity(capacity).
                realCharacteristics(realCharacteristics).
                build();
    }

    @org.junit.jupiter.api.Test
    void load()
    {
        ClassCourse classCourseTest = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_PATH).get(0);
        assertEquals(classCourseTest.getCourses(), classCourse.getCourses());
        assertEquals(classCourseTest.getUnits(), classCourse.getUnits());
        assertEquals(classCourseTest.getClassesOfCourse(), classCourse.getClassesOfCourse());
        assertEquals(classCourseTest.getNumberOfStudentsInClass(), classCourse.getNumberOfStudentsInClass());
        assertEquals(classCourseTest.getShiftsWithFreeSpots(), classCourse.getShiftsWithFreeSpots());
        assertEquals(classCourseTest.getShiftsWithMoreThanTheCapacity(), classCourse.getShiftsWithMoreThanTheCapacity());
        assertEquals(classCourseTest.getWeekday(), classCourse.getWeekday());
        assertEquals(classCourseTest.getBeginningHour(), classCourse.getBeginningHour());
        assertEquals(classCourseTest.getEndHour(), classCourse.getEndHour());
        assertEquals(classCourseTest.getDate(), classCourse.getDate());
        assertEquals(classCourseTest.getAskedCharacteristics(), classCourse.getAskedCharacteristics());
        assertEquals(classCourseTest.getClassroom(), classCourse.getClassroom());
        assertEquals(classCourseTest.getCapacity(), classCourse.getCapacity());
        assertEquals(classCourseTest.getRealCharacteristics(), classCourse.getRealCharacteristics());
    }
}
