package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.Collection;
import java.util.List;

public class BadClassroomMetric implements MetricCalculator{

	/**
	 * <p>Metric to evaluate the quality of the assigned Classrooms compared to the characteristics the class needed.</p>
	 *
	 * @return a float between 0 and 1 representing the percentage of characteristics that were fulfilled.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList) {
		float totalRealCharacteristics = 0f;
		float totalAskedCharacteristics = 0f;
		for(ClassCourse classCourse : classCourseList){
			if(classCourse.getClassroom() != null){
				totalRealCharacteristics += containsCounter(classCourse.getRealCharacteristics(), classCourse.getAskedCharacteristics());
				totalAskedCharacteristics += classCourse.getAskedCharacteristics().size();
			}
		}
		return totalRealCharacteristics / totalAskedCharacteristics;
	}

	/**
	 * <p>Auxiliary method to calculate how many items from iterable1 are contained in iterable 2</p>
	 *
	 * */
	private <T> int containsCounter(Collection<T> iterable1,Collection<T> iterable2){
		int counter = 0;
		for(T item: iterable1){
			if(iterable2.contains(item)){
				counter++;
			}
		}
		return counter;
	}

}

