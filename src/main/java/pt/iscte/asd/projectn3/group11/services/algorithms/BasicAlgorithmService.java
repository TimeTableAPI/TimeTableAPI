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

        TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = ClassroomService.organizeClassCourseByDate(classCourses);
        TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = ClassroomService.organizeClassroomByDate(classCoursedateMap);

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

    }

    private boolean checkClassRoomAvailability(Classroom classRoom, Date date, TimeShift timeShift, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap) {
        return !classRoomAvailabilityMap.get(date).get(timeShift).contains(classRoom);
    }

}
