package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");

        TreeMap<Date, EnumMap<TimeShift, List<ClassCourse>>> classCoursedateMap = initializeClassCourseDateMap(classCourses);
        TreeMap<Date, EnumMap<TimeShift, List<Classroom>>> classRoomdateMap = initializeClassRoomDateMap(classCoursedateMap);

        for(Map.Entry<Date, EnumMap<TimeShift, List<ClassCourse>>> classCourseOfDay: classCoursedateMap.entrySet())
        {
            Date currDate = classCourseOfDay.getKey();
            System.out.println(currDate + " -> " + classCourseOfDay.getValue().size());

            for(Map.Entry<TimeShift, List<ClassCourse>> classCourseOfDayOfTime : classCourseOfDay.getValue().entrySet())
            {
                TimeShift currHour = classCourseOfDayOfTime.getKey();

                System.out.println(currHour + " -> " + classCourseOfDayOfTime.getValue().size());
                for(ClassCourse classCourse : classCourseOfDayOfTime.getValue() ){
                    System.out.println(classCourse.getAskedCharacteristics());

                    ClassroomService.getWithCharacteristic(classrooms,classCourse.getAskedCharacteristics());

                }
            }
        }
    }

    private TreeMap<Date, EnumMap<TimeShift, List<ClassCourse>>> initializeClassCourseDateMap(List<ClassCourse> classCourses) {
        TreeMap<Date, EnumMap<TimeShift, List<ClassCourse>>> classCoursedateMap = new TreeMap<>();
        for(ClassCourse classCourse: classCourses)
        {
            classCoursedateMap.computeIfAbsent(classCourse.getDate(), k -> new EnumMap<TimeShift, List<ClassCourse>>(TimeShift.class));
            classCoursedateMap.get(classCourse.getDate()).computeIfAbsent(classCourse.getBeginningHour(), k -> new ArrayList<>());
            classCoursedateMap.get(classCourse.getDate()).get(classCourse.getBeginningHour()).add(classCourse);

        }
        return classCoursedateMap;
    }

    private TreeMap<Date, EnumMap<TimeShift, List<Classroom>>> initializeClassRoomDateMap(TreeMap<Date, EnumMap<TimeShift, List<ClassCourse>>> classCoursedateMap) {
        TreeMap<Date, EnumMap<TimeShift, List<Classroom>>>classRoomDateMap = new TreeMap<>();

        for (Map.Entry<Date, EnumMap<TimeShift, List<ClassCourse>>> date : classCoursedateMap.entrySet()) {
            for (Map.Entry<TimeShift, List<ClassCourse>> hour : date.getValue().entrySet()) {
                System.out.println();
                classRoomDateMap.computeIfAbsent(date.getKey(), k ->new EnumMap<TimeShift, List<Classroom>>(TimeShift.class));
                classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k-> new ArrayList<Classroom>());
            }

        }
        return classRoomDateMap;
    }
}
