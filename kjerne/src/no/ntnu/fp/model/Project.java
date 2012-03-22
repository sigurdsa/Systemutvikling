package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * The <code>Project</code> class is a list of zero or more {@link Person} objects.
 * 
 * @author Thomas &Oslash;sterlie
 * @version $Revision: 1.9 $ - $Date: 2005/02/22 07:53:33 $
 *
 */
public class Project implements PropertyChangeListener {

	/**
	 * The member variable storing all registered {@link Person} objects.
	 */
	private ArrayList<Person> personList;
	
	/**
	 * Alle møterom er lagret her
	 */
	private ArrayList<Meetingroom> meetingRooms;
	
	/**
	 * This member variable provides functionality for notifying of changes to
	 * the <code>Project</code> class.
	 */
	private PropertyChangeSupport propChangeSupp;
	
	/**
	 * Innlogget som
	 */

	Person loggedInAs = null;
	
	/**
	 * Default constructor.  Must be called to initialise the object's member variables.
	 *
	 */
	public Project() {
		personList = new java.util.ArrayList();
		propChangeSupp = new java.beans.PropertyChangeSupport(this);
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
	
	public void createMeeting(Date startTime, Date endTime){}

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
	
	public ArrayList<Meetingroom> generateAvailableRooms(Date start, Date end) {
		ArrayList<Meetingroom> rooms = new ArrayList<Meetingroom>();
		Iterator itr = meetingRooms.iterator();
		
		while(itr.hasNext()) {
			Meetingroom m = (Meetingroom) itr.next();
			if (m.isFree(start,end)) rooms.add(m);
		}
//
		return rooms;
		
	}

	public void createMeeting(Date st, Date et, Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		
	}

	public void showAllMeetings() {
		// TODO Auto-generated method stub
		
	}

	public Person showMeetingRequests() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
