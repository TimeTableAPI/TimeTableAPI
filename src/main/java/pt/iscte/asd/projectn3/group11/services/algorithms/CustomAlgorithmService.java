package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.apache.commons.beanutils.LazyDynaMap;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.util.Problem;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassCourseLoaderService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.MetricCalculator;

import java.util.*;

public class CustomAlgorithmService implements IAlgorithmService {

    private final String algorithmName;
    private final int maxEvaluation;

    public CustomAlgorithmService(String algorithmName, int maxEvaluation) {
        this.algorithmName = algorithmName.trim().toUpperCase(Locale.ROOT);
        this.maxEvaluation = maxEvaluation;
    }

    /**
     * Executes the algorithm.
     * @param inputClasses
     * @param classrooms
     */
    @Override
    public void execute(List<ClassCourse> inputClasses, List<Classroom> classrooms) {
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
        final LinkedList<ClassCourse> BestclassCourses = Problem.solutionToTimetable(bestSolution, inputClasses, classrooms);

        System.out.println(Arrays.toString(bestSolution.getObjectives()));
        System.out.println(TimetableEvaluationService.evaluateTimetable(BestclassCourses,classrooms));
        inputClasses = BestclassCourses;
    }

    private static Solution getBestSolution (NondominatedPopulation result){
        List<Double> distancesList = new LinkedList<>();
        List<double[]> listObjectives = new LinkedList<>();
        result.forEach(solution -> {
            listObjectives.add(solution.getObjectives());
            distancesList.add(euclidianMetricDistance(solution.getObjectives(), TimetableEvaluationService.METRICSLIST));
        });
        Double minValue =  Collections.min(distancesList);
        final int bestIndex = distancesList.indexOf(minValue);

        return result.get(bestIndex);
    }

    private static double euclidianMetricDistance(double[] metricResults, List<MetricCalculator> metricCalculatorList){
        double value = 0;
        for(int i = 0 ; i< metricResults.length;i++){
            value += Math.pow(metricResults[i] - metricCalculatorList.get(i).getObjective() ,2);
        }

        return Math.pow(value, 0.5);
    }


    public static void main(String[] args) {
        LinkedList<Classroom> loadedClassRooms = ClassroomLoaderService.load("src/main/resources/ADS - Caracterizacao das salas.csv");
        LinkedList<ClassCourse> loadedClassCourses = ClassCourseLoaderService.load("src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv");

        Context context = new Context(loadedClassCourses, loadedClassRooms, new BasicAlgorithmService());
        CustomAlgorithmService customAlgorithmService = new CustomAlgorithmService("GDE3",10);
        customAlgorithmService.execute(loadedClassCourses,loadedClassRooms);




    }


    }
