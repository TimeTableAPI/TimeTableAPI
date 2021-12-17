package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;


import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.List;

/**
 *<p>Interface to Describe and group all the MetricCalculators</p>
 *<p>Has a single method {@link #evaluate(List, List)} </p>
 *
 */
public abstract class MetricCalculator {

	/**
	 * <p>Method to analyze a List of classCourse  </p>
	 *
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 * @return float value with the score of the implemented metric
	 *
	 * */
	public abstract float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList);

	//TODO
	/**
	 *
	 * @return
	 */
	public abstract float getObjective();
}

