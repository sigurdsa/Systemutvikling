package no.ntnu.fp.model;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Server {
	private String host;
	private int port;
	private ServerSocket serverSocket;
	private Socket clientSocket;

	private StringBuffer process;
	private String TimeStamp;
	
	public Server() {
		this.host = "localhost";
		this.port = 20000;
		
	}
	private void initializeConnection() {
		try {
		    serverSocket = new ServerSocket(port);
		} 
		catch (IOException e) {
		    System.out.println("Could not listen on port: " + port);
		    System.exit(-1);
		}
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: " + port);
			System.exit(-1);
		}
		
		while(true) {
			try {
				InputStream is = clientSocket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object o = ois.readObject();
			}
		}
		
		
		try {
			clientSocket.close();
		} catch (IOException e){}
	}
}
