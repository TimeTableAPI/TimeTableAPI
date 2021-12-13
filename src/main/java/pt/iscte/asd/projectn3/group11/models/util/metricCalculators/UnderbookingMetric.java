package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByDate;

/**
 * <p>Metric that calculates the number of classes with timeshifts that aren't being used</p>
 * <p>The <b>smaller</b> the result the better.</p>
 */
public class UnderbookingMetric implements MetricCalculator{
	@Override
	public float evaluate(List<ClassCourse> classCourseList, LinkedList<Classroom> classroomsList) {
		TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> organizedclassCourseList = organizeClassCourseByDate(classCourseList);

		TreeMap<Date, EnumMap<TimeShift, LinkedList<Classroom>>> classRoomDateMap = new TreeMap<>();

		for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : organizedclassCourseList.entrySet()) {
			for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
				classRoomDateMap.computeIfAbsent(date.getKey(), k -> new EnumMap<TimeShift, LinkedList<Classroom>>(TimeShift.class));
				classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k -> new LinkedList<Classroom>());
				classRoomDateMap.get(date.getKey()).get(hour.getKey()).addAll(classroomsList);

				for (ClassCourse classCourse: hour.getValue()){
					classRoomDateMap.get(date.getKey()).get(hour.getKey()).remove(classCourse.getClassroom());
				}
			}
		}
		int freecounter = 0;
		for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : organizedclassCourseList.entrySet()) {
			for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
				freecounter+= hour.getValue().size();
			}
		}
		return freecounter;
	}
}
