package no.ntnu.fp.model;

public class Room {

	private int seats;
	private String name;
	private boolean available;
	
	public Room(int _seats, String _name){
		this.seats = _seats;
		this.name = _name;
		this.available = true;
	}
	
	public int getSeats(){
		return this.seats;
	}
	
	public boolean getAvailable(){
		return this.available;
	}
	
}
