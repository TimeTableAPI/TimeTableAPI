package pt.iscte.asd.projectn3.group11;

import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.List;

/**
 * <h1>Context</h1>
 * <p> The Context class agglomerates the behaviour of a list of Classrooms, corresponding ClassCourses and algorithm</p>
 * <p>
 * @see IAlgorithmService
 * @see ClassCourse
 * @see Classroom
 * </p>
 *
 */
public class Context {
    private final List<ClassCourse> classCourses;
    private final List<Classroom> classrooms;
    private final IAlgorithmService algorithm;
    /**
     *
     * @param classCourses List<{@link ClassCourse}>
     * @param classrooms List<{@link Classroom}>
     * @param algorithm {@link IAlgorithmService}
     */
    public Context(List<ClassCourse> classCourses, List<Classroom> classrooms, IAlgorithmService algorithm) {
        this.classCourses = classCourses;
        this.classrooms = classrooms;
        this.algorithm = algorithm;
    }

    /**
     *<p>Method that modifies the stored classCourses and allocates classrooms to the classcourses that dont have classrooms </p>
     */
    public void computeSolutionWithAlgorithm()
    {
        algorithm.execute(classCourses, classrooms);
    }

    /**
     * @return List<ClassCourse> of ClassCourse
     */
    public List<ClassCourse> getClassCourses() {
        return classCourses;
    }

    /**
     * @return List< Classroom> of Classroom
     */
    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}
