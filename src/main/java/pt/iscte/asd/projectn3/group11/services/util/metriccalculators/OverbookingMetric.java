package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByDate;


/**
 * <p>Metric that calculates the number of classes booked two or more times in the same timeshift</p>
 * 	<p>The <b>smaller</b> the result the better.</p>
 */
public class OverbookingMetric  implements  MetricCalculator{

	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 * @return a float number representing the quantity of overbooked classrooms
	 */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> organizedclassCourseList = organizeClassCourseByDate(classCourseList);

		TreeMap<Date, EnumMap<TimeShift, LinkedList<Classroom>>> classRoomDateMap = new TreeMap<>();

		for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : organizedclassCourseList.entrySet()) {
			for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
				classRoomDateMap.computeIfAbsent(date.getKey(), k -> new EnumMap<TimeShift, LinkedList<Classroom>>(TimeShift.class));
				classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k -> new LinkedList<Classroom>());

				for (ClassCourse classCourse: hour.getValue()){
					classRoomDateMap.get(date.getKey()).get(hour.getKey()).add(classCourse.getClassroom());
				}
			}
		}
		int dupcounter = 0;
		HashSet<Classroom> uniqueSet = new HashSet<>();
		for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : organizedclassCourseList.entrySet()) {
			for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
				uniqueSet.clear();
				for (Classroom classroom: classRoomDateMap.get(date.getKey()).get(hour.getKey())){
					if(uniqueSet.contains(classroom)){
						dupcounter++;
					}else{
						uniqueSet.add(classroom);
					}
				}
			}
		}
		return dupcounter;
	}
}
