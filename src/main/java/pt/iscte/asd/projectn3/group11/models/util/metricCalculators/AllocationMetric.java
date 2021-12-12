package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

public class AllocationMetric implements MetricCalculator{

	/**
	 * <p>Metric to evaluate the quantity of classes with assigned Classrooms.</p>
	 * <p>The <b>bigger</b> the result the better.</p>
	 *
	 * @return a float value between 0 and 1, which represents the percentage of classes with assigned classroom from the algorithm.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList) {
		float counter = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				counter ++;
			}
		}
		return counter / classCourseList.size();
	}
}