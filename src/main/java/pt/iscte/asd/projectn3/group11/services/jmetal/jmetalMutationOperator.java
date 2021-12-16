package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.operator.mutation.MutationOperator;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;

public class jmetalMutationOperator implements MutationOperator<jmetalSolution> {

	@Override
	public jmetalSolution execute(jmetalSolution classCourse) {
		return classCourse;
	}

	@Override
	public double getMutationProbability() {
		return 0;
	}
}
