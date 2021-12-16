package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

public class jmetalCrossoverOperator implements CrossoverOperator<jmetalSolution> {
	@Override
	public double getCrossoverProbability() {
		return 0;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return 0;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return 0;
	}

	@Override
	public List<jmetalSolution> execute(List<jmetalSolution> classCourses) {
		return null;
	}
}
