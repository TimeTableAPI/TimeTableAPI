package Models;

import java.util.LinkedList;

public class ClassRoom {
    /**
     * <p>ClassRoom is a class used to hold all the proprieties belonging to the classRoom of a School or University </p>
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
     * <p>Depending on the position in the list the {@link Boolean} values have certain meaning, which follow the next order: {@link ClassRoom#NUMBER_OF_CHARACTERISTICS}</p>
     *
     * */
    public static final int NUMBER_OF_CHARACTERISTICS = 30;
    public static final String[] CHARACTERISTICS_LIST = new String[]{"Anfiteatro aulas", "Apoio técnico eventos", "Arq 1", "Arq 2", "Arq 3", "Arq 4", "Arq 5", "Arq 6", "Arq 9", "BYOD (Bring Your Own Device)", "Focus Group", "Horário sala visível portal público", "Laboratório de Arquitectura de Computadores I", "Laboratório de Arquitectura de Computadores II", "Laboratório de Bases de Engenharia", "Laboratório de Electrónica", "Laboratório de Informática", "Laboratório de Jornalismo", "Laboratório de Redes de Computadores I", "Laboratório de Redes de Computadores II", "Laboratório de Telecomunicações", "Sala Aulas Mestrado", "Sala Aulas Mestrado Plus", "Sala NEE", "Sala Provas", "Sala Reunião", "Sala de Arquitectura", "Sala de Aulas normal", "videoconferencia", "Átrio"};

    final LinkedList<Boolean> characteristics;

    final String building;
    final String classroomName;
    final int normal_capacity;
    final int exam_capacity;
    final int number_characteristics;

    public ClassRoom(
            LinkedList<Boolean> characteristics,
            String building,
            String classroomName,
            int normal_capacity,
            int exam_capacity,
            int number_characteristics
    ) {
        this.characteristics = characteristics;
        this.building = building;
        this.classroomName = classroomName;
        this.normal_capacity = normal_capacity;
        this.exam_capacity = exam_capacity;
        this.number_characteristics = number_characteristics;
    }

    @Override
    public final String toString() {
        return "ClassRoom{" +
                " building='" + building + '\'' +
                ", classroomName='" + classroomName + '\'' +
                ", normal_capacity=" + normal_capacity +
                ", exam_capacity=" + exam_capacity +
                ", number_characteristics=" + number_characteristics +
                ", characteristics=" + characteristics +
                '}';
    }
/**
 * @return {@link ClassRoom#CHARACTERISTICS_LIST}
 * */
    public LinkedList<Boolean> getCharacteristics() {
        return characteristics;
    }

/** Retrieves the Building where the classRoom is located
 * @return {@link ClassRoom#building}
 * */
    public String getBuilding() {
        return building;
    }

/**Retrieves the classRoom's name
 * @return {@link ClassRoom#classroomName}
 * */
    public String getClassroomName() {
        return classroomName;
    }

/**Retrieves the normal capacity of the classRoom
 * @return {@link ClassRoom#normal_capacity}
 * */
    public int getNormal_capacity() {
        return normal_capacity;
    }

/**Retrieves the capacity of the classRoom if an exam where to occur in it
 * @return {@link ClassRoom#exam_capacity}
 * */
    public int getExam_capacity() {
        return exam_capacity;
    }

/**Retrieves the number of characteristics/proprieties the classRoom fulfills
 * @return {@link ClassRoom#number_characteristics}
 * */
    public int getNumber_characteristics() {
        return number_characteristics;
    }
/**
 *<p> Retrieves whether or not the classRoom fulfills the input characteristic</p>
 * <p> If the input characteristic is not in the static {@link ClassRoom#CHARACTERISTICS_LIST} the method throws {@link IllegalArgumentException} </p>
 *
 * @param characteristic a String that represents a characteristic the classRoom may have, like if it is a Lab for example, the full list can be seen here {@link ClassRoom#CHARACTERISTICS_LIST}
 * @return <b>true</b> if classRoom fulfills the characteristic and <b>false</b> if it doesn't
 * */
    public boolean hasCharacteristic(String characteristic) throws IllegalArgumentException{
        for(int i =0 ;i<ClassRoom.CHARACTERISTICS_LIST.length;i++){
            if(ClassRoom.CHARACTERISTICS_LIST[i].equals(characteristic)){
                return this.characteristics.get(i);
            }
        }
        throw new IllegalArgumentException();
    }

