package no.ntnu.fp.model;

import java.util.ArrayList;

public class Message {
	
	private Person sender;
	private Person reciever;
	private String text;
	private boolean isRead; // denne gir lite mening dersom vi lager en message for en liste..!
	
	
	public Message(Person sender, Person reciever, String text){
		this.sender = sender;
		this.reciever = reciever;
		this.text = text;
		isRead = false;
	}
	public Message(Person sender, Person reciever, String text, String isRead){
		this( sender,  reciever,  text);
		this.isRead = Boolean.parseBoolean(isRead);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public Person getSender(){
		return sender;
	}

}
