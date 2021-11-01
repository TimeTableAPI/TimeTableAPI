package Models;

import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomTest {
    ClassRoom classRoom;

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

        classRoom = new ClassRoom(characteristics,
                building,
                classroomName,
                normal_capacity,
                exam_capacity,
                number_characteristics
        );


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