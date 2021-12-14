package pt.iscte.asd.projectn3.group11.models;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import java.util.LinkedList;
import java.util.List;


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
            "Sala da aula,Lotação",
            "Características reais da sala",
    };
    //region MEMBERS

    private final LinkedList<String> courses;
    private final LinkedList<String> units;
    private final String shift;
    private final int numberOfStudentsInClass;
    private final int shiftsWithFreeSpots;
    private final int shiftsWithMoreThanTheCapacity;
    private final String weekday;
    private final TimeShift beginningHour;
    private final TimeShift endHour;
    private final Date date;
    private final LinkedList<String> askedCharacteristics;
    private final LinkedList<String> classesOfCourse;
    private final int capacity;
    private final LinkedList<String> realCharacteristics;

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

    //endregion

    //region BUILDER

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

    //region GETTERS

    /**
     * Gets Course.
     * @return {@link ClassCourse#courses}
     */
    public final LinkedList<String> getCourses() {
        return courses;
    }

    /**
     * Gets Unit.
     * @return {@link ClassCourse#units}
     */
    public final LinkedList<String> getUnits() {
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
    public final LinkedList<String> getClassesOfCourse() {
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
    public final LinkedList<String> getAskedCharacteristics() {
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
    public final LinkedList<String> getRealCharacteristics() {
        return realCharacteristics;
    }

    //endregion

    //region SETTERS

    /**
     * Sets the classroom.
     * @param classroom classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    //endregion

    //region TO_STRINGS

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
                "," + ((classroom != null)?  classroom.getClassroomName(): "") +
                "," + capacity +
                ",\"" + String.join(", ",realCharacteristics) +"\""
                ;
    }
    //endregion

}
