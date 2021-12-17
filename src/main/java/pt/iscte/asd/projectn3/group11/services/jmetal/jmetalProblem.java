package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.permutationproblem.PermutationProblem;
import org.uma.jmetal.problem.permutationproblem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;
import pt.iscte.asd.projectn3.group11.Context;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.MetricCalculator;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class jmetalProblem implements Problem<TimetableSolution> {
	static int counter = 0 ;

	private final List<Float> objectiveDirections = new LinkedList<>();

	private final List<Classroom> variableClassrooms;
	private final List<ClassCourse> variableClassCourses;
	private final List<MetricCalculator> variableMetrics;
	private final int numberOfVariables;
	private final int numberOfObjectives;
	private final int numberOfConstraints;


	protected jmetalProblem(Context context, List<MetricCalculator> metrics) {
		variableClassCourses = context.getClassCourses();
		variableClassrooms = context.getClassrooms();
		variableMetrics = metrics;
		variableMetrics.forEach(metricCalculator -> objectiveDirections.add(metricCalculator.getObjective()));
		numberOfVariables = Math.max(variableClassCourses.size(), variableClassrooms.size());
		numberOfObjectives = variableMetrics.size();
		numberOfConstraints = 0;

	}

	@Override
	public TimetableSolution evaluate(TimetableSolution solution) {
		final List<Integer> variables = solution.variables();
		final double[] objectives = solution.objectives();

		List<ClassCourse> createdTimetable = new LinkedList<>();
		for(int i = 0; i < variables.size() && i < variableClassCourses.size() ; i++){
			final Integer classroomIndex = variables.get(i);
			final ClassCourse classCourse = variableClassCourses.get(i);
			if (classroomIndex > -1) {
				//classroom assigned
				final Classroom classroom = variableClassrooms.get(classroomIndex);
				classCourse.setClassroom(classroom);
				createdTimetable.add(classCourse);
			}else {
				//no classroom assigned
				createdTimetable.add(classCourse);
			}
		}
		final Hashtable<String, Float> metricsHashTable = TimetableEvaluationService.evaluateTimetable(createdTimetable, variableClassrooms, variableMetrics);
		for (int i = 0; i < variableMetrics.size(); i++) {
			MetricCalculator metricCalculator = variableMetrics.get(i);
			int finalI = i;
			metricsHashTable.forEach((s, aFloat) -> {
				if (metricCalculator.getClass().getSimpleName().equals(s)) {
					objectives[finalI] = aFloat;
				}
			});
		}
		counter++;
		System.out.println(counter);

		return solution;
	}

	@Override
	public TimetableSolution createSolution() {
		return new TimetableSolution(getNumberOfVariables(),variableClassrooms.size(), getNumberOfObjectives());
	}

	@Override
	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

	@Override
	public int getNumberOfConstraints() {
		return numberOfConstraints;
	}

	@Override
	public String getName() {
		return "";
	}



}
