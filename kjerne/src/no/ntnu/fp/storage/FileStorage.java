package no.ntnu.fp.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;

import no.ntnu.fp.model.Project;
import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.XmlSerializer;
import no.ntnu.fp.swingutil.FPFileFilter;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.Serializer;

/**
 * This class handles all file operations concering storing and loading files.
 * More in detail, this constitutes I/O for flat data files and XML files.
 * 
 * @author Thomas &Oslash;sterlie
 * @author Rune Molden
 * @version $Revision: 1.3 $ - $Date: 2005/02/20 21:32:17 $
 */
public class FileStorage implements Storage {

    private XmlSerializer serializer = new XmlSerializer();
    private static String XML_FILE_EXTENSION = "xml";
    private static String DATA_FILE_EXTENSION = "data";
    
    /**
     * Default constructor.
     */
	public FileStorage() {
	}
	
	/**
	 * Loads a file from the system and returns an instanciated {@link Project}
	 * object if the operation was successfully executed. This method handles both
	 * flat data file types (<code>*.data</code>) and XML files (<code>*.xml</code>).
	 *  
	 * @param aFile The file to load
	 * @return A project found in the loaded file
	 * @throws IOException If the file could not loaded properly.
	 * @throws ParseException If the content of the file could not be properly
	 *         parsed.
	 */
	public Project load(File aFile) throws IllegalArgumentException, IOException, ParseException {
	    String extension = FPFileFilter.getExtension(aFile);
	    if (extension != null && extension.toLowerCase().equals(XML_FILE_EXTENSION)) {
            return loadXOMFile(aFile);
        } else if (extension != null && extension.toLowerCase().equals(DATA_FILE_EXTENSION)) {
            return loadFlatFile(aFile);
        } else {
            throw new IllegalArgumentException("Invalid filetype! The extension (" + 
                    extension + ") is not supported.");
        }
	}

	/**
	 * Transforms an {@link URL} to a {@link File} object if it originally 
	 * represents a file. 
	 * 
	 * @param url the URL to transform
	 * @return A File object representing the URL
	 * @throws IllegalArgumentException If the protocol of the URL is not "file".
	 */
	protected File getURLFile(URL url) throws IllegalArgumentException {
		if (! url.getProtocol().equals("file")) {
			throw new IllegalArgumentException("FileStorage only support the file protocol, not " + url);
		}
		return new File(url.getPath());
	}

	/**
	 * Loads a file from a network resource and returns an instanciated 
	 * {@link Project} object if the operation performed successful.
	 * 
	 * @see #load(File)
	 */
	public Project load(URL url) throws IOException, ParseException {
		return load(getURLFile(url));
	}

	/**
	 * Here, a date, represented as a String is parsed into a Date object.
	 * The date format is {@link DateFormat#MEDIUM} and the locale is set
	 * to {@link Locale#US}.
	 * 
	 * @param date The date, represented as a String, to parse
	 * @return An instanciated Date object
	 * @throws ParseException If the parse operation fails
	 */
	protected Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}
	
	protected String parseDateToString(Date date) {
	    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
	    return format.format(date);
//	    StringTokenizer tokenizer = new StringTokenizer(dateFormatted);
	}
	
	/**
	 * A {@link Project} is stored to a proper file format based on it's 
	 * storage representation. If the extension indicates it is to be stored as 
	 * (<code>*.xml</code>), the representation will be in XML. Otherwise, the 
	 * {@link Project} is stored in a flat file format (<code>*.data</code>).
	 * 
	 * @param aProject The project to save.
	 * @param aFile The file to save the project to.
	 * @throws IOException If file I/O in some way fails
	 */
	public void save(Project aProject, File aFile) throws IOException {
	    String extension = FPFileFilter.getExtension(aFile);
	    if (extension != null && extension.equals("data")) {
	        saveFlatFile(aProject, aFile);
	    }
	    else if (extension != null && extension.equals("xml")) {
	        saveXmlFile(aProject, aFile);
	    }
	    else {
	        throw new IllegalArgumentException("The file type (" 
	                + extension + ") is not supported as a storage format!");
	    }
	}

	/**
	 * 
	 * @param aProject The project to save.
	 * @param aFile The file to store the project to.
	 * @throws IOException If some file I/O fails.
	 */
	private void saveXmlFile(Project aProject, File aFile) throws IOException {
		Serializer serial = 
		    new Serializer(new FileOutputStream(aFile), "iso-8859-1");
		serial.setIndent(5);
		serial.write(serializer.toXml(aProject));	
    }

    /**
	 * 
	 * @param url the url to save to.
	 * @param aProject The project to save.
	 * @throws IOException
	 * @throws ParseException
	 */
	public void save(URL url, Project aProject) throws IOException {
		save(aProject, getURLFile(url));
	}

	private void saveFlatFile(Project aProject, File aFile) throws IOException {
		FileWriter aFileWriter = new FileWriter(aFile);
		BufferedWriter aWriter = new BufferedWriter(aFileWriter);
		PrintWriter printWriter = new PrintWriter(aWriter);
		for (int i = 0; i < aProject.getPersonCount(); i++) {
			printWriter.println(aProject.getPerson(i).getName() + ";" + 
			        aProject.getPerson(i).getEmail() + ";" + 
			        parseDateToString(aProject.getPerson(i).getDateOfBirth()));
        }
		printWriter.close();
	}	
	
	/**
	 * 
	 * @param aFile
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private Project loadFlatFile(File aFile) throws java.io.IOException, ParseException {
		Project aProject = new Project();
		FileReader aFileReader = new FileReader(aFile);
		BufferedReader aReader = new BufferedReader(aFileReader);
		String line = aReader.readLine();
		while (line != null) {
			aProject.addPerson(assemblePerson(line));
			line = aReader.readLine();
		}
		aReader.close();
		return aProject;
	}
	
	private Project loadXOMFile(File aFile) throws java.io.IOException, ParseException {
		Document doc = null;
		try {
			doc = new Builder().build(aFile);
		} catch (ParsingException pe) {
			throw new ParseException("Exception when building from " + aFile, -1);
		}
		return serializer.toProject(doc);
	}
	
	private Person assemblePerson(String line) throws ParseException {
		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		String name = tokenizer.nextToken();
		String email = tokenizer.nextToken();
		
		Date date = parseDate(tokenizer.nextToken());
		return new Person(name, email, date);
	}	
}
