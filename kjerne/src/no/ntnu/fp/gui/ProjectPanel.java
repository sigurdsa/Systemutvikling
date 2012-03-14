package no.ntnu.fp.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.Project;

import no.ntnu.fp.storage.FileStorage;

/**
 * GroupPanel is JPanel for presenting the hierarhical Group structure and editing its contents.
 * GroupPanel instantiated three panes that support viewing and editing:
 * 
 * <ul>
 * <li>the Group tree (a JTree, with supporting GroupTreeModel, GroupTreeCellRenderer and GroupTreeCellEditor classes)</li>
 * <li>the Person list (JList and JTable in a JTabbedPane, with supporting PersonListModel, PersonTableModel and PersonCellRenderer)</li>
 * <li>Person panel (PersonPanel)</li>
 * </ul>
 * 
 * GroupPanel implements main logic connecting the three panes, by listening to selection events.
 * 
 * @author Hallvard Tr¾tteberg
 * @author Thomas &Oslash;sterlie
 * 
 * @version $Revision: 1.7 $ - $Date: 2005/02/22 08:45:46 $
 */
public class ProjectPanel extends JPanel implements ListSelectionListener, ListDataListener {
    
	/**
	 * The underlying data model
	 */
	private PersonListModel model;
	
    private JList personList;
    private ListSelectionModel personSelection;
    private PersonPanel personPanel;
    
    /**
     * Constructor for objects of class GroupPanel
     */
    public ProjectPanel() {
    		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
         personList = new JList();
         personList.setCellRenderer(new PersonCellRenderer());
         personSelection = personList.getSelectionModel();
         personSelection.addListSelectionListener(this);

         personPanel = new PersonPanel();
         personPanel.setEditable(false);

         add(personList);
         add(Box.createHorizontalStrut(20));
         add(personPanel);
    }

    /**
     * Returns the underlying data model.
     * 
     * @return The underlying data model.
     */
    PersonListModel getModel() {
        return model;
    }

    /**
     * Sets a new underlying data model for the {@link ProjectPanel}.  The first element of
     * the data model will automatically be selected.<P>
     * 
     * WARNING: Changes in the existing data model will be discarded.
     * 
     * @param model The underlying data model.
     */
    public void setModel(PersonListModel model) {
    		this.model = model;
    		personList.setModel(model);
    		model.addListDataListener(this);
    		listElementSelected((Person)model.getElementAt(0));
    		personList.setSelectedIndex(0);
    }

    /**
     * Returns the index of the element selected in <code>personList</code>.
     * 
     * @return Index of the selected element.
     */
    public int getSelectedElement() {
    		return personList.getSelectedIndex();
    }
    
    /**
     * Called when a Person is selected in (one of) the list(s).
     * 
     * @param p The selected {@link no.ntnu.fp.model.Person} object.
     */
    private void listElementSelected(Person p) {
        personPanel.setModel(p);
        personPanel.setEditable(p != null);
    }

    /**
     * ListSelectionListener, called when the JList or JTable selection changes.
     * 
     * @param e The ListSelectionEvent
     */
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == personSelection) {
            listElementSelected((Person)personList.getSelectedValue());
        }
    }

    /**
     * ListDataListener methods, called when the underlying (Person)ListModel is added to
     */
    public void intervalAdded(ListDataEvent e) {
        personList.setSelectedIndex(e.getIndex0());
    }
    public void contentsChanged(ListDataEvent e) {}
    
    /**
     * This method is invoked when a person is deleted from the list.  If the first person in
     * the list was removed, the method will select the new first person.  If the last person
     * in the list is removed, the method will select the new last person.  Else the method
     * will select the person after the person removed in the list.
     */
    public void intervalRemoved(ListDataEvent e) {
    		int index = e.getIndex0();
    		if (index == 0) 
    			personList.setSelectedIndex(1);
    		else if (index >= model.getSize()-1)
    			personList.setSelectedIndex(personList.getSelectedIndex()-1);
    		else
    			personList.setSelectedIndex(index+1);
    }

    /**
     * Main entry point for application.
     * Takes the frame title as a command line argument.
     */
    public static void main(String args[]) {
        JFrame frame = new JFrame(args.length > 0 ? args[0] : "Project panel example");

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu netMenu = new JMenu("Net");
        
        ProjectPanel projectPanel = new ProjectPanel();
        
        NewAction newAction = new NewAction(projectPanel);
        newAction.putValue(Action.NAME, "New");
        newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        
        OpenAction openAction = new OpenAction(projectPanel);
        openAction.putValue(Action.NAME, "Open");
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        
        SaveAction saveAction = new SaveAction(projectPanel);
        saveAction.putValue(Action.NAME, "Save");
        
        SaveAsAction saveAsAction = new SaveAsAction(projectPanel);
        saveAsAction.putValue(Action.NAME, "Save As");
        
        AddPersonAction addPersonAction = new AddPersonAction(projectPanel);
        addPersonAction.putValue(Action.NAME, "Add person");
        
        RemovePersonAction removePersonAction = new RemovePersonAction(projectPanel);
        removePersonAction.putValue(Action.NAME, "Remove person");

        DisconnectAction disconnectAction = new DisconnectAction(projectPanel);
        disconnectAction.putValue(Action.NAME, "Disconnect");
        
        ConnectAction connectAction = new ConnectAction(projectPanel);
        connectAction.putValue(Action.NAME, "Connect");
        
        fileMenu.add(newAction);
        fileMenu.add(openAction);
        fileMenu.add(saveAction);
	fileMenu.add(saveAsAction);
	menuBar.add(fileMenu);
	editMenu.add(addPersonAction);
	editMenu.add(removePersonAction);
	menuBar.add(editMenu);
	netMenu.add(connectAction);
	netMenu.add(disconnectAction);
	menuBar.add(netMenu);
	
	frame.setJMenuBar(menuBar);
	Container parent = frame.getContentPane();
	parent.setLayout(new BorderLayout());
	parent.add(projectPanel, BorderLayout.CENTER);
	
        frame.pack();
        frame.setSize (800,300);
        frame.setVisible(true);

	projectPanel.setModel(new PersonListModel(new Project(), null));
    }
}
