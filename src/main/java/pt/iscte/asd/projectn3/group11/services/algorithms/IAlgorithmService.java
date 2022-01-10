package pt.iscte.asd.projectn3.group11.services.algorithms;

import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

/**
 *  Interface to encapsulate the behaviour of all algorithms used in the project.
 */
public interface IAlgorithmService {
    /**
     * Executes the algorithm.
     * @param classes
     * @param classrooms
     */
    void execute(List<ClassCourse> classes, List<Classroom> classrooms);

    /**
     * Returns if algorithm is running.
     * @return Algorithm State
     */
    boolean isRunning();

    double getProgress();
}
