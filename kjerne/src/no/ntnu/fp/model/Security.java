package no.ntnu.fp.model;

import java.util.ArrayList;

/**
 * 
 * @author Øyvind
 * 
 * En klasse som lagrer user-objekter knyttet til innlogging i systemet
 */

public class Security {
	/**
	 * Holder userobjeker
	 */
	private ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * Konstruktør
	 * Lager user-objekter, og legger inn i users ArrayList
	 */
	public Security(String[] usernames, String[] passwords) {
		
	}
	
	/**
	 * Lager en user, og legger til i users
	 */
	public void addUser(String username, String password) {
		
	}
	
	/**
	 * Fjerner user fra users
	 */
	public void deleteUser(String username) {
		
	}
	
	/**
	 * Returnerer true om brukernavn og passord stemmer overens
	 */
	public boolean authorizeUser(String username, String password) {
		return false;
	}
	
		
	
}
