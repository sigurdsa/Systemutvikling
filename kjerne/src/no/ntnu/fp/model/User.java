package no.ntnu.fp.model;

/**
 * 
 * @author �yvind
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
	 * Konstrukt�r
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
}
