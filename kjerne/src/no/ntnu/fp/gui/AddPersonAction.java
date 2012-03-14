package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import no.ntnu.fp.model.Project;
import no.ntnu.fp.model.Person;

/**
 * Implements the command for adding new {@link no.ntnu.fp.model.Person} objects.
 * 
 * @author Thomas &Oslash;sterlie
 *
 * @version $Revision: 1.1 $ - $Date: 2005/02/20 21:39:34 $
 */
public class AddPersonAction extends AbstractAction {

	/**
	 * The parent component.
	 */
	private ProjectPanel projectPanel;
	
	/**
	 * Default constructor.  Initialises all member variables.
	 * 
	 * @param projectPanel Parent component
	 */
	public AddPersonAction(ProjectPanel projectPanel) {
		super();
		putValue(Action.NAME, "New person");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		this.projectPanel = projectPanel;
	}
	

	/**
     * Invoked when an action occurs.
     * 
     * @param e The action event.
     */
	public void actionPerformed(ActionEvent event) {
		PersonListModel plm = projectPanel.getModel();
		Project project = plm.getProject();
		project.addPerson(new Person());
	}
	
}
