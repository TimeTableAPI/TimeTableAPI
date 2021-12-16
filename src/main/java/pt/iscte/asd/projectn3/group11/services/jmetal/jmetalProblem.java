package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.problem.Problem;

public class jmetalProblem implements Problem<jmetalSolution> {
	@Override
	public int getNumberOfVariables() {
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 0;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public jmetalSolution evaluate(jmetalSolution jmetalSolution) {
		return null;
	}

	@Override
	public jmetalSolution createSolution() {
		return null;
	}
}
