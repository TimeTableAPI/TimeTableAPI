package pt.iscte.asd.projectn3.group11.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomServiceTest {
	private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classRoomTest.csv";
	private List<Classroom> classRoomsTestList;

	@BeforeEach
	void setUp() {
		classRoomsTestList = ClassroomLoaderService.getInstance().load(SAMPLE_CSV_FILE_CLASS_PATH);
	}


}