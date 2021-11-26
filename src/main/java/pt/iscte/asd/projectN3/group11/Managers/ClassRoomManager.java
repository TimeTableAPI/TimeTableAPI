package pt.iscte.asd.projectN3.group11.Managers;

import pt.iscte.asd.projectN3.group11.models.Classroom;

import java.util.LinkedList;
import java.util.List;

public class ClassRoomManager {
	final LinkedList<Classroom> classrooms;

	public ClassRoomManager(LinkedList<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public final List<Classroom> getWithCapacity(int  capacity){
		LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
			if (x.getNormalCapacity() >= capacity){
				result.add(x);
			}
		}
		return result;
	}
	public final List<Classroom> getWithExamCapacity(int  capacity){
		LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
			if (x.getExamCapacity() >= capacity){
				result.add(x);
			}
		}
		return result;
	}

	public final List<Classroom> getWithCharacteristics(List<String> characteristics){
		LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
			if (x.hasALLCharacteristics(characteristics )){
				result.add(x);
			}
		}
		return result;
	}

	public final List<Classroom> getWithCharacteristic(String characteristic){
		LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
				if (x.hasCharacteristic(characteristic )){
					result.add(x);
				}
			}
		return result;
	}

	public final List<Classroom> getWithBuilding(String building){
		LinkedList<Classroom> result = new LinkedList<>();
		for (Classroom x :this.classrooms){
			if (x.isInBuilding(building)){
				result.add(x);
			}
		}
		return result;
	}
}
