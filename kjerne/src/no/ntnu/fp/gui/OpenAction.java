package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import no.ntnu.fp.storage.FileStorage;
import no.ntnu.fp.swingutil.FPFileFilter;

/**
 * Implements the application's open command.
 * 
 * @author Hallvard Tr¾tteberg
 * @author Thomas &Oslash;sterlie
 * @author Rune Molden
 * 
 * @version $Revision: 1.5 $ - $Date: 2005/02/20 21:37:35 $
 */
public class OpenAction extends AbstractAction {

	/**
	 * The parent component.
	 */
    private ProjectPanel projectPanel;
    
    /**
     * Default constructor.  Initialises all member variables.
     * 
     * @param projectPanel Parent component.
     */
    public OpenAction(ProjectPanel projectPanel) {
        super();
        putValue(Action.NAME, "Open");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        this.projectPanel = projectPanel;
    }

    /**
     * Invoked when an action occurs.
     * 
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {
      try {
        String urlString = getFileUrlFromUser();
        if (urlString == null || urlString.length() == 0) {
	  return;
        }        
        loadGroupFromFile(urlString);   
      } catch (java.net.MalformedURLException mue) {
	JOptionPane.showMessageDialog(projectPanel, "No such file.");
	mue.printStackTrace();
      } catch (java.text.ParseException pe) {
	JOptionPane.showMessageDialog(projectPanel, "Wrong file format.");
	pe.printStackTrace();
      } catch (Exception anException) {
	JOptionPane.showMessageDialog(projectPanel, "Wrong file format.");
	anException.printStackTrace();
      }
    }
    
    /**
     * Loads the data from a file
     * 
     * @param urlString Absolute path to the file to be loaded.
     */
    private void loadGroupFromFile(String urlString) throws java.io.IOException, 
      java.net.MalformedURLException, java.text.ParseException
  {
      URL url = new URL(urlString);
      FileStorage storage = new FileStorage();
      projectPanel.setModel(new PersonListModel(storage.load(url), url));
    }

    /**
     * Retrieves the absolute path of the file picked in the file chooser.
     * 
     * @return The absolute path of the file to open.
     */
    private String getFileUrlFromUser() {
    		JFileChooser fc = new JFileChooser();
    		FPFileFilter fpFilter = new FPFileFilter();
        fpFilter.addExtension("XML");
        fpFilter.addExtension("DATA");
        fpFilter.setDescription("XML & Flat data files");
        fc.addChoosableFileFilter(fpFilter);
        
        int result = fc.showOpenDialog(projectPanel);
        return (result == JFileChooser.APPROVE_OPTION ? fc.getSelectedFile().toURI().toString() : null);
    }
    
}

