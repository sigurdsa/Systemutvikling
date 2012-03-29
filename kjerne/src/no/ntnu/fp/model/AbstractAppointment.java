package no.ntnu.fp.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractAppointment {
	protected int id;
	protected Date startTime;
	protected Date endTime;
	protected String description;
	
	public AbstractAppointment(int id,String startTime, String endTime, String description){
		this.id = id;
		int[] startdato = {Integer.parseInt(startTime.substring(0, 4)),Integer.parseInt(startTime.substring(5, 7)),Integer.parseInt(startTime.substring(8, 10)),Integer.parseInt(startTime.substring(11, 13)),Integer.parseInt(startTime.substring(14, 16))};
		Calendar c = Calendar.getInstance();
		c.set(startdato[0], startdato[1]-1,startdato[2],startdato[3],startdato[4],0);
		this.startTime = c.getTime();
		
		int[] enddato = {Integer.parseInt(endTime.substring(0, 4)),Integer.parseInt(endTime.substring(5, 7)),Integer.parseInt(endTime.substring(8, 10)),Integer.parseInt(endTime.substring(11, 13)),Integer.parseInt(endTime.substring(14, 16))};
		c.set(enddato[0], enddato[1]-1,enddato[2],enddato[3],enddato[4],0);
		this.endTime = c.getTime();
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
	
	public int getId() { 
		return this.id;
	}

}
