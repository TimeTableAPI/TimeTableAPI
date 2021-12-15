package pt.iscte.asd.projectn3.group11.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassroomTest {
    Classroom classRoom;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        final LinkedList<Boolean> characteristics= new LinkedList<>(Arrays.asList(
                true, //Anfiteatro aulas
                false,//Apoio técnico eventos
                false,//Arq 1
                false,//Arq 2
                false,//Arq 3
                false,//Arq 4
                false,//Arq 5
                false,//Arq 6
                false,//Arq 9
                true, //BYOD (Bring Your Own Device)
                false,//Focus Group
                false,//Horário sala visível portal público
                false,//Laboratório de Arquitectura de Computadores I
                false,//Laboratório de Arquitectura de Computadores II
                false,//Laboratório de Bases de Engenharia
                false,//Laboratório de Electrónica
                false,//Laboratório de Informática
                false,//Laboratório de Jornalismo
                false,//Laboratório de Redes de Computadores I
                false,//Laboratório de Redes de Computadores II
                false,//Laboratório de Telecomunicações
                false,//Sala Aulas Mestrado
                false,//Sala Aulas Mestrado Plus
                false,//Sala NEE
                false,//Sala Provas
                false,//Sala Reunião
                false,//Sala de Arquitectura
                false,//Sala de Aulas normal
                false,//videoconferencia
                false//Átrio
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


    @Test
    void testHasCharacteristic () {
        assertTrue(classRoom.hasCharacteristic("Anfiteatro aulas"));
        assertFalse(classRoom.hasCharacteristic("Arq 2"));
        assertTrue(classRoom.hasCharacteristic("BYOD (Bring Your Own Device)"));
        assertFalse(classRoom.hasCharacteristic("HelloWorld"));
        assertDoesNotThrow(() -> {
            classRoom.hasCharacteristic("Focus Group");

        });
    }

    @Test
    void hasALLCharacteristics() {
        assertTrue(classRoom.hasALLCharacteristics(Arrays.asList("Anfiteatro aulas","BYOD (Bring Your Own Device)")));
        assertFalse(classRoom.hasALLCharacteristics(Arrays.asList("Arq 2","Sala de Arquitectura")));

        assertFalse( classRoom.hasALLCharacteristics(Arrays.asList("HelloWorld", "testFalse")));
        assertDoesNotThrow(() -> {
            classRoom.hasALLCharacteristics(Arrays.asList("Focus Group","Átrio"));

        });
    }

    @Test
    void isInBuilding() {
        assertTrue(classRoom.isInBuilding("TestBuilding"));
        assertFalse(classRoom.isInBuilding("TestFALSEBuilding"));
        assertDoesNotThrow(() -> {
            assertTrue(classRoom.isInBuilding("TestBuilding"));
            assertFalse(classRoom.isInBuilding("TestFALSEBuilding"));
        });

    }

    @Test
    void isInANYBuilding() {
        assertTrue(classRoom.isInANYBuilding(Arrays.asList("TestBuilding","TestFALSEBuilding")));
        assertFalse(classRoom.isInANYBuilding(Arrays.asList("TestFALSEBuilding","SquareBuilding")));

        assertDoesNotThrow(() -> {
            assertTrue(classRoom.isInANYBuilding(Arrays.asList("TestBuilding","TestFALSEBuilding")));
            assertFalse(classRoom.isInANYBuilding(Arrays.asList("TestFALSEBuilding","SquareBuilding")));
        });
    }

    @Test
    void isNamed() {
        assertTrue(classRoom.isNamed("TestClassRoom"));
        assertFalse(classRoom.isNamed("FalseRoom"));

        assertDoesNotThrow(() -> {
            assertTrue(classRoom.isNamed("TestClassRoom"));
            assertFalse(classRoom.isNamed("FalseRoom"));
        });
    }
}