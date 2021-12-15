package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.List;

/**
 * <p>Metric to evaluate the quantity of classes with assigned Classrooms.</p>
 */
public class AllocationMetric implements MetricCalculator{

	/**
	 * <p> The <b>bigger</b> the result the better.</p>
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 *
	 * @return a float value between 0 and 1, which represents the percentage of classes with assigned classroom from the algorithm.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		float counter = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				counter ++;
			}
		}
		return counter / classCourseList.size();
	}
}
