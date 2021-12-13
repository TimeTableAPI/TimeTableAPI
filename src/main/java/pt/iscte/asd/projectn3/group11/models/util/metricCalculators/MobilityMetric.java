package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByClass;
import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByDate;

/**
 * <p>Evaluator to evaluate the Mobility of the timetable</p>
 * <p>What mobility means is if there is a class where the students need to change classroom in the middle</p>
 * <p>So the returned value from the {@link #evaluate(List)} is a percentage of classes where you dont move rooms, that way the highes the better </p>
 * */
public class MobilityMetric implements MetricCalculator{

	@Override
	public float evaluate(List<ClassCourse> classCourseList) {
		final TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> organizedClassCourseByClass =
				organizeClassCourseByClass(classCourseList, organizeClassCourseByDate(classCourseList));

		int classMoveCounter = 0;
		int nClassSameRoom = 0;

		int uniqueClassRooms = 0;
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
