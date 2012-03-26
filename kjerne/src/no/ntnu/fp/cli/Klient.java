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

	//hovedmetoden som bytter mellom å ikke være innlogget, og å vise hovedmenyen
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

	// metode for å logge inn. vil kjøres helt til brukernavn/passord er riktig.
	public static void LogIn() throws IOException{
		System.out.print("/nType username: ");
		username = br.readLine();

		System.out.print("/nEnter your password: ");
		password = br.readLine();

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
		System.out.println("2.  Show an other persons calendar"); 
		System.out.println("3.  Create a new meeting");
		System.out.println("4.  Show all of your created meetings");
		System.out.println("5.  Show meeting requests. You have got " + p.CountNewRequests() + " requests.");
		System.out.println("0.  Log out");


		System.out.println("/nChoose a value from the menu: ");

		menuCounter = in.nextInt();
		
		int week; // her må uken settes til nåveærende uke. 
		
		Meeting m;
		
		switch (menuCounter){
		
		// viser egen kalender
		case 1: p.showCalendar(p.getLoggedInAs(), week);
		break;

		// viser en annen valgt person sin kalender
		case 2: System.out.println("Choose one of the following persons"); 
		for (int j = 0; j < p.getPersonList().size(); j++){
			System.out.println(j + ".  " + p.getPersonList().get(j));
		}
		int i = in.nextInt();
		p.showCalendar(p.getPersonList().get(i), week);


		// oppretter et møte
		case 3: m = createMeeting();
		addParticipants(0, m);
		p.sendMeetingRequests(m);
		break;
		
		// viser alle møter som bruker har opprettet
		case 4: showAllCreatedMeetings();
		break;

		case 5: showMeetingRequests();
		break;

		case 0: p.logout();
		break;

		default: System.out.println("You have not chosen a vaild number. Please try again.");
		break;

		}
	}

	private static void showAllCreatedMeetings() {
		ArrayList<Meeting> l = p.showAllCreatedMeetings();
		
		System.out.println("Would you like to cancel a meeting? (y/n)");
		String a = br.readLine();

		switch(a){
		case "y": System.out.println("Which one? Type a number from the list above");
		int i = in.nextInt();
		cancelMeeting(l.get(i));
		showAllCreatedMeetings();
		break;

		case "n": System.out.println("Would you like to change a meeting?");
		a  = br.readLine();
		if (a.equals("y")){
			System.out.println("Which one? Type a number from the list above");
			int j = in.nextInt();
			changeMeeting(l.get(j)); 
					break;
		}
		default: ;


		
	}

	public void showCalendar(Person p, int week){
	}


	// viser de requestene som innlogger ikke har svart på
	private static void showMeetingRequests() throws IOException {
		for (int i = 0; i < p.getLoggedInAs().getMeetingRequestList().size(); i++){
			if (!p.getLoggedInAs().getMeetingRequestList().get(i).hasAnswered()){

				System.out.println("Are you able to attend this meeting? (y / n / (any other letter if you don't want to answer yet");
				String s = br.readLine();
				switch(s){
				case "n":  
					Meetingrequest r = p.getLoggedInAs().getMeetingRequestList().get(i);
					r.setAttending(false);
					p.getLoggedInAs().addMeetingToCalendar(r.getMeeting());
					break;
				case "y": p.getLoggedInAs().getMeetingRequestList().get(i).setAttending(true);
				break;
				default:; // hva gjør jeg når jeg ikke vil at noe skal skje?
				break;
				}

			}
		}
	

	}


	private static void addParticipants(int i, Meeting m) throws IOException {
		while (i == 0) {
			System.out.println("Would you like to add another participant? (y/n)");
			String a = br.readLine();

			switch(a){
			case "y": 
				System.out.println("Choose one of the following persons"); 
				for (int j = 0; j < p.getPersonList().size(); j++){
					if (!m.getParticipants().contains(p.getPersonList().get(j))){
						System.out.println(j + ".  " + p.getPersonList().get(j));
					}
				}
			
				// bruker velger en person den ønsker å legge til møtet
				int o = in.nextInt();
				m.addParticipant(p.getPersonList().get(o)); 
				Meetingrequest r = new Meetingrequest(m, p.getPersonList().get(o));
				p.getPersonList().get(o).getMeetingRequestList().add(r);
				m.getMeetingRequests().add(r);
				break;

			default: i = 1;
			break;


			}
		}
	}


	public static Meeting createMeeting() throws IOException{
		Meeting m = null;
		System.out.println("Type date (dd/mm/yy): ");
		String date = br.readLine();

		System.out.println("Type start time (hh:mm): ");
		String startTime = br.readLine();

		System.out.println("Type end time (hh:mm): ");
		String endTime = br.readLine();
	
		Date st = stringToDate(date, startTime);
		Date et = stringToDate(date, endTime);

		System.out.println("Add a description");
		String descr = br.readLine();

		System.out.println("Do you want to book a room? (y/n)");
		String a = br.readLine();
	
		switch (a){
		case "n": m = p.createMeeting(st,et, descr); 
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

	public static void changeMeeting(Meeting meeting) throws IOException{
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
		
		// her må det gjøres noe i forhold til rom!! Er det allerede booket et, i så fall, beholde det samme, eller booke et nytt?
		// må også være et alternativ i forhold til å legge til flere deltakere.
		
		showAllCreatedMeetings();
	}

	public static void cancelMeeting(Meeting m) throws IOException{
		System.out.println("Write an explanation for why the meeting was cancelled");
		String a = br.readLine();
		Message msg = new Message(p.getLoggedInAs(), m.getParticipants(), a);
		// her må det gjøres noe i forhold til meldinger.
		//Møtet må også bli slettet fra alle lister!!! How to do it? Dvs alle møteromslistene, samt alle requester møte inngår i. 
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
}
