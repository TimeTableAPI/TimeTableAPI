package pt.iscte.asd.projectn3.group11.models.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClassCourseTimeTest {

    @Test
    void testToString() {

        final String timeCheck1 = "07:30:00";
        assertEquals(timeCheck1, ClassCourseTime.HOUR_07H30.toString());
        final String timeCheck2 = "11:30:00";
        assertEquals(timeCheck2, ClassCourseTime.HOUR_11H30.toString());
    }

    @Test
    void stringToClassTime() {

        final ClassCourseTime classCourseTimeCheck1 = ClassCourseTime.HOUR_07H30;
        final ClassCourseTime classCourseTime1 = ClassCourseTime.stringToClassTime("07:30:00");
        assertEquals(classCourseTimeCheck1, classCourseTime1);

        final ClassCourseTime classCourseTimeCheck2 = ClassCourseTime.HOUR_11H30;
        final ClassCourseTime classCourseTime2 = ClassCourseTime.stringToClassTime("11:30:00");
        assertEquals(classCourseTimeCheck2, classCourseTime2);
    }
}