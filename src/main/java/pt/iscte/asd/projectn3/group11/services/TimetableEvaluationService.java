package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.MetricResult;
import pt.iscte.asd.projectn3.group11.models.util.metricCalculators.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TimetableEvaluationService {

	public static final LinkedList<MetricCalculator> METRICSLIST = new LinkedList<MetricCalculator>(Arrays.asList(
			new AllocationMetric(),
			new GoodClassroomMetric(),
			new EnoughCapacityMetric(),
			new RoomMovementsMetric(),
			new OverbookingMetric(),
			new UnderbookingMetric()
	));

	public static List<MetricResult> evaluateTimetable(List<ClassCourse> classCourseList, LinkedList<Classroom> classroomsList){
		List<MetricResult> results = new LinkedList<>();
		Queue<MetricResult> globalQueue = new ConcurrentLinkedQueue<MetricResult>();
		List<Thread> threadList = new LinkedList<>();

		for(MetricCalculator metric : METRICSLIST){
			Thread t1 = new Thread(() ->
					globalQueue.add(new MetricResult(
							metric.getClass().getSimpleName(),
							metric.evaluate(classCourseList,classroomsList)
							)
					)
			);
			t1.start();
			threadList.add(t1);
		}

		for (Thread thread : threadList){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return new LinkedList<>(globalQueue);
	}

}
