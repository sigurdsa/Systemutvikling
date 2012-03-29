package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import no.ntnu.fp.model.Meetingrequest;
import no.ntnu.fp.storage.Db;

/**
 * The <code>Project</code> class is a list of zero or more {@link Person} objects.
 * 
 * @author Thomas &Oslash;sterlie
 * @version $Revision: 1.9 $ - $Date: 2005/02/22 07:53:33 $
 *
 */
public class Project implements PropertyChangeListener {


	private ArrayList<Person> personList;
	private ArrayList<Meetingroom> meetingRooms;
	private ArrayList<Meeting> meetings;
	private ArrayList<Meetingrequest> meetingRequests;
	private ArrayList<Message> messages;
	private ArrayList<AbstractAppointment> appointments;
	
	private Db db;

	private PropertyChangeSupport propChangeSupp;

	Person loggedInAs = null;

	public Project() {
		// Leser inn fra textfil
		db = new Db();
		personList = db.getPersons();
		meetingRooms = db.getMeetingrooms();
		meetings = db.getMeetings(this);
		meetingRequests = db.getMeetingRequests(this);
		messages = db.getMessages(this);
		appointments = new ArrayList<AbstractAppointment>();
		Iterator itr = personList.iterator();
		while(itr.hasNext()) {
			Person p = (Person) itr.next();
			p.addCalendarList(db.getAppointments(p.getId()));
			appointments.addAll(db.getAppointments(p.getId()));
			// Legg til møter?
		}
		propChangeSupp = new java.beans.PropertyChangeSupport(this);
	}

	private ArrayList<AbstractAppointment> getMeetingsByPersonId(int id) {
		ArrayList<AbstractAppointment> liste = new ArrayList<AbstractAppointment>();
		Iterator itr = liste.iterator();
		while(itr.hasNext()){
			AbstractAppointment a = (AbstractAppointment) itr.next();
			
		}
		return liste;
	}

	public int getObjectId (ArrayList liste){
		Iterator itr = liste.iterator();
		int id = 1;
		while(itr.hasNext()) {
			Object o = itr.next();
			if (o instanceof Person) {
				Person p = (Person) o;
				if (id < p.getId()) id = p.getId();
			} else if (o instanceof Meeting) {
				Meeting m = (Meeting) o;
				if (id < m.getId()) id = m.getId();
			} else if (o instanceof Meetingroom) {
				Meetingroom m = (Meetingroom) o;
				if (id < m.getId()) id = m.getId();
			}
		}
		return id;
	}

	public void addToMeetings(Meeting m){
		meetings.add(m);
	}

	public void addToMeetingRoomsList(Meetingroom mr){
		meetingRooms.add(mr);
	}

	/**
	 * Returns the number of {@linkplain #addPerson(Person) <code>Person</code> objects
	 * registered} with this class.
	 * 
	 * @return The number of {@link Person} objects in this class.
	 */
	public int getPersonCount() {
		return personList.size();
	}

	/**
	 * Returns the {@link Person} object at the specified position in the list.
	 * 
	 * @param i Index of object to return.
	 * 
	 * @return The {@link Person} object at the specified position in the list.
	 */
	public Person getPerson(int i) {
		return (Person)personList.get(i);
	}

	public ArrayList<Person> getPersonList(){
		return personList;
	}

