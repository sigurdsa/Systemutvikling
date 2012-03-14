package no.ntnu.fp.gui;

/**
 * A ListCellRenderer for Person objects.
 * 
 * @author Hallvard Tr¾tteberg 
 * @version $Revision: 1.2 $ - $Date: 2005/02/20 15:01:17 $
 */

import java.util.Date;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.text.DateFormatter;

import no.ntnu.fp.model.Person;

public class PersonCellRenderer extends DefaultListCellRenderer
{
    /**
     * Constructor for objects of class PersonLine
     */
    public PersonCellRenderer()
    {
    }

    /**
     * The date format used through the application
     */
    public final static DateFormatter dateFormatter = new DateFormatter();

    /**
     * Configures the renderer Component according to the value.
     *
     * @param list the JList
     * @param value the value to render
     * @param index the index in the list
     * @param isSelected tells if the cell is selected
     * @param cellHasFocus tells if the cell has focus
     * 
     * @return the renderer Component
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Person p = (Person)value;
        // the default method always returns a JLabel,
        // in fact the superclass inherits from JLabel
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // build a string from the various Person properties
        String s = p.getName();
        String text = (s != null ? s : "???");
        s = p.getEmail();
        if (s != null) {
            text = text + " (" + p.getEmail() + ")";
        }
        Date date = p.getDateOfBirth();
        if (date != null) {
            // use the date formatter object to compute the date string
            try {
                text = text + " " + dateFormatter.valueToString(date);
            } catch (java.text.ParseException pe) {
            }
        }
        label.setText(text);
        // use the icon corresponding to the Person's sex

        return label;
    }
}
