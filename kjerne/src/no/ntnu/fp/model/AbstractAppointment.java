package no.ntnu.fp.model;

import java.util.Date;

public abstract class AbstractAppointment {
	private int id;
	private Date startTime;
	private Date endTime;
	private String description;
	
	public AbstractAppointment(int id,Date startTime, Date endTime, String description){
		this.id = id;
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
	
	public Date getStartTime(){
		return startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public String getDescription(){
		return description;
	}
	

}
