package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseDate;
import pt.iscte.asd.projectn3.group11.models.util.ClassCourseTime;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");

        TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> classCoursedateMap = initializeClassCourseDateMap(classCourses);
        TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<Classroom>>> classRoomdateMap = initializeClassRoomDateMap(classCoursedateMap);

        for(Map.Entry<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> classCourseOfDay: classCoursedateMap.entrySet())
        {
            System.out.println(classCourseOfDay.getKey() + " -> " + classCourseOfDay.getValue().size());
            for(Map.Entry<ClassCourseTime, List<ClassCourse>> classCourseOfDayOfTime : classCourseOfDay.getValue().entrySet())
            {
                System.out.println(classCourseOfDayOfTime.getKey() + " -> " + classCourseOfDayOfTime.getValue().size());
                for(ClassCourse classCourse : classCourseOfDayOfTime.getValue() ){
                    System.out.println(classCourse);

                }
            }
        }
    }

    private TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> initializeClassCourseDateMap(List<ClassCourse> classCourses) {
        TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> classCoursedateMap = new TreeMap<>();
        for(ClassCourse classCourse: classCourses)
        {
            classCoursedateMap.computeIfAbsent(classCourse.getDate(), k -> new EnumMap<ClassCourseTime, List<ClassCourse>>(ClassCourseTime.class));
            classCoursedateMap.get(classCourse.getDate()).computeIfAbsent(classCourse.getBeginningHour(), k -> new ArrayList<>());
            classCoursedateMap.get(classCourse.getDate()).get(classCourse.getBeginningHour()).add(classCourse);

        }
        return classCoursedateMap;
    }

    private TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<Classroom>>> initializeClassRoomDateMap(TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> classCoursedateMap) {
        TreeMap<ClassCourseDate, EnumMap<ClassCourseTime, List<Classroom>>>classRoomDateMap = new TreeMap<>();

        for (Map.Entry<ClassCourseDate, EnumMap<ClassCourseTime, List<ClassCourse>>> date : classCoursedateMap.entrySet()) {
            for (Map.Entry<ClassCourseTime, List<ClassCourse>> hour : date.getValue().entrySet()) {
                System.out.println();
                classRoomDateMap.computeIfAbsent(date.getKey(), k ->new EnumMap<ClassCourseTime, List<Classroom>>(ClassCourseTime.class));
                classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k-> new ArrayList<Classroom>());
            }

        }
        return classRoomDateMap;
    }
}
