package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

public class EnoughCapacityMetric implements MetricCalculator {

	/**
	 * <p>Metric to evaluate the capacity of the assigned Classrooms compared to the number of students in each class.</p>
	 * <p>The <b>bigger</b> the result the better.</p>
	 *
	 * @return a float between 0 and 1 representing the percentage of classes with enough capacity.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, LinkedList<Classroom> classroomsList) {
		float totalClassCoursesWithClassroom = 0f;
		float totalClassCoursesWithEnoughCapacity = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				if(classCourse.getNumberOfStudentsInClass() > classCourse.getCapacity()){
					totalClassCoursesWithEnoughCapacity++;
				}
				totalClassCoursesWithClassroom ++;
			}
		}
		return totalClassCoursesWithEnoughCapacity/totalClassCoursesWithClassroom;
	}
}