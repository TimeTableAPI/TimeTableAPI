package pt.iscte.asd.projectn3.group11.services.algorithms;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;

import java.util.List;

public class CustomAlgorithmService implements IAlgorithmService {
    @Override
    public void execute(List<ClassCourse> classes, List<Classroom> classrooms) {
        //configure and run this experiment
        NondominatedPopulation result = new Executor()
                .withProblemClass(Problem.class,classes.size(), TimetableEvaluationService.METRICSLIST.size(), classes, classrooms)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(1)
                .run();

        //display the results
        System.out.format("Objective1  Objective2%n");

        for (Solution solution : result) {
            System.out.format("%.4f      %.4f%n",
                    solution.getObjective(0),
                    solution.getObjective(1));
        }
    }
}
