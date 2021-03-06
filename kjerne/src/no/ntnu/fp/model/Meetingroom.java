package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Meetingroom {
	private int id;
	private String name;
	private int seats;
	private ArrayList<Meeting>	meetings = new ArrayList<Meeting>();
	
	public Meetingroom(String id, String name, String seats) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.seats = Integer.parseInt(seats);		
	}
	
	public ArrayList<Meeting> getMeetings(){
		return meetings;
	}
	
	public void removeMeeting(Meeting m){
		meetings.remove(m);
		
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void addMeetingToList(Meeting m){
		meetings.add(m);
		Collections.sort(meetings, new CustomComparator());
	}
	

	public boolean isFree(Date start, Date end) {
		int r1;
		int r2;
		for(int i = 0; i < meetings.size(); i++){
			r1 = (meetings.get(i)).getStartTime().compareTo(end);
			r2 = (meetings.get(i)).getEndTime().compareTo(start);
			if (!(r2 <0 || r1 > 0 )){
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
	
}
