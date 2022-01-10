package pt.iscte.asd.projectn3.group11.models;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <h1>ClassCourse</h1>
 * <p>The classCourse class represents a class in a School</p>
 * <p>Has the following nested Classes</p>
 * <ul>
 *     <li>{@link ClassCourse.Builder}</li>
 *     <li>{@link ClassCourse.ClassCourseJson}</li>
 * </ul>
 * <p>To hold all of these proprieties the class holds the following variables</p>
 * <ul>
 *     <li>{@link LinkedList<String>} courses </li>
 *     <li>{@link LinkedList<String>} units </li>
 *     <li>{@link String} shift </li>
 *     <li>{@link Integer} numberOfStudentsInClass </li>
 *     <li>{@link Integer} shiftsWithFreeSpots </li>
 *     <li>{@link Integer} shiftsWithMoreThanTheCapacity </li>
 *     <li>{@link String} weekday </li>
 *     <li>{@link TimeShift} beginningHour </li>
 *     <li>{@link TimeShift} endHour </li>
 *     <li>{@link Date} date </li>
 *     <li>{@link LinkedList<String>} askedCharacteristics </li>
 *     <li>{@link LinkedList<String>} classesOfCourse </li>
 *     <li>{@link Integer} capacity </li>
 *     <li>{@link LinkedList<String>} realCharacteristics </li>
 *     <li>{@link Classroom} classroom </li>
 * </ul>
 *
 */
public class ClassCourse {

    public static final String[] HEADER = {
            "Curso",
            "Unidade de execução",
            "Turno",
            "Turma",
            "Inscritos no turno (no 1º semestre é baseado em estimativas)",
            "Turnos com capacidade superior à capacidade das características das salas",
            "Turno com inscrições superiores à capacidade das salas",
            "Dia da Semana",
            "Início",
            "Fim",
            "Dia",
            "Características da sala pedida para a aula",
            "Sala da aula",
            "Lotação",
            "Características reais da sala",
    };

    //region MEMBERS

    private final List<String> courses;
    private final List<String> units;
    private final String shift;
    private final int numberOfStudentsInClass;
    private final int shiftsWithFreeSpots;
    private final int shiftsWithMoreThanTheCapacity;
    private final String weekday;
    private final TimeShift beginningHour;
    private final TimeShift endHour;
    private final Date date;
    private final List<String> askedCharacteristics;
    private final List<String> classesOfCourse;
    private int capacity;
    private List<String> realCharacteristics;

    private Classroom classroom;

    //endregion

    //region CONSTRUCTORS

    private ClassCourse(Builder builder) {
        this.courses = builder.courses;
        this.units = builder.units;
        this.shift = builder.shift;
        this.classesOfCourse = builder.classesOfCourse;
        this.numberOfStudentsInClass = builder.numberOfStudentsInClass;
        this.shiftsWithFreeSpots = builder.shiftsWithFreeSpots;
        this.shiftsWithMoreThanTheCapacity = builder.shiftsWithMoreThanTheCapacity;
        this.weekday = builder.weekday;
        this.beginningHour = builder.beginningHour;
        this.endHour = builder.endHour;
        this.date = builder.date;
        this.askedCharacteristics = builder.askedCharacteristics;
        this.capacity = builder.capacity;
        this.realCharacteristics = builder.realCharacteristics;
        this.classroom = builder.classroom;
    }

    public boolean hasClassRoomAllocated() {
        try{
        return (capacity > 0 || realCharacteristics.size() > 1  || !classroom.isDummy()) &&  (classroom != null);
        }catch (NullPointerException e ){
            return false;
        }
    }

    //endregion

    //region BUILDER

    /**
     * <h2>ClassCourse.Builder</h2>
     * <p>Simple builder class for {@link ClassCourse}</p>
     */
    public static class Builder {
        private LinkedList<String> courses;
        private LinkedList<String> units;
        private String shift;
        private LinkedList<String> classesOfCourse;
        private int numberOfStudentsInClass;
        private int shiftsWithFreeSpots;
        private int shiftsWithMoreThanTheCapacity;
        private String weekday;
        private TimeShift beginningHour;
        private TimeShift endHour;
        private Date date;
        private LinkedList<String> askedCharacteristics;
        private int capacity;
        private LinkedList<String> realCharacteristics;
        private Classroom classroom;

        public Builder courses(List<String> courses) {
            this.courses = new LinkedList<>(courses);
            return this;
        }

        public Builder units(List<String> units) {
            this.units = new LinkedList<>(units);
            return this;
        }

        public Builder shift(String shift) {
            this.shift = shift;
            return this;
        }

        public Builder classesOfCourse(List<String> classesOfCourse) {
            this.classesOfCourse = new LinkedList<>(classesOfCourse);
            return this;
        }

        public Builder numberOfStudentsInClass(int numberOfStudentsInClass) {
            this.numberOfStudentsInClass = numberOfStudentsInClass;
            return this;
        }

