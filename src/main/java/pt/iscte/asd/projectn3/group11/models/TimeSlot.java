package pt.iscte.asd.projectn3.group11.models;

import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;

public class TimeSlot {
	private TimeShift timeShift;
	private Date date;

	public TimeShift getTimeShift() {
		return timeShift;
	}

	public void setTimeShift(TimeShift timeShift) {
		this.timeShift = timeShift;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TimeSlot(TimeShift timeShift, Date date){
		this.timeShift=timeShift;
		this.date=date;
	}




}