	/**
	 * Returns the index of the first occurrence of the specified object, or 
	 * -1 if the list does not contain this object.
	 * 
	 * @param obj Object to search for.
	 *
	 * @return The index in this list of the first occurrence of the specified element, 
	 * or -1 if this list does not contain this element.
	 */
	public int indexOf(Object obj) {
		return personList.indexOf(obj);
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.<P>
	 *
	 * @return A {@link java.util.Iterator} over the elements in this list in proper sequence.
	 * 
	 * @see java.util.Iterator <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/Iterator.html">java.util.Iterator</a>.
	 */
	public Iterator iterator() {
		return personList.iterator();
	}

	/**
	 * Adds a new {@link Person} object to the <code>Project</code>.<P>
	 * 
	 * Calling this method will invoke the 
	 * <code>propertyChange(java.beans.PropertyChangeEvent)</code> method on 
	 * all {@linkplain
	 * #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs}.  The {@link java.beans.PropertyChangeEvent}
	 * passed with the {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * method has the following characteristics:
	 * 
	 * <ul>
	 * <li>the <code>getNewValue()</code> method returns the {@link Person} object added</li>
	 * <li>the <code>getOldValue()</code> method returns <code>null</code></li>
	 * <li>the <code>getSource()</code> method returns this {@link Project} object
	 * </ul>
	 * 
	 * @param person The {@link Person} object added.
	 * 
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void addPerson(Person person) {
		personList.add(person);
		person.addPropertyChangeListener(this);
		propChangeSupp.firePropertyChange("person", null, person);
	}

	/**
	 * Removes the specified {@link Person} object from the <code>Project</code>.<P>
	 * 
	 * Calling this method will invoke the 
	 * <code>propertyChange(java.beans.PropertyChangeEvent)</code> method on 
	 * all {@linkplain
	 * #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs}.  The {@link java.beans.PropertyChangeEvent}
	 * passed with the {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * method has the following characteristics:
	 * 
	 * <ul>
	 * <li>the <code>getNewValue()</code> method returns an {@link java.lang.Integer} object 
	 *     with the index of the removed element</li>
	 * <li>the <code>getOldValue()</code> method returns the {@link Person} object added</li>
	 * <li>the <code>getSource()</code> method returns this {@link Project} object
	 * </ul>
	 * 
	 * @param person The {@link Person} object to be removed.
	 * 
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void removePerson(Person person) {
		int i = personList.indexOf(person);
		Integer index = new Integer(i);
		personList.remove(person);
		person.removePropertyChangeListener(this);
		propChangeSupp.firePropertyChange("person", person, index);
	}

	/**
	 * Add a {@link java.beans.PropertyChangeListener} to the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a {@link java.beans.PropertyChangeListener} from the listener list.
	 * 
	 * @param listener The {@link java.beans.PropertyChangeListener} to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propChangeSupp.removePropertyChangeListener(listener);
	}

	public void propertyChange(PropertyChangeEvent event) {
		propChangeSupp.firePropertyChange(event);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (super.equals(o))
			return true;

		if (o.getClass() != this.getClass())
			return false;

		Project aProject = (Project)o;

		if (aProject.getPersonCount() != getPersonCount())
			return false;

		Iterator it = this.iterator();
		while (it.hasNext()) {
			Person aPerson = (Person) it.next();
			if (aProject.indexOf(aPerson) < 0)
				return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String s = "project:\n";
		Iterator it = this.iterator();
		while (it.hasNext()) {
			s += it.next().toString() + "\n";
		}
		return s;
	}

	/**
	 * Login
	 * Går igjennom alle Person-objektene, og sjekker om brukernavn/passord eksiterer
	 * loggedInAs blir så satt til Person-objektet
	 * Returnerer true om innlogget
	 */
	public boolean login(String username, String password){
		Iterator itr = personList.iterator();
		while(itr.hasNext()){
			Person p = (Person) itr.next();
			if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
				this.loggedInAs = p;
				return true;
			}
		}
		return false;
	}

	public Person getLoggedInAs(){
		return loggedInAs;
	}

	/**
	 * Logger brukeren ut, setter loggedInAs til null
	 */
	public void logout() {
		loggedInAs = null;
	}

	/**
	 * Genererer en liste med møterom som er ledig i den gitte tidsperioden
	 * @return 
	 */
	public int generateWeekNumber(Date d){
		@SuppressWarnings("deprecation")
		int wn = (d.getMonth() * 30 - 30 + d.getDate()) / 7;
		return wn;
	}
	public ArrayList<Meetingroom> generateAvailableRooms(Date start, Date end, int nbr) {
		ArrayList<Meetingroom> rooms = new ArrayList<Meetingroom>();
		Iterator itr = meetingRooms.iterator();

		while(itr.hasNext()) {
			Meetingroom m = (Meetingroom) itr.next();
			if (m.isFree(start,end) && m.getSeats() >= nbr) rooms.add(m);
		}
		//
		return rooms;

	}


