package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.MetricResult;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TimetableEvaluationService {

	public static final LinkedList<MetricCalculator> METRICSLIST = new LinkedList<MetricCalculator>(Arrays.asList(
			new AllocationMetric(),
			new BadClassroomMetric(),
			new NotEnoughCapacityMetric(),
			new RoomMovementsMetric(),
			new OverbookingMetric(),
			new UnderbookingMetric()
	));

	public static List<MetricResult> evaluateTimetable(List<ClassCourse> classCourseList){
		List<MetricResult> results = new LinkedList<>();
		for(MetricCalculator metric : METRICSLIST){
			results.add(new MetricResult(
					metric.getClass().getSimpleName(),
					metric.evaluate(classCourseList)
			));
		}
		return results;
	}

}
