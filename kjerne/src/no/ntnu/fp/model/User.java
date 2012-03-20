package no.ntnu.fp.model;

/**
 * 
 * @author Øyvind
 * 
 * 
 */
public class User {

	/**
	 * Brukernavn
	 */
	private String username;
	/**
	 * passord kryptert
	 */
	private String password;
	
	/**
	 * Konstruktør
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}