	public boolean isFree(Date start, Date end, ArrayList<AbstractAppointment> arrayList) {
		int r1;
		int r2;
		for(int i = 0; i < arrayList.size(); i++){
			r1 = (arrayList.get(i)).getStartTime().compareTo(end);
			r2 = (arrayList.get(i)).getEndTime().compareTo(start);
			if (!(r2 <0 || r1 > 0 )){
				return false;
			}
		}
		return true;
	}

	public Meeting createMeeting(String startTime, String endTime, String description, Meetingroom meetingroom) {		
		Meeting meeting = new Meeting(""+getMeetingID(),startTime, endTime, description, loggedInAs, meetingroom);
		meetings.add(meeting);
		loggedInAs.addMeeting(meeting);
		return meeting;
	}

	public ArrayList<Meeting> showAllCreatedMeetings() {
		ArrayList<Meeting> createdMeetings = new ArrayList<Meeting>();
		for(int i = 0; i < meetings.size(); i++){
			if(meetings.get(i).getMeetingLeader().equals(loggedInAs)){
				createdMeetings.add(meetings.get(i));
			}
		}
		return createdMeetings;	
	}

	public ArrayList<Appointment> showAllCreatedAppointments(){
		ArrayList<Appointment> createdAppointments = new ArrayList<Appointment>();
		for (int i= 0; i < loggedInAs.getCalendar().size(); i++){
			if(loggedInAs.getCalendar().get(i) instanceof Appointment){
				createdAppointments.add((Appointment) loggedInAs.getCalendar().get(i));
			}
		}
		return createdAppointments;
	}


	public ArrayList<AbstractAppointment> showCalendar(Person p, int week) {
		ArrayList<AbstractAppointment> liste = new ArrayList<AbstractAppointment>();
		if (p == null) { // Denne brukeren
			p = loggedInAs;
		}
		
		// Fikser fradato
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, 6*(week-1));
	
		now = c.getTime();
		if (week != 1 && now.getDay() != 0) { // Dersom ikke inneværende uke, fikser slik at man starter på mandag
			c.setTime(now);
			c.add(Calendar.DATE, -now.getDay()+1);
			now = c.getTime();
		}
		now.setHours(00);
		now.setMinutes(00);
		now.setSeconds(00);
		
		// Fikser tildato
		Date end = now;
		c.setTime(end);
		c.add(Calendar.DATE, 7-now.getDay());
		end = c.getTime();
		end.setHours(23);
		end.setMinutes(59);
		end.setSeconds(00);
		
