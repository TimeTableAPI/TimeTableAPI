package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;

import org.uma.jmetal.operator.mutation.MutationOperator;
import pt.iscte.asd.projectn3.group11.models.util.Tuple;

public class JmetalMutation implements MutationOperator<JmetalSolution> {
	private final double mutationProbability;

	public JmetalMutation(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	@Override
	public double getMutationProbability() {
		return this.mutationProbability;
	}

	@Override
	public JmetalSolution execute(JmetalSolution jmetalSolution) {
		for (int i = 0; i < jmetalSolution.variables().size(); i++) {
			if (Math.random() <= this.mutationProbability) {
				final int lowerBoundClassRoom = jmetalSolution.getLowerBoundClassrooms();
				final int upperBoundClassRoom = jmetalSolution.getHigherBoundClassrooms();
				final int valueClassRooms = (int) (lowerBoundClassRoom + ((upperBoundClassRoom - lowerBoundClassRoom) * Math.random()));

				final int lowerBoundTime = jmetalSolution.getLowerBoundTime();
				final int upperBoundTime = jmetalSolution.getHigherBoundTime();
				final int valueTime = (int) (lowerBoundTime + ((upperBoundTime - lowerBoundTime) * Math.random()));

				jmetalSolution.variables().set(i, new Tuple<>(valueClassRooms, valueTime));
			}
		}

		return null;
	}
}
