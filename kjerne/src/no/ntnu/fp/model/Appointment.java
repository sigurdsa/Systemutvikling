package no.ntnu.fp.model;

import java.util.Date;


/**
 * 
 * @author Hilde Kristin
 * 
 */
public class Appointment extends AbstractAppointment{
	
	String place;

	public Appointment(Date startTime, Date endTime, String description, String place) {
		super(startTime, endTime, description);
		this.place = place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getPlace(){
		return place;
	}

		
	}