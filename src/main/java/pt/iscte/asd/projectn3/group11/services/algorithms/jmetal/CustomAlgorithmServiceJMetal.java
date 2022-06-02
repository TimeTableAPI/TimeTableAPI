package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal;


import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.services.LogService;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.JmetalCrossOver;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.JmetalMutation;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.JmetalSolution;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.JmetalProblem;

import java.time.LocalDateTime;
import java.util.List;

public final class CustomAlgorithmServiceJMetal extends AbstractAlgorithmRunner implements IAlgorithmService {
    private final String algorithmName = "jmetal";
    private final int maxEvaluation = 40;
    private final int populationSize = 10;
    private boolean isRunning;
    private double progress;
    private Algorithm<List<JmetalSolution>> algorithm;
    private AlgorithmRunner algorithmRunner;


    @Override
    public void execute(List<ClassCourse> inputClasses, List<Classroom> classrooms) {
        try{
            LogService.getInstance().info(this.algorithmName + "::EXECUTE");
            this.isRunning = true;
            final JmetalProblem problem = new JmetalProblem(TimetableEvaluationService.METRICSLIST,
                    inputClasses,
                    classrooms,
                    new Date(1,7, LocalDateTime.now().getYear()),
                    new Date(1,6, LocalDateTime.now().getYear()+1)
            );

            CrossoverOperator<JmetalSolution> crossover;
            MutationOperator<JmetalSolution> mutation;
            SelectionOperator<List<JmetalSolution>, JmetalSolution> selection;

            double crossoverProbability = 0.9;
            double crossoverDistributionIndex = 20.0;
            crossover = new JmetalCrossOver(crossoverProbability);

            double mutationProbability = 1.0 / problem.getNumberOfVariables();
            double mutationDistributionIndex = 20.0;
            mutation = new JmetalMutation(mutationProbability);
            selection = new BinaryTournamentSelection<>();

            algorithm = new NSGAIIBuilder<JmetalSolution>(problem, crossover, mutation, populationSize)
                    .setSelectionOperator(selection)
                    .setMaxEvaluations(maxEvaluation)
                    .build();


            algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
            List<JmetalSolution> finalSolutions = algorithm.getResult();

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
