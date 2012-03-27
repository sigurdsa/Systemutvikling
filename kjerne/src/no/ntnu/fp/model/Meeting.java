package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Øyvind
 * 
 */
public class Meeting extends AbstractAppointment {
	private int id;
	private Person meetingLeader;
	private Meetingroom meetingRoom;
	private ArrayList<Person> participants = new ArrayList<Person>();
	private ArrayList<Meetingrequest> meetingRequests = new ArrayList<Meetingrequest>();
	
	public Meeting(int id, Date startTime, Date endTime, String description, Person meetingLeader) {
		super(id,startTime, endTime, description);
		this.meetingLeader = meetingLeader;
	}

	public Meeting(int id, Date startTime, Date endTime, String description, Person meetingLeader, Meetingroom meetingRoom) {
		super(id, startTime,endTime,description);
		this.meetingLeader = meetingLeader;
		this.meetingRoom = meetingRoom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getMeetingLeader() {
		return meetingLeader;
	}

	public void addMeetingrequest(Meetingrequest mr){
		this.meetingRequests.add(mr);
	}
	
	public ArrayList<Meetingrequest> getMeetingRequests(){
		return this.meetingRequests;
	}


	public Meetingroom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(Meetingroom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public ArrayList<Person> getParticipants() {
		return participants;
	}
	
	/**
	 * Må legge til i participants, samt å sende innkallelse
	 */
	public void addParticipant(Person participant) {
		
	}
	
	/**
	 * Fjerner fra participants, samt fjerner innkallelse
	 */
	public void removeParticipant(Person participant){
		
	}
	
}