        public Builder shiftsWithFreeSpots(int shiftsWithFreeSpots) {
            this.shiftsWithFreeSpots = shiftsWithFreeSpots;
            return this;
        }

        public Builder shiftsWithMoreThanTheCapacity(int shiftsWithMoreThanTheCapacity) {
            this.shiftsWithMoreThanTheCapacity = shiftsWithMoreThanTheCapacity;
            return this;
        }

        public Builder weekday(String weekday) {
            this.weekday = weekday;
            return this;
        }

        public Builder beginningHour (TimeShift beginningHour ){
            this.beginningHour = beginningHour;
            return this;
        }

        public Builder endHour (TimeShift endHour ){
            this.endHour = endHour;
            return this;
        }

        public Builder date (Date date){
            this.date = date;
            return this;
        }

        public Builder askedCharacteristics (List<String> askedCharacteristics ){
            this.askedCharacteristics = new LinkedList<>(askedCharacteristics);
            return this;
        }

        public Builder classroom (Classroom classroom ){
            this.classroom = classroom;
            return this;
        }

        public Builder capacity (int capacity ){
            this.capacity = capacity;
            return this;
        }

        public Builder realCharacteristics(List<String> realCharacteristics) {
            this.realCharacteristics = new LinkedList<>(realCharacteristics);
            return this;
        }

        public ClassCourse build() {
            return new ClassCourse(this);
        }
    }

    //endregion

    //region JSONTYPE

    /**
     * <h2>ClassCourse.ClassCourseJson</h2>
     * <p>The ClassCourseJson class is a mere copy of {@link ClassCourse} but all of its variables are {@link String}.</p>
     * <p>This is to more easily transfer all of the information into our frontEnd, kinda like a JSON</p>
     */
    public static class ClassCourseJson{
        public final List<String> courses;
        public final List<String> units;
        public final String shift;
        public final String numberOfStudentsInClass;
        public final String shiftsWithFreeSpots;
        public final String shiftsWithMoreThanTheCapacity;
        public final String weekday;
        public final String beginningHour;
        public final String endHour;
        public final String date;
        public final List<String> askedCharacteristics;
        public final List<String> classesOfCourse;
        public final String capacity;
        public final List<String> realCharacteristics;

        public final String classroom;


        public ClassCourseJson(List<String> courses, List<String> units, String shift, int numberOfStudentsInClass, int shiftsWithFreeSpots, int shiftsWithMoreThanTheCapacity, String weekday, TimeShift beginningHour, TimeShift endHour, Date date, List<String> askedCharacteristics, List<String> classesOfCourse, int capacity, List<String> realCharacteristics, Classroom classroom) {
            this.courses = courses;
            this.units = units;
            this.shift = shift;
            this.numberOfStudentsInClass = String.valueOf(numberOfStudentsInClass);
            this.shiftsWithFreeSpots = String.valueOf(shiftsWithFreeSpots);
            this.shiftsWithMoreThanTheCapacity = String.valueOf(shiftsWithMoreThanTheCapacity);
            this.weekday = weekday;
            this.beginningHour = beginningHour.toString();
            this.endHour = endHour.toString();
            this.date = date.toString();
            this.askedCharacteristics = askedCharacteristics;
            this.classesOfCourse = classesOfCourse;
            this.classroom = (classroom != null)? classroom.getBuilding()+","+classroom.getClassroomName() : "";
            this.capacity = String.valueOf(capacity);
            this.realCharacteristics = realCharacteristics;

        }
    }

    public ClassCourseJson toJsonType() {
        return new ClassCourseJson(
                courses,
                units,
                shift,
                numberOfStudentsInClass,
                shiftsWithFreeSpots,
                shiftsWithMoreThanTheCapacity,
                weekday,
                beginningHour,
                endHour,
                date,
                askedCharacteristics,
                classesOfCourse,
                capacity,
                realCharacteristics,
                classroom
        );
    }
    //endregion

    //region GETTERS

    /**
     * Gets Course.
     * @return {@link ClassCourse#courses}
     */
    public final List<String> getCourses() {
        return courses;
    }

    /**
     * Gets Unit.
     * @return {@link ClassCourse#units}
     */
    public final List<String> getUnits() {
        return units;
    }

    /**
     * Gets Shift.
     * @return {@link ClassCourse#shift}
     */
    public final String getShift() {
        return shift;
    }

    /**
     * Gets Class of course.
     * @return {@link ClassCourse#classesOfCourse}
     */
    public final List<String> getClassesOfCourse() {
        return classesOfCourse;
    }

    /**
     * Gets the number of students.
     * @return {@link ClassCourse#numberOfStudentsInClass}
     */
    public final int getNumberOfStudentsInClass() {
        return numberOfStudentsInClass;
    }

