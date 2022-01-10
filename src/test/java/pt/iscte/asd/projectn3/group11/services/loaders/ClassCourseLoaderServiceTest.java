package pt.iscte.asd.projectn3.group11.services.loaders;

import org.junit.jupiter.api.BeforeAll;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassCourseLoaderServiceTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classTest.csv";
    private static ClassCourse classCourse1;
    private static ClassCourse classCourse2;
    private static List<ClassCourse> loadedClassCourses ;
    private static ClassCourse aClassCourse;

    @BeforeAll
    static void setUp()
    {
        loadedClassCourses = ClassCourseLoaderService.load(SAMPLE_CSV_FILE_CLASS_PATH);

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


        final List<Boolean> characteristics = new LinkedList<>();
        //realCharacteristics.stream().map(realCharacteristic ->characteristics.add(realCharacteristic.equals("X") || realCharacteristic.equals("x")));
        Arrays.stream(Classroom.CHARACTERISTICS_LIST).forEach(realCharacteristic -> {
            characteristics.add(realCharacteristics.contains(realCharacteristic));
        });

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
                classroom(new Classroom.Builder().
                        building("Edifício Teste").
                        classroomName("123").
                        normalCapacity(capacity).
                        examCapacity(capacity).
                        numberCharacteristics(realCharacteristics.size()).
                        characteristics(characteristics)
                        .build()
                ).
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
        assertEquals(classCourse1.getCourses() , classCourseTest.getCourses());
        assertEquals(classCourse1.getUnits() , classCourseTest.getUnits());
        assertEquals(classCourse1.getClassesOfCourse() , classCourseTest.getClassesOfCourse());
        assertEquals(classCourse1.getNumberOfStudentsInClass() , classCourseTest.getNumberOfStudentsInClass());
        assertEquals(classCourse1.getShiftsWithFreeSpots() , classCourseTest.getShiftsWithFreeSpots());
        assertEquals(classCourse1.getShiftsWithMoreThanTheCapacity() , classCourseTest.getShiftsWithMoreThanTheCapacity());
        assertEquals(classCourse1.getWeekday() , classCourseTest.getWeekday());
        assertEquals(classCourse1.getDate() , classCourseTest.getDate());
        assertEquals(classCourse1.getAskedCharacteristics() , classCourseTest.getAskedCharacteristics());
        assertEquals(classCourse1.getClassroom() , classCourseTest.getClassroom());
        assertEquals(classCourse1.getCapacity() , classCourseTest.getCapacity());
        assertEquals(classCourse1.getRealCharacteristics() , classCourseTest.getRealCharacteristics());
    }

    @org.junit.jupiter.api.Test
    void testLoadedTimeShifts(){
        assertEquals(classCourse1.getBeginningHour(),loadedClassCourses.get(0).getBeginningHour() );
        assertEquals(classCourse1.getEndHour(),loadedClassCourses.get(0).getEndHour() );

        assertEquals(classCourse2.getBeginningHour(),loadedClassCourses.get(1).getBeginningHour() );
        assertEquals(classCourse2.getEndHour(),loadedClassCourses.get(1).getEndHour() );

    }

    @Test
    void export() {
        try {
            List<ClassCourse> classCourses = ClassCourseLoaderService.load(SAMPLE_CSV_FILE_CLASS_PATH);
            File file = ClassCourseLoaderService.export(classCourses);
            List<ClassCourse> classesExported = ClassCourseLoaderService.load(file);
            for (int i = 0; i < min(classCourses.size(), classesExported.size()); i++) {
                assertEquals(classCourses.get(i), classesExported.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