    /*  final boolean anfiteatro;
    final boolean apoio_tecnico;
    final boolean arq1;
    final boolean arq2;
    final boolean arq3;
    final boolean arq4;
    final boolean arq5;
    final boolean arq6;
    final boolean arq9;
    final boolean byod;
    final boolean focus_group;
    final boolean horario_visible_public;
    final boolean computer_arquitecture_lab_1;
    final boolean computer_arquitecture_lab_2;
    final boolean engineering_basis_lab;
    final boolean electronics_lab;
    final boolean informatics_lab;
    final boolean journalism_lab;
    final boolean computer_network_lab_1;
    final boolean computer_network_lab_2;
    final boolean telecomunications_lab;
    final boolean masters_classroom;
    final boolean masters_classroom_plus;
    final boolean nee_classroom;
    final boolean prova_classroom;
    final boolean meeeting_classroom;
    final boolean arquitecture_classroom;
    final boolean normal_classroom;
    final boolean videoconference_classroom;
    final boolean atrium;


    public ClassRoom(String building, String classroomName, int normal_capacity, int exam_capacity, int number_characteristics, LinkedList<Boolean> characteristics, boolean anfiteatro, boolean apoio_tecnico, boolean arq1, boolean arq2, boolean arq3, boolean arq4, boolean arq5, boolean arq6, boolean arq9, boolean byod, boolean focus_group, boolean horario_visible_public, boolean computer_arquitecture_lab_1, boolean computer_arquitecture_lab_2, boolean engineering_basis_lab, boolean electronics_lab, boolean informatics_lab, boolean journalism_lab, boolean computer_network_lab_1, boolean computer_network_lab_2, boolean telecomunications_lab, boolean masters_classroom, boolean masters_classroom_plus, boolean nee_classroom, boolean prova_classroom, boolean meeeting_classroom, boolean arquitecture_classroom, boolean normal_classroom, boolean videoconference_classroom, boolean atrium){
        this.building = building;
        this.classroomName = classroomName;
        this.normal_capacity = normal_capacity;
        this.exam_capacity = exam_capacity;
        this.number_characteristics = number_characteristics;
        this.characteristics = characteristics;
        this.anfiteatro = anfiteatro;
        this.apoio_tecnico = apoio_tecnico;
        this.arq1 = arq1;
        this.arq2 = arq2;
        this.arq3 = arq3;
        this.arq4 = arq4;
        this.arq5 = arq5;
        this.arq6 = arq6;
        this.arq9 = arq9;
        this.byod = byod;
        this.focus_group = focus_group;
        this.horario_visible_public = horario_visible_public;
        this.computer_arquitecture_lab_1 = computer_arquitecture_lab_1;
        this.computer_arquitecture_lab_2 = computer_arquitecture_lab_2;
        this.engineering_basis_lab = engineering_basis_lab;
        this.electronics_lab = electronics_lab;
        this.informatics_lab = informatics_lab;
        this.journalism_lab = journalism_lab;
        this.computer_network_lab_1 = computer_network_lab_1;
        this.computer_network_lab_2 = computer_network_lab_2;
        this.telecomunications_lab = telecomunications_lab;
        this.masters_classroom = masters_classroom;
        this.masters_classroom_plus = masters_classroom_plus;
        this.nee_classroom = nee_classroom;
        this.prova_classroom = prova_classroom;
        this.meeeting_classroom = meeeting_classroom;
        this.arquitecture_classroom = arquitecture_classroom;
        this.normal_classroom = normal_classroom;
        this.videoconference_classroom = videoconference_classroom;
        this.atrium = atrium;
    }
*/

}
