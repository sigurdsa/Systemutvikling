package no.ntnu.fp.model;

import java.util.Date;

public abstract class AbstractAppointment {
	
	private Date startTime;
	private Date endTime;
	private String description;
	
	public AbstractAppointment(Date startTime, Date endTime, String description){
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public void setDescription(String description){
		this.description = description;
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
	

}
