package no.ntnu.fp.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The <code>Person</code> class stores information about a single person.
 * 
 * @author Thomas &Oslash;sterlie
 *
 * @version $Revision: 1.5 $ - $Date: 2005/02/20 14:52:29 $
 */
public class Employee {
	
	/**
	 * This member variable holds the person's name.
	 */
	private String name;
	private String username;
	private int id;
	private String password;
	private boolean logged;
	private boolean is_root;
	private HashMap<SuperClass, Boolean> _list_notifications = new HashMap<SuperClass, Boolean>();
	private CalendarEmployee calendar;
	
	/**
	 * This member variable holds the person's email address.
	 */
	private String email;
	
	/**
	 * This member variable holds the person's date of birth.
	 */
	private Date dateOfBirth;
	
	/**
	 * This member variable holds a unique identifier for this object.
	 */
	
	/**
	 * This member variable provides functionality for notifying of changes to
	 * the <code>Group</code> class.
	 */
	private PropertyChangeSupport propChangeSupp;
	
	/**
	 * Constant used when calling 
	 * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * on {@linkplain #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs} when the person's name is changed.
	 * 
	 * @see #setName(String) the setName(String) method
	 */
	public final static String NAME_PROPERTY_NAME = "name";

	/**
	 * Constant used when calling 
	 * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * on {@linkplain #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs} when the person's email address is changed.
	 * 
	 * @see #setEmail(String) the setEmail(String) method
	 */
	public final static String EMAIL_PROPERTY_NAME = "email";
	
	/**
	 * Constant used when calling 
	 * {@link java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
	 * on {@linkplain #addPropertyChangeListener(java.beans.PropertyChangeListener) registered
	 * <code>PropertyChangeListener<code> objecs} when the person's date of birth is changed.
	 * 
	 * @see #setEmail(String) the setDateOfBirth(java.util.Date) method
	 */
	public final static String DATEOFBIRTH_PROPERTY_NAME = "dateOfBirth";
	
	/**
	 * Default constructor. Must be called to initialise the object's member variables.
	 * The constructor sets the name and email of this person to empty
	 * {@link java.lang.String}, while the date of birth is given today's date. The 
	 * {@linkplain #getId() id field} is set to current time when the object is created.
	 */
	public Employee(int _id, String _username, String _password, Boolean _is_root) {
		this.id = _id;
		this.username = _username;
		this.password = _password;
		this.logged = false;
		this.is_root = _is_root;
		this.calendar = new CalendarEmployee();
		propChangeSupp = new PropertyChangeSupport(this);
	}
	
        public Employee(){
            name = "";
            email = "";
            dateOfBirth = new Date();
            id = (int) System.currentTimeMillis();
            propChangeSupp = new PropertyChangeSupport(this);
        }
        
        public Employee(String _name, String _email, Date d){
            this();
            name = _name;
            email = _email;
            dateOfBirth = d;
        }
	
	/**
	 * Assigns a new name to the person.<P>
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
	 * <li>the <code>getNewValue()</code> method returns a {@link java.lang.String} 
	 * with the newly assigned name</li>
	 * <li>the <code>getOldValue()</code> method returns a {@link java.lang.String} 
	 * with the person's old name</li>
	 * <li>the <code>getPropertyName()</code> method returns a {@link java.lang.String} 
	 * with the value {@link #NAME_PROPERTY_NAME}.</li>
	 * <li>the <code>getSource()</code> method returns this {@link Person} object
	 * </ul>
	 * 
	 * @param name The person's new name.
	 *
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		PropertyChangeEvent event = new PropertyChangeEvent(this, NAME_PROPERTY_NAME, oldName, name);
		propChangeSupp.firePropertyChange(event);
	}
	
	/**
	 * Assigns a new email address to the person.<P>
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
	 * <li>the <code>getNewValue()</code> method returns a {@link java.lang.String} 
	 * with the newly assigned email address</li>
	 * <li>the <code>getOldValue()</code> method returns a {@link java.lang.String} 
	 * with the person's old email address</li>
	 * <li>the <code>getPropertyName()</code> method returns a {@link java.lang.String} 
	 * with the value {@link #EMAIL_PROPERTY_NAME}.</li>
	 * <li>the <code>getSource()</code> method returns this {@link Person} object
	 * </ul>
	 * 
	 * @param email The person's new email address.
	 *
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */
	public void setEmail(String email) {
		String oldEmail = this.email;
		this.email = email;
		PropertyChangeEvent event = new PropertyChangeEvent(this, EMAIL_PROPERTY_NAME, oldEmail, this.email);
		propChangeSupp.firePropertyChange(event);
	}
	
