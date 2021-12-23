package pt.iscte.asd.projectn3.group11.services.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.FileUploadService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ClassCourseLoaderService {


	private ClassCourseLoaderService() {
	}

	/**
	 * Loads a Class csv file from given path.
	 *
	 * @param path path to the classroom csv.
	 * @return List of classrooms
	 */
	public static final List<ClassCourse> load(final String path) {
		List<ClassCourse> classCourses = new LinkedList<>();
		try (
				final Reader reader = Files.newBufferedReader(Paths.get(path));
				final CSVReader csvReader = new CSVReader(reader)
		) {

			extract(csvReader, classCourses);

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return classCourses;
	}

	/**
	 * Loads a Class csv file.
	 *
	 * @param file object containing the csv for the Classes.
	 * @return List of classrooms
	 */
	public static final List<ClassCourse> load(final File file) {
		List<ClassCourse> classCourses = new LinkedList<>();
		try (
				final Reader reader = new FileReader(file);
				final CSVReader csvReader = new CSVReader(reader)
		) {
			extract(csvReader, classCourses);

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return classCourses;
	}

	/**
	 * Loads a Class csv file.
	 *
	 * @param multipartFile object containing the csv for the Classes.
	 * @return List of classrooms
	 */
	public static final List<ClassCourse> load(final MultipartFile multipartFile, boolean toDisk) throws IOException {
		File file;
		if (toDisk) {
			file = new File(FileUploadService.UPLOADED_FILES_LOCATION + multipartFile.getOriginalFilename());
		} else {
			Path temp = Files.createTempFile(multipartFile.getOriginalFilename(), ".csv");
			file = new File(temp.toUri());
		}
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(multipartFile.getBytes());
		}
		return load(file);
	}

	private static void extract(CSVReader csvReader, List<ClassCourse> classCourses) throws IOException, CsvValidationException {
		csvReader.readNext();
		String[] nextRecord;

		while ((nextRecord = csvReader.readNext()) != null) {

			final String course = nextRecord[0];
			final String unit = nextRecord[1];
			final String shift = nextRecord[2];
			final String classOfCourse = nextRecord[3];
			final String studentNum = nextRecord[4];
			final String shiftsWithFreeSpots = nextRecord[5];
			final String shiftsWithMoreThanTheCapacity = nextRecord[6];
			final String weekday = nextRecord[7];
			final String beginningHourString = nextRecord[8];
			final String endHourString = nextRecord[9];
			final String dateString = nextRecord[10];
			final String askedCharacteristics = nextRecord[11];
			final String classroom = nextRecord[12];
			final String capacityString = nextRecord[13];
			final String realCharacteristics = nextRecord[14];

			final int capacity = capacityString.isEmpty() ? -1 : Integer.parseInt(capacityString);
			final TimeShift beginningHour = TimeShift.stringToClassTime(beginningHourString);
			final TimeShift endHour = TimeShift.stringToClassTime(endHourString);
			final Date date = new Date(dateString);

			for (int i = beginningHour.getId(); i < endHour.getId(); i++) {
				TimeShift beginningTime = TimeShift.getById(i);
				TimeShift endTime = TimeShift.getById(i + 1);

				final List<String> realCharacteristicsList = Arrays.asList((realCharacteristics.split(", ")));
				List<Boolean> classroomCharacteristics = new LinkedList<>();
				for (String s : Classroom.CHARACTERISTICS_LIST) {
					classroomCharacteristics.add(realCharacteristicsList.contains(s));
				}

				final String[] classroomStringSplit = classroom.split(",");
				Classroom classroomBuild;
				if (classroomStringSplit.length > 1) {
					classroomBuild = new Classroom.Builder()
							.building(classroomStringSplit[0])
							.classroomName(classroomStringSplit[1])
							.normalCapacity(capacity)
							.examCapacity(capacity)
							.numberCharacteristics(realCharacteristicsList.size())
							.characteristics(classroomCharacteristics)
							.build();
				} else {
					classroomBuild = new Classroom.Builder()
							.classroomName(classroomStringSplit[0])
							.normalCapacity(capacity)
							.examCapacity(capacity)
							.numberCharacteristics(realCharacteristicsList.size())
							.characteristics(classroomCharacteristics)
							.build();
				}

				ClassCourse aclassCourse = new ClassCourse.Builder().
						courses(Arrays.asList(course.split(", "))).
						units(Arrays.asList(unit.split(", "))).
						shift(shift).
						classesOfCourse(Arrays.asList(classOfCourse.split(", "))).
						numberOfStudentsInClass(Integer.parseInt(studentNum)).
						shiftsWithFreeSpots(Integer.parseInt(shiftsWithFreeSpots)).
						shiftsWithMoreThanTheCapacity(Integer.parseInt(shiftsWithMoreThanTheCapacity)).
						weekday(weekday).
						beginningHour(beginningTime).
						endHour(endTime).
						date(date).
						askedCharacteristics(Arrays.asList((askedCharacteristics).split(","))).
						capacity(capacity).
						realCharacteristics(realCharacteristicsList).
						classroom(
								classroomBuild
						).
						build();
				classCourses.add(aclassCourse);
			}
		}
	}

	public static File export(List<ClassCourse> classCourses) throws IOException {
		try {
			Path temp = Files.createTempFile("tempExportedClasses", ".csv");
			File myObj = new File(temp.toUri());
			try (FileWriter writer = new FileWriter(myObj)) {
				writer.write(String.join(",", ClassCourse.HEADER) + "\n");
				for (ClassCourse classCourse : classCourses) {
					writer.write(classCourse.toCSVEntry());
					writer.write("\n");
				}

			}
			return myObj;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
