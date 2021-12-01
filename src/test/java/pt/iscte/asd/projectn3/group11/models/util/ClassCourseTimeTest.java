package pt.iscte.asd.projectn3.group11.models.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassCourseTimeTest {

    @Test
    void testToString() {
        final String timeCheck = "11:30:00";
        assertEquals(timeCheck, ClassCourseTime.HOUR_11H30.toString());
    }

    @Test
    void stringToClassTime() {
        final ClassCourseTime classCourseTimeCheck = ClassCourseTime.HOUR_11H30;
        final ClassCourseTime classCourseTime = ClassCourseTime.stringToClassTime("11:30:00");
        assertEquals(classCourseTimeCheck, classCourseTime);
    }
}