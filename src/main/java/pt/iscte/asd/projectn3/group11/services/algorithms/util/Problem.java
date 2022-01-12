package pt.iscte.asd.projectn3.group11.services.algorithms.util;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.MetricCalculator;

import java.util.*;

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
        double[] metricNumber = new double[numberOfObjectives];

        LinkedList<ClassCourse> solutionClassCourses = solutionToTimetable(solution, classCourses, classrooms);

        Hashtable<String, Float> stringFloatHashtable = TimetableEvaluationService.evaluateTimetable(solutionClassCourses, this.classrooms);

        int itr = 0;

        for (MetricCalculator me : TimetableEvaluationService.METRICSLIST) {
            for(Map.Entry<String, Float> entry: stringFloatHashtable.entrySet()){
                if (me.getClass().getSimpleName().equals(entry.getKey())){
                    metricNumber[itr] = entry.getValue();
                    itr++;
                    break;
                }
            }
        }
        ArrayList<Double> objectivesDoubleList = new ArrayList<>();
        stringFloatHashtable.values().forEach(aFloat -> objectivesDoubleList.add((double)aFloat));
        double[] arr = objectivesDoubleList.stream().mapToDouble(Double::doubleValue).toArray();
        solution.setObjectives(arr);
    }

    public static  LinkedList<ClassCourse> solutionToTimetable(Solution solution, List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        LinkedList<ClassCourse> classCourses = new LinkedList<>();
        double[] doubles = EncodingUtils.getReal(solution);
        for(int i = 0; i < doubles.length; i++)
        {
            try
            {
                ClassCourse classCourse = inputClasses.get(i);
                Classroom classroom = classrooms.get((int)doubles[i]);
                classCourse.setClassroom(classroom);
                classCourses.add(classCourse);
            }catch(Exception e) {
                e.printStackTrace();
                ClassCourse classCourse = classCourses.get(i);
                classCourses.add(classCourse);
            }
        }
        return classCourses;
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(),
                getNumberOfObjectives());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            solution.setVariable(i, new RealVariable(0.0, this.classrooms.size()-1));
        }

        return solution;
    }
}
