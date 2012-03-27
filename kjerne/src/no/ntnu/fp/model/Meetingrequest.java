package no.ntnu.fp.model;

/**
 * 
 * @author Øyvind
 * 
 */
public class Meetingrequest {

	private Meeting meeting;
	private Person participant;
	
	/**
	 * 0 declined, 1 accepted, 2 not answered
	 */
	private int answer;
	
	public Meetingrequest(Meeting meeting, Person participant) {
		this.meeting = meeting;
		this.participant = participant;
		this.answer = 2;
	}
	public Meetingrequest(Meeting meeting, Person participant, String answer) {
		this.meeting = meeting;
		this.participant = participant;
		this.answer = Integer.parseInt(answer);
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
		if (answer == 1) return true;
		return false;
	}

	public void setAttending(boolean attending) {
		if (attending) this.answer = 1;
		else this.answer = 0;
	}

	public boolean hasAnswered() {
		if(answer != 2) return true;
		return false;
	}
	
	public void resetAnswer(){
		answer = 2;
	}
}
