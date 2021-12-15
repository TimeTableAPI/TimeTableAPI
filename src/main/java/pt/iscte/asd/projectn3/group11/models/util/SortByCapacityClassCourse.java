package pt.iscte.asd.projectn3.group11.models.util;

import java.util.Comparator;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
/**
 * <p>Class to implement a comparator for ClassCourses based on their capacity</p>
 */
public class SortByCapacityClassCourse implements Comparator<ClassCourse> {
    /**
     *
     * <p>Method to compare two classCourses capacities</p>
     * <p>
     * @param classCourse1 ClassCourse
     * @param classCourse2 ClassCourse
     * @return integer number representing if classCourse1 has a bigger capacity than classCourse2
     * </p>
     */
    @Override
    public final int compare(ClassCourse classCourse1, ClassCourse classCourse2) {
        final int classCourse1Capacity = classCourse1.getCapacity();
        final int classCourse2Capacity = classCourse2.getCapacity();
        if(classCourse1Capacity == -1 && classCourse2Capacity == -1)
        {
            return 0;
        }
        else if(classCourse1Capacity == -1 )
        {
            return 1;
        }
        else if(classCourse2Capacity == -1)
        {
            return -1;
        }
        return classCourse1Capacity - classCourse2Capacity;
    }
}