    /**
     * Gets Shifts with free spots.
     * @return {@link ClassCourse#shiftsWithFreeSpots}
     */
    public final int getShiftsWithFreeSpots() {
        return shiftsWithFreeSpots;
    }

    /**
     * Gets Shifts with more students than the capacity.
     * @return {@link ClassCourse#shiftsWithMoreThanTheCapacity}
     */
    public final int getShiftsWithMoreThanTheCapacity() {
        return shiftsWithMoreThanTheCapacity;
    }

    /**
     * Gets weekday.
     * @return {@link ClassCourse#weekday}
     */
    public final String getWeekday() {
        return weekday;
    }

    /**
     * Gets beginning hour of the class.
     * @return {@link ClassCourse#beginningHour}
     */
    public final TimeShift getBeginningHour() {
        return beginningHour;
    }

    /**
     * Gets end hour.
     * @return {@link ClassCourse#endHour}
     */
    public final TimeShift getEndHour() {
        return endHour;
    }

    /**
     * Gets date.
     * @return {@link ClassCourse#date}
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Gets asked characteristics.
     * @return {@link ClassCourse#askedCharacteristics}
     */
    public final List<String> getAskedCharacteristics() {
        return askedCharacteristics;
    }

    /**
     * Gets classroom.
     * @return {@link ClassCourse#classroom}
     */
    public final Classroom getClassroom() {
        return classroom;
    }

    /**
     * Gets capacity.
     * @return {@link ClassCourse#capacity}
     */
    public final int getCapacity() {
        return capacity;
    }

    /**
     * Gets real characteristics
     * @return {@link ClassCourse#realCharacteristics}
     */
    public final List<String> getRealCharacteristics() {
        return realCharacteristics;
    }

    //endregion

    //region SETTERS

    /**
     * <p>Sets the classroom.</p>
     * <p>Changes the capacity and realCharacteristics of the {@link ClassCourse} object</p>
     * <p>@param classroom classroom to set</p>
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
        capacity = classroom.getNormalCapacity();
        realCharacteristics = classroom.getCharacteristicsToString();
    }

    //endregion

    //region TO_STRINGS

    /**
     * <p>Simple toString implementation</p>
     * @return {@link String}
     */
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
                ", date='" + date + '\'' +
                ", askedCharacteristics='" + askedCharacteristics + '\'' +
                ", classroom='" + classroom + '\'' +
                ", capacity='" + capacity + '\'' +
                ", realCharacteristics=" + realCharacteristics +
                '}';
    }

    /**
     * Transforms Class course to csv file entry.
     * @return string of csv file entry.
     */
    public final String toCSVEntry() {
        return "\""+String.join(", ",courses) +"\""+
                ",\"" + String.join(", " , units) +"\""+
                "," + shift + "" +
                ",\"" + String.join(", " , classesOfCourse) +"\""+
                "," + numberOfStudentsInClass  +
                "," + shiftsWithFreeSpots +
                "," + shiftsWithMoreThanTheCapacity +
                "," + weekday +
                "," + beginningHour +
                "," + endHour +
                "," + date +
                ",\"" + String.join(", ",askedCharacteristics) +"\""+
                "," + ((classroom != null)?  "\""+classroom.getBuilding()+","+classroom.getClassroomName()+"\"": "") +
                "," + capacity +
                ",\"" + String.join(", ",realCharacteristics) +"\""
                ;
    }
    //endregion

    //region EQUALS_HASH
    /**
     * <p>Equals method for Comparing ClassCourses</p>
     * <p>Uses all of the variables minus the startTime and EndTime. This way two classes back to back are considered equal</p>
     *
     * */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClassCourse that = (ClassCourse) o;
        return getNumberOfStudentsInClass() == that.getNumberOfStudentsInClass() &&
                getShiftsWithFreeSpots() == that.getShiftsWithFreeSpots() &&
                getShiftsWithMoreThanTheCapacity() == that.getShiftsWithMoreThanTheCapacity() &&
                getUnits().equals(that.getUnits()) && getShift().equals(that.getShift()) &&
                getWeekday().equals(that.getWeekday()) && getDate().equals(that.getDate()) &&
                getAskedCharacteristics().equals(that.getAskedCharacteristics()) &&
                getClassesOfCourse().equals(that.getClassesOfCourse()
                );
    }

    /**
     * <p>Simple Hashcode implementation</p>
     * <p>@return {@link Integer} value for the Hash</p>
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                getCourses(),
                getUnits(),
                getShift(),
                getNumberOfStudentsInClass(),
                getShiftsWithFreeSpots(),
                getShiftsWithMoreThanTheCapacity(),
                getWeekday(),
                getDate(),
                getBeginningHour(),
                getEndHour(),
                getAskedCharacteristics(),
                getClassesOfCourse(),
                getClassroom(),
                getCapacity(),
                getRealCharacteristics()
        );
    }
    //endregion


}
