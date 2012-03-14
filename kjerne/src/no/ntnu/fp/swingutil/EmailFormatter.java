package no.ntnu.fp.swingutil;

/**
 * Formats an e-mail string on the format <code>&lt;usernamet&gt;@&lt;domain&gt;
 * 
 * @author Hallvard Tr¾tteberg 
 * 
 * @version $Revision: 1.2 $ - $Date: 2005/02/20 18:38:07 $
 */
public class EmailFormatter extends RegexFormatter
{
	/**
	 * Regular expression defining all legal email patterns.
	 */
    public final static String EMAIL_PATTERN_STRING = "\\w+(\\.\\w+)*@(\\w+\\.)+\\w+";

    /**
     * Constructor for objects of class EmailFormatter
     */
    public EmailFormatter()
    {
        super(EMAIL_PATTERN_STRING);
    }
}
