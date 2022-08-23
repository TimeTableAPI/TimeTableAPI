package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;
import pt.iscte.asd.projectn3.group11.services.LogService;

import java.util.*;

public final class BasicAlgorithmService implements IAlgorithmService {

    private double progress;
    private final String name;

    private boolean isRunning;
    private boolean canRun;

    public BasicAlgorithmService() {
        this.isRunning = false;
        this.progress = 0;
        this.name = "Basic";
        this.canRun = true;
    }

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        this.isRunning = true;
        double quanityOfClassCourses = classCourses.size();
        try
        {
            LogService.getInstance().info("BASIC_ALGORITHM::EXECUTE");

            TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = ClassroomService.organizeClassCourseByDate(classCourses);
            TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = ClassroomService.organizeClassroomByDate(classCoursedateMap, classrooms);

            for (int i = 0; i < quanityOfClassCourses && this.canRun; i++) {
                ClassCourse classCourse = classCourses.get(i);
                final boolean hasClassRoomAllocated = classCourse.hasClassRoomAllocated();
                if (!hasClassRoomAllocated) {
                    ClassroomService.allocate(classCourse, classrooms, classRoomAvailabilityMap, 0.5F);
                }
                this.progress = i/quanityOfClassCourses;
                LogService.getInstance().info("BASIC_ALGORITHM::PROGRESS" + this.progress);

            }
        }
        finally
        {
            this.isRunning = false;
            if(this.canRun) {
                this.progress = 1;
            }
            LogService.getInstance().info("Finished BASIC_ALGORITHM");
        }
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public double getProgress() {
        return this.progress;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void stop() {
        this.canRun = false;

    }

}
