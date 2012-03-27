package no.ntnu.fp.model;

import java.util.Date;

public class TextSerializer {
	Project p;
	
	public TextSerializer(Project p) {
		this.p = p;
	}
	/**
	 * 
	 * @param text - id;name;email;dateOfBirth;username;password;
	 * @return
	 */
	public Person textToPerson(String text) {
		String [] liste = text.split(";");
		return new Person(liste[0],liste[1],liste[2],liste[3],liste[4],liste[5]);
	}
	
	@SuppressWarnings("deprecation")
	public String personToText(Person p) {
		return "" + p.getId() + ";" + p.getName() + ";" + p.getEmail() + ";" + p.getDateOfBirth().getYear() + "-" + ((p.getDateOfBirth().getMonth() < 10) ? ("0"+p.getDateOfBirth().getMonth()) : p.getDateOfBirth().getMonth())  + "-" + ((p.getDateOfBirth().getDate() < 10) ? ("0"+p.getDateOfBirth().getDate()) : p.getDateOfBirth().getDate())  + ";" + p.getUsername() + ";" + p.getPassword(); 
	}
	
	/**
	 * 				 0		 1		  2				 3			4		 5
	 * @param text - moteID, LederID, meetingRoomID, startDate, endDate, description
	 * @return
	 */
	public Meeting textToMeeting(String text) {
		String[] liste = text.split(";");
		Person leder = p.getPersonById(Integer.parseInt(liste[1]));
		Meetingroom meetingroom = p.getMeetingroomById(Integer.parseInt(liste[2]));
		Date start = new Date(Integer.parseInt(liste[3].substring(0, 3), Integer.parseInt(liste[3].substring(5,6), Integer.parseInt(liste[3].substring(7,8)))));
		Date end = new Date(Integer.parseInt(liste[4].substring(0, 3), Integer.parseInt(liste[4].substring(5,6), Integer.parseInt(liste[4].substring(7,8)))));
		return new Meeting(Integer.parseInt(liste[0]),start,end,liste[5],leder,meetingroom);
	}
	
	public String meetingToText(Meeting m) {
		return "" + m.getId() + ";" + m.getMeetingLeader().getId() + ";" + m.getMeetingRoom().getId() + ";" + 
				m.getStartTime().getYear() + "-" + ((m.getStartTime().getMonth() < 10) ? ("0"+m.getStartTime().getMonth()) :m.getStartTime().getMonth())  + "-" + ((m.getStartTime().getDate() < 10) ? ("0"+m.getStartTime().getDate()) : m.getStartTime().getDate())  + ";" +
				m.getEndTime().getYear() + "-" + ((m.getEndTime().getMonth() < 10) ? ("0"+m.getEndTime().getMonth()) :m.getEndTime().getMonth())  + "-" + ((m.getEndTime().getDate() < 10) ? ("0"+m.getEndTime().getDate()) : m.getEndTime().getDate())  + ";" +
				m.getDescription();
	}
	
	public Meetingroom textToMeetingroom(String text) {
		String[] liste = text.split(";");
		//return new Meetingroom();
		return null;
	}
}
