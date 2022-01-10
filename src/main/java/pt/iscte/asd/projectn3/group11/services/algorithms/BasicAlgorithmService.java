package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.ClassroomService;

import java.util.*;

public class BasicAlgorithmService implements IAlgorithmService {
    private static final Logger LOGGER  = LogManager.getLogger(BasicAlgorithmService.class);
    private double progress;

    private boolean isRunning;
    public BasicAlgorithmService() {
        this.isRunning = false;
        this.progress = 0;
    }

    @Override
    public void execute(List<ClassCourse> classCourses, List<Classroom> classrooms) {
        this.isRunning = true;
        double quanityOfClassCourses = classCourses.size();
        try
        {
            LOGGER.info("BASIC_ALGORITHM::EXECUTE");

            TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = ClassroomService.organizeClassCourseByDate(classCourses);
            TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap = ClassroomService.organizeClassroomByDate(classCoursedateMap, classrooms);

            for (int i = 0; i < quanityOfClassCourses; i++) {
                ClassCourse classCourse = classCourses.get(i);
                final boolean hasClassRoomAllocated = classCourse.hasClassRoomAllocated();
                if (!hasClassRoomAllocated) {
                    ClassroomService.allocate(classCourse, classrooms, classRoomAvailabilityMap, 0.5F);
                }
                this.progress = i/quanityOfClassCourses;
                LOGGER.info("BASIC_ALGORITHM::PROGRESS" + this.progress);

            }
        }
        finally
        {
            this.isRunning = false;
            this.progress = 1;
            LOGGER.info("Finished BASIC_ALGORITHM");
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

}
