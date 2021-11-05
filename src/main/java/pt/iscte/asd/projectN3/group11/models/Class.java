package pt.iscte.asd.projectN3.group11.models;

import java.util.LinkedList;
import java.util.List;

public class Class {
    private final LinkedList<String> courses;
    private final LinkedList<String> units;
    private final String shift;
    private final LinkedList<String> classesOfCourse;
    private final int numberOfStudentsInClass;
    private final int shiftsWithFreeSpots;
    private final int shiftsWithMoreThanTheCapacity;
    private final String weekday;
    private final String beginningHour;
    private final String endHour;
    private final String monthDay;
    private final String askedCharacteristics;
    private final String classroom;
    private final String capacity;
    private final LinkedList<String> realCharacteristics;

    public Class(final List<String> courses, final List<String> units, final String shift, final List<String> classesOfCourse, final int numberOfStudentsInClass, final int shiftsWithFreeSpots, final int shiftsWithMoreThanTheCapacity, final String weekday, final String beginningHour, final String endHour, final String monthDay, final String askedCharacteristics, final String classroom, final String capacity, final List<String> realCharacteristics) {
        this.courses = new LinkedList<>(courses);
        this.units = new LinkedList<>(units);
        this.shift = shift;
        this.classesOfCourse =  new LinkedList<>(classesOfCourse);
        this.numberOfStudentsInClass = numberOfStudentsInClass;
        this.shiftsWithFreeSpots = shiftsWithFreeSpots;
        this.shiftsWithMoreThanTheCapacity = shiftsWithMoreThanTheCapacity;
        this.weekday = weekday;
        this.beginningHour = beginningHour;
        this.endHour = endHour;
        this.monthDay = monthDay;
        this.askedCharacteristics = askedCharacteristics;
        this.classroom = classroom;
        this.capacity = capacity;
        this.realCharacteristics = new LinkedList<>(realCharacteristics);
    }

    @Override
    public final String toString() {
        return "Class{" +
                "courses=" + courses +
                ", units=" + units +
                ", shift='" + shift + '\'' +
                ", classesOfCourse=" + classesOfCourse +
                ", numberOfStudentsInClass='" + numberOfStudentsInClass + '\'' +
                ", shiftsWithFreeSpots='" + shiftsWithFreeSpots + '\'' +
                ", shiftsWithMoreThanTheCapacity='" + shiftsWithMoreThanTheCapacity + '\'' +
                ", weekday='" + weekday + '\'' +
                ", beginningHour='" + beginningHour + '\'' +
                ", endHour='" + endHour + '\'' +
                ", monthDay='" + monthDay + '\'' +
                ", askedCharacteristics='" + askedCharacteristics + '\'' +
                ", classroom='" + classroom + '\'' +
                ", capacity='" + capacity + '\'' +
                ", realCharacteristics=" + realCharacteristics +
                '}';
    }

    /**
     * Gets Course.
     * @return {@link Class#courses}
     */
    public final LinkedList<String> getCourses() {
        return courses;
    }

    /**
     * Gets Unit.
     * @return {@link Class#units}
     */
    public final LinkedList<String> getUnits() {
        return units;
    }

    /**
     * Gets Shift.
     * @return {@link Class#shift}
     */
    public final String getShift() {
        return shift;
    }

    /**
     * Gets Class of course.
     * @return {@link Class#classesOfCourse}
     */
    public final LinkedList<String> getClassesOfCourse() {
        return classesOfCourse;
    }

    /**
     * Gets the number of students.
     * @return {@link Class#numberOfStudentsInClass}
     */
    public final int getNumberOfStudentsInClass() {
        return numberOfStudentsInClass;
    }

    /**
     * Gets Shifts with free spots.
     * @return {@link Class#shiftsWithFreeSpots}
     */
    public final int getShiftsWithFreeSpots() {
        return shiftsWithFreeSpots;
    }

    /**
     * Gets Shifts with more students than the capacity.
     * @return {@link Class#shiftsWithMoreThanTheCapacity}
     */
    public final int getShiftsWithMoreThanTheCapacity() {
        return shiftsWithMoreThanTheCapacity;
    }

    /**
     * Gets weekday.
     * @return {@link Class#weekday}
     */
    public final String getWeekday() {
        return weekday;
    }

    /**
     * Gets beginning hour of the class.
     * @return {@link Class#beginningHour}
     */
    public final String getBeginningHour() {
        return beginningHour;
    }

    /**
     * Gets end hour.
     * @return {@link Class#endHour}
     */
    public final String getEndHour() {
        return endHour;
    }

    /**
     * Gets moth day.
     * @return {@link Class#monthDay}
     */
    public final String getMonthDay() {
        return monthDay;
    }

    /**
     * Gets asked characteristics.
     * @return {@link Class#askedCharacteristics}
     */
    public final String getAskedCharacteristics() {
        return askedCharacteristics;
    }

    /**
     * Gets classroom.
     * @return {@link Class#classroom}
     */
    public final String getClassroom() {
        return classroom;
    }

    /**
     * Gets capacity.
     * @return {@link Class#capacity}
     */
    public final String getCapacity() {
        return capacity;
    }

    /**
     * Gets real characteristics
     * @return {@link Class#realCharacteristics}
     */
    public final LinkedList<String> getRealCharacteristics() {
        return realCharacteristics;
    }
}
