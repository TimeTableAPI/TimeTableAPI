package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;


import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

/**
 *<p>Interface to Describe and group all the MetricCalculators</p>
 *<p>Has a single method {@link #evaluate(List)} </p>
 *
 */
public interface MetricCalculator {

	/**
	 * <p>Method to analyze a List of classCourse  </p>
	 *
	 * @param classCourseList
	 * @return float value with the score of the implemented metric
	 *
	 * */
	float evaluate(List<ClassCourse> classCourseList);
}

