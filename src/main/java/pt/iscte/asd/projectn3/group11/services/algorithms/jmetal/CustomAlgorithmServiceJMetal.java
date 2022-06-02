package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal;


import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.LogService;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.Problem;

import java.util.List;

public final class CustomAlgorithmServiceJMetal extends AbstractAlgorithmRunner implements IAlgorithmService {
    private final String algorithmName = "jmetal";
    private final int maxEvaluation = 3;
    private final int populationSize = 10;
    private boolean isRunning;
    private double progress;
    private Algorithm<List<IntegerSolution>> algorithm;
    private AlgorithmRunner algorithmRunner;


    @Override
    public void execute(List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        try{
            LogService.getInstance().info(this.algorithmName + "::EXECUTE");
            this.isRunning = true;
            final Problem problem = new Problem(TimetableEvaluationService.METRICSLIST, inputClasses, classrooms);

            CrossoverOperator<IntegerSolution> crossover;
            MutationOperator<IntegerSolution> mutation;
            SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;

            double crossoverProbability = 0.9;
            double crossoverDistributionIndex = 20.0;
            crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex);

            double mutationProbability = 1.0 / problem.getNumberOfVariables();
            double mutationDistributionIndex = 20.0;
            mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex);
            selection = new BinaryTournamentSelection<IntegerSolution>();

            algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation, populationSize)
                    .setSelectionOperator(selection)
                    .setMaxEvaluations(maxEvaluation)
                    .build();


            algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
            List<IntegerSolution> finalSolutions = algorithm.getResult();

            printFinalSolutionSet(finalSolutions);
            //printQualityIndicators(finalSolutions, "");
            LogService.getInstance().debug("Total execution time: " + algorithmRunner.getComputingTime() + "ms");
            inputClasses =problem.solutionToTimetable( finalSolutions.get(0));

        }finally
        {
            this.isRunning = false;
            LogService.getInstance().info("Finished " + algorithmName);
        }
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    public double getProgress() {
        final double computingTime = (double) this.algorithmRunner.getComputingTime();
        LogService.getInstance().info("Progress: " + computingTime);

        return computingTime;
    }

    @Override
    public String getName() {
        return this.algorithmName;
    }

    @Override
    public void stop() {

    }
}
