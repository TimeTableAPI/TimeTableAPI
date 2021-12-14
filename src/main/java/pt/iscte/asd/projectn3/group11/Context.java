package pt.iscte.asd.projectn3.group11;

import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.List;

public class Context {
    private final List<ClassCourse> classCourses;
    private final List<Classroom> classrooms;
    private final IAlgorithmService algorithm;

    public Context(List<ClassCourse> classCourses, List<Classroom> classrooms, IAlgorithmService algorithm) {
        this.classCourses = classCourses;
        this.classrooms = classrooms;
        this.algorithm = algorithm;
    }

    public void computeSolutionWithAlgorithm()
    {
        algorithm.execute(classCourses, classrooms);
    }

    public List<ClassCourse> getClassCourses() {
        return classCourses;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}
