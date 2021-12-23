package pt.iscte.asd.projectn3.group11.services.util.metriccalculators;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;

import java.util.*;

import static pt.iscte.asd.projectn3.group11.services.ClassroomService.organizeClassCourseByClassStudents;

/**
 * <p>Metric to evaluate the quantity of Movements between buildings that the Students have to make throughout the day.</p>
 * 	<p>The <b>smaller</b> the result the better.</p>
 */
public class StudentBuildingMovementsMetric implements MetricCalculator{
	private static float objective = 0f;

	/**
	 * @param classCourseList List<ClassCourse>
	 * @param classroomsList List<Classroom>
	 * @return a float number representing the quantity of Movements between buildings
	 */
	@Override
	public float evaluate(List<ClassCourse> classCourseList, List<Classroom> classroomsList) {
		final Map<Date, HashMap<String, HashSet<ClassCourse>>> organizedClassCourseByClass = organizeClassCourseByClassStudents(classCourseList);

		int classMoveBuildingCounter = 0;


		HashSet<String> buildingsHashSet = new HashSet<>();
		for(Map.Entry<Date, HashMap<String, HashSet<ClassCourse>>> dateEntry:organizedClassCourseByClass.entrySet()){
			for(Map.Entry<String, HashSet<ClassCourse>> StudentsGroupEntry: dateEntry.getValue().entrySet()){
				buildingsHashSet.clear();

				for (ClassCourse classCourse:StudentsGroupEntry.getValue()){
					final Classroom classroom = classCourse.getClassroom();
					if (classroom != null) buildingsHashSet.add(classroom.getBuilding());

				}
				if (buildingsHashSet.size() > 1){
					classMoveBuildingCounter += buildingsHashSet.size();
				}
			}
		}

		return classMoveBuildingCounter ;
	}
	@Override
	public float getObjective() {
		return objective;
	}
}
