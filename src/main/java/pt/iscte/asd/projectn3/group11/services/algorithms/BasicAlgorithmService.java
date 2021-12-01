package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseDate;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAlgorithmService implements IAlgorithmService {

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");
        HashMap<ClassCourseDate, List<ClassCourse>> dateMap = new HashMap<>();
        for(ClassCourse classCourse: classCourses)
        {
            dateMap.computeIfAbsent(classCourse.getDate(), k -> new ArrayList<>());
            dateMap.get(classCourse.getDate()).add(classCourse);
        }

        for(Map.Entry<ClassCourseDate, List<ClassCourse>> set: dateMap.entrySet())
        {
            for(ClassCourseTime classCourseTime : ClassCourseTime.values())
            {

            }
            System.out.println(set.getKey() + " -> " + set.getValue().size());
        }


    }
}
