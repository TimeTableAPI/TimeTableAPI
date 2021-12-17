package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByClass;

/**
 * <p>Metric that calculates the number of classes where the students have to move mid-Class</p>
 * 	<p>The <b>smaller</b> the result the better.</p>
 * */
public class RoomMovementsMetric implements MetricCalculator{
	private static float objective = 0f;

	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
 	 * @return a float number representing the number of classes where the students have to move mid-Class
	 */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		final TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> organizedClassCourseByClass =
				organizeClassCourseByClass(classCourseList);

		int classMoveCounter = 0;
		int nClassSameRoom = 0;

		LinkedList<Classroom> uniqueClassroomsList;

		for(Map.Entry<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> dateEntry:organizedClassCourseByClass.entrySet()){
			for(Map.Entry<ClassCourse, HashSet<ClassCourse>>  classCourseEntry: dateEntry.getValue().entrySet()){

				uniqueClassroomsList = new LinkedList<>();
				for (ClassCourse innerClassCourse:classCourseEntry.getValue()){
					if( !uniqueClassroomsList.contains(innerClassCourse.getClassroom())){
						uniqueClassroomsList.add(innerClassCourse.getClassroom());
					}
				}
				if (uniqueClassroomsList.size() == 1){
					nClassSameRoom++;
				}else{
					classMoveCounter++;
				}
			}
		}

		return (float)classMoveCounter ;
	}

	@Override
	public float getObjective() {
		return objective;
	}
}
