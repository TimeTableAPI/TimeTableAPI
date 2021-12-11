package pt.iscte.asd.projectn3.group11.models.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeShiftTest {

    @Test
    void testToString() {

        final String timeCheck1 = "07:30:00";
        assertEquals(timeCheck1, TimeShift.HOUR_07H30.toString());
        final String timeCheck2 = "11:30:00";
        assertEquals(timeCheck2, TimeShift.HOUR_11H30.toString());
    }

    @Test
    void stringToClassTime() {

        final TimeShift timeShiftCheck1 = TimeShift.HOUR_07H30;
        final TimeShift timeShift1 = TimeShift.stringToClassTime("07:30:00");
        assertEquals(timeShiftCheck1, timeShift1);

        final TimeShift timeShiftCheck2 = TimeShift.HOUR_11H30;
        final TimeShift timeShift2 = TimeShift.stringToClassTime("11:30:00");
        assertEquals(timeShiftCheck2, timeShift2);
    }
}