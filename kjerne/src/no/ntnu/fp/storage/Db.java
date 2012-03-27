package no.ntnu.fp.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.fp.model.*;


public class Db {
	PrintWriter out;
	BufferedReader in;
	public Db() {

	}
	
	public void writePersons(ArrayList<Person> persons) {
		try{
			out =  new PrintWriter(new FileWriter("db.txt", true));
			Iterator itr = persons.iterator();
			while(itr.hasNext()) {
				out.println("Person;" + ((Person) itr.next()).personToDb());
			}
			out.close();			
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
		}

	}

	public void writeAppointments(ArrayList<Person> persons) {
		try{
			out =  new PrintWriter(new FileWriter("db.txt", true));
			Iterator itr = persons.iterator();
			while(itr.hasNext()) {
				out.println(((Person) itr.next()).appointmentsToDb());
			}
			out.close();			
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
		}		
	}
	
	public ArrayList<Person> getPersons() {
		ArrayList<Person> liste = new ArrayList<Person>();
		try{
			in =  new BufferedReader(new FileReader("db.txt"));
			String linje;
			while ((linje = in.readLine()) != null) {
				String[] linjeliste = linje.split(";");
				if(linjeliste[0].equals("Person")) {	
					liste.add(new Person(linjeliste[1], linjeliste[2], linjeliste[3], linjeliste[4], linjeliste[5], linjeliste[6]));
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
			System.out.println(e.getMessage());
		}		
		return liste;
	}
	
	public ArrayList<Meetingroom> getMeetingrooms() {
		ArrayList<Meetingroom> liste = new ArrayList<Meetingroom>();
		try{
			in =  new BufferedReader(new FileReader("db.txt"));
			String linje;
			while ((linje = in.readLine()) != null) {
				String[] linjeliste = linje.split(";");
				if(linjeliste[0].equals("Meetingroom")) {	
					liste.add(new Meetingroom(linjeliste[1],linjeliste[2],linjeliste[3]));
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
			System.out.println(e.getMessage());
		}		
		return liste;
	}	
	
	public ArrayList<Meeting> getMeetings(Project p) {
		ArrayList<Meeting> liste = new ArrayList<Meeting>();
		try{
			in =  new BufferedReader(new FileReader("db.txt"));
			String linje;
			while ((linje = in.readLine()) != null) {
				String[] linjeliste = linje.split(";");
				if(linjeliste[0].equals("Meeting")) {	
					liste.add(new Meeting(linjeliste[1],linjeliste[2],linjeliste[3],linjeliste[4],p.getPersonById(Integer.parseInt(linjeliste[5])),p.getMeetingroomById(Integer.parseInt(linjeliste[6]))));
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
			System.out.println(e.getMessage());
		}		
		return liste;
	}
	
	public ArrayList<Meetingrequest> getMeetingRequests(Project p) {
		ArrayList<Meetingrequest> liste = new ArrayList<Meetingrequest>();
		try{
			in =  new BufferedReader(new FileReader("db.txt"));
			String linje;
			while ((linje = in.readLine()) != null) {
				String[] linjeliste = linje.split(";");
				if(linjeliste[0].equals("Meetingrequest")) {	
					liste.add(new Meetingrequest(p.getMeetingById(Integer.parseInt(linjeliste[1])), p.getPersonById(Integer.parseInt(linjeliste[2])), linjeliste[3]));
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
			System.out.println(e.getMessage());
		}		
		return liste;
	}			

	public ArrayList<AbstractAppointment> getAppointments(int personid) {
		ArrayList<AbstractAppointment> liste = new ArrayList<AbstractAppointment>();
		try{
			in =  new BufferedReader(new FileReader("db.txt"));
			String linje;
			while ((linje = in.readLine()) != null) {
				String[] linjeliste = linje.split(";");
				if(linjeliste[0].equals("Appointment") && linjeliste[1].equals(""+personid)) {	
					liste.add(new Appointment(linjeliste[2],linjeliste[3],linjeliste[4],linjeliste[5],linjeliste[6]));
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Kunne ikke åpne fil");
			System.out.println(e.getMessage());
		}		
		return liste;
	}


}
