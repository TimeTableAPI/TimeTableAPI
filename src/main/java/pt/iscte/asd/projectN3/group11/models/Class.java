package pt.iscte.asd.projectN3.group11.models;

public class Class {
    private final String course;
    private final String unit;
    private final String shift;
    private final String classOfCourse;
    private final String studentNum;
    private final String shiftsWithFreeSpots;
    private final String shiftsWithMoreThanTheCapacity;
    private final String weekday;
    private final String beginningHour;
    private final String endHour;
    private final String monthDay;
    private final String askedCharacteristics;
    private final String classroom;
    private final String capacity;
    private final String realCharacteristics;

    public Class(final String course, final String unit, final String shift, final String classOfCourse, final String studentNum, final String shiftsWithFreeSpots, final String shiftsWithMoreThanTheCapacity, final String weekday, final String beginningHour, final String endHour, final String monthDay, final String askedCharacteristics, final String classroom, final String capacity, final String realCharacteristics) {
        this.course = course;
        this.unit = unit;
        this.shift = shift;
        this.classOfCourse = classOfCourse;
        this.studentNum = studentNum;
        this.shiftsWithFreeSpots = shiftsWithFreeSpots;
        this.shiftsWithMoreThanTheCapacity = shiftsWithMoreThanTheCapacity;
        this.weekday = weekday;
        this.beginningHour = beginningHour;
        this.endHour = endHour;
        this.monthDay = monthDay;
        this.askedCharacteristics = askedCharacteristics;
        this.classroom = classroom;
        this.capacity = capacity;
        this.realCharacteristics = realCharacteristics;
    }

    @Override
    public final String toString() {
        return "Timetable{" +
                "course='" + course + '\'' +
                ", unit='" + unit + '\'' +
                ", shift='" + shift + '\'' +
                ", classOfCourse='" + classOfCourse + '\'' +
                ", studentNum='" + studentNum + '\'' +
                ", shiftsWithFreeSpots='" + shiftsWithFreeSpots + '\'' +
                ", shiftsWithMoreThanTheCapacity='" + shiftsWithMoreThanTheCapacity + '\'' +
                ", weekday='" + weekday + '\'' +
                ", beginningHour='" + beginningHour + '\'' +
                ", endHour='" + endHour + '\'' +
                ", monthDay='" + monthDay + '\'' +
                ", askedCharacteristics='" + askedCharacteristics + '\'' +
                ", classroom='" + classroom + '\'' +
                ", capacity='" + capacity + '\'' +
                ", realCharacteristics='" + realCharacteristics + '\'' +
                '}';
    }

    /**
     * Gets Course.
     * @return {@link Class#course}
     */
    public final String getCourse() {
        return course;
    }

    /**
     * Gets Unit.
     * @return {@link Class#unit}
     */
    public final String getUnit() {
        return unit;
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
     * @return {@link Class#classOfCourse}
     */
    public final String getClassOfCourse() {
        return classOfCourse;
    }

    /**
     * Gets the number of students.
     * @return {@link Class#studentNum}
     */
    public final String getStudentNum() {
        return studentNum;
    }

    /**
     * Gets Shifts with free spots.
     * @return {@link Class#shiftsWithFreeSpots}
     */
    public final String getShiftsWithFreeSpots() {
        return shiftsWithFreeSpots;
    }

    /**
     * Gets Shifts with more students than the capacity.
     * @return {@link Class#shiftsWithMoreThanTheCapacity}
     */
    public final String getShiftsWithMoreThanTheCapacity() {
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
    public final String getRealCharacteristics() {
        return realCharacteristics;
    }
}
