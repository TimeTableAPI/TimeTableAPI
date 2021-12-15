package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {
//TODO
    /**
     *
     * @param classCourses
     * @param classrooms
     */
    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");

        TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = ClassroomService.organizeClassCourseByDate(classCourses);
        TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = ClassroomService.organizeClassroomByDate(classCoursedateMap, classrooms);

        for( ClassCourse classCourse : classCourses){
            final boolean hasClassRoomAllocated = classCourse.hasClassRoomAllocated();
            if(!hasClassRoomAllocated){
                ClassroomService.allocate(classCourse, classrooms, classRoomAvailabilityMap, 0.5F);
            }
        }
    }
//TODO
    /**
     *
     * @param classCourses
     * @return
     */
    private TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> initializeClassCourseDateMap(List<ClassCourse> classCourses) {
        TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = new TreeMap<>();
        for (ClassCourse classCourse : classCourses) {
            classCoursedateMap.computeIfAbsent(classCourse.getDate(), k -> new EnumMap<TimeShift, HashSet<ClassCourse>>(TimeShift.class));
            classCoursedateMap.get(classCourse.getDate()).computeIfAbsent(classCourse.getBeginningHour(), k -> new HashSet<ClassCourse>());
            classCoursedateMap.get(classCourse.getDate()).get(classCourse.getBeginningHour()).add(classCourse);
        }
        return classCoursedateMap;
    }
//TODO
    /**
     *
     * @param classCoursedateMap
     * @return
     */
    private TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> initializeClassRoomDateMap(TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap) {
        TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomDateMap = new TreeMap<>();

        for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : classCoursedateMap.entrySet()) {
            for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
                System.out.println();
                classRoomDateMap.computeIfAbsent(date.getKey(), k -> new EnumMap<TimeShift, HashSet<Classroom>>(TimeShift.class));
                classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k -> new HashSet<Classroom>());
            }

        }
        return classRoomDateMap;
    }
}
