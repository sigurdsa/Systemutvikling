package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jdom.output.EscapeStrategy;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;
import no.ntnu.fp.net.co.AbstractConnection.State;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	super();
    	this.myPort = myPort;
        usedPorts.put(myPort, true);
        myAddress = getIPv4Address();
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
            SocketTimeoutException {
        if (state != state.CLOSED) {
        	throw new ConnectException("Socket not closed :("); 
        	}
        this.remoteAddress = remoteAddress.getHostAddress(); //"78.91.22.145";
        this.remotePort = remotePort;
        try {
        	state = State.SYN_SENT;
        	KtnDatagram retur = sendHelper(constructInternalPacket(Flag.SYN));
        	lastValidPacketReceived = retur;//kun for � ikke ha denne med null-verdi etter connection
        	sendAck(retur, false);
        	state = State.ESTABLISHED;
        }
        catch (Exception E){
        	state = state.CLOSED;
        	throw new IOException("Klarer ikke koble til host: " + E);
        }
    }

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
        if (state != state.CLOSED && state != state.LISTEN){
        	throw new IOException("Kobling er ikke lukket");
        }
        state = State.LISTEN;
        KtnDatagram mottatt = null;
        while (!isValid(mottatt)) {
        	mottatt = receivePacket(true); //venter p� � motta syn-pakke
        }
        ConnectionImpl newConnection = new ConnectionImpl(createPort()); //ny connection lages
        newConnection.finalizeConnection(mottatt); //avslutter three-way handshake
        return newConnection;
    }
    
       // en ny klasse for � fullf�re en tilkobling startet med accept
    private void finalizeConnection(KtnDatagram synpack) throws IOException {
    	lastValidPacketReceived = synpack;
    	state = State.SYN_RCVD;
    	remoteAddress = synpack.getSrc_addr();
    	remotePort = synpack.getSrc_port(); //syncer seg mot tilkobleren
    	System.out.println("----------------------------------------------------------New socket at port " + myPort);
    	sendAck(synpack, true);
    	KtnDatagram retur = receiveAck();
    	if (!isValid(retur)) {
    		state = State.CLOSED;
    		throw new IOException("Error during connection");
    	}
    	state = State.ESTABLISHED;
    }
    
    
    private static int createPort() {
    	int port = (int)(Math.random()*9) + 31030; // velger en port mellom 31030 og 31039
    	while(usedPorts.containsKey(port)){
    		port = (int)(Math.random()*9) + 31030;
    	}
    	return port;
    }
    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
    	if (state != State.ESTABLISHED) {
    		throw new ConnectException("Error sending, not connected");
    	}
    	sendHelper(constructDataPacket(msg));
    	System.out.println("----------------------------------------------------------Packet succsessfully sent");
    }
    
  //sender pakken til gyldig ack/respons mottas
    private KtnDatagram sendHelper(KtnDatagram sendPacket) throws ConnectException, IOException {
    	int retry = 30; //antall fors�k f�r godkjent ack m� ha kommet (SDWR stopper � sende uansett om ack er gyldig eller ikke)	
    	KtnDatagram retur = null;
    	while (!isValid(retur) && retry-- > 0) {
    		try {
    			if (retur != null && retur.getFlag() == Flag.ACK) { //var en ack, bare ikke riktig sekvensnr
    				retry++; //connection er antageligvis fortsatt oppe s� ikke senk retry pga det.
    			}
    			System.out.println("----------------------------------------------------------" + retry + " attempts left to send");
    			simplySendPacket(sendPacket);
    			retur = receiveAck();
    		}
    		catch (ClException e) {
    			//pr�v p� nytt
    		}
    	}
    	if (!isValid(retur)) { // betyr mest sannsynlig at ingen ack var mottatt
    		state = State.CLOSED;
    		throw new IOException("Could not send packet");
    	}
    	return retur;
    }

    /**
     * Wait for incoming data.
     * 
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
        if(state != State.ESTABLISHED) {
        	throw new ConnectException("~~ Connection must be established to recieve packages! ~~");
        }
        KtnDatagram mottatt = receivePacket(false);
        //sjekker her seq nr, tatt h�yde for header feil og uregelmessige acker som forskyver seq nr
        if (isValid(mottatt) && mottatt.getSeq_nr() > lastValidPacketReceived.getSeq_nr() && mottatt.getSeq_nr() < (lastValidPacketReceived.getSeq_nr() + 30)){
        	sendAck(mottatt, false);
        	lastValidPacketReceived = mottatt;
        	return mottatt.toString();
 	        }
        else {
        	sendAck(lastValidPacketReceived, false); //sender ny ack p� forrige mottatt, for � f� den riktige pakken!
        	return null; //returnerer null, skal g� bra
        }
        	
    }

    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
        try {
        	if (state != State.ESTABLISHED) {
        		throw new IOException("~~Connection is/was/are/am/he/she/it established CAFEBABE~~");
        	}
        
        	state = State.FIN_WAIT_1;
        	KtnDatagram retur = null;
        	KtnDatagram finpakke = constructInternalPacket(Flag.FIN);
        	int retry = 4;
        	while ((retur == null || retur.getFlag() == Flag.FIN) && retry-- > 0){
        		if (disconnectRequest != null){ //hvis allerede motpart har sendt fin
        			sendAck(disconnectRequest, false);
        			state = State.FIN_WAIT_2;
        		}
        		retur = sendHelper(finpakke);
        		if (retur.getFlag() == Flag.FIN); {
        		disconnectRequest = retur;
        		}
        	}
        	if (state == State.FIN_WAIT_1 && retry > 0){
        		state = State.CLOSE_WAIT;
        		retry = 4;
        		while (!isValid(disconnectRequest) && retry-- > 0) {
        			disconnectRequest = receivePacket(true);
        		}
        		sendAck(disconnectRequest, false);
        	}
        }
        catch (Exception e) {
        	System.out.println("~~~~~~~~~~Error during system close~~~~~~~~~~~~ " + e ); 
        	}
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Connection closed");
        state = State.CLOSED;
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */

    protected boolean isValid(KtnDatagram packet) {
	// Pakken m� v�re annet enn null, ha riktig checksum og v�re passende for den staten systemet er i
	if (packet != null && packet.calculateChecksum() == packet.getChecksum() && isStateValid(packet)) {
		//lastValidPacketReceived = packet; fjernet pga ack-feil
		return true;
	}
	return false;
}
//Sjekker her om den er valid i forhold til den staten den er i M� SJEKKE SEKVENS VED ACKING
private boolean isStateValid(KtnDatagram packet) {
	//er det en ack pakke sjekkes det at pakken acker forrige pakke sent, ellers er det uansett false
	if ((packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.SYN_ACK) && packet.getAck() != lastDataPacketSent.getSeq_nr()) {
		return false;
	}
	// Hvis det er en fin pakke s� m� data v�re null
	if (packet.getFlag() == Flag.FIN && packet.getPayload() != null) {
		return false; //hadde fikset problemet med at fin dukker opp i datapakker av og til hvis abstractconnection hadde kallt isValid som i dokumentasjonen
	}
	// Hvis state er SYN_SENT, betyr det at pakken b�r v�re SYN_ACK og at den er fra riktig host
	if (state == State.SYN_SENT) {
		remotePort = packet.getSrc_port(); //verdien blir uansett satt riktig neste gang connect kj�res selv om pakken ikke var synack, sjekker acknr
		return (packet.getFlag() == Flag.SYN_ACK && remoteAddress.equals(packet.getSrc_addr()));
	}
	else if (state == State.LISTEN) {
		return (packet.getFlag() == Flag.SYN);
	}
	//alle andre pakker skal port og source v�re stilt inn for programmet s� her sjekker den etter feil
	else if (packet.getSrc_addr() != remoteAddress && packet.getSrc_port() != remotePort) {
		return false;
	}
	//dette m� v�re ack
	else if (state == State.SYN_RCVD) {
		return (packet.getFlag() == Flag.ACK);
	}
	//�nsker her ack tilbake eller fin
	else if (state == State.FIN_WAIT_1 || state == State.FIN_WAIT_2) {
		return (packet.getFlag() == Flag.FIN || packet.getFlag() == Flag.ACK);
	}
	//dette m� v�re fin-pakke
	else if (state == State.CLOSE_WAIT) {
		return (packet.getFlag() == Flag.FIN);
	}
	return true;
	//sjekker til sammen etter null-pakker, checksum, remoteaddress, remoteport, seqno, 
}
}

