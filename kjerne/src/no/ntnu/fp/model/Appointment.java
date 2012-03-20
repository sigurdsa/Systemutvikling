package no.ntnu.fp.model;

import java.util.Date;
/**
 * 
 * @author Hilde Kristin
 * 
 */
public class Appointment {
	
	private Date startTime;
	private Date endTime;
	private String description;
	private Meetingroom meetingRoom;
	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setLocation(Meetingroom meetingRoom){
		this.meetingRoom = meetingRoom;
	}
	
	public Date getStart(){
		return startTime;
	}
	
	public Date getEnd(){
		return endTime;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Meetingroom getLocation(){
		return meetingRoom;
	}
	}