package no.ntnu.fp.su;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import no.ntnu.fp.metrogui.MACGui;
import no.ntnu.fp.net.co.KTNListener;
import no.ntnu.fp.net.co.KTNWorker;
import no.ntnu.fp.storage.DBStorage;
//import no.ntnu.fp.storage.DbStorage;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class MainControl implements Runnable, KTNListener{
	
	private MACGui gui;
	private XmlSerializer serializer = new XmlSerializer();
	private KTNWorker connection;
	private ArrayList<LACinfo> LACs;
	private boolean alarmActivated;
	private String alarmMsg;
	private DBStorage database = new DBStorage();
	
	private Thread sjekkeRutine;
	
	public MainControl() {
		//LACs = new ArrayList<LACinfo>();
		LACs = database.getLACs();
		connection = new KTNWorker(false, this, 4001);
		alarmActivated = false;
		
		gui = new MACGui("MAC-gui",this);
	}
	
	public void addLAC(LACinfo lac) {
		//if(database.addLAC(lac))
			LACs.add(lac);
	}
	
	public ArrayList<LACinfo> getLACs(){
		return LACs;
	}
	
	public void run(){
		int i = 0;
		while(!alarmActivated){
			if(i>LACs.size())
				i = 0;
            gui.addMessage("Test LAC id: \""+(i+1)+"\"!!", "MAC");
            getLACStatus(i);
			try {
				// sjekker
				sjekkeRutine.sleep(15000);
			}
			catch (InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}
	
	public void getLACStatus(int LAC){
		LACinfo info = LACs.get(LAC);
		try {
			//connection.connect(info.getRemoteAddress(), info.getRemotePort());
			connection.connect(InetAddress.getLocalHost(), 5555);
			connection.sendMSG("checkAlarms");
		}
		catch (Exception e) {
			System.err.println("***FEIL i getLACStatus()*** " + e.getMessage() + e.toString());
		}
	}

	
	public void startLACCheck(){
		alarmActivated = false;
		if(sjekkeRutine == null){
			sjekkeRutine = new Thread(this);
			sjekkeRutine.start();
		}
		if(!sjekkeRutine.isAlive()) {
			sjekkeRutine = new Thread(this);
			sjekkeRutine.start();
		}
		gui.addMessage("Tester...", "MAC");
	}
	
	public void pauseLACCheck(){
		alarmActivated = true;
		gui.addMessage("Testing er pauset", "OPERATOR");
	}
	
	/**
	 * Dette er KTNListener-interfacet
	 */
	public void connectionReceived(KTNWorker worker) {
    	//klient ignorerer dette
    }
    public void messageReceived(KTNWorker worker, String msg) {
    	//får bare melding om alarm har gått!
    	System.err.println("***MESSAGE RECEIVED*** " + msg);
    	alarmActivated = true;
    	alarmMsg = msg;
    	
    }
    public void connectionClosed(KTNWorker worker) {
    	System.err.println("***CONNECTION CLOSED***");
    	if(alarmMsg != null){
    		Document doc = null;
    		String msg = alarmMsg;
	    	try{
	    		doc = serializer.XmlStringToDocument(msg);
	    	}
	    	catch (Exception e) {
				gui.addMessage("ALARM HAR GÅTT! Feil ved overføring av informasjon fra LAC!", "MAC");
			}
	    	msg = createAlarmMessage(doc);
	    	alarmActivated = true;
	    	//JOptionPane.showMessageDialog(gui, msg, "ALARM!", JOptionPane.WARNING_MESSAGE);
	        gui.addMessage(msg, "LAC");
	    	gui.addMessage("ALARM REGISTRERT", "OPERATOR");
	        startLACCheck();
    	}
    }
    
    /**
     * Metode som lager melding som skal sendes til MAC. Inneholder info om AlarmUnits som har gått.
     * Meldingen kommer på formen:
     * 
     * <LAC>
     * 		<LACid>0001</LACid>
     * 		<alarmUnits>
     * 			<alarmUnit>
     * 				<id>1234</id>
     * 				<isReal>EKTE ALARM</isReal>
     *    			<romNavn>STUE</romNavn>
     *    			<romBeskrivelse>Møblert</romBeskrivelse>
     * 			</alarmUnit> 
     * 		</alarmUnits>
	 * </LAC>
     */
    public String createAlarmMessage(Document doc){
    	String msg = "Det har gått en alarm i LAC id: ";
    	String str = null;
		Element lac = doc.getRootElement();
		str = lac.getFirstChildElement("LACid").getValue();
		msg += str+"\n\n";
		Element alarm = lac.getFirstChildElement("alarmUnits");
		Elements alarmList = alarm.getChildElements("alarmUnit");
		for(int i = 0; i<alarmList.size(); i++){
			Element au = alarmList.get(i);
			str = au.getFirstChildElement("rom").getValue();
			msg += "Rom: "+str+"\n";
			str = au.getFirstChildElement("id").getValue();
			msg += "AlarmID: "+str+", Type: ";
			msg += au.getFirstChildElement("isReal").getValue()+"\n\n";
			
		}
		
		return msg;
    }

}

