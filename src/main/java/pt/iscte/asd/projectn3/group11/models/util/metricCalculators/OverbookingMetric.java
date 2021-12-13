package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;


/**
 * <p>Metric that calculates the number of classes booked two or more times in the same timeshift</p>
 * 	<p>The <b>smaller</b> the result the better.</p>
 */
public class OverbookingMetric  implements  MetricCalculator{


	@Override
	public float evaluate(List<ClassCourse> classCourseList) {

		return 0;
	}
}
