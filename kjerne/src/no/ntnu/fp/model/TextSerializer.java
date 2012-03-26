package no.ntnu.fp.model;

public class TextSerializer {
	
	public TextSerializer() {
		
	}
	/**
	 * 
	 * @param text - id;name;email;dateOfBirth;username;password;
	 * @return
	 */
	public Person textToPerson(String text) {
		String [] liste = text.split(";");
		return new Person(liste[0],liste[1],liste[2],liste[3],liste[4],liste[5]);
	}
	
	@SuppressWarnings("deprecation")
	public String personToText(Person p) {
		return "" + p.getId() + ";" + p.getName() + ";" + p.getEmail() + ";" + p.getDateOfBirth().getYear() + "-" + ((p.getDateOfBirth().getMonth() < 10) ? ("0"+p.getDateOfBirth().getMonth()) : p.getDateOfBirth().getMonth())  + "-" + ((p.getDateOfBirth().getDate() < 10) ? ("0"+p.getDateOfBirth().getDate()) : p.getDateOfBirth().getDate())  + ";" + p.getUsername() + ";" + p.getPassword(); 
	}
	
	public Meeting
}
