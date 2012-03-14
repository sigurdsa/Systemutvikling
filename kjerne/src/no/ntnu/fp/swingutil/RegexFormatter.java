package no.ntnu.fp.swingutil;

import javax.swing.text.DefaultFormatter;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * A Formatter that checks the syntax of a String according to a regex Pattern.
 * 
 * @author Hallvard Tr¾tteberg
 *  
 * @version $Revision: 1.2 $ - $Date: 2005/02/20 18:33:16 $
 */
public class RegexFormatter extends DefaultFormatter
{
    /**
     * The pattern used to check the syntax
     */
    private Pattern pattern;

    /**
     * Constructor for objects of class RegexFormatter
     * 
     * @param pat the Pattern used to check the syntax
     */
    public RegexFormatter(Pattern pat)
    {
        pattern = pat;
    }
    
    /**
     * Constructor for objects of class RegexFormatter
     * 
     * @param pat the String regex used to check the syntax
     */
    public RegexFormatter(String pat)
    {
        this(Pattern.compile(pat));
    }
    
    /**
     * Converts an Object value to a String, effectively a no-op.
     * 
     * @param o the object to convert
     * 
     * @return the resulting String
     */
    public String valueToString(Object o) {
        return (o != null ? o.toString() : "");
    }
    
    /**
     * Converts a String to an Object value. Does the syntax check and return the same 
     * string if OK, else throws a ParseException.
     * 
     * @param s the String to convert
     * 
     * @return the resulting Object (possibly the same String)
     */
    public Object stringToValue(String s) throws ParseException {
        boolean matches = (pattern != null ? pattern.matcher(s).matches() : true);
        if (! matches) {
            throw new ParseException("Illegal value: " + s, 0);
        }
        return (matches ? s : null);
    }
}
