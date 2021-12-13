package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByClass;
import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByDate;

/**
 * <p>Metric that calculates the percentage of classes where the students don't have to move mid-Class</p>
 * */
public class RoomMovementsMetric implements MetricCalculator{

	/**
	 * @param classCourseList
	 * @param classroomsList
	 * @return
	 */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, LinkedList<Classroom> classroomsList) {
		final TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> organizedClassCourseByClass =
				organizeClassCourseByClass(classCourseList, organizeClassCourseByDate(classCourseList));

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

		return (float)nClassSameRoom / (float)classMoveCounter ;
	}
}
