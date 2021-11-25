package pt.iscte.asd.projectN3.group11.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import pt.iscte.asd.projectN3.group11.models.Classroom;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class ClassRoomLoader {

	/**
	 * Loads a Classroom csv file.
	 *
	 * @param path path to the classroom csv.
	 * @return List of classrooms
	 */
	public static final LinkedList<Classroom> load(final String path) {
		final LinkedList<Classroom> classrooms = new LinkedList<>();
		try (
				final Reader reader = Files.newBufferedReader(Paths.get(path));
				final CSVReader csvReader = new CSVReader(reader)
		) {

			final String[] headers = csvReader.readNext();

			String[] nextRecord;
			System.out.println("ClassRoomLoad.main::Arrays are equal, loading...");

			while ((nextRecord = csvReader.readNext()) != null) {
				final String building = nextRecord[0];
				final String classroomName = nextRecord[1];
				final int normal_capacity = Integer.parseInt(nextRecord[2]);
				final int exam_capacity = Integer.parseInt(nextRecord[3]);
				final int number_characteristics = Integer.parseInt(nextRecord[4]);
				final LinkedList<Boolean> characteristics = new LinkedList<>();

				for (int index = 5; index < nextRecord.length; index++) {
					if (nextRecord[index].equals("X") || nextRecord[index].equals("x")) {
						characteristics.add(true);
					} else {
						characteristics.add(false);
					}
				}
				Classroom classN = new Classroom.Builder().
						building(building).
						classroomName(classroomName).
						normalCapacity(normal_capacity).
						examCapacity(exam_capacity).
						numberCharacteristics(number_characteristics).
						characteristics(characteristics).
						build();
				classrooms.add(classN);
			}
			System.out.println("ClassRoomLoad.main::classRooms = " + classrooms);

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return classrooms;
	}

}
