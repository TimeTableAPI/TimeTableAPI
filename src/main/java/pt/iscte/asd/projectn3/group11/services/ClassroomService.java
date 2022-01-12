package pt.iscte.asd.projectn3.group11.services;

import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.services.util.LogicOperation;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

import java.util.*;

/**
 * <h1>ClassRoom service</h1>
 * <p>Provides Getters for specific ClassRooms</p>
 */
public class ClassroomService {


//region ALLOCATOR

	/**
	 *<p>Method that receives one classCourse and tries to allocate the best classroom available</p>
	 *<p>
	 * @param classCourse ClassCourse
	 * @param classrooms List<Classroom>
	 * @param classRoomAvailabilityMap TreeMap
	 * @param percentageCharacteristicsNeeded  Float
	 * @see ClassCourse
	 * @see Classroom
	 * @see TimeShift
	 * @see Date
	 * @see Float
	 * @see TreeMap
	 * @see EnumMap
	 * @see HashSet
	 *</p>
	 *
	 */
	public static void allocate(ClassCourse classCourse, List<Classroom> classrooms, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap, Float percentageCharacteristicsNeeded){
		Date date = classCourse.getDate();
		TimeShift timeShift = classCourse.getBeginningHour();
		List <Classroom> availableClassrooms = new LinkedList<>();
		classRoomAvailabilityMap.get(date).get(timeShift).forEach(classroom -> availableClassrooms.add(classroom));

		Iterator<Classroom> iterator = availableClassrooms.iterator();
		boolean foundClassroom = false;
		while (iterator.hasNext() && !foundClassroom) {
			Classroom availableClassroom = iterator.next();
			if (checkClassRoomFeatures(availableClassroom, classCourse, percentageCharacteristicsNeeded)) {
				classCourse.setClassroom(availableClassroom);
				classRoomAvailabilityMap.get(date).get(timeShift).remove(availableClassroom);
				foundClassroom = true;
			}
		}

		iterator = availableClassrooms.iterator();
		if (!foundClassroom){
			while (iterator.hasNext() && !foundClassroom) {
				Classroom availableClassroom = iterator.next();
				if (checkClassRoomCapacity(availableClassroom, classCourse)) {
					classCourse.setClassroom(availableClassroom);
					classRoomAvailabilityMap.get(date).get(timeShift).remove(availableClassroom);
					foundClassroom = true;
				}
			}
		}
	}

	/**
	 * <p>Checks whether the given Classroom has enough capacity for the given ClassCourse</p>
	 *<p>
	 * @param availableClassroom Classroom
	 * @param classCourse ClassCourse
	 * @return boolean value, true if availableClassroom has capacity for classCourse
	 * @see Classroom
	 * @see ClassCourse
	 *</p>
	 */
	private static boolean checkClassRoomCapacity(Classroom availableClassroom, ClassCourse classCourse) {
		return availableClassroom.getNormalCapacity() >= classCourse.getNumberOfStudentsInClass();
	}
	/**
	 * <p>Checks whether the given Classroom has the required percentage of features (Characteristics) for the given ClassCourse</p>
	 *<p>
	 * @param classRoom Classroom
	 * @param classCourse ClassCourse
	 * @param percentageCharacteristicsNeeded Float
	 * @return boolean value, true if availableClassroom has capacity for classCourse
	 *
	 * @see Classroom
	 * @see ClassCourse
	 *</p>
	 *
	 */
	private static boolean checkClassRoomFeatures(Classroom classRoom, ClassCourse classCourse, Float percentageCharacteristicsNeeded) {
		final boolean  capacity_bool = classRoom.getNormalCapacity() < classCourse.getNumberOfStudentsInClass();
		int characteristics_counter = 0;
		LinkedList<String> askedCharacteristics = classCourse.getAskedCharacteristics();
		for(String classroomCharacteristic: classRoom.getCharacteristicsToString()){
			if(askedCharacteristics.contains(classroomCharacteristic)){
				characteristics_counter ++;
			}
		}
		final float includedCharacteristicsPercentage = (characteristics_counter / askedCharacteristics.size());
		boolean  characteristics_bool = includedCharacteristicsPercentage > percentageCharacteristicsNeeded;

		return capacity_bool && characteristics_bool;
	}
	/**
	 * <p>Checks whether the given Classroom is asvailable for the given TimeShift and Date in the given TreeMap</p>
	 *<p>
	 * @param classRoom Classroom
	 * @param date Date
	 * @param timeShift TimeShift
	 * @param classRoomAvailabilityMap TreeMap
	 * @return boolean value representing if the classroom is available
	 *
	 * @see Classroom
	 * @see ClassCourse
	 *</p>
	 */
	private static boolean checkClassRoomAvailability(Classroom classRoom, Date date, TimeShift timeShift, TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomAvailabilityMap) {
		return !classRoomAvailabilityMap.get(date).get(timeShift).contains(classRoom);
	}
	//endregion

