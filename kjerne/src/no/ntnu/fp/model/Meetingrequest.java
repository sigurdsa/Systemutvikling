package no.ntnu.fp.model;

/**
 * 
 * @author Øyvind
 * 
 */
public class Meetingrequest {
	/**
	 * Det aktuelle møtet 
	 */
	private Meeting meeting;
	
	/**
	 * Den aktuelle personen
	 */
	private Person participant;
	
	/**
	 * True om deltakeren er med
	 */
	private boolean attending;
	
	/**
	 * Om personen har svart 
	 */
	private boolean hasAnswered;
	/**
	 * Konstruktør
	 */
	public Meetingrequest(Meeting meeting, Person participant) {
		this.meeting = meeting;
		this.participant = participant;
		attending = false; 
		hasAnswered = false;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Person getParticipant() {
		return participant;
	}

	public void setParticipant(Person participant) {
		this.participant = participant;
	}

	public boolean isAttending() {
		return attending;
	}

	public void setAttending(boolean attending) {
		this.attending = attending;
		hasAnswered = true;
	}

	public boolean hasAnswered() {
		return hasAnswered;
	}
}
