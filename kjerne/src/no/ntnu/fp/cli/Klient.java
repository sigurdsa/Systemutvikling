package no.ntnu.fp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import no.ntnu.fp.model.Meetingroom;
import no.ntnu.fp.model.Project;;

public class Klient {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Project p = new Project();
	static Scanner in = new Scanner(System.in);
	
	static String username;
	static String password;
	
	static int menuCounter = -1;
	
	public static void main(String [] args){
		while (menuCounter != 0){
			
			switch (menuCounter){
			
			case -1: LogIn();
			break;
			
			case 1: mainMenu();
			break;
			
			default: System.out.print("Invalid value. Choose a value from 1-x. Type 0 if you want to log out");
			}
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
		System.out.println("4.  Show meeting requests. You have got " + p.getCountNewRequests + " requests.");
		System.out.println("0.  Log out");
		
		
		// bare et forslag til meny, vet ikke om alt skal vises av meetingalternativer dersom man ikke er møteleder
		// i noen prosjekter? Skal det kanskje stå i pkt 4 hvor mange man har fått?!
		
		System.out.println();
		System.out.println("Choose a value from the menu: ");
	
		menuCounter = in.nextInt();
		
		switch (menuCounter){
		case 1: showCalendar();
		break;
		
		case 2: createMeeting();
		
		case 3: p.showAllMeetings();
		
		case 4: p.addPerson(showMeetingRequests())
		
		case 0: p.logout();
		
		}
	}
	
	public static void showCalendar(){
		
	}
	
	public static void createMeeting(){
		System.out.println("Type date (dd/mm/yy): ");
		String date;
		try {
			date = br.readLine();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		System.out.println("Type start time (hh:mm): ");
		String startTime;
		try {
			startTime = br.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.out.println("Type end time (hh:mm): ");
		String endTime;
		try {
			endTime = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
		
		System.out.println("Do you want to book a room? (y/n)");
		String a;
		try {
			String a = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date st = sdf.parse(date + " " + startTime);
		Date et = sdf.parse(date + " " + endTime);
		
		switch (a){
			case "n": p.createMeeting(st,et); // kan man bruke string her? Eller char?!
			break;
			
			case "y": chooseRoom(st, et); 
			
		
	}
	}
		
		public static void chooseRoom(Date st, Date et){
			ArrayList<Meetingroom> availableRooms =	p.generateAvailableRooms(st, et);
			
			if (availableRooms.size() == 0){
				System.out.println("No room is available in your specified period. Do you want to try another period" +
						"(type x), or do you just want to create meeting without roomreservation (type y)?");
				String a = br.readLine();
				
				switch(a){
				case "x": p.createMeeting(st, et);
				break;
				case "y": createMeeting();
				}
			}
			
			else{
			System.out.println("/nThe following rooms are available in your specified period: ");
			
			for (int i = 0; i < availableRooms.size(); i++){
				System.out.println("Room number: " + i + ", " + availableRooms.get(i).getName());
			}
		
			System.out.println("/n Type a room number: ");
			int i = in.nextInt();
			
			p.createMeeting(st, et, availableRooms.get(i));
			
			
		}
		}
		
		
		// hva gjør vi her om det krasjer med noe du allerede har ikalenderen din?
	}
