package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;

import org.jetbrains.annotations.NotNull;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.IMetricCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solution implements IntegerSolution {

	private final List<ClassCourse> classes;
	private final List<Classroom> classrooms;
	private final List<IMetricCalculator> metricList;
	private List<Integer> variables;
	private double[] objectives;
	private double[] constraints;

	public Solution(
			@NotNull List<IMetricCalculator> metricList,
			@NotNull List<ClassCourse> classes,
			@NotNull List<Classroom> classrooms) {
		this.classes = classes;
		this.classrooms = classrooms;
		this.metricList = metricList;

		createVariables();
		createObjectives();
		createConstraints();

	}

	private void createVariables() {
		this.variables = new ArrayList<>(classes.size());
		for (int i = 0; i < classes.size(); i++) {
			final int randomRoom = (int) (Math.random() * classrooms.size());
			variables.add(i, randomRoom);
			//LogService.getInstance().debug(i + " " + randomRoom);
		}
	}

	private void createObjectives() {
		this.objectives = new double[metricList.size()];
	}

	private void createConstraints() {
	}


	@Override
	public List<Integer> variables() {
		return this.variables;
	}

	@Override
	public double[] objectives() {
		return this.objectives;
	}

	@Override
	public double[] constraints() {
		return new double[0];
	}

	@Override
	public Map<Object, Object> attributes() {
		return null;
	}

	@Override
	public org.uma.jmetal.solution.Solution<Integer> copy() {
		return null;
	}

	@Override
	public Integer getLowerBound(int i) {
		return 0;
	}

	@Override
	public Integer getUpperBound(int i) {
		return classrooms.size();
	}

}
