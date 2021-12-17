package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimetableSolution implements PermutationSolution<Integer> {

	private final List<Integer> variables;
	private int numberOfClassCourses;
	private int numberOfClassrooms;
	private final double[] objectives;
	private final double[] constraints;
	private Map<Object, Object> atributes;

	public TimetableSolution(int numberOfClassCourses, int numberOfClassrooms , int numberOfObjectives) {
		variables= new ArrayList<>(numberOfClassCourses);

		this.numberOfClassCourses = numberOfClassCourses;
		this.numberOfClassrooms = numberOfClassrooms;

		objectives= new double[numberOfObjectives];
		constraints=  new double[0];

		for (int i = 0 ; i < numberOfClassCourses ; i++){
			variables.add( (int)(Math.random() * numberOfClassrooms));
		}
		atributes = new HashMap<>();

	}
	public TimetableSolution(TimetableSolution other) {
		  variables = new ArrayList<>(other.variables());
		  objectives = other.objectives().clone();
		  constraints = other.constraints().clone();

		  numberOfClassCourses = other.numberOfClassCourses;
		  numberOfClassrooms = other.numberOfClassrooms;
		  atributes = new HashMap<>(other.atributes);
	}

	@Override
	public int getLength() {
		return 0;
	}

	@Override
	public List<Integer> variables() {
		return variables;
	}

	@Override
	public double[] objectives() {
		return objectives;
	}

	@Override
	public double[] constraints() {
		return constraints;
	}

	@Override
	public Map<Object, Object> attributes() {
		return atributes;
	}

	@Override
	public Solution<Integer> copy() {
		return new TimetableSolution(this);
	}
}
