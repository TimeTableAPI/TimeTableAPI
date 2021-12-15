package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByClassStudents;

/**
 * <p>Metric to evaluate the quantity of Movements between classrooms that the Students have to make throughout the day.</p>
 * 	<p>The <b>smaller</b> the result the better.</p>
 */
public class StudentClassMovementsMetric implements MetricCalculator{
	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 * @return a float number representing the quantity of Movements between classrooms
	 */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		final TreeMap<Date, HashMap<String, HashSet<ClassCourse>>> organizedClassCourseByClass = organizeClassCourseByClassStudents(classCourseList);

		int classMoveCounter = 0;

		LinkedList<Classroom> uniqueClassroomsList;
		HashSet<Classroom> classroomHashSet = new HashSet<>();
		for(Map.Entry<Date, HashMap<String, HashSet<ClassCourse>>> dateEntry:organizedClassCourseByClass.entrySet()){
			for(Map.Entry<String, HashSet<ClassCourse>> StudentsGroupEntry: dateEntry.getValue().entrySet()){
				classroomHashSet.clear();

				for (ClassCourse classCourse:StudentsGroupEntry.getValue()){
					final Classroom classroom = classCourse.getClassroom();
					if (classroom != null) classroomHashSet.add(classroom);

				}
				if (classroomHashSet.size() > 1){
					classMoveCounter += classroomHashSet.size();
				}
			}
		}

		return (float)classMoveCounter ;
	}
}
