package pt.iscte.asd.projectn3.group11.models.util;

import java.util.Comparator;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;

public class SortByCapacityClassCourse implements Comparator<ClassCourse> {
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
