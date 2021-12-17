package pt.iscte.asd.projectn3.group11.services.loaders;

import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassroomLoaderServiceTest {
    private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classRoomTest.csv";
    private static Classroom classroom;
    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        final String building = "Edifício Teste";
        final String classroomName="123";
        final int normalCapacity=50;
        final int examCapacity=25;
        final int numberCharacteristics=4;
        final LinkedList<Boolean> characteristics = new LinkedList<>(Arrays.asList(
            false, //Anfiteatro aulas
            false,//Apoio técnico eventos,
            false,//Arq 1,
            false,//Arq 2,
            false,//Arq 3,
            false,//Arq 4,
            false,//Arq 5,
            false,//Arq 6,
            false,//Arq 9,
            false, //BYOD (Bring Your Own Device),
            false,//Focus Group,
            true,//Horário sala visível portal público,
            false,//Laboratório de Arquitectura de Computadores I,
            false,//Laboratório de Arquitectura de Computadores II,
            false,//Laboratório de Bases de Engenharia,
            false,//Laboratório de Electrónica,
            false,//Laboratório de Informática,
            false,//Laboratório de Jornalismo,
            false,//Laboratório de Redes de Computadores I,
            false,//Laboratório de Redes de Computadores II,
            false,//Laboratório de Telecomunicações,
            true,//Sala Aulas Mestrado,
            true,//Sala Aulas Mestrado Plus,
            false,//Sala NEE,
            false,//Sala Provas,
            false,//Sala Reunião,
            false,//Sala de Arquitectura,
            true,//Sala de Aulas normal,
            false,//videoconferencia,
            false//Átrio
    ));
        classroom = new Classroom.Builder().
                building(building).
                classroomName(classroomName).
                normalCapacity(normalCapacity).
                examCapacity(examCapacity).
                numberCharacteristics(numberCharacteristics).
                characteristics(characteristics).
                build();
        //Edifício Teste,123,50,25,4,,,,,,,,,,,,X,,,,,,,,,,X,X,,,,,X,,
    }

    @org.junit.jupiter.api.Test
    void load()
    {
        Classroom classRoomTest = ClassroomLoaderService.load(SAMPLE_CSV_FILE_CLASS_PATH).get(0);
        assertEquals(classRoomTest.getBuilding(), classroom.getBuilding());
        assertEquals(classRoomTest.getClassroomName(), classroom.getClassroomName());
        assertEquals(classRoomTest.getExamCapacity(), classroom.getExamCapacity());
        assertEquals(classRoomTest.getNormalCapacity(), classroom.getNormalCapacity());
        assertEquals(classRoomTest.getNumberCharacteristics(), classroom.getNumberCharacteristics());
        assertEquals(classRoomTest.getNormalCapacity(), classroom.getNormalCapacity());
        assertEquals(classRoomTest.getCharacteristics(), classroom.getCharacteristics());
    }
}
