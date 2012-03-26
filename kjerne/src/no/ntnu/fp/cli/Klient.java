package no.ntnu.fp.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import no.ntnu.fp.model.Meeting;
import no.ntnu.fp.model.Meetingrequest;
import no.ntnu.fp.model.Meetingroom;
import no.ntnu.fp.model.Message;
import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.AbstractAppointment;
import no.ntnu.fp.model.Project;;

public class Klient {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Project p = new Project();
	static Scanner in = new Scanner(System.in);
	
	static String username;
	static String password;
	
	static int menuCounter = -1;
	
	public static void main(String [] args) throws IOException{
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
	
	public static void mainMenu() throws IOException{
		System.out.println("***MENU***");
		System.out.println("1.  Show my calendar");
		System.out.println("1.  Show an other persons calendar"); 
		System.out.println("2.  Create a new meeting");
		System.out.println("3.  Show all of your created meetings");
		System.out.println("4.  Show meeting requests. You have got " + p.CountNewRequests() + " requests.");
		System.out.println("0.  Log out");
		
		
		// bare et forslag til meny, vet ikke om alt skal vises av meetingalternativer dersom man ikke er møteleder
		// i noen prosjekter? Skal det kanskje stå i pkt 4 hvor mange man har fått?!
		
		System.out.println("/nChoose a value from the menu: ");
	
		menuCounter = in.nextInt();
		
		Meeting m;
		switch (menuCounter){
		case 1: p.showCalendar(p.getLoggedInAs());
		break;
		
		case 2: System.out.println("Choose one of the following persons"); // skal vi ta vekk dem som allerede er med på prosjektet??!
		for (int j = 0; j < p.getPersonList().size(); j++){
			System.out.println(j + ".  " + p.getPersonList().get(j));
		}
		
		int i = in.nextInt();
		
		p.showCalendar(p.getPersonList().get(i));
		
		
		
		case 3: m = createMeeting();
		addParticipants(0, m);
		p.sendMeetingRequests(m);
		break;
		
		case 4: p.showAllCreatedMeetings(); // må muligens returnere listen her.. 
		System.out.println("Would you like to cancel a meeting? (y/n)");
		String a = br.readLine();
		
		switch(a){
		case "y": System.out.println("Which one? Type a number from the list above");
		int i = in.nextInt();
		cancelMeeting(//møte nr i); // ikke ferdig her.. Hvordan blir det med rekkfølge?! Sortert og sånn?
		break;
		
		case "n": System.out.println("Would you like to change a meeting?");
		a  = br.readLine();
		if (a.equals("y")){
			System.out.println("Which one? Type a number from the list above");
			int j = in.nextInt();
			changeMeeting(//møte nr j)); // feil her, kommer an på hvordan listen er.
			break;
		}
		default: ;
		
		
		}
		break;
		
		case 5: showMeetingRequests();
		break;
		
		case 0: p.logout();
		break;
		
		default: System.out.println("You have not chosen a vaild number. Please try again.");
		break;
		
		}
	}
	
	// viser de requestene som innlogger ikke har svart på
	private static void showMeetingRequests() throws IOException {
		for (int i = 0; i < p.getLoggedInAs().getMeetingRequestList().size(); i++){
			if (!p.getLoggedInAs().getMeetingRequestList().get(i).hasAnswered()){
				//printe første møte bruke en tostringmetode . samme her som når man lister kalender?
				// skal man se om det krasjer med noe fra før? EVt vise hele kalenderen?
				System.out.println("Are you able to attend this meeting? (y / n / (any other letter if you don't want to answer yet");
				String s = br.readLine();
				switch(s){
				case "n":  p.getLoggedInAs().getMeetingRequestList().get(i).setAttending(false);
				break;
				case "y": p.getLoggedInAs().getMeetingRequestList().get(i).setAttending(true);
				break;
				default:; // hva gjør jeg når jeg ikke vil at noe skal skje?
				break;
				}
				
			}
		}
		// gå gjennom alle requester som ikke er svart på
		// gi alternativ
		// TODO Auto-generated method stub
		
	}


	private static void addParticipants(int i, Meeting m) throws IOException {
		while (i == 0) {
		System.out.println("Would you like to add a participant? (y/n)");
		String a = br.readLine();
				
		switch(a){
		case "y": 
			System.out.println("Choose one of the following persons"); // skal vi ta vekk dem som allerede er med på prosjektet??!
			for (int j = 0; j < p.getPersonList().size(); j++){
				System.out.println(j + ".  " + p.getPersonList().get(j));
			}
			
			int o = in.nextInt();
			m.addParticipant(p.getPersonList().get(o)); 
			Meetingrequest r = new Meetingrequest(m, p.getPersonList().get(o));
			p.getPersonList().get(o).getMeetingRequestList().add(r);
			// er dette litt tungvindt?!
			break;
			
		case "n": i = 1;  
		break;
		
		default: System.out.println("WRONG ANSWER");

		
			}
		}
	}
		

	public static Meeting createMeeting() throws IOException{
		Meeting m = null;
		System.out.println("Type date (dd/mm/yy): ");
		String date = br.readLine();
		
		System.out.println("Type start time (hh:mm): ");
		String startTime = null;
		try {
			startTime = br.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.out.println("Type end time (hh:mm): ");
		String endTime = null;
		try {
			endTime = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Date st = stringToDate(date, startTime);
		Date et = stringToDate(date, endTime);

		
		String descr = null;
		
		System.out.println("Add a description");
		try {
			descr = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String a = null;
		System.out.println("Do you want to book a room? (y/n)");
		try {
			a = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		switch (a){
			case "n": m = p.createMeeting(st,et, descr); // kan man bruke string her? Eller char?!
			break;
			
			case "y": m = chooseRoom(st, et, descr); 		
		
	}
		return m;
	}
		
		public static Meeting chooseRoom(Date st, Date et, String descr) throws IOException{
			Meeting m = null;
			
			System.out.println("How many seats do you need?");
			int nbr = in.nextInt();
			
			ArrayList<Meetingroom> availableRooms =	p.generateAvailableRooms(st, et, nbr);
			
			if (availableRooms.size() == 0){
				System.out.println("No room is available in your specified period. Do you want to try another period" +
						"(type x), or do you just want to create meeting without roomreservation (type y)?");
				
				String a = br.readLine();
					
				switch(a){
				case "x": m = p.createMeeting(st, et, descr);
				break;
				case "y": m = createMeeting();
				}
			}
			
			else{
			System.out.println("/nThe following rooms are available in your specified period: ");
			
			for (int i = 0; i < availableRooms.size(); i++){
				System.out.println("Room number: " + i + ", " + availableRooms.get(i).getName());
			}
		
			System.out.println("/n Type a room number: ");
			int i = in.nextInt();
			
			m = p.createMeeting(st, et, descr, availableRooms.get(i));	
			availableRooms.get(i).addMeetingToList(m);
		}
			return m;
		}
		
		public void changeMeeting(Meeting meeting) throws IOException{
			System.out.println("New date");
			String date = br.readLine();
			
			System.out.println("New start time");
			String st = br.readLine();
			Date startTime = stringToDate(date, st);
			
			meeting.setStartTime(startTime);
			
			System.out.println("New start time");
			String et = br.readLine();
			Date endTime = stringToDate(date, et);
			
			meeting.setEndTime(endTime);
			
			// setter requesten til usvart
			for (int i = 0; i < meeting.getMeetingRequests().size(); i++){
				meeting.getMeetingRequests().get(i).resetAnswer();
			}	
		}
		
		public void cancelMeeting(Meeting m) throws IOException{
			System.out.println("Write an explanation for why the meeting was cancelled");
			String a = br.readLine();
			Message msg = new Message(p.getLoggedInAs(), m.getParticipants(), a);
			// egentlig.. Må vi lage en melding for alle deltakere om vi skal sjekke om den er sett! Eks nye meldinger.
		}
		
		
		
		public static Date stringToDate(String date, String time){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
			
			Date c = null;
			try {
				c = sdf.parse(date + " " + time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return c;
			
		}
		
		
		// hva gjør vi her om det krasjer med noe du allerede har ikalenderen din?
	}
