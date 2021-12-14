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

	//region GETTERS
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


	//endregion

	//region ORGANIZER

	/**
	 * <p>Organizes a List of ClassCourses into a TreeMap organized by Date which is then organized by ClassCourseStudentGroups </p>
	 * @param classCourses List<ClassCourse>
	 * @return TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>>
	 */
	public static  TreeMap<Date, HashMap<String, HashSet<ClassCourse>>> organizeClassCourseByClassStudents(List<ClassCourse> classCourses) {
		TreeMap<Date, HashMap<String, HashSet<ClassCourse>>> classCourseMap = new TreeMap<>();
		//LinkedList<ClassCourse> classCoursesClone = new LinkedList<>(classCourses);

	    for (ClassCourse classCourse : classCourses) {
		    classCourseMap.computeIfAbsent(classCourse.getDate(), k -> new HashMap<>());

				for (String s :	classCourse.getClassesOfCourse()) {
					classCourseMap.get(classCourse.getDate()).computeIfAbsent(s, k -> new HashSet<>());
					classCourseMap.get(classCourse.getDate()).get(s).add(classCourse);
				}
			}

	    return classCourseMap;
	}

	/**
	 * <p>Organizes a List of ClassCourses into a TreeMap organized by Date which is then organized by ClassCourse </p>
	 * @param classCourses List<ClassCourse>
	 * @return TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>>
	 */
	public static  TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> organizeClassCourseByClass(List<ClassCourse> classCourses) {
		TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> classCourseMap = new TreeMap<>();
		//LinkedList<ClassCourse> classCoursesClone = new LinkedList<>(classCourses);

	    for (ClassCourse classCourse : classCourses) {
		    classCourseMap.computeIfAbsent(classCourse.getDate(), k -> new HashMap<>());

			boolean foundClassInMap = false;
			for(ClassCourse classCourseInMap : classCourseMap.get(classCourse.getDate()).keySet() ){
				if(classCourseInMap.equals(classCourse)){
					classCourseMap.get(classCourse.getDate()).get(classCourseInMap).add(classCourse);
					foundClassInMap = true;
					break;
				}
			}
		    if(!foundClassInMap){
			    classCourseMap.get(classCourse.getDate()).computeIfAbsent(classCourse, k -> new HashSet<>());
				classCourseMap.get(classCourse.getDate()).get(classCourse).add(classCourse);
		    }
	    }
	    return classCourseMap;
	}

	/**
	 * <p>Organizes a List of ClassCourses into a TreeMap organized by Date which is then organized by TimeShift </p>
	 * @param classCourses List<ClassCourse>
	 * @return TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>>
	 */
	public static  TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> organizeClassCourseByDate(List<ClassCourse> classCourses) {
	    TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap = new TreeMap<>();
	    for (ClassCourse classCourse : classCourses) {
	        classCoursedateMap.computeIfAbsent(classCourse.getDate(), k -> new EnumMap<TimeShift, HashSet<ClassCourse>>(TimeShift.class));
	        classCoursedateMap.get(classCourse.getDate()).computeIfAbsent(classCourse.getBeginningHour(), k -> new HashSet<ClassCourse>());
	        classCoursedateMap.get(classCourse.getDate()).get(classCourse.getBeginningHour()).add(classCourse);
	    }
	    return classCoursedateMap;
	}

	/**
	 * <p>Creates a framework for organizing Classrooms based on a List of ClassCourses </p>
	 * <p> The framwework is a TreeMap organized by Date wihch is then organized by TimeShift </p>
	 * <p>
	 * @param classCoursedateMap TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>>
	 * @return TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>>
	 * </p>
	 */
	public static TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> organizeClassroomByDate(TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap) {
	    TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomDateMap = new TreeMap<>();

	    for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : classCoursedateMap.entrySet()) {
	        for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
	            classRoomDateMap.computeIfAbsent(date.getKey(), k -> new EnumMap<TimeShift, HashSet<Classroom>>(TimeShift.class));
	            classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k -> new HashSet<Classroom>());
	        }
	    }
	    return classRoomDateMap;
	}


	//endregion

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