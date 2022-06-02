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
		JmetalSolution newSolution = new JmetalSolution(jmetalSolution.metricList,jmetalSolution.classes,jmetalSolution.classrooms,jmetalSolution.timeSlotsList);
		for (int i = 0; i < newSolution.variables().size(); i++) {
			if (Math.random() <= this.mutationProbability) {
				final int lowerBoundClassRoom = newSolution.getLowerBoundClassrooms();
				final int upperBoundClassRoom = newSolution.getHigherBoundClassrooms();
				final int valueClassRooms = (int) (lowerBoundClassRoom + ((upperBoundClassRoom - lowerBoundClassRoom) * Math.random()));

				final int lowerBoundTime = newSolution.getLowerBoundTime();
				final int upperBoundTime = newSolution.getHigherBoundTime();
				final int valueTime = (int) (lowerBoundTime + ((upperBoundTime - lowerBoundTime) * Math.random()));

				newSolution.variables().set(i, new Tuple<>(valueClassRooms, valueTime));
			}
		}

		return newSolution;
	}
}
