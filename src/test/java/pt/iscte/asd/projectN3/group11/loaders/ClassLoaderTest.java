package pt.iscte.asd.projectN3.group11.loaders;

import pt.iscte.asd.projectN3.group11.models.Class;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClassLoaderTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classTest.csv";
    private static Class aClass;

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
        final String beginningHour = "11:00:00";
        final String endHour = "13:00:00";
        final String monthDay = "23-11-2015";
        final String askedCharacteristics = "Sala de Aulas normal";
        final String classroom = "";
        final String capacity = "50";
        final List<String> realCharacteristics = Arrays.asList("Sala Aulas Mestrado Plus", "Horário sala visível portal público", "Sala Aulas Mestrado", "Sala de Aulas normal");
        aClass = new Class.Builder().
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
    void load()
    {
        Class classTest = ClassLoader.load(SAMPLE_CSV_FILE_CLASS_PATH).get(0);
        assertEquals(classTest.getCourses(), aClass.getCourses());
        assertEquals(classTest.getUnits(), aClass.getUnits());
        assertEquals(classTest.getClassesOfCourse(), aClass.getClassesOfCourse());
        assertEquals(classTest.getNumberOfStudentsInClass(), aClass.getNumberOfStudentsInClass());
        assertEquals(classTest.getShiftsWithFreeSpots(), aClass.getShiftsWithFreeSpots());
        assertEquals(classTest.getShiftsWithMoreThanTheCapacity(), aClass.getShiftsWithMoreThanTheCapacity());
        assertEquals(classTest.getWeekday(), aClass.getWeekday());
        assertEquals(classTest.getBeginningHour(), aClass.getBeginningHour());
        assertEquals(classTest.getEndHour(), aClass.getEndHour());
        assertEquals(classTest.getMonthDay(), aClass.getMonthDay());
        assertEquals(classTest.getAskedCharacteristics(), aClass.getAskedCharacteristics());
        assertEquals(classTest.getClassroom(), aClass.getClassroom());
        assertEquals(classTest.getCapacity(), aClass.getCapacity());
        assertEquals(classTest.getRealCharacteristics(), aClass.getRealCharacteristics());
    }
}
