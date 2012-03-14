package no.ntnu.fp.gui;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.net.URL;

import javax.swing.AbstractListModel;

import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.Project;

/**
 * A ListModel implementation that wraps a Project object.
 * Makes it possible to directly view the Project's contained Persons in a 
 * {@link javax.swing.JList}. It extends {@link javax.swing.AbstractListModel}, 
 * to inherit support for listeners and firing events.
 *
 * @see javax.swing.AbstractListModel <a href="http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/AbstractListModel.html">javax.swing.AbstractListModel</a>
 *
 * @author Hallvard Tr¾tteberg
 * @author Thomas &Oslash;sterlie
 * 
 * @version $Revision: 1.4 $ - $Date: 2005/02/22 07:54:10 $
 */
class PersonListModel extends AbstractListModel implements PropertyChangeListener {

	/**
     * The data model that is wrapped
     */
    private Project project;

    /**
     * Path to where the data model is saved.  <code>null</code> means that the
     * user has not assigned a file to save the model.
     */
    private URL url;

    /**
     * Default constructor. Initialises member variables.
     * 
     * @param project The underlying data model
     * @param url Path to save the data model
     */
    PersonListModel(Project project, URL url) {
    		setProject(project);
    		setUrl(url);
    	}

    /**
     * Sets a new underlying data model.
     * 
     * @param project The new underlying data model.
     */
    void setProject(Project project) {
        if (this.project == project) {
            return;
        }

        if (this.project != null) {
        		this.project.removePropertyChangeListener(this);
        }
        this.project = project;
        if (this.project != null) {
        		this.project.addPropertyChangeListener(this);
        }
    }
    
    /**
     * Returns the underlying data model.
     * 
     * @return The underlying data model.
     */
    Project getProject() {
    		return project;
    }

    /**
     * This method is defined in ListModel and
     * is called to get the number of element in the list.
     * In our case it is the number of Person objects in the project.
     * 
     * @return the number of Person objects in the underlying Project object
     */
    public int getSize() {
        return (project == null ? 0 : project.getPersonCount());
    }
    
    /**
     * This method is defined in ListModel and
     * is called to get specific elements in the list.
     * In our case it returns the appropriate Person.
     * 
     * @return the Person object at the specific position in the underlying Project object
     */
    public Object getElementAt(int i) {
      try {
        return (project == null ? null : (Person)project.getPerson(i));
      } catch (java.lang.IndexOutOfBoundsException e) { //handling of empty models
	return null;
      }
    }

    /**
     * This method is defined in ProjectListener and
     * is called to notify of changes in the Project structure or
     * to changes in the contained objects' properties
     * 
     * @param event the ProjectEvent detailing what has changed
     */
     public void propertyChange(PropertyChangeEvent event) {
        Object source = event.getSource();
        Person person = null;
   
        int index;
        if ((source instanceof Project) && (event.getNewValue() instanceof Person)) {
        		person = (Person)event.getNewValue();
        		index = project.indexOf(person);
        } else if ((source instanceof Project) && (event.getNewValue() instanceof Integer)) {
        		person = (Person)event.getOldValue();
        		Integer i = (Integer)event.getNewValue();
        		index = i.intValue();
        } else if (source instanceof Person) { 
        		person = (Person)source;
        		index = project.indexOf(person);
        } else {
        	    return;
        }

        if ((source instanceof Project) && (event.getNewValue() instanceof Person))
        		fireIntervalAdded(project, index, index);
        else if ((source instanceof Project) && (event.getNewValue() instanceof Integer))
        		fireIntervalRemoved(project, index, index);
        else if (source instanceof Person)
        		fireContentsChanged(project, index, index);
    }
     
     /**
      * Sets the path to save the data model.
      * 
      * @param url The path.
      */
     public void setUrl(URL url) {
     	this.url = url;
     }
     
     /**
      * Get the path for the data model.  Returns <code>null</code> if no path has been
      * assigned.
      * 
      * @return The path
      */
     public URL getUrl() {
     	return url;
     }
}
