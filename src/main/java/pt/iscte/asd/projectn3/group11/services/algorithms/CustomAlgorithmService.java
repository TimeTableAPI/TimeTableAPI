package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.apache.logging.log4j.LogManager;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.apache.logging.log4j.Logger;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.util.Problem;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.MetricCalculator;

import java.util.*;

public class CustomAlgorithmService implements IAlgorithmService {
    private static final Logger LOGGER  = LogManager.getLogger(CustomAlgorithmService.class);
    private final String algorithmName;
    private final int maxEvaluation;
    private boolean isRunning;
    private double progress;
    private Executor executor;


    private final CustomProgressListener customProgressListener;
    public CustomAlgorithmService(String algorithmName, int maxEvaluation) {
        this.algorithmName = algorithmName.trim().toUpperCase(Locale.ROOT);
        this.maxEvaluation = maxEvaluation;
        this.isRunning = false;
        this.progress = 0;
        this.customProgressListener = new CustomProgressListener(this);
    }

    @Override
    public void execute(List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        this.isRunning = true;
        try
        {
            LOGGER.info(this.algorithmName + "::EXECUTE");
            LinkedList<ClassCourse> classes = new LinkedList<>(inputClasses);



            //configure and run this experiment
            Executor newExecutor = new Executor()
                    .withProblemClass(Problem.class, classes.size(), TimetableEvaluationService.METRICSLIST.size(), classes, classrooms)
                    .withAlgorithm(this.algorithmName)
                    .withMaxEvaluations(this.maxEvaluation)
                    .withProgressListener(this.customProgressListener);
            setExecutor(newExecutor);

            NondominatedPopulation result = newExecutor.run();
            LOGGER.info(result);

            //display the results
            LOGGER.info(String.format("Objective1   Objective2   Objective3   Objective4   Objective5   Objective6   Objective7   Objective8%n"));
            for (Solution solution : result) {
                LOGGER.info(String.format("%.4f     %.4f     %.4f     %.4f     %.4f     %.4f     %.4f     %.4f%n",
                        solution.getObjective(0),
                        solution.getObjective(1),
                        solution.getObjective(2),
                        solution.getObjective(3),
                        solution.getObjective(4),
                        solution.getObjective(5),
                        solution.getObjective(6),
                        solution.getObjective(7)
                ));
            }
            final Solution bestSolution = getBestSolution(result);
            final LinkedList<ClassCourse> bestClassCourses = Problem.solutionToTimetable(bestSolution, inputClasses, classrooms);

            LOGGER.info(Arrays.toString(bestSolution.getObjectives()));
            LOGGER.info(TimetableEvaluationService.evaluateTimetable(bestClassCourses,classrooms));

            inputClasses = bestClassCourses;
        }
        finally
        {
            this.isRunning = false;
            LOGGER.info("Finished " + algorithmName);
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

    @Override
    public String getName() {
        return this.algorithmName;
    }

    @Override
    public void stop() {
        stopExecutor();
    }

    public void setProgress(Double progressValue) {
        this.progress = progressValue;
    }
    private void setExecutor(Executor executor) {
        this.executor = executor;
    }
    private void stopExecutor() {
        this.executor.cancel();
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
