package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.List;

/**
 * <p>Metric to evaluate the capacity of the assigned Classrooms compared to the number of students in each class.</p>
 * <p>The <b>smaller</b> the result the better.</p>
 */
public final class UnderCapacityMetric implements IMetricCalculator {
	private static final float OBJECTIVE = 0f;

	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 *
	 * @return a float number representing the ammount of classes with OUT enough capacity.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		//float totalClassCoursesWithClassroom = 0f;
		float totalClassCoursesNotEnoughCapacity = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				if(classCourse.getNumberOfStudentsInClass() > classCourse.getCapacity()){
					totalClassCoursesNotEnoughCapacity++;
				}
		//		totalClassCoursesWithClassroom ++;
			}
		}
		return totalClassCoursesNotEnoughCapacity;
		//return totalClassCoursesNotEnoughCapacity/totalClassCoursesWithClassroom;
	}
	@Override
	public float getObjective() {
		return OBJECTIVE;
	}
}
