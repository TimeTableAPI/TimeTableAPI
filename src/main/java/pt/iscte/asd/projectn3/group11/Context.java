package pt.iscte.asd.projectn3.group11;

import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.*;

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

    private List<MetricResult> metricResults;
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
        this.metricResults = new LinkedList<>();
    }

    /**
     *<p>Method that modifies the stored classCourses and allocates classrooms to the classcourses that dont have classrooms </p>
     */
    public void computeSolutionWithAlgorithm()
    {
        algorithm.execute(classCourses, classrooms);
    }

    /**
     * Calculates metrics of current context.
     */
    public void calculateMetrics()
    {
        final Hashtable<String, Float> stringFloatHashtable =  TimetableEvaluationService.evaluateTimetable(classCourses, classrooms);
        this.metricResults = new LinkedList<>();
        for(Map.Entry<String,Float> resultEntry : stringFloatHashtable.entrySet()){
            metricResults.add(new MetricResult(resultEntry.getKey(),resultEntry.getValue()));
        }
    }

    /**
     * @return List<MetricResult> of metrics.
     */
    public List<MetricResult> getMetricResults()
    {
        return metricResults;
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
