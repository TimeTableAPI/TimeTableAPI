package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.MetricResult;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.*;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TimetableEvaluationService {

	public static final LinkedList<MetricCalculator> METRICSLIST = new LinkedList<MetricCalculator>(Arrays.asList(
			new AllocationMetric(),
			new GoodCharacteristicsMetric(),
			new EnoughCapacityMetric(),
			new RoomMovementsMetric(),
			new OverbookingMetric(),
			new UnderbookingMetric(),
			new StudentClassMovementsMetric(),
			new StudentBuildingMovementsMetric()
	));

	public static Hashtable<String, Float> evaluateTimetable(List<ClassCourse> classCourseList, List<Classroom> classroomsList){
		List<MetricResult> results = new LinkedList<>();
		Queue<MetricResult> globalQueue = new ArrayBlockingQueue<MetricResult>(METRICSLIST.size());
		List<Thread> threadList = new LinkedList<>();

		for(MetricCalculator metric : METRICSLIST){
			Thread t1 = new Thread(() -> {
				float metricresult = metric.evaluate(classCourseList, classroomsList);
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
