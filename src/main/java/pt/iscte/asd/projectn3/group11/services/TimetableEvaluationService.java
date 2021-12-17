package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.*;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <h1>TimetableEvaluationService</h1>
 * <p>Service to Evaluate a given Timetable (List of {@link Classroom})</p>
 * <p>Computing a list of results for all the different implemented {@link MetricCalculator} stored in METRICSLIST</p>
 * <p>
 * @see MetricCalculator
 * @see ClassCourse
 * @see ClassCourse
 * @see AllocationMetric
 * @see GoodCharacteristicsMetric
 * @see EnoughCapacityMetric
 * @see RoomMovementsMetric
 * @see OverbookingMetric
 * @see UnderbookingMetric
 * @see StudentClassMovementsMetric
 * @see StudentBuildingMovementsMetric
 * </p>
 */
public class TimetableEvaluationService {
	/**
	 * <p>List with all the implemented metrics</p>
	 */
	public static final LinkedList<MetricCalculator> ALLMETRICSLIST = new LinkedList<MetricCalculator>(Arrays.asList(
			new AllocationMetric(),
			new GoodCharacteristicsMetric(),
			new EnoughCapacityMetric(),
			new RoomMovementsMetric(),
			new OverbookingMetric(),
			new UnderbookingMetric(),
			new StudentClassMovementsMetric(),
			new StudentBuildingMovementsMetric()
	));

	/**
	 *<p>Method to evaluate The given Timetable</p>
	 * <p> Uses the deault list with all the metrics {@link TimetableEvaluationService#ALLMETRICSLIST}</p>
	 * <p>
	 * @param timetableList List<ClassCourse> of {@link ClassCourse}
	 * @param classroomsList List<Classroom> of {@link Classroom}
	 * @return Hashtable<String, Float> of {@link String} and {@link Float}
	 *
	 * @see ClassCourse
	 * @see Classroom
	 * </p>
	 */
	public static Hashtable<String, Float> evaluateTimetable(List<ClassCourse> timetableList,
	                                                         List<Classroom> classroomsList){
		return evaluateTimetable(timetableList,classroomsList, ALLMETRICSLIST);

	}
		/**
		 *<p>Method to evaluate The given Timetable</p>
		 * <p>
		 * @param timetableList List<ClassCourse> of {@link ClassCourse}
		 * @param classroomsList List<Classroom> of {@link Classroom}
		 * @param metricsList List<MetricCalculator> of {@link MetricCalculator}
		 * @return Hashtable<String, Float> of {@link String} and {@link Float}
		 *
		 * @see ClassCourse
		 * @see Classroom
		 * </p>
		 */
	public static Hashtable<String, Float> evaluateTimetable(List<ClassCourse> timetableList,
	                                                         List<Classroom> classroomsList,
	                                                         List<MetricCalculator> metricsList){
		List<MetricResult> results = new LinkedList<>();
		Queue<MetricResult> globalQueue = new ArrayBlockingQueue<MetricResult>(metricsList.size());
		List<Thread> threadList = new LinkedList<>();

		for(MetricCalculator metric : metricsList){
			Thread t1 = new Thread(() -> {
				float metricresult = metric.evaluate(timetableList, classroomsList);
				String metricName = metric.getClass().getSimpleName();
				globalQueue.add(new MetricResult(metricName, metricresult));
			}
			);
			threadList.add(t1);
		}

		for (Thread thread : threadList){
			try {
				thread.start();
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Hashtable<String, Float> result = new Hashtable<String, Float>();
		for(MetricResult metricResult : globalQueue){
			result.put(metricResult.metricName,metricResult.result);
		}
		return result;
	}

}
