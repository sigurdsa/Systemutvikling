package no.ntnu.fp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import no.ntnu.fp.model.Project;;

public class Klient {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Project p = new Project();
	static Scanner in = new Scanner(System.in);
	
	static String username;
	static String password;
	
	static int menuCounter = -1;
	
	public static void main(String [] args){
		do while (menuCounter != 0){
			
			switch (menuCounter){
			
			case -1: LogIn();
			break;
			
			case 1: mainMenu();
			break;
			
			default: System.out.print("Invalid value. Choose a value from 1-x. Type 0 if you want to log out");
			break;
			
			}
			

	}
	
	public static void LogIn(){
		System.out.print("Type username: ");
		try {
			username = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("");
		
		System.out.print("Enter your password: ");
		try {
		password = br.readLine();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		if (p.login(username, password)){
			menuCounter = 1;
		}
		else{
			System.out.println("Wrong username / password. Please try again. ");
		}
		
		}
	
	public static void mainMenu(){
		System.out.println("***MENU***");
		System.out.println("1.  Show my calendar");
		System.out.println("2.  Create a new meeting");
		System.out.println("3.  Show all meetings");
		System.out.println("4.  Show meetingrequests");
		System.out.println("0.  Log out");
		
		
		// bare et forslag til meny, vet ikke om alt skal vises av meetingalternativer dersom man ikke er møteleder
		// i noen prosjekter? Skal det kanskje stå i pkt 4 hvor mange man har fått?!
		
		System.out.println();
		System.out.println("Choose a value from the menu: ");
	
		menuCounter = in.nextInt();
		
		switch (menuCounter){
		case 1: showCalendar();
		break;
		
		
		}
	}
	
	public static void showCalendar(){
		
	}
}
