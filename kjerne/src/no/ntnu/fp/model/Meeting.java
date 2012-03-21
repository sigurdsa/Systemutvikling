package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Øyvind
 * 
 */
public class Meeting extends AbstractAppointment {
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
	private Meetingrequest meetingRequests;
	
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
	
	public Person getMeetingLeader() {
		return meetingLeader;
	}

	public void setMeetingLeader(Person meetingLeader) {
		this.meetingLeader = meetingLeader;
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
