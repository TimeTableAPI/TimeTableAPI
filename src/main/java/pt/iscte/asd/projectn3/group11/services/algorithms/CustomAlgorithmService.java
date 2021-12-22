package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.util.Problem;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.MetricCalculator;

import java.util.*;

public class CustomAlgorithmService implements IAlgorithmService {

    private final String algorithmName;
    private final int maxEvaluation;
    private boolean isRunning;

    public CustomAlgorithmService(String algorithmName, int maxEvaluation) {
        this.algorithmName = algorithmName.trim().toUpperCase(Locale.ROOT);
        this.maxEvaluation = maxEvaluation;
        this.isRunning = false;
    }

    @Override
    public void execute(List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        this.isRunning = true;
        try
        {
            LinkedList<ClassCourse> classes = new LinkedList<>(inputClasses);

            //configure and run this experiment
            NondominatedPopulation result = new Executor()
                    .withProblemClass(Problem.class,classes.size(), TimetableEvaluationService.METRICSLIST.size(), classes, classrooms)
                    .withAlgorithm(algorithmName)
                    .withMaxEvaluations(maxEvaluation)
                    .run();

            System.out.println(result);
            //display the results
            System.out.format("Objective1   Objective2   Objective3   Objective4   Objective5   Objective6   Objective7   Objective8%n");
            for (Solution solution : result) {
                System.out.format("%.4f     %.4f     %.4f     %.4f     %.4f     %.4f     %.4f     %.4f%n",
                        solution.getObjective(0),
                        solution.getObjective(1),
                        solution.getObjective(2),
                        solution.getObjective(3),
                        solution.getObjective(4),
                        solution.getObjective(5),
                        solution.getObjective(6),
                        solution.getObjective(7)
                );
            }
            final Solution bestSolution = getBestSolution(result);
            final LinkedList<ClassCourse> bestClassCourses = Problem.solutionToTimetable(bestSolution, inputClasses, classrooms);

            System.out.println(Arrays.toString(bestSolution.getObjectives()));
            System.out.println(TimetableEvaluationService.evaluateTimetable(bestClassCourses,classrooms));
            inputClasses = bestClassCourses;
        }
        finally
        {
            this.isRunning = false;
        }

    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    private static Solution getBestSolution (NondominatedPopulation result){
        List<Double> distancesList = new LinkedList<>();
        List<double[]> listObjectives = new LinkedList<>();
        result.forEach(solution -> {
            listObjectives.add(solution.getObjectives());
            distancesList.add(euclideanMetricDistance(solution.getObjectives(), TimetableEvaluationService.METRICSLIST));
        });
        Double minValue =  Collections.min(distancesList);
        final int bestIndex = distancesList.indexOf(minValue);

        return result.get(bestIndex);
    }

    private static double euclideanMetricDistance(double[] metricResults, List<MetricCalculator> metricCalculatorList){
        double value = 0;
        for(int i = 0 ; i< metricResults.length;i++){
            value += Math.pow(metricResults[i] - metricCalculatorList.get(i).getObjective() ,2);
        }

        return Math.pow(value, 0.5);
    }

}
