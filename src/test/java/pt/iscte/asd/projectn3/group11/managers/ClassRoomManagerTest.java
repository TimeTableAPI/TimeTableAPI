package pt.iscte.asd.projectn3.group11.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.iscte.asd.projectn3.group11.loaders.ClassRoomLoader;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassRoomManagerTest {
	private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classRoomTest.csv";
	private List<Classroom> classRoomsTestList;
	private ClassRoomManager classRoomManager;

	@BeforeEach
	void setUp() {
		classRoomsTestList = ClassRoomLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
		classRoomManager = new ClassRoomManager(classRoomsTestList);
	}

	@Test
	void getWithCapacity() {
		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(0));
			add(classRoomsTestList.get(1));
			add(classRoomsTestList.get(3));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getWithCapacity(50));
	}

	@Test
	void getWithExamCapacity() {
		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(0));
			add(classRoomsTestList.get(1));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getWithExamCapacity(15));
	}

	@Test
	void getWithCharacteristics() {

		LinkedList<String> desiredCharacteristics = new LinkedList<String>(){{
			add("Anfiteatro aulas");
			add("Apoio técnico eventos");
		}};
		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(1));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getWithCharacteristics(desiredCharacteristics));
	}

	@Test
	void getWithCharacteristic() {


		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(2));
			add(classRoomsTestList.get(3));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getWithCharacteristic("Arq 6"));

	}


	@Test
	void getWithBuilding() {
		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(1));
			add(classRoomsTestList.get(2));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getInBuilding("Edifício 1"));

	}

	@Test
	void getInAnyBuilding() {
		LinkedList<String> desiredBuildings = new LinkedList<String>(){{
			add("Edifício 1");
			add("Edifício 3");
		}};
		LinkedList<Classroom> expectedClassRooms = new LinkedList<Classroom>(){{
			add(classRoomsTestList.get(1));
			add(classRoomsTestList.get(2));
			add(classRoomsTestList.get(3));
		}};

		assertIterableEquals(expectedClassRooms, classRoomManager.getInAnyBuilding(desiredBuildings));

	}
}