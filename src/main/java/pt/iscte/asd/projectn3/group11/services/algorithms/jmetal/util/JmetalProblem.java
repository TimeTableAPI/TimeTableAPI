package pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.util;


import org.jetbrains.annotations.NotNull;
import org.uma.jmetal.problem.AbstractGenericProblem;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.TimeSlot;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.models.util.Tuple;
import pt.iscte.asd.projectn3.group11.services.LogService;
import pt.iscte.asd.projectn3.group11.services.TimetableEvaluationService;
import pt.iscte.asd.projectn3.group11.services.util.metriccalculators.IMetricCalculator;

import java.util.*;


public class JmetalProblem extends AbstractGenericProblem<JmetalSolution> {

    private final List<ClassCourse> classes;
    private final List<Classroom> classrooms;
    private final List<TimeSlot> timeSlots;
    private final List<IMetricCalculator> metricList;

    private final Date startDate;
    private final Date endDate;

    public JmetalProblem(
            @NotNull List<IMetricCalculator> metricList,
            @NotNull List<ClassCourse> classes,
            @NotNull List<Classroom> classrooms,
            @NotNull Date startDate,
            @NotNull Date endDate
    ) {
        this.classes = classes;
        this.classrooms = classrooms;
        this.metricList = metricList;
        this.startDate = startDate;
        this.endDate = endDate;

        this.timeSlots = createTimeSlots(startDate,endDate);

        setName("Jmetal");
        setNumberOfVariables(classes.size());
        setNumberOfObjectives(metricList.size());
        setNumberOfConstraints(0);
        /*
         List<Integer> lowerBounds = new ArrayList<>();
         List<Integer> upperBounds = new ArrayList<>();
         for(int i = 0 ; i<classes.size(); i++){
             lowerBounds.add(0);
             upperBounds.add(classes.size());
         }
        setVariableBounds(lowerBounds,upperBounds);
        */
    }

    private List<TimeSlot> createTimeSlots(Date startDate,Date endDate) {
        List<TimeSlot> slots = new ArrayList<>();
        for(int year = startDate.getYear(); year <= endDate.getYear(); year++){
            for (int month = startDate.getMonth(); month <= endDate.getMonth() ; month++) {
                for (int day = startDate.getDay(); day <= endDate.getDay() ; day++){
                    for (TimeShift timeShift : TimeShift.values()) {
                        if(timeShift != TimeShift.NOTHING) {
                            slots.add(new TimeSlot(timeShift, new Date(day,month,year)));
                        }
                    }
                }
            }
        }
        LogService.getInstance().info(slots);
        return slots;
    }

    public LinkedList<ClassCourse> solutionToTimetable(
            JmetalSolution solution
            //List<ClassCourse> inputClasses,
            //List<Classroom> classrooms
    ) {
        List<Tuple<Integer, Integer>> variables = solution.variables();
        return solutionToTimetable(variables);

    }

    private LinkedList<ClassCourse> solutionToTimetable(List<Tuple<Integer, Integer>> variables){
        LinkedList<ClassCourse> classCourses = new LinkedList<>();
        for(int i = 0; i < variables.size(); i++) {
            try {
                final TimeSlot timeSlot = this.timeSlots.get(variables.get(i).y);
                ClassCourse classCourse = this.classes.get(i);
                classCourse.setDate(timeSlot.getDate());
                classCourse.setBeginningHour(timeSlot.getTimeShift());
                classCourse.setEndHour(timeSlot.getTimeShift());
                Classroom classroom = classrooms.get(variables.get(i).x);
                classCourse.setClassroom(classroom);
                classCourses.add(classCourse);
            }catch(Exception e) {
                e.printStackTrace();
                ClassCourse classCourse = classCourses.get(i);
                classCourses.add(classCourse);
            }
        }
        return classCourses;

    }


    @Override
    public JmetalSolution createSolution() {
        LogService.getInstance().debug("created new solution");
        return new JmetalSolution(this.metricList,this.classes,this.classrooms,this.timeSlots);

    }

    @Override
    public JmetalSolution evaluate(JmetalSolution integerSolution) {

        final LinkedList<ClassCourse> solutionClassCourses = this.solutionToTimetable(integerSolution);
        final Hashtable<String, Float> stringFloatHashtable = TimetableEvaluationService.evaluateTimetable(solutionClassCourses, this.classrooms);
        int itr = 0;
        for (IMetricCalculator metricCalculator : TimetableEvaluationService.METRICSLIST) {
            for(Map.Entry<String, Float> entry: stringFloatHashtable.entrySet()){
                if (metricCalculator.getClass().getSimpleName().equals(entry.getKey())){
                    integerSolution.objectives()[itr] = entry.getValue();
                    itr++;
                    break;
                }
            }
        }
        LogService.getInstance().debug(Arrays.toString(integerSolution.objectives()));

        /*
        ArrayList<Double> objectivesDoubleList = new ArrayList<>();
        stringFloatHashtable.values().forEach(aFloat -> objectivesDoubleList.add((double)aFloat));
        double[] arr = objectivesDoubleList.stream().mapToDouble(Double::doubleValue).toArray();
        integerSolution.objectives()[0] = 1;
        */

        return integerSolution;
    }

}
