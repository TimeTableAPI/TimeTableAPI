package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal;


import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.lab.experiment.component.impl.ComputeQualityIndicators;
import org.uma.jmetal.lab.experiment.component.impl.ExecuteAlgorithms;
import org.uma.jmetal.lab.experiment.component.impl.GenerateReferenceParetoSetAndFrontFromDoubleSolutions;
import org.uma.jmetal.lab.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.lab.experiment.util.ExperimentProblem;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util.ProblemJMetal;

import java.util.List;

import static org.uma.jmetal.qualityindicator.QualityIndicatorUtils.printQualityIndicators;
import static org.uma.jmetal.util.AbstractAlgorithmRunner.printFinalSolutionSet;

public final class CustomAlgorithmServiceJMetal extends AbstractAlgorithmRunner implements IAlgorithmService {

    @Override
    public void execute(List<ClassCourse> classes, List<Classroom> classrooms) {
        final ProblemJMetal problem = new ProblemJMetal(TimetableEvaluationService.METRICSLIST, classes, classrooms);
        Algorithm<List<IntegerSolution>> algorithm;
        CrossoverOperator<IntegerSolution> crossover;
        MutationOperator<IntegerSolution> mutation;
        SelectionOperator<List<IntegerSolution>, IntegerSolution> selection;

/*        Algorithm algorithm =
                new NSGAIIBuilder(
                        problem.getProblem(),
                        new SBXCrossover(1.0, 5),
                        new PolynomialMutation(
                                1.0 / problem.getProblem().getNumberOfVariables(), 10.0),
                        100)
                        .setMaxEvaluations(25000)
                        .build();*/

        double crossoverProbability = 0.9 ;
        double crossoverDistributionIndex = 20.0 ;
        crossover = new IntegerSBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
        double mutationDistributionIndex = 20.0 ;
        mutation = new IntegerPolynomialMutation(mutationProbability, mutationDistributionIndex) ;

        selection = new BinaryTournamentSelection<IntegerSolution>() ;
        int populationSize = 100 ;
        algorithm = new NSGAIIBuilder<IntegerSolution>(problem, crossover, mutation, populationSize)
                .setSelectionOperator(selection)
                .setMaxEvaluations(25000)
                .build() ;

        new AlgorithmRunner.Executor(algorithm).execute();
        List<IntegerSolution> finalSolutions= algorithm.getResult();

        printFinalSolutionSet(finalSolutions);
        printQualityIndicators(finalSolutions, "") ;

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public double getProgress() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void stop() {

    }
}