		return p.getCalendar(now,end);	
	}


	public String printMeeting(AbstractAppointment m){

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		String startTime = formatter.format(m.getStartTime());
		String endTime = formatter.format(m.getEndTime());
		String room = ((Meeting) m).getMeetingRoom().getName();
		String descr = m.getDescription();

		return (startTime + "  " + endTime + "  " + room + "  " + descr);		

	}

	private ArrayList<Person> getMeetingRequestList() {
		// TODO Auto-generated method stub
		return null;
	}

	public int CountNewRequests() {
		int c = 0;
		Iterator itr = meetingRequests.iterator();
		while(itr.hasNext()) {
			Meetingrequest mr = (Meetingrequest) itr.next();
			if(mr.getParticipant() == loggedInAs && !mr.hasAnswered()) c++;
		}
		return c;
	}

	public void sendMeetingRequests(Meeting m) {
		for (int i = 0; i < m.getParticipants().size(); i++ ){
			Meetingrequest mr = new Meetingrequest(m, m.getParticipants().get(i));
			m.addMeetingrequest(mr);
		}		
	}

	public void cancelMeeting(Meeting meeting){
		for (int i = 0; i < meeting.getMeetingRequests().size(); i++){
			meeting.getMeetingRequests().get(i).setParticipant(null);

		}
	}

	public Person getPersonById(int id) {
		Iterator itr = personList.iterator();
		while(itr.hasNext()) {
			Person p = (Person)itr.next();
			if (p.getId() == id) return p;
		}
		return null;
	}

	public Meetingroom getMeetingroomById(int id) {
		Iterator itr = meetingRooms.iterator();
		while(itr.hasNext()) {
			Meetingroom p = (Meetingroom)itr.next();
			if (p.getId() == id) return p;
		}
		return null;
	}
	
	public Meeting getMeetingById(int id) {
		Iterator itr = meetings.iterator();
		while(itr.hasNext()) {
			Meeting p = (Meeting)itr.next();
			if (p.getId() == id) return p;
		}
		return null;
	}	
	
	public int getMeetingroomID() {
		int i = 1;
		Iterator itr = meetingRooms.iterator();
		while(itr.hasNext()) {
			if (((Meetingroom)itr.next()).getId() > i) i = ((Meetingroom)itr.next()).getId();
		}
		return i;
	}
	
	public int getMeetingID() {
		int i = 1;
		Iterator itr = meetings.iterator();
		while(itr.hasNext()) {
			Meeting p = (Meeting) itr.next();
			if (p.getId() >= i) i = p.getId() + 1;
		}
		return i;
	}
	
	public int getPersonID() {
		int i = 1;
		Iterator itr = personList.iterator();
		while(itr.hasNext()) {
			Person p = (Person) itr.next();
			if (p.getId() >= i) i = p.getId() + 1;
		}
		return i;
	}
	
	public void getFromDb() {
		
	}
	
	public void putToDb() {
		
	}

	public ArrayList<Meetingroom> getAvailableMeetingrooms(String startTime,String endTime, int nbr) {
		ArrayList<Meetingroom> liste = new ArrayList<Meetingroom>();
		liste.addAll(meetingRooms);
		int[] startdato = {Integer.parseInt(startTime.substring(0, 4)),Integer.parseInt(startTime.substring(5, 7)),Integer.parseInt(startTime.substring(8, 10)),Integer.parseInt(startTime.substring(11, 13)),Integer.parseInt(startTime.substring(14, 16))};
		int[] enddato = {Integer.parseInt(endTime.substring(0, 4)),Integer.parseInt(endTime.substring(5, 7)),Integer.parseInt(endTime.substring(8, 10)),Integer.parseInt(endTime.substring(11, 13)),Integer.parseInt(endTime.substring(14, 16))};
		Calendar c = Calendar.getInstance();
		c.set(startdato[0], startdato[1]-1,startdato[2],startdato[3],startdato[4],0);
		Date start = c.getTime();
		c.set(enddato[0], enddato[1]-1,enddato[2],enddato[3],enddato[4],0);
		Date end = c.getTime();
		
		Iterator itr = meetings.iterator();
		
		while(itr.hasNext()) {
			Meeting m = (Meeting) itr.next();
			if ((start.compareTo(m.getStartTime()) >0 && start.compareTo(m.getEndTime()) < 0) || (end.compareTo(m.getStartTime()) >0 && end.compareTo(m.getEndTime()) < 0)){
				liste.remove(m.getMeetingRoom());
			}
		}
		
		for (int i = 0; i < liste.size(); i++) {
			if(liste.get(i).getSeats() < nbr) liste.remove(i);
		}
		return liste;
	}

	public ArrayList<Person> getAvailablePersons(Meeting m) {
		ArrayList<Person> liste = new ArrayList<Person>();
		liste.addAll(personList);
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).isBusy(m.getStartTime(), m.getEndTime())) liste.remove(i);
		}
		return liste;
	}

	public void addMeetingrequest(Meeting m, Person p) {
		meetingRequests.add(new Meetingrequest(m, p, "2"));
	}

	public void changeMeeting(Meeting meeting, String start, String end) {
		// Fjerner fra alle kalendere
		Iterator itr = personList.iterator();
		
		while(itr.hasNext()) {
			Person p = (Person) itr.next();
			if (p.getCalendar().contains(meeting)) {
				p.removeFromCalendar(meeting);
				messages.add(new Message(loggedInAs,p, "Møtet som opprinnelig startet " + meeting.getStartTime() + " er nå flyttet til " + start + " - " + start.substring(11) + ". Du må på nytt svare på om det passer."));
			}
		}
		
		// Reset på svar
		itr = meetingRequests.iterator();
		while(itr.hasNext()) {
			Meetingrequest mr = (Meetingrequest) itr.next();
			if (mr.getMeeting() == meeting) mr.resetAnswer();
		}
		
		meeting.setStartTime(stringToDate(start));
		meeting.setStartTime(stringToDate(end));
		
	}
	
	private Date stringToDate(String startTime) {
		Calendar c = Calendar.getInstance();
		int[] startdato = {Integer.parseInt(startTime.substring(0, 4)),Integer.parseInt(startTime.substring(5, 7)),Integer.parseInt(startTime.substring(8, 10)),Integer.parseInt(startTime.substring(11, 13)),Integer.parseInt(startTime.substring(14, 16))};
		c.set(startdato[0], startdato[1]-1,startdato[2],startdato[3],startdato[4],0);
		return c.getTime();	
	}

	public void removeMeeting(Meeting meeting, String a) {
		Iterator itr = personList.iterator();
		
		while(itr.hasNext()) {
			Person p = (Person) itr.next();
			if (p.getCalendar().contains(meeting)) {
				p.removeFromCalendar(meeting);
				messages.add(new Message(loggedInAs,p, "Møtet som opprinnelig startet " + meeting.getStartTime() + " er nå slettet grunnet " + a));
			}
		}
		// Fjerne meetingreq
		Iterator it = meetingRequests.iterator();
		ArrayList<Meetingrequest> deleteList = new ArrayList<Meetingrequest>();
		while(it.hasNext()) {
			Meetingrequest mr = (Meetingrequest) it.next();
			if (mr.getMeeting() == meeting) deleteList.add(mr);
		}	
		itr = deleteList.iterator();
		while(itr.hasNext()) {
			meetingRequests.remove(itr.next());
		}
		
		meetings.remove(meeting);
	}

	public boolean changeAppointment(Appointment appointment, String start,String end) {
		if(loggedInAs.isBusy(stringToDate(start), stringToDate(end)))  return false;
		appointment.setStartTime(stringToDate(start));
		appointment.setEndTime(stringToDate(end));
		return true;
	}

	public void removeAppointment(Appointment appointment) {
		loggedInAs.removeFromCalendar(appointment);
	}

	public void addAppointment(String descr, String where, String start,
			String end) {
		Appointment a = new Appointment(""+getObjectId(appointments),start,end, descr, where);
		loggedInAs.addSomethingToCalendar(a);
		appointments.add(a);		
	}

	public ArrayList<Meetingrequest> getMeetingrequests() {
		ArrayList<Meetingrequest> liste = new ArrayList<Meetingrequest>();
		Iterator itr = meetingRequests.iterator();
		while(itr.hasNext()) {
			Meetingrequest mr = (Meetingrequest) itr.next();
			if (mr.getParticipant() == loggedInAs && !mr.hasAnswered()) liste.add(mr);
		}
		return liste;
	}

	public void answerMeetingrequest(Meetingrequest meetingrequest, boolean b) {
		if(b) {
			meetingrequest.setAttending(true);
			loggedInAs.addSomethingToCalendar(meetingrequest.getMeeting());
		} else meetingrequest.setAttending(false);
	}

	public int countMessages() {
		int c = 0;
		Iterator itr = messages.iterator();
		while(itr.hasNext()) {
			Message m = (Message) itr.next();
			if(m.getReciever() == loggedInAs && !m.isRead()) c++;
		}
		return c;	
	}

	public ArrayList<Message> getMessages() {
		ArrayList<Message> liste = new ArrayList<Message>();
		Iterator itr = messages.iterator();
		while(itr.hasNext()) {
			Message m = (Message) itr.next();
			m.setRead(true);
			if(m.getReciever() == loggedInAs) liste.add(m);
		}
		return liste;
	}
}
