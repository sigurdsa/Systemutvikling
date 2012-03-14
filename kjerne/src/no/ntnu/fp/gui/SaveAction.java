package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;

import no.ntnu.fp.storage.FileStorage;

/**
 * Implements the command for saving the {@link no.ntnu.fp.model.Project}.
 * 
 * @author Thomas &Oslash;sterlie
 * 
 * @version $Revision: 1.6 $ - $Date: 2005/02/22 08:45:27 $
 */
class SaveAction extends AbstractAction {
	
	/**
	 * The parent component.
	 */
	private ProjectPanel projectPanel;
	
	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel The parent component.
	 */
	public SaveAction(ProjectPanel projectPanel) {
		this.projectPanel = projectPanel;
	}
	
	/**
	 * Invoked when an action occurs.  If the model has not yet been saved,
	 * the {@link SaveAsAction} is invoked instead.
	 * 
	 * @param e The action event.
	 */
	public void actionPerformed(ActionEvent event) {
		PersonListModel plm = projectPanel.getModel();
		URL path = plm.getUrl();
		
		if (path == null) {
			SaveAsAction saveAs = new SaveAsAction(projectPanel);
			saveAs.actionPerformed(null);

		} else {
			try {
				FileStorage file = new FileStorage();
				file.save(plm.getUrl(), plm.getProject());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
