package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import pt.iscte.asd.projectn3.group11.Context;

public class jmetal {
	public static void main(String[] args) {
		NSGAII<jmetalSolution> nsgai = new NSGAIIBuilder<jmetalSolution>(
				new jmetalProblem(),
				new jmetalCrossoverOperator(),
				new jmetalMutationOperator(),
				10
		).build();
		nsgai.run();

	}
}
