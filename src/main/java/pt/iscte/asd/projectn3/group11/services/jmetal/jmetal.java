package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.PMXCrossover;
import org.uma.jmetal.operator.mutation.impl.PermutationSwapMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassCourseLoaderService;
import pt.iscte.asd.projectn3.group11.services.loaders.ClassroomLoaderService;

import java.util.LinkedList;
import java.util.List;

public class jmetal {
	public static void main(String[] args) {
		LinkedList<Classroom> loadedClassRooms = ClassroomLoaderService.load("src/main/resources/ADS - Caracterizacao das salas.csv");
		LinkedList<ClassCourse> loadedClassCourses = ClassCourseLoaderService.load("src/main/resources/ADS - Exemplo de horario do 1o Semestre.csv");

		Context context = new Context(loadedClassCourses, loadedClassRooms, new BasicAlgorithmService());

		Problem<TimetableSolution> problem = new jmetalProblem(context, TimetableEvaluationService.ALLMETRICSLIST);
		Algorithm< List<PermutationSolution<Integer>>> algorithmNSGAII = nsgaiii(problem);
		long start = System.currentTimeMillis();
		algorithmNSGAII.run();
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.println("Elapsed Time: " + elapsedTime );
		List<PermutationSolution<Integer>> solutions = algorithmNSGAII.getResult();
		System.out.println("Solutions: " + solutions);

	}

	private static void getBestResult(){

	}

	private static Algorithm< List<PermutationSolution<Integer>>> nsgaiii(Problem<TimetableSolution> problem){
		int maxEvaluations = 1;
		int populationSize = 10;
		int matingPoolSize = 20;
		int offspringPopulationSize = 20;

		return new NSGAIIBuilder<TimetableSolution>(
				problem,
				new PMXCrossover(0.5),
				new PermutationSwapMutation(0.5),
				populationSize
		).
				setMaxEvaluations(maxEvaluations).
				setMatingPoolSize(matingPoolSize).
				setOffspringPopulationSize(offspringPopulationSize).
				build();

				/*maxEvaluations,
				populationSize,
				matingPoolSize,
				offspringPopulationSize,
				new
				new jmetalSolutionEvaluator(),
*/
	}


}
