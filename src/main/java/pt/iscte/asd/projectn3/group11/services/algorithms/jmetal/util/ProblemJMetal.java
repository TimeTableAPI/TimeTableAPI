package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;


import org.jetbrains.annotations.NotNull;
import org.uma.jmetal.problem.integerproblem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.LogService;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.IMetricCalculator;

import java.util.*;


public class ProblemJMetal extends AbstractIntegerProblem {

    List<ClassCourse> classes;
    List<Classroom> classrooms;

    public ProblemJMetal(
            @NotNull List<IMetricCalculator> metricList,
            @NotNull List<ClassCourse> classes,
            @NotNull List<Classroom> classrooms) {
        this.classes = classes;
        this.classrooms = classrooms;
        setName("Jmetal");
        setNumberOfVariables(classes.size());
        setNumberOfObjectives(metricList.size());
        setNumberOfConstraints(0);
        List<Integer> lowerBounds = new ArrayList<>();
        List<Integer> upperBounds = new ArrayList<>();
        for(int i = 0 ;i<classes.size();i++){
            lowerBounds.add(0);
            upperBounds.add(classes.size());
        }
        setVariableBounds(lowerBounds,upperBounds);
    }

    private static LinkedList<ClassCourse> solutionToTimetable(IntegerSolution solution, List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        LinkedList<ClassCourse> classCourses = new LinkedList<>();
        List<Integer> doubles = solution.variables();
        for(int i = 0; i < doubles.size(); i++) {
            try {
                ClassCourse classCourse = inputClasses.get(i);
                Classroom classroom = classrooms.get(doubles.get(i));
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
    public IntegerSolution evaluate(IntegerSolution integerSolution) {

        final LinkedList<ClassCourse> solutionClassCourses = solutionToTimetable(integerSolution, this.classes, this.classrooms);
        final Hashtable<String, Float> stringFloatHashtable = TimetableEvaluationService.evaluateTimetable(solutionClassCourses, this.classrooms);
        int itr = 0;
        for (IMetricCalculator metricCalculator : TimetableEvaluationService.METRICSLIST) {
            for(Map.Entry<String, Float> entry: stringFloatHashtable.entrySet()){
                if (metricCalculator.getClass().getSimpleName().equals(entry.getKey())){
                    integerSolution.objectives()[itr] = entry.getValue();
                    itr++;
                    break;
                }
            }
        }
        LogService.getInstance().debug(Arrays.toString(integerSolution.objectives()));

        /*
        ArrayList<Double> objectivesDoubleList = new ArrayList<>();
        stringFloatHashtable.values().forEach(aFloat -> objectivesDoubleList.add((double)aFloat));
        double[] arr = objectivesDoubleList.stream().mapToDouble(Double::doubleValue).toArray();
        integerSolution.objectives()[0] = 1;
        */

        return integerSolution;
    }
}