	//region ORGANIZER

	/**
	 * <p>Organizes a List of ClassCourses into a TreeMap organized by ClassCourseStudentGroups which is then organized by Date </p>
	 * @param classCourses List<ClassCourse>
	 * @return TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>>
	 */
	public static  TreeMap<String , HashMap<Date, HashMap<Integer,HashSet<ClassCourse>>>> organizeClassCourseByClassStudentsByDate(List<ClassCourse> classCourses) {
		TreeMap<String , HashMap<Date, HashMap<Integer,HashSet<ClassCourse>>>> classCourseMap = new TreeMap<>();

	    for (ClassCourse classCourse : classCourses) {
			for (String className :	classCourse.getClassesOfCourse()) {
		        classCourseMap.computeIfAbsent(className, k -> new HashMap<>());
				final Date date = classCourse.getDate();
				classCourseMap.get(className).computeIfAbsent(date, k -> new HashMap<>());

				final TimeShift beginningHour = classCourse.getBeginningHour();
				classCourseMap.get(className).get(date).computeIfAbsent(beginningHour.getId(), k -> new HashSet<>());
				classCourseMap.get(className).get(date).get(beginningHour.getId()).add(classCourse);
				}
			}

	    return classCourseMap;
	}
	/**
	 * <p>Organizes a List of ClassCourses into a TreeMap organized by Date which is then organized by ClassCourseStudentGroups </p>
	 * @param classCourses List<ClassCourse>
	 * @return TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>>
	 */
	public static  TreeMap<Date, HashMap<String, HashSet<ClassCourse>>> organizeClassCourseByDateByClassStudents(List<ClassCourse> classCourses) {
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
	public static synchronized TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> organizeClassCourseByDateByClassCourse(List<ClassCourse> classCourses) {
		TreeMap<Date, HashMap<ClassCourse, HashSet<ClassCourse>>> classCourseMap = new TreeMap<>();
		//LinkedList<ClassCourse> classCoursesClone = new LinkedList<>(classCourses);

	    for (ClassCourse classCourse : classCourses) {
		    classCourseMap.computeIfAbsent(classCourse.getDate(), k -> new HashMap<>());

			boolean foundClassInMap = false;
		    final HashMap<ClassCourse, HashSet<ClassCourse>> classCourseHashSetHashMap = classCourseMap.get(classCourse.getDate());
		    for(ClassCourse classCourseInMap : classCourseHashSetHashMap.keySet() ){
				if(classCourseInMap.equals(classCourse)){
					classCourseHashSetHashMap.computeIfAbsent(classCourseInMap, k -> new HashSet<>());

					final HashSet<ClassCourse> classCourses1 = classCourseHashSetHashMap.get(classCourseInMap);
					classCourses1.add(classCourse);
					foundClassInMap = true;
					break;
				}
			}
		    if(!foundClassInMap){
			    classCourseHashSetHashMap.computeIfAbsent(classCourse, k -> new HashSet<>());
				classCourseHashSetHashMap.get(classCourse).add(classCourse);
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
	public static TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> organizeClassroomByDate(TreeMap<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> classCoursedateMap, List<Classroom> classroomList) {
	    TreeMap<Date, EnumMap<TimeShift, HashSet<Classroom>>> classRoomDateMap = new TreeMap<>();

	    for (Map.Entry<Date, EnumMap<TimeShift, HashSet<ClassCourse>>> date : classCoursedateMap.entrySet()) {
	        for (Map.Entry<TimeShift, HashSet<ClassCourse>> hour : date.getValue().entrySet()) {
	            classRoomDateMap.computeIfAbsent(date.getKey(), k -> new EnumMap<TimeShift, HashSet<Classroom>>(TimeShift.class));
	            classRoomDateMap.get(date.getKey()).computeIfAbsent(hour.getKey(), k -> new HashSet<Classroom>(classroomList));

	        }
	    }
	    return classRoomDateMap;
	}


	//endregion

	//region Request

	/**
	 * <p>
	 * Object created to Store information about a request for the algorithm
	 * </p>
	 *
	 */
	@Deprecated
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
			int capacity = Integer.MAX_VALUE;
			int examCapacity = Integer.MAX_VALUE;
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