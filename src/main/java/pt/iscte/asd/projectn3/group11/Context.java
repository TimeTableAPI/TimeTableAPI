package pt.iscte.asd.projectn3.group11;

import org.apache.commons.math3.exception.NullArgumentException;
import pt.iscte.asd.projectn3.group11.models.MetricResult;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.AlgorithmService;
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
    private List<ClassCourse> classCourses;
    private List<Classroom> classrooms;
    private IAlgorithmService algorithm;

    private List<MetricResult> metricResults;
    /**
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

    public Context(Builder builder)
    {
        this.classCourses = builder.classCourses;
        this.classrooms = builder.classrooms;
        this.algorithm = builder.algorithm;
    }

    /**
     *<p>Method that modifies the stored classCourses and runs the algorithm to allocate classrooms to the classcourses that dont have classrooms </p>
     */
    public void computeSolutionWithAlgorithm()  throws NullArgumentException , IllegalStateException
    {
        if(algorithm == null ||classCourses == null ||classrooms == null){
            throw new NullArgumentException();
        }
        if (classCourses.isEmpty() || classrooms.isEmpty()){
            throw new IllegalStateException();
        }
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

    /**
     * Gets the current algorithm
     * @return {@link IAlgorithmService}
     */
    public IAlgorithmService getAlgorithm() {
        return algorithm;
    }

    /**
     * Stops the current algorithm
     */
    public void stopAlgorithm() {
        algorithm.stop();
    }



    /**
     * Sets classCourses
     * @param classCourses
     */
    public void setClassCourses(List<ClassCourse> classCourses) {
        this.classCourses = classCourses;
    }

    /**
     * Sets classrooms
     * @param classrooms
     */
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    /**
     * Stops the current algorithm and changes it to a new one
     * */
    public void changeAlgorithm(String newAlgoName) {
        this.algorithm.stop();
        this.algorithm = AlgorithmService.generateAlgorithm(newAlgoName);
    }

    //region BUILDER

    public static class Builder {
        private List<ClassCourse> classCourses;
        private List<Classroom> classrooms;
        private IAlgorithmService algorithm;

        public Builder classCourses(final List<ClassCourse> classCourses)
        {
            this.classCourses = classCourses;
            return this;
        }

        public Builder classrooms(final List<Classroom> classrooms)
        {
            this.classrooms = classrooms;
            return this;
        }

        public Builder algorithm(final IAlgorithmService algorithm)
        {
            this.algorithm = algorithm;
            return this;
        }

        public Context build()
        {
            return new Context(this);
        }
    }

    //endregion
}
