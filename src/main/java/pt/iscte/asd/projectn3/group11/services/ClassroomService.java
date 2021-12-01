package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>ClassRoom service</h1>
 * <p>Provides Getters for specific ClassRooms</p>
 */
public class ClassroomService {

	/**
	 * Getter for specified capacity
	 * @param capacity integer
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 * */
	public static final List<Classroom> getWithCapacity(List<Classroom> classrooms, int capacity){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
			if (x.getNormalCapacity() >= capacity){
				result.add(x);
			}
		}
		return result;
	}
	/**
	 * Getter for specified Examcapacity
	 * @param capacity integer
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 * */
	public static final List<Classroom> getWithExamCapacity(List<Classroom> classrooms, int capacity){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
			if (x.getExamCapacity() >= capacity){
				result.add(x);
			}
		}
		return result;
	}
	/**
	 * Getter for specified characteristics
	 * @param characteristics List<String>
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 * */
	public static final List<Classroom> getWithCharacteristics(List<Classroom> classrooms, List<String> characteristics){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
			if (x.hasALLCharacteristics(characteristics )){
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 * @param characteristic String
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 * */
	public static final List<Classroom> getWithCharacteristic(List<Classroom> classrooms, String characteristic){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
				if (x.hasCharacteristic(characteristic )){
					result.add(x);
				}
			}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 * @param building String
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 * */
	public static final List<Classroom> getInBuilding(List<Classroom> classrooms, String building){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
			if (x.isInBuilding(building)){
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 * @param buildings List<String>
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 * */
	public static final List<Classroom> getInAnyBuilding(List<Classroom> classrooms, List<String> buildings){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :classrooms){
			if (x.isInANYBuilding(buildings)){
				result.add(x);
			}
		}
		return result;
	}
}
