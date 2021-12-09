package pt.iscte.asd.projectn3.group11.models;


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
    private final String beginningHour;
    private final String endHour;
    private final String monthDay;
    private final String askedCharacteristics;
    private final String classroom;
    private final String capacity;
    private final LinkedList<String> realCharacteristics;
    private final LinkedList<String> classesOfCourse;

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
        this.monthDay = builder.monthDay;
        this.askedCharacteristics = builder.askedCharacteristics;
        this.classroom = builder.classroom;
        this.capacity = builder.capacity;
        this.realCharacteristics = builder.realCharacteristics;
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
        private String beginningHour;
        private String endHour;
        private String monthDay;
        private String askedCharacteristics;
        private String classroom;
        private String capacity;
        private LinkedList<String> realCharacteristics;

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

        public Builder beginningHour(String beginningHour) {
            this.beginningHour = beginningHour;
            return this;
        }

        public Builder endHour(String endHour) {
            this.endHour = endHour;
            return this;
        }

        public Builder monthDay(String monthDay) {
            this.monthDay = monthDay;
            return this;
        }

        public Builder askedCharacteristics(String askedCharacteristics) {
            this.askedCharacteristics = askedCharacteristics;
            return this;
        }

        public Builder classroom(String classroom) {
            this.classroom = classroom;
            return this;
        }

        public Builder capacity(String capacity) {
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
     *
     * @return {@link ClassCourse#courses}
     */
    public final LinkedList<String> getCourses() {
        return courses;
    }

    /**
     * Gets Unit.
     *
     * @return {@link ClassCourse#units}
     */
    public final LinkedList<String> getUnits() {
        return units;
    }

    /**
     * Gets Shift.
     *
     * @return {@link ClassCourse#shift}
     */
    public final String getShift() {
        return shift;
    }

    /**
     * Gets Class of course.
     *
     * @return {@link ClassCourse#classesOfCourse}
     */
    public final LinkedList<String> getClassesOfCourse() {
        return classesOfCourse;
    }

    /**
     * Gets the number of students.
     *
     * @return {@link ClassCourse#numberOfStudentsInClass}
     */
    public final int getNumberOfStudentsInClass() {
        return numberOfStudentsInClass;
    }

    /**
     * Gets Shifts with free spots.
     *
     * @return {@link ClassCourse#shiftsWithFreeSpots}
     */
    public final int getShiftsWithFreeSpots() {
        return shiftsWithFreeSpots;
    }

    /**
     * Gets Shifts with more students than the capacity.
     *
     * @return {@link ClassCourse#shiftsWithMoreThanTheCapacity}
     */
    public final int getShiftsWithMoreThanTheCapacity() {
        return shiftsWithMoreThanTheCapacity;
    }

    /**
     * Gets weekday.
     *
     * @return {@link ClassCourse#weekday}
     */
    public final String getWeekday() {
        return weekday;
    }

    /**
     * Gets beginning hour of the class.
     *
     * @return {@link ClassCourse#beginningHour}
     */
    public final String getBeginningHour() {
        return beginningHour;
    }

    /**
     * Gets end hour.
     *
     * @return {@link ClassCourse#endHour}
     */
    public final String getEndHour() {
        return endHour;
    }

    /**
     * Gets moth day.
     *
     * @return {@link ClassCourse#monthDay}
     */
    public final String getMonthDay() {
        return monthDay;
    }

    /**
     * Gets asked characteristics.
     *
     * @return {@link ClassCourse#askedCharacteristics}
     */
    public final String getAskedCharacteristics() {
        return askedCharacteristics;
    }

    /**
     * Gets classroom.
     *
     * @return {@link ClassCourse#classroom}
     */
    public final String getClassroom() {
        return classroom;
    }

    /**
     * Gets capacity.
     *
     * @return {@link ClassCourse#capacity}
     */
    public final String getCapacity() {
        return capacity;
    }

    /**
     * Gets real characteristics
     *
     * @return {@link ClassCourse#realCharacteristics}
     */
    public final LinkedList<String> getRealCharacteristics() {
        return realCharacteristics;
    }

    //endregion

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

    public final String toCSVString() {
        return courses +
                "," + String.join("," , units) +
                ",'" + shift + '\'' +
                "," + classesOfCourse +
                ",'" + numberOfStudentsInClass + '\'' +
                ",'" + shiftsWithFreeSpots + '\'' +
                ",'" + shiftsWithMoreThanTheCapacity + '\'' +
                ",'" + weekday + '\'' +
                ",'" + beginningHour + '\'' +
                ",'" + endHour + '\'' +
                ",'" + monthDay + '\'' +
                ",'" + askedCharacteristics + '\'' +
                ",'" + classroom + '\'' +
                ",'" + capacity + '\'' +
                "," + realCharacteristics
                ;
    }

}
