package pt.iscte.asd.projectn3.group11.services.algorithms.util;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Problem extends AbstractProblem {

    private final List<ClassCourse> classCourses;
    private final List<Classroom> classrooms;

    public Problem(int numberOfVariables, int numberOfObjectives, List<ClassCourse> classes, List<Classroom> classrooms) {
        super(numberOfVariables, numberOfObjectives);
        this.classCourses = classes;
        this.classrooms = classrooms;
    }

    @Override
    public void evaluate(Solution solution) {
        double[] doubles = EncodingUtils.getReal(solution);
        double[] metricNumber = new double[numberOfObjectives];

        LinkedList<ClassCourse> classCourses = new LinkedList<>();
        for(int i = 0; i < doubles.length; i++)
        {
            try
            {
                ClassCourse classCourse = this.classCourses.get(i);
                Classroom classroom = this.classrooms.get((int)doubles[i]);
                classCourse.setClassroom(classroom);
                classCourses.add(classCourse);
            }catch(Exception e) {
                e.printStackTrace();
                ClassCourse classCourse = classCourses.get(i);
                classCourses.add(classCourse);
            }
        }

        Hashtable<String, Float> stringFloatHashtable = TimetableEvaluationService.evaluateTimetable(classCourses, this.classrooms);

        int itr = 0;
        for(Map.Entry<String, Float> entry: stringFloatHashtable.entrySet())
        {
            metricNumber[itr] = entry.getValue();
            itr++;
        }

        solution.setObjectives(metricNumber);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(),
                getNumberOfObjectives());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            solution.setVariable(i, new RealVariable(0.0, this.classrooms.size()));
        }

        return solution;
    }
}
