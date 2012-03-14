package no.ntnu.fp.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.util.Date;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import no.ntnu.fp.model.Person;

import no.ntnu.fp.swingutil.EmailFormatter;

/**
 * A panel for viewing and editing {@link no.ntnu.fp.model.Person} objects. The various
 * interfaces are implemented to react to sub-components and model changes.
 * 
 * @author Hallvard Tr¾tteberg
 * @author Thomas &Oslash;sterlie 
 * 
 * @version $Revision: 1.4 $ - $Date: 2005/02/22 07:54:45 $
 */
public class PersonPanel extends JPanel
    implements PropertyChangeListener, ActionListener, ItemListener, FocusListener
{
    /**
     * The underlying data model is the {@link no.ntnu.fp.model.Person} class.
     */
    private Person model;

    /**
     * Text field for displaying and editing the person's name.
     */
    private JFormattedTextField nameTextField;
    
    /**
     * Text field for displaying and editing the person's email address.
     */
    private JFormattedTextField emailTextField;
    
    /**
     * Text field for displaying and editing the person's date of birth.
     */
    private JFormattedTextField dateOfBirthTextField;
    
    /**
     * Used by {@link #propertyChanged(String, String, JTextField)} and
     * {@link #sourceChanged(Object)}.
     */
    private Object eventSource = null;

    /**
     * Constructor for objects of class PersonPanel
     */
    public PersonPanel() {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
 
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets insets = new Insets(2, 2, 2, 2);
        constraints.insets = insets;
        constraints.anchor = GridBagConstraints.LINE_START;
        
        nameTextField = new JFormattedTextField();
        nameTextField.addPropertyChangeListener(this);
        nameTextField.setColumns(20);
        addGridBagLabel("Name: ", 0, constraints);
        addGridBagComponent(nameTextField, 0, constraints);
        
        emailTextField = new JFormattedTextField(new EmailFormatter());
        emailTextField.addPropertyChangeListener(this);
        emailTextField.setColumns(20);
        addGridBagLabel("Email: ", 1, constraints);
        addGridBagComponent(emailTextField, 1, constraints);

        dateOfBirthTextField = new JFormattedTextField(PersonCellRenderer.dateFormatter);
        dateOfBirthTextField.addPropertyChangeListener(this);
        dateOfBirthTextField.setColumns(15);
        addGridBagLabel("Birthday: ", 2, constraints);
        addGridBagComponent(dateOfBirthTextField, 2, constraints);
        
        setEditable(false);
    }
    
    /**
     * Controls whether the components are editable or not.
     * 
     * @param editable whether to turn on or off the editable property
     */
    public void setEditable(boolean editable) {
        nameTextField.setEditable(editable);
        emailTextField.setEditable(editable);
        dateOfBirthTextField.setEditable(editable);
    }


    /**
     * This method is defined in {@link java.beans.PropertyChangeListener} and
     * is called when a property of an object listened to is changed.<P>
     * 
     * Note that <code>PersonPanel</code> listens to both the underlying model object,
     * i.e. {@link no.ntnu.fp.model.Person}, and to the value property of the 
     * {@link javax.swing.JFormattedTextField}, i.e. nameTextField, emailTextField, and
     * dateOfBirthTextField.
     * 
     * @see java.beans.PropertyChangeListener <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/beans/PropertyChangeListener.html">java.beans.PropertyChangeListener</a>
     * @see javax.swing.JFormattedTextField <a href="http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JFormattedTextField.html">javax.swing.JFormattedTextField</a>
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getSource() == dateOfBirthTextField) {
            sourceChanged(dateOfBirthTextField);
        } else if (evt.getSource() == emailTextField) {
            sourceChanged(emailTextField);
        } else if (evt.getSource() == nameTextField) {
        		sourceChanged(nameTextField);
        } else {
            updatePanel(evt.getPropertyName());
        }
    }
    
    /**
     * Sets the <code>PersonPanel</code>'s underlying data model.
     * 
     * @param p The underlying data model.
     */
    public void setModel(Person p) {
    		if (p != null) {
    			if (model != null)
    				model.removePropertyChangeListener(this);
    			model = p;
    			model.addPropertyChangeListener(this);
    			updatePanel(null);
    		}
     }

    
    /**
     * Utility method for adding a label to the GridBagLayout.
     * Labels are placed in column 0, occupy only one cell and do not stretch.
     * 
     * @param s the label
     * @param row the row
     * @param constraints the GridBagConstraints
     */
    private void addGridBagLabel(String s, int row, GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridheight = 1;
        constraints.gridwidth  = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        add(new JLabel(s), constraints);
    }

    /**
     * Utility method for adding a component to the GridBagLayout.
     * Components are placed in column 1, occupy only one cell and stretches.
     * 
     * @param s the label
     * @param row the row
     * @param constraints the GridBagConstraints
     */
    private void addGridBagComponent(Component c, int row, GridBagConstraints constraints) {
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.gridheight = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        add(c, constraints);
    }

    private boolean propertyChanged(String changed, String prop, JTextField tf) {
        return (changed == null || (changed.equals(prop) && eventSource != tf && eventSource != tf.getDocument()));
    }

    /**
     * Updates the sub-components according to the underlying model.
     * 
     * @param property the name of the property that triggered the update (may be null)
     */
    private void updatePanel(String property) {
        if (model == null) {
            setEditable(false);
        }
 
        if (propertyChanged(property, Person.NAME_PROPERTY_NAME, nameTextField)) {
            String name = (model != null ? model.getName() : "");
            nameTextField.setText(name != null ? name : "");
        }

        if (propertyChanged(property, Person.EMAIL_PROPERTY_NAME, emailTextField)) {
            String email = (model != null ? model.getEmail() : "");
            emailTextField.setValue(email);
        }

        if (propertyChanged(property, Person.DATEOFBIRTH_PROPERTY_NAME, dateOfBirthTextField)) {
            Date birthday = (model != null ? model.getDateOfBirth() : null);
            dateOfBirthTextField.setValue(birthday);
        }
    }

    /**
     * Handles changes in sub-components,
     * that should be propagated to the underlying Person model object.
     * 
     * @param source the source of the change, typicall a sub-component
     */
    private void sourceChanged(Object source) {
        if (model == null) {
            return;
        }
        eventSource = source;
        if (source == nameTextField) {
            model.setName(nameTextField.getText());
        } else if (source == emailTextField) {
            model.setEmail((String)emailTextField.getValue());
        } else if (source == dateOfBirthTextField) {
            model.setDateOfBirth((Date)dateOfBirthTextField.getValue());
        }
        eventSource = null;
    }

    /**
     * This method is defined in ActionListener and
     * is called when ENTER is typed in a JTextField
     * (or a JButton is hit or JMenuItem is selected, but that's not relevant for PersonPanel)
     * 
     * @param event the ActionEvent describing the action
     */
    public void actionPerformed(ActionEvent event) {
        sourceChanged(event.getSource());
    }

    /**
     * This method is defined in ItemListener and
     * is called when an item is selected in a JComboBox
     * (or a JSlider or JSpinner, but that's not relevant for PersonPanel)
     * 
     * @param event the ItemEvent describing the selection
     */
    public void itemStateChanged(ItemEvent event) {
        sourceChanged(event.getSource());
    }

    /**
     * This method is defined in FocusListener and
     * is called when a JTextField (or in fact any component) loses the keyboard focus.
     * This normally happens when the user TABs out of the text field or clicks outside it.
     * 
     * @param event the FocusEvent describing what happened
     */
    public void focusLost(FocusEvent event) {
        sourceChanged(event.getSource());
    }

    public void focusGained(FocusEvent event) {}
}