package pt.iscte.asd.projectn3.group11.services.jmetal;

import org.uma.jmetal.solution.Solution;
import pt.iscte.asd.projectn3.group11.Context;

import java.util.List;
import java.util.Map;

public class jmetalSolution implements Solution<Context> {



	@Override
	public List<Context> variables() {
		return null;
	}

	@Override
	public double[] objectives() {
		return new double[0];
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
	public Solution<Context> copy() {
		return null;
	}
}
