package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * This is a dummy implementation of the functionality for making a network connection with
 * another application.
 * 
 * @author Thomas &Oslash;sterlie
 *
 * @version $Revision: 1.2 $ - $Date: 2005/02/20 21:43:25 $
 */
public class ConnectAction extends AbstractAction {

	/**
	 * Parent component.
	 */
	private ProjectPanel projectPanel;
	
	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel Parent component.
	 */
	public ConnectAction(ProjectPanel projectPanel) {
		this.projectPanel = projectPanel;
	}
	
   /**
    * Invoked when an action occurs.
    * 
    * @param e The action event.
    */
	public void actionPerformed(ActionEvent arg0) {
		JOptionPane.showMessageDialog(projectPanel, "Function not implemented.");
	}

}

