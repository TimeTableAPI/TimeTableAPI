package pt.iscte.asd.projectN3.group11.Managers;

import pt.iscte.asd.projectN3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

	/**
	 * <h1>Manager of ClassRooms</h1>
	 * <p>Encapsulates our list of {@link Classroom}</p>
	 * <p>Provides Getters for specific ClassRooms</p>
	 */
public class ClassRoomManager {

	final LinkedList<Classroom> classrooms;

	public ClassRoomManager(List<Classroom> classrooms) {
		this.classrooms = (LinkedList<Classroom>) classrooms;
	}

	//region GETTERS

	/**
	 * Getter for specified capacity
	 * @param capacity integer
	 * @return  {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 * */
	public final List<Classroom> getWithCapacity(int  capacity){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
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
	public final List<Classroom> getWithExamCapacity(int  capacity){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
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
	public final List<Classroom> getWithCharacteristics(List<String> characteristics){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
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
	public final List<Classroom> getWithCharacteristic(String characteristic){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
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
	public final List<Classroom> getInBuilding(String building){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
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
	public final List<Classroom> getInAnyBuilding(List<String> buildings){
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
			if (x.isInANYBuilding(buildings)){
				result.add(x);
			}
		}
		return result;
	}
	//endregion GETTERS
}
