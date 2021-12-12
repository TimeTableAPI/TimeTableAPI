package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

public class CapacityMetric implements MetricCalculator {
	/**
	 * <p>Metric to evaluate the capacity of the assigned Classrooms compared to the number of students in each class.</p>
	 *
	 * @return a float between 0 and 1 representing the percentage of classes with enough capacity.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList) {
		float totalCapacity = 0f;
		float totalStudents = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				final int classCapacity = classCourse.getCapacity();
				final int numberOfStudentsInClass = classCourse.getNumberOfStudentsInClass();
				if (classCapacity >0) totalCapacity += classCapacity;
				if (numberOfStudentsInClass >0) totalStudents += numberOfStudentsInClass;
			}
		}
		return totalStudents/totalCapacity ;
	}
}
