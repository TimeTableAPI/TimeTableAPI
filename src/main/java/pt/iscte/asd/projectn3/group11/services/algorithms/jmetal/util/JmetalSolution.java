package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;

import org.jetbrains.annotations.NotNull;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.TimeSlot;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.Tuple;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.IMetricCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JmetalSolution implements org.uma.jmetal.solution.Solution<Tuple<Integer,Integer>> {

	public final List<ClassCourse> classes;
	public final List<Classroom> classrooms;
	public final List<IMetricCalculator> metricList;
	public final  List<TimeSlot> timeSlotsList;
	private List<Tuple<Integer,Integer>> variables;
	private double[] objectives;
	private double[] constraints;

	public JmetalSolution(
			@NotNull List<IMetricCalculator> metricList,
			@NotNull List<ClassCourse> classes,
			@NotNull List<Classroom> classrooms,
			@NotNull List<TimeSlot> timeSlotsList
			) {

		this.classes = classes;
		this.classrooms = classrooms;
		this.metricList = metricList;
		this.timeSlotsList = timeSlotsList;
		createVariables();
		createObjectives();
		createConstraints();

	}



	private void createVariables() {
		this.variables = new ArrayList<>(classes.size());

		for (int i = 0; i < classes.size(); i++) {
			final int randomRoom = (int) (Math.random() * classrooms.size());
			final int randomTimeSlot = (int) (Math.random() * timeSlotsList.size());

			variables.add(i, new Tuple<>(randomRoom, randomTimeSlot));
			//LogService.getInstance().debug(i + " " + randomRoom);
		}
	}

	private void createObjectives() {
		this.objectives = new double[metricList.size()];
	}

	private void createConstraints() {
	}

	public int getLowerBoundClassrooms(){return 0;}
	public int getHigherBoundClassrooms(){return this.classrooms.size();}
	public int getLowerBoundTime(){return 0;}
	public int getHigherBoundTime(){return this.timeSlotsList.size();}

	@Override
	public List<Tuple<Integer, Integer>> variables() {
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
	public JmetalSolution copy() {
		return new JmetalSolution(this.metricList,this.classes,this.classrooms,this.timeSlotsList);
	}

}
