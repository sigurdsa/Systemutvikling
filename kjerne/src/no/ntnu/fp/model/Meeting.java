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
	/**
	 * Person møteleder
	 */
	private Person meetingLeader;

	/**
	 * Møterom 
	 */
	private Meetingroom meetingRoom;
	
	/**
	 * Deltakere
	 */
	private ArrayList<Person> participants = new ArrayList<Person>();
	
	/**
	 * Innkallelser
	 */
	private ArrayList<Meetingrequest> meetingRequests = new ArrayList<Meetingrequest>();
	
	/**
	 * Konstruktør - Legg inn det som trengs
	 */
	
	public Meeting(Date startTime, Date endTime, String description, Person meetingLeader) {
		super(startTime, endTime, description);
		this.meetingLeader = meetingLeader;
	}
	
	public Meeting(Date startTime, Date endTime, String description, Person meetingLeader, Meetingroom meetingRoom) {
		super(startTime, endTime, description);
		this.meetingLeader = meetingLeader;
		this.meetingRoom = meetingRoom;
	}
	
	/**
	 * 
	 * @param moteID
	 * @param lederID
	 * @param meetingRoomID
	 */
	public Meeting(String moteID, String string2, String string3) {
		// TODO Auto-generated constructor stub
		
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
