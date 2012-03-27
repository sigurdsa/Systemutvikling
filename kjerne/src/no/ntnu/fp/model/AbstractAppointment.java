package no.ntnu.fp.model;

import java.util.Date;

public abstract class AbstractAppointment {
	protected int id;
	protected Date startTime;
	protected Date endTime;
	protected String description;
	
	public AbstractAppointment(int id,String startTime, String endTime, String description){
		this.id = id;
		Date start = new Date();
		start.setYear(Integer.parseInt(startTime.substring(0, 3)));
		start.setMonth(Integer.parseInt(startTime.substring(5, 6)));
		start.setDate(Integer.parseInt(startTime.substring(8, 9)));
		start.setHours(Integer.parseInt(startTime.substring(11, 12)));
		start.setMinutes(Integer.parseInt(startTime.substring(14, 15)));
		Date slutt = new Date();
		slutt.setYear(Integer.parseInt(endTime.substring(0, 3)));
		slutt.setMonth(Integer.parseInt(endTime.substring(5, 6)));
		slutt.setDate(Integer.parseInt(endTime.substring(8, 9)));
		slutt.setHours(Integer.parseInt(endTime.substring(11, 12)));
		slutt.setMinutes(Integer.parseInt(endTime.substring(14, 15)));		
		this.startTime = start;
		this.endTime = slutt;
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
