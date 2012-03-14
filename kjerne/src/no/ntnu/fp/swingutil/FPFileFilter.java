package no.ntnu.fp.swingutil;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.filechooser.FileFilter;

/**
 * This class is used for file filtering based on the file extension. If a given
 * file extension is not added to the filter, files with such an extension is
 * not visible in the file viewer dialog. The extensions are case insensitive.
 * 
 * Example of usage:
 * 
 * public class MyFrame extends javax.swing.JFrame {
 * 
 * [...] FPFileFilter fpFilter = null; [...]
 * 
 * public MyFrame() { super();
 * 
 * [...] fpFilter = new FPFileFilter(); fpFilter.addExtension("JPG");
 * fpFilter.addExtension("GIF"); fpFilter.setDescription("JPEG & GIF Image
 * files"); [...] }
 * 
 * [...] private JFileChooser jc = new JFileChooser();
 * fc.addChoosableFileFilter(fpFilter); [...] }
 * 
 * @author Rune Molden
 * @version $Revision: 1.2 $ - $Date: 2005/02/15 12:02:20 $
 */
public class FPFileFilter extends FileFilter {

    private Hashtable filters = null;

    private String description = null;

    private String fullDescription = null;
    
    private String extension = null;

    private boolean useExtensionsInDescription = true;

    /**
     * Constructor which initialises the extension table to employ for filtering
     * file extensions.
     */
    public FPFileFilter() {
        this.filters = new Hashtable(5);
    }

    /**
     * Returns <code>true</code> if the given file is to be shown in the
     * directory pane, <code>false</code> if not.
     * 
     * Files beginning which a period (.) are ignored.
     * 
     * @param file The file to check if it should be filtered.
     * @return <code>true</code> if it is to be shown, <code>false</code> if
     *         not.
     */
    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }
            String extension = getExtension(file);
            if (extension != null && filters.get(getExtension(file)) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the human readable description of this filter. For example: "JPEG
     * and GIF Image Files (*.jpg, *.gif)"
     * 
     * @return The description of the filter.
     */
    public String getDescription() {
        if (description == null || isExtensionListInDescription()) {
            fullDescription = description == null ? "(" : description + " (";
            Enumeration extensions = filters.keys();
            if (extensions != null) {
                fullDescription += "*." + (String) extensions.nextElement();
                while (extensions.hasMoreElements()) {
                    fullDescription += ", *."
                            + (String) extensions.nextElement();
                }
            }
            fullDescription += ")";
        }
        return fullDescription;
    }

    private boolean isExtensionListInDescription() {
        return useExtensionsInDescription;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     * 
     * For example: the following code will create a filter that filters out all
     * files except those that end in ".jpg" and ".tif":
     * 
     * ExampleFileFilter filter = new ExampleFileFilter();
     * filter.addExtension("jpg"); filter.addExtension("tif");
     * 
     * Note that the "." before the extension is not needed and will be ignored.
     * 
     * @param extension the extension to add to the filter
     */
    public void addExtension(String extension) {
        if (!filters.containsKey(extension)) {
            this.extension = extension;
            filters.put(extension.toLowerCase(), this);
            fullDescription = null;
        }
    }
 
    /**
     * 
     * @return The 
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Returns the extension of the given file
     * 
     * @param file the file from which to get the extension of
     * @return The extension of the given file
     */
    public static String getExtension(File file) {
        if (file != null) {
            String filename = file.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    /**
     * Sets the human readable description of this filter. For
     * example: filter.setDescription("GIF and JPG Images");
     * 
     * @param description the description for the given filter
     */
    public void setDescription(String description) {
        this.description = description;
        this.fullDescription = null;
    }
}