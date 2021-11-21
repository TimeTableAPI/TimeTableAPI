package pt.iscte.asd.projectN3.group11.models;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomTest {
    Classroom classRoom;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        final LinkedList<Boolean> characteristics= new LinkedList<>(Arrays.asList(
                true, //Anfiteatro aulas
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                true, //BYOD
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
                ));
        final String building="TestBuilding";
        final String classroomName="TestClassRoom";
        final int normal_capacity=30;
        final int exam_capacity=10;
        final int number_characteristics=2;

        classRoom = new Classroom.Builder().
                building(building).
                classroomName(classroomName).
                normalCapacity(normal_capacity).
                examCapacity(exam_capacity).
                numberCharacteristics(number_characteristics).
                characteristics(characteristics).
                build();


    }

    @org.junit.jupiter.api.Test
    void hasCharacteristic() {

        assertTrue(classRoom.hasCharacteristic("Anfiteatro aulas"));
        assertFalse(classRoom.hasCharacteristic("Arq 2"));
        assertTrue(classRoom.hasCharacteristic("BYOD (Bring Your Own Device)"));
        assertThrows(IllegalArgumentException.class, () -> classRoom.hasCharacteristic("HelloWorld"));
        assertDoesNotThrow(() -> {
            classRoom.hasCharacteristic("Focus Group");

        });


    }
}