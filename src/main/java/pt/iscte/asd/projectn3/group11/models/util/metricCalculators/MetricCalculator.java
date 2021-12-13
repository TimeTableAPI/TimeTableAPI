package pt.iscte.asd.projectn3.group11.models.util.metricCalculators;


import pt.iscte.asd.projectn3.group11.models.ClassCourse;

import java.util.List;

public interface MetricCalculator {
	float evaluate(List<ClassCourse> classCourseList);
}

