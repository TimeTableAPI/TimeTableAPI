package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.AllocationMetric;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.CapacityMetric;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.CharacteristicsMetric;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.MetricCalculator;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class TimetableEvaluationService {

	public static final LinkedList<MetricCalculator> METRICSLIST = new LinkedList<MetricCalculator>(Arrays.asList(
			new CharacteristicsMetric(),
			new AllocationMetric(),
			new CapacityMetric()
	));

	public static Hashtable<String, Float> evaluateTimetable(List<ClassCourse> classCourseList){
		Hashtable<String, Float> results = new Hashtable<>();
		for(MetricCalculator metric : METRICSLIST){
			results.put(metric.getClass().getSimpleName(),metric.evaluate(classCourseList));
		}
		return results;
	}

}
