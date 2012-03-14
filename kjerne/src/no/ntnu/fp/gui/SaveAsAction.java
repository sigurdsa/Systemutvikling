package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import no.ntnu.fp.storage.FileStorage;
import no.ntnu.fp.swingutil.FPFileFilter;

/**
 * Implements the action for saving {@link no.ntnu.fp.model.Project} to an alternative file.
 * 
 * @author Thomas &Oslash;sterlie
 * @author Rune Molden
 * 
 * @version $Revision: 1.3 $ - $Date: 2005/02/22 08:46:51 $
 */
public class SaveAsAction extends AbstractAction {

	/**
	 * Parent component.
	 */
	private ProjectPanel aProjectPanel;

	/**
	 * Filter used by the {@link javax.swing.JFileChooser.
	 */
	private static FPFileFilter fileFilterXml = new FPFileFilter();
	
	/**
	 * Filter used by the {@link javax.swing.JFileChooser.
	 */
	private static FPFileFilter fileFilterData = new FPFileFilter();
	
	/**
	 * 
	 */
	private FileStorage storage = new FileStorage(); 
	
	/**
	 * Default constructor.  Initialises member variables.
	 * 
	 * @param projectPanel Parent component.
	 */
	public SaveAsAction(ProjectPanel projectPanel) {
		this.aProjectPanel = projectPanel;
	}
	
	/**
	 * Invoked when an action occurs.
	 * 
	 * @param e The action event.
	 */
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save As");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileFilterXml.addExtension("XML");
		fileFilterData.addExtension("DATA");
		fileFilterXml.setDescription("XML data files");
		fileFilterData.setDescription("Flat data files");
		chooser.addChoosableFileFilter(fileFilterXml);
		chooser.addChoosableFileFilter(fileFilterData);
		int result = chooser.showSaveDialog(aProjectPanel);
		
        if (result == JFileChooser.APPROVE_OPTION) {
            File aFile = chooser.getSelectedFile();
            PersonListModel plm = aProjectPanel.getModel();
            try {
                FPFileFilter chosenFilter = (FPFileFilter) chooser
                	.getFileFilter();
                String chosenExtension = chosenFilter.getExtension().toLowerCase();
                if (aFile.getPath().endsWith("." + chosenExtension)) {
                    storage.save(plm.getProject(), aFile);
                } else if (FPFileFilter.getExtension(aFile) == null){
                    StringBuffer strBuffer = new StringBuffer(aFile.getPath());
                    strBuffer.append("." + chosenExtension.toLowerCase());
                    storage.save(plm.getProject(), new File(strBuffer.toString()));
                } else {
                    String filePath = aFile.getPath();
                    String substFilePath = 
                        filePath.replaceAll("${1}\\.(.+)", "." + 
                                chosenExtension.toLowerCase());
                    storage.save(plm.getProject(), new File(substFilePath));
                }
                plm.setUrl(aFile.toURL());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
