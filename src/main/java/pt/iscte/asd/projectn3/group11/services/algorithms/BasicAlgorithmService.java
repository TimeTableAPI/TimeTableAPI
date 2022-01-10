package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {

    private boolean isRunning;

    public BasicAlgorithmService() {
        this.isRunning = false;
    }

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        this.isRunning = true;
        try
        {
            SortedMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = ClassroomService.organizeClassCourseByDate(classCourses);
            SortedMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = ClassroomService.organizeClassroomByDate(classCoursedateMap, classrooms);

            for( ClassCourse classCourse : classCourses){
                final boolean hasClassRoomAllocated = classCourse.hasClassRoomAllocated();
                if(!hasClassRoomAllocated){
                    ClassroomService.allocate(classCourse, classRoomAvailabilityMap, 0.5F);
                }
            }
        }
        finally
        {
            this.isRunning = false;
        }
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

}