	/**
	 * Assigns a new date of birth to the person.<P>
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
	 * <li>the <code>getNewValue()</code> method returns a {@link java.util.Date} 
	 * with the newly assigned date of birth</li>
	 * <li>the <code>getOldValue()</code> method returns a {@link java.util.Date} 
	 * with the person's old date of birth</li>
	 * <li>the <code>getPropertyName()</code> method returns a {@link java.lang.String} 
	 * with the value {@link #DATEOFBIRTH_PROPERTY_NAME}.</li>
	 * <li>the <code>getSource()</code> method returns this {@link Person} object
	 * </ul>
	 * 
	 * @param dateOfBirth The person's new date of birth.
	 * 
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/Date.html">java.util.Date</a>
	 * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
	 * @see java.beans.PropertyChangeEvent <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeEvent.html">java.beans.PropertyChangeEvent</a>
	 */	
	public void setDateOfBirth(Date dateOfBirth) {
		Date oldDateOfBirth = this.dateOfBirth;
		this.dateOfBirth = dateOfBirth;
		PropertyChangeEvent event = new PropertyChangeEvent(this, DATEOFBIRTH_PROPERTY_NAME, oldDateOfBirth, this.dateOfBirth);
		propChangeSupp.firePropertyChange(event);
	}
	
	/**
	 * Returns the person's name.
	 * 
	 * @return The person's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the person's email address.
	 * 
	 * @return The person's email address.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Returns the person's date of birth.
	 * 
	 * @return The person's date of birth.
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	/**
	 * Returns this object's unique identification.
	 * 
	 * @return The person's unique identification.
	 */
	public int getId() {
		return id;
	}
	public String getUsername(){
		return username;
	}
	
	public boolean getLogged(){
		return logged;
	}

	public boolean getIsRoot(){
		return is_root;
	}
	
	public HashMap<SuperClass,Boolean>getList(){
		return _list_notifications;
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
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		if (obj.getClass() != this.getClass())
			return false;
		
		Employee aPerson = (Employee)obj;
		
		if (aPerson.getName().compareTo(getName()) != 0) 
			return false;
		if (aPerson.getEmail().compareTo(getEmail()) != 0)
			return false;
		if (aPerson.getDateOfBirth().compareTo(getDateOfBirth()) != 0)
			return false;
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String s = "Name: " + getName() + "; ";
		s += "Email: " + getEmail() + "; ";
		s += "Date of birth: " + getDateOfBirth().toString();
		return s;
	}
	
	
	public void answer_meeting() throws IOException{
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader (isr);
            
            
            if (!this._list_notifications.isEmpty()){
                for (SuperClass m : _list_notifications.keySet()){
                    if (this._list_notifications.get(m)==true){
                        m.toString();
                        System.out.println("\nAnswer: Attend(1), Deny(2), Wait(3)");
                        int op = Integer.parseInt(br.readLine());
                        Meeting m1 = (Meeting)m;
                        if (op==1)
                            m1.add_employee(this, op);
                        else if (op==2)
                            m1.add_employee(this, op);
                        else if (op==3)
                            m1.add_employee(this, op);
                        this._list_notifications.put(m, false);
                        
                    }
                    
                }
            }
		
	}
	
        public void create_app(Appointment a){
            this.calendar.addA(a);
            this._list_notifications.put(a, false);
        }
        
        public void delete_app(Appointment a){
            this.calendar.deleteA(a);
            this._list_notifications.remove(a);
        }
        
        public void change_app(Appointment a, int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description){
            a.change(_date, _month, _year, _week, _minute, _hour, _name, _description);
        }
        
        public void create(Meeting m) throws IOException{
            this.calendar.addM(m);
            
            for (Employee e : m.getInvitedList()){
                e.calendar.addM(m);
                e._list_notifications.put(m,true);
            }
            this._list_notifications.put(m,true);
            Server.getServer().synchronize();
        }
        
        public void delete(Meeting m){
            if (m.getModerator().equals(this)){
                this.calendar.deleteM(m);
                for (Employee e : m.getInvitedList()){
                    e.calendar.deleteM(m);
                    e._list_notifications.remove(m);
                }
                this._list_notifications.remove(m);
            }
        }
        
        public void change(int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description, Room _r, Employee _moderator, ArrayList<Employee> list, Meeting m) throws IOException{
            m.change( _date, _month, _year, _week,  _minute, _hour, _name, _description, _r, _moderator, list);
            this.calendar.modify(m);
            for (Employee e : m.getInvitedList()){
                e.calendar.modify(m);
                e._list_notifications.put(m,true);
            }
            this._list_notifications.put(m,true);
            Server.getServer().synchronize();
        }
        
        public void setCalendar(CalendarEmployee c){
            this.calendar = c;
        }
        
        public CalendarEmployee getCalendar(){
            return this.calendar;
        }
}
