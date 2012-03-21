package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author �yvind
 * 
 */
public class Meeting extends AbstractAppointment {
	/**
	 * Person m�teleder
	 */
	private Person meetingLeader;

	/**
	 * M�terom 
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
	 * Konstrukt�r - Legg inn det som trengs
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
	 * M� legge til i participants, samt � sende innkallelse
	 */
	public void addParticipant(Person participant) {
		
	}
	
	/**
	 * Fjerner fra participants, samt fjerner innkallelse
	 */
	public void removeParticipant(Person participant){
		
	}
	
}