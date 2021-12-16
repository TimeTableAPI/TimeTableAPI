package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.Collection;
import java.util.List;

/**
 * <p>Metric that calculates the percentage of Characteristics fulfilled</p>
 * <p>The <b>bigger</b> the result the better.</p>
 */
public class GoodCharacteristicsMetric extends MetricCalculator{
	private static float objective =1f;
	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 *
	 * @return a float between 0 and 1 representing the percentage of characteristics that were fulfilled.
	 * */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
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

	@Override
	float getObjective() {
		return objective;
	}

	/**
	 * <p>Auxiliary method to calculate how many items from iterable1 are contained in iterable 2</p>
	 * @param iterable1 Collection<T>
	 * @param iterable2 Collection<T>
	 * @param <T> object abstraction
	 * @return int value representing the counter of items from iterable 1 contained in iterable2
	 */
	private <T> int containsCounter(Collection<T> iterable1, Collection<T> iterable2){
		int counter = 0;
		for(T item: iterable1){
			if(iterable2.contains(item)){
				counter++;
			}
		}
		return counter;
	}

}

