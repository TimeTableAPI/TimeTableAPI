package pt.iscte.asd.projectn3.group11.models;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>ClassRoom</h1>
 * <p>Class used to hold all the proprieties belonging to the classRoom of a School or University </p>
 * <p>To hold all of these proprieties the class has some {@link String} and {@link Integer} final variables like the ones below</p>
 * <ul>
 *     <li>{@link String} Building</li>
 *     <li>{@link String} Name</li>
 *     <li>{@link Integer} Normal capacity</li>
 *     <li>{@link Integer} Exam capacity</li>
 * </ul>
 * <p>Then it also has a list of {@link Boolean} values representing if the classroom has or does not have specific proprieties like:</p>
 * <ul>
 *     <li>If it is an Anfiteatro</li>
 *     <li>If it is a Lab</li>
 *     <li>If it is for Events</li>
 *     <li>If it is BYOD</li>
 *     <li>etc...</li>
 * </ul>
 *
 * <p>Depending on the position in the list the {@link Boolean} values have certain meaning, which follow the next order: {@link Classroom#NUMBER_OF_CHARACTERISTICS}</p>
 *
 * */
public class Classroom {

    public static final int NUMBER_OF_CHARACTERISTICS = 30;
    public static final String[] CHARACTERISTICS_LIST = new String[]{"Anfiteatro aulas", "Apoio técnico eventos", "Arq 1", "Arq 2", "Arq 3", "Arq 4", "Arq 5", "Arq 6", "Arq 9", "BYOD (Bring Your Own Device)", "Focus Group", "Horário sala visível portal público", "Laboratório de Arquitectura de Computadores I", "Laboratório de Arquitectura de Computadores II", "Laboratório de Bases de Engenharia", "Laboratório de Electrónica", "Laboratório de Informática", "Laboratório de Jornalismo", "Laboratório de Redes de Computadores I", "Laboratório de Redes de Computadores II", "Laboratório de Telecomunicações", "Sala Aulas Mestrado", "Sala Aulas Mestrado Plus", "Sala NEE", "Sala Provas", "Sala Reunião", "Sala de Arquitectura", "Sala de Aulas normal", "videoconferencia", "Átrio"};

    //region MEMBERS

    private final LinkedList<Boolean> characteristics;

    private final String building;
    private final String classroomName;
    private final int normalCapacity;
    private final int examCapacity;
    private final int numberCharacteristics;

    //endregion

    //region CONSTRUCTORS

    private Classroom(Builder builder){
        this.characteristics = builder.characteristics;
        this.building = builder.building;
        this.classroomName = builder.classroomName;
        this.normalCapacity = builder.normalCapacity;
        this.examCapacity = builder.examCapacity;
        this.numberCharacteristics = builder.numberCharacteristics;
    }

    //endregion

    //region BUILDER

    public static class  Builder{
        private LinkedList<Boolean> characteristics;
        private String building;
        private String classroomName;
        private int normalCapacity;
        private int examCapacity;
        private int numberCharacteristics;

        public Builder characteristics(final LinkedList<Boolean> characteristics){
            this.characteristics = characteristics;
            return this;
        }
        public Builder building(final String building){
            this.building = building;
            return this;
        }
        public Builder classroomName(final String classroomName){
            this.classroomName = classroomName;
            return this;
        }
        public Builder normalCapacity(final int normalCapacity){
            this.normalCapacity = normalCapacity;
            return this;
        }
        public Builder examCapacity(final int examCapacity){
            this.examCapacity = examCapacity;
            return this;
        }
        public Builder numberCharacteristics(final int numberCharacteristics){
            this.numberCharacteristics = numberCharacteristics;
            return this;
        }

        public Classroom build(){
            return new Classroom(this);
        }


    }

    //endregion

    //region GETTERS
/**Retrieves the Characteristics this ClassRoom has as a list of Booleans
 * @return {@link Classroom#characteristics}
 * */
    public final LinkedList<Boolean> getCharacteristics() {
        return characteristics;
    }

/** Retrieves the Building where the classRoom is located
 * @return {@link Classroom#building}
 * */
    public final String getBuilding() {
        return building;
    }

/**Retrieves the classRoom's name
 * @return {@link Classroom#classroomName}
 * */
    public final String getClassroomName() {
        return classroomName;
    }

/**Retrieves the normal capacity of the classRoom
 * @return {@link Classroom#normalCapacity}
 * */
    public final int getNormalCapacity() {
        return normalCapacity;
    }

/**Retrieves the capacity of the classRoom if an exam where to occur in it
 * @return {@link Classroom#examCapacity}
 * */
    public final int getExamCapacity() {
        return examCapacity;
    }

/**Retrieves the number of characteristics/proprieties the classRoom fulfills
 * @return {@link Classroom#numberCharacteristics}
 * */
    public final int getNumberCharacteristics() {
        return numberCharacteristics;
    }

/**
 *<p> Retrieves whether or not the classRoom fulfills the input characteristic</p>
 * <p> If the input characteristic is not in the static {@link Classroom#CHARACTERISTICS_LIST} the method throws {@link IllegalArgumentException} </p>
 *
 * @param characteristic a String that represents a characteristic the classRoom may have, like if it is a Lab for example, the full list can be seen here {@link Classroom#CHARACTERISTICS_LIST}
 * @return <b>true</b> if classRoom fulfills the characteristic and <b>false</b> if it doesn't
 * */
    public final boolean hasCharacteristic(final String characteristic) throws IllegalArgumentException {
        for (int i = 0; i < Classroom.CHARACTERISTICS_LIST.length; i++) {
            if (Classroom.CHARACTERISTICS_LIST[i].equals(characteristic)) {
                return this.characteristics.get(i);
            }
        }
        return false;
    }
/**
 *<p> Same as {@link Classroom#hasCharacteristic(String)} but for a List of Characteristics</p>
 *
 * @param characteristics a List of Strings that represents a characteristic the classRoom may have, like if it is a Lab for example, the full list can be seen here {@link Classroom#CHARACTERISTICS_LIST}
 * @return <b>true</b> if classRoom fulfills the characteristic and <b>false</b> if it doesn't
 * */
    public final boolean hasALLCharacteristics(final List<String> characteristics) throws IllegalArgumentException{
        boolean result= true;
        for(String characteristic: characteristics){
            result = result && this.hasCharacteristic(characteristic);
        }
        return result;
    }

    /**
     *<p> Method to check wether the classroom is located in the specified building or not</p>
     *
     * @param building a String representing the building where the classRoom is located
     * @return <b>true</b> if  {@link Classroom#building} is equal to the given parameter and <b>false</b> if it is not equal
     * */
    public final boolean isInBuilding(final String building) {
        return this.building.equals(building);
    }

    /**
     *<p> Same as {@link Classroom#isInBuilding(String)} but for a List of Buildings</p>
     *
     * @param buildings a List of Strings representing the buildings where the classRoom can be located in
     * @return <b>true</b> if  {@link Classroom#building} is equal to the given parameter and <b>false</b> if it is not equal
     * */
    public final boolean isInANYBuilding(final List<String> buildings) {
        boolean result = false;
        for(int i = 0; i< buildings.size() && !result ;i++){
            result = result || this.isInBuilding(buildings.get(i));
        }
        return result;
    }

    /**
     *<p> Method to check wether the classroom's name is equal to the given one </p>
     *
     * @param name a String representing the building where the classRoom is located
     * @return <b>true</b> if  {@link Classroom#classroomName} is equal to the given parameter and <b>false</b> if it is not equal
     * */
    public final boolean isNamed(final String name) {
        return this.classroomName.equals(name);
    }
    //endregion

    @Override
    public final String toString() {
        return "ClassRoom{" +
                " building='" + building + '\'' +
                ", classroomName='" + classroomName + '\'' +
                ", normal_capacity=" + normalCapacity +
                ", exam_capacity=" + examCapacity +
                ", number_characteristics=" + numberCharacteristics +
                ", characteristics=" + characteristics +
                '}';
    }

}
