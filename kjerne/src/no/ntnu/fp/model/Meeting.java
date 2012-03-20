package no.ntnu.fp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Øyvind
 * 
 */
public class Meeting {
	/**
	 * Person møteleder
	 */
	private Person meetingLeader;
	
	/**
	 * Start
	 */
	private Date startTime;
	
	/**
	 * Slutt
	 */
	private Date endTime;
	
	/**
	 * Beskrivelse av møtet
	 */
	private String description;
	
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
	public Meeting() {
		
	}
	
	public Person getMeetingLeader() {
		return meetingLeader;
	}

	public void setMeetingLeader(Person meetingLeader) {
		this.meetingLeader = meetingLeader;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
