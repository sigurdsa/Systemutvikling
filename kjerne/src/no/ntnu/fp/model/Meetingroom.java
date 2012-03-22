package no.ntnu.fp.model;

import java.util.Date;

public class Meetingroom {
	private String name;
	private int seats;
	
	public Meetingroom(String name, int seats) {
		this.name = name;
		this.seats = seats;		
	}

	public boolean isFree(Date start, Date end) {
		// TODO Auto-generated method stub
		return false;
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
