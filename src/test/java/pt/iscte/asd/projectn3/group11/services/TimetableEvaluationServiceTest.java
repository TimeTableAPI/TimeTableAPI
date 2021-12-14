package pt.iscte.asd.projectn3.group11.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.iscte.asd.projectn3.group11.loaders.ClassCourseLoader;
import pt.iscte.asd.projectn3.group11.loaders.ClassroomLoader;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.MetricResult;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.*;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TimetableEvaluationServiceTest {

	private static final String SAMPLE_CSV_FILE_CLASS_PATH = "./src/test/resources/classRoomTest.csv";
	private static final String SAMPLE_CSV_FILE_CLASS_FILLED_PATH = "src/test/resources/classTest-Filled.csv";

	private LinkedList<Classroom> classroomsTestList;
	private LinkedList<ClassCourse> classCoursesTestList;
	private LinkedList<MetricResult> metricResultList = new LinkedList<>();

	@BeforeEach
	void setUp()
	{
		classroomsTestList = ClassroomLoader.load(SAMPLE_CSV_FILE_CLASS_PATH);
		classCoursesTestList = ClassCourseLoader.load(SAMPLE_CSV_FILE_CLASS_FILLED_PATH);
		metricResultList.add(new MetricResult(AllocationMetric.class.getSimpleName(),            1.0f));
		metricResultList.add(new MetricResult(GoodCharacteristicsMetric.class.getSimpleName(),   0.266667f));
		metricResultList.add(new MetricResult(EnoughCapacityMetric.class.getSimpleName(),        0.8f));
		metricResultList.add(new MetricResult(RoomMovementsMetric.class.getSimpleName(),         1f));
		metricResultList.add(new MetricResult(OverbookingMetric.class.getSimpleName(),           0f));
		metricResultList.add(new MetricResult(UnderbookingMetric.class.getSimpleName(),          45f));
		metricResultList.add(new MetricResult(StudentClassMovementsMetric.class.getSimpleName(), 1f));
	}

	@Test
	void evaluateTimetable() {
		final List<MetricResult> metricResultListTimetable = TimetableEvaluationService.evaluateTimetable(classCoursesTestList, classroomsTestList);
		DecimalFormat df_obj = new DecimalFormat("#.###");
		for (MetricResult metricResult : metricResultList ){
			for (MetricResult s : metricResultListTimetable){
				if(s.metricName.equals(metricResult.metricName)){

					assertEquals(
							df_obj.format(metricResult.result) ,
							df_obj.format(s.result)
					);
				}
			}
		}

	}
}