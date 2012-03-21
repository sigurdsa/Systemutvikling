package no.ntnu.fp.model;

import java.util.Date;


/**
 * 
 * @author Hilde Kristin
 * 
 */
public class Appointment extends AbstractAppointment{
	
	private Date startTime;
	private Date endTime;
	private String description;
	private Meetingroom meetingRoom;
	
	/**
	 * 
	 * @param startTime
	 */
	
	public Appointment(Date startTime, Date endTime, String description){
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;	
	}
	
	public Appointment(Date startTime, Date endTime, String description, Meetingroom meetingRoom){
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;	
		this.meetingRoom = meetingRoom;
	}
	

	}