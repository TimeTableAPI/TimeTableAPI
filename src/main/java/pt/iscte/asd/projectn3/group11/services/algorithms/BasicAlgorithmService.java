package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.Operations;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");

        TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = initializeClassCourseDateMap(classCourses);
        TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = initializeClassRoomDateMap(classCoursedateMap);

        for( ClassCourse classCourse : classCourses){
            ClassroomService.allocate(classCourse, classrooms ,classRoomAvailabilityMap, 0.5F);

        }
    }

    /*
    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        System.out.println("BASIC_ALGORITHM::EXECUTE");

        TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = initializeClassCourseDateMap(classCourses);
        TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = initializeClassRoomDateMap(classCoursedateMap);

        for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCourseOfDay : classCoursedateMap.entrySet()) {
            Date currDate = classCourseOfDay.getKey();
            //System.out.println(currDate + " -> " + classCourseOfDay.getValue().size());

            for (Map.Entry<TimeShift, HashSet<ClassCourse>> classCourseOfDayOfTime : classCourseOfDay.getValue().entrySet()) {
                TimeShift currHour = classCourseOfDayOfTime.getKey();

                //System.out.println(currHour + " -> " + classCourseOfDayOfTime.getValue().size());
                for (ClassCourse classCourse : classCourseOfDayOfTime.getValue()) {
                    //System.out.println(classCourse.getAskedCharacteristics());

                    //List<Classroom> suitableClassRooms = ClassroomService.getWithCapacity(classrooms, classCourse.getCapacity());
                    ClassroomService.RequestInformation requestInformation = new ClassroomService.RequestInformation.Builder()
                            .capacity(classCourse.getCapacity())
                            .characteristics(classCourse.getAskedCharacteristics())
                            .build();

                    List<Classroom> suitableClassRooms = ClassroomService.get(classrooms, requestInformation, Operations.OR);


                    for (Classroom classroom : suitableClassRooms) {
                        if (checkClassRoomAvailability(classroom, currDate, currHour, classRoomAvailabilityMap)) {
                            classRoomAvailabilityMap.get(currDate).get(currHour).add(classroom);
                            classCourse.setClassroom(classroom);
                            break;
                        }
                    }
                }
            }
        }

    }*/
/*
    private static boolean checkClassRoomAvailability(Classroom classRoom, Date date, TimeShift timeShift, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap) {
        return !classRoomAvailabilityMap.get(date).get(timeShift).contains(classRoom);
    }
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
