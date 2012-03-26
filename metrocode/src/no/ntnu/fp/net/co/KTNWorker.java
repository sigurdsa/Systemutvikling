package no.ntnu.fp.net.co;
import java.net.InetAddress;
import java.io.IOException;
import java.io.EOFException;

public class KTNWorker implements ConnectionListener, MessageListener { //hvorfor forstår ikke denne hvor de er...
	private Connection ConnectionInstance; //tilkoblingen workeren jobber mot
	private ReceiveConnectionWorker ConnectionReceiver;
	private ReceiveMessageWorker MessageReceiver;
	private KTNListener listener;
	boolean server; //sier om denne lytter etter innkommende tilkoblinger
	
	public KTNWorker(boolean server, KTNListener listener, int port) {
		ConnectionInstance = new ConnectionImpl(port);
		this.listener = listener;
		this.server = server;
		if (server) { //this instance should listen for clients
			ConnectionReceiver = new ReceiveConnectionWorker(ConnectionInstance, this);
			ConnectionReceiver.start();
		}
	}
	private KTNWorker(KTNListener listener, Connection connection) { //constructor som brukes for connectionreceived
		this.listener = listener;
		this.server = false;
		ConnectionInstance = connection;
		MessageReceiver = new ReceiveMessageWorker(ConnectionInstance, this);
		MessageReceiver.start();//starter å lytte etter pakker
	}
	
	public void connectionReceived(Connection newConnection) { //server mottatt connection
		if (server) { //ugyldig om denne ikke lyttet etter connection
			listener.connectionReceived(new KTNWorker(listener, newConnection)); //gir en ny workerinstans for ny connection
		}
		System.out.println("----------------------------------------------------------Connection established");
	}
	public void messageReceived(String message) {
		System.out.println("----------------------------------------------------------Message received: " + message);
		listener.messageReceived(this, message);
	}
	public void connectionClosed(Connection conn) {
		listener.connectionClosed(this);
	}
	public void sendMSG(String msg) throws IOException {
		//trenger ikke sjekke om den er tilkoblet eller noe sånnt, exception vil uansett bli sendt fra connectionimpl
		try {
			ConnectionInstance.send(msg);
		}
		catch (EOFException e) { //Avsluttet tilkobling mens data ble sendt
			ConnectionInstance.close();
			throw new IOException("Could not send, other side terminated the connection");
		}
	}
	public void connect(String ip, int port) throws IOException {
		connect(InetAddress.getByName(ip), port);
		System.out.println("----------------------------------------------------------Connection established");
	}
	public void connect(InetAddress ip, int port) throws IOException {
		ConnectionInstance.connect(ip, port);
		MessageReceiver = new ReceiveMessageWorker(ConnectionInstance, this); //lager ny receiveworker for hver tilkobling for å ikke starte samme thread mer enn en gang
		MessageReceiver.start(); //Starter å lytte etter pakker
	}
	public void disconnect() {
		ConnectionInstance.close();
	}
}
