package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.LogicOperation;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

import java.util.*;

/**
 * <h1>ClassRoom service</h1>
 * <p>Provides Getters for specific ClassRooms</p>
 */
public class ClassroomService {

	public static final int INFINITY = 999999999;

	/**
	 * Getter for specified capacity
	 *
	 * @param capacity integer
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 */
	public static List<Classroom> getWithCapacity(List<Classroom> classrooms, int capacity) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.getNormalCapacity() >= capacity) {
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for specified Examcapacity
	 *
	 * @param capacity integer
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 */
	public static List<Classroom> getWithExamCapacity(List<Classroom> classrooms, int capacity) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.getExamCapacity() >= capacity) {
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for specified characteristics
	 *
	 * @param characteristics List<String>
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirements
	 */
	public static List<Classroom> getWithCharacteristics(List<Classroom> classrooms, List<String> characteristics) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.hasALLCharacteristics(characteristics)) {
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 *
	 * @param characteristic String
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 */
	public static List<Classroom> getWithCharacteristic(List<Classroom> classrooms, String characteristic) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.hasCharacteristic(characteristic)) {
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 *
	 * @param building String
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 */
	public static List<Classroom> getInBuilding(List<Classroom> classrooms, String building) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.isInBuilding(building)) {
				result.add(x);
			}
		}
		return result;
	}

	/**
	 * Getter for a specified characteristic
	 *
	 * @param buildings List<String>
	 * @return {@link LinkedList<Classroom>} with all the ClassRooms that fulfill the requirement
	 */
	public static List<Classroom> getInAnyBuilding(List<Classroom> classrooms, List<String> buildings) {
		final LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x : classrooms) {
			if (x.isInANYBuilding(buildings)) {
				result.add(x);
			}
		}
		return result;
	}

	public static List<Classroom> get(List<Classroom> classrooms, RequestInformation requestInformation, LogicOperation logicOp) {
		List<List<Classroom>> requestClassRooms = new LinkedList<>();
		requestClassRooms.add(ClassroomService.getWithCapacity(classrooms, requestInformation.capacity));
		requestClassRooms.add(ClassroomService.getWithExamCapacity(classrooms, requestInformation.examCapacity));
		requestClassRooms.add(ClassroomService.getWithCharacteristics(classrooms, requestInformation.characteristics));
		requestClassRooms.add(ClassroomService.getInAnyBuilding(classrooms, requestInformation.buildings));

		List<Classroom> agregatedRequestClassRooms = new LinkedList<>();
		for (List<Classroom> requestClassRoomList : requestClassRooms) {
			for (Classroom cls : requestClassRoomList) {
				if (!agregatedRequestClassRooms.contains(cls)) {
					agregatedRequestClassRooms.add(cls);
				}
			}
		}
		List<Classroom> finalrequestClassRooms = new LinkedList<>();
		for (Classroom cls : agregatedRequestClassRooms) {
			boolean isClassRoomPresentInAll = true;
			for (List<Classroom> requestClassRoomList : requestClassRooms) {
				boolean listContain = requestClassRoomList.contains(cls);
				isClassRoomPresentInAll = logicOp.op(isClassRoomPresentInAll, listContain);
			}
			if (isClassRoomPresentInAll) {
				finalrequestClassRooms.add(cls);
			}
		}

		return finalrequestClassRooms;

	}

	public static ClassCourse allocate(ClassCourse classCourse, List<Classroom> classrooms, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap, Float percentageCharacteristicsNeeded){
		Date date = classCourse.getDate();
		TimeShift timeShift = classCourse.getBeginningHour();
		for( Classroom classroom: classrooms){
			if(checkClassRoomAvailability(classroom,date,timeShift,classRoomAvailabilityMap) &&
					checkClassRoomFeatures(classroom, classCourse,percentageCharacteristicsNeeded)
			){
				classCourse.setClassroom(classroom);
			}
		}

		return classCourse;
	}

	private static boolean checkClassRoomFeatures(Classroom classRoom, ClassCourse classCourse, Float percentageCharacteristicsNeeded) {
		final boolean  capacity_bool = classRoom.getNormalCapacity() < classCourse.getNumberOfStudentsInClass();
		int characteristics_counter = 0;
		LinkedList<String> askedCharacteristics = classCourse.getAskedCharacteristics();
		for(String classroomCharacteristic: classRoom.getCharacteristicsToString()){
			if(askedCharacteristics.contains(classroomCharacteristic)){
				characteristics_counter ++;
			}
		}
		//boolean  characteristics_bool = classCourse.getAskedCharacteristics().containsAll(classRoom.getCharacteristicsToString());
		final float includedCharacteristicsPercentage = (characteristics_counter / askedCharacteristics.size());
		boolean  characteristics_bool = includedCharacteristicsPercentage > percentageCharacteristicsNeeded;

		return capacity_bool && characteristics_bool;
	}

	private static boolean checkClassRoomAvailability(Classroom classRoom, Date date, TimeShift timeShift, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap) {
		return !classRoomAvailabilityMap.get(date).get(timeShift).contains(classRoom);
	}

	//endregion
	//region OPERATIONS
	//region Request
	public static final class RequestInformation {
		final int capacity;
		final int examCapacity;
		final List<String> characteristics;
		final List<String> buildings;

		public RequestInformation(Builder builder) {
			this.capacity = builder.capacity;
			this.examCapacity = builder.examCapacity;
			this.characteristics = builder.characteristics;
			this.buildings = builder.buildings;

		}

		public static final class Builder {
			int capacity = INFINITY;
			int examCapacity = INFINITY;
			List<String> characteristics = new ArrayList<>();
			List<String> buildings = new ArrayList<>();

			public Builder capacity(final int capacity) {
				this.capacity = capacity;
				return this;
			}

			public Builder examCapacity(final int examCapacity) {
				this.examCapacity = examCapacity;
				return this;
			}

			public Builder characteristics(List<String> characteristics) {
				this.characteristics = characteristics;
				return this;
			}

			public Builder characteristics(String characteristics) {
				this.characteristics = Collections.singletonList(characteristics);
				return this;
			}

			public Builder buildings(List<String> buildings) {
				this.buildings = buildings;
				return this;
			}

			public Builder buildings(String buildings) {
				this.buildings = Collections.singletonList(buildings);
				return this;
			}

			public RequestInformation build() {
				return new RequestInformation(this);
			}

		}

	}


	//endregion
}