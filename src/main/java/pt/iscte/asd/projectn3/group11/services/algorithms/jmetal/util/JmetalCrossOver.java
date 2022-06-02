package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;

import org.uma.jmetal.operator.crossover.CrossoverOperator;

import java.util.ArrayList;
import java.util.List;

public class JmetalCrossOver implements CrossoverOperator<JmetalSolution> {
	private final double crossoverProbability;
	private final int numberOfRequiredParents = 2;
	private final int numberOfGeneratedChildren = 1;
	public JmetalCrossOver( double crossoverProbability){
		this.crossoverProbability = crossoverProbability;
	}
	@Override
	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	@Override
	public int getNumberOfRequiredParents() {
		return numberOfRequiredParents;
	}

	@Override
	public int getNumberOfGeneratedChildren() {
		return numberOfGeneratedChildren;
	}

	@Override
	public List<JmetalSolution> execute(List<JmetalSolution> s) {
		JmetalSolution mom = s.get(0);
		JmetalSolution dad = s.get(1);
		assert (mom.variables().size() == dad.variables().size());

		JmetalSolution girl = mom.copy();
		JmetalSolution boy = dad.copy();
		boolean swap = false;

		for (int i = 0; i < mom.variables().size(); i++) {
			if (Math.random()<crossoverProbability) {
				boy.variables().set(i, mom.variables().get(i));
				girl.variables().set(i, dad.variables().get(i));
			}

		}
		List<JmetalSolution> result = new ArrayList<>();
		result.add(girl);
		result.add(boy);
		return result;


	}
}
