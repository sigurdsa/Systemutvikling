package no.ntnu.fp.su;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.fp.metrogui.LACGui;
import no.ntnu.fp.net.co.KTNListener;
import no.ntnu.fp.net.co.KTNWorker;
import no.ntnu.fp.storage.DBStorage;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
/**
 * A local control unit. Extend/modify this class as needed.
 *  
 * @author petterw
 */
public class LocalControl implements KTNListener{
	
	protected ArrayList<AlarmUnit> alarmUnits;
	private String id;
	private XmlSerializer serializer = new XmlSerializer();
	private KTNWorker connection;
	private KTNWorker activeConnection;
	private DBStorage database = new DBStorage();
	private LACGui gui;
	
	public LocalControl(String id) {
		this.id=id;
		connection = new KTNWorker(true, this, 5555);
		alarmUnits = database.getAlarmunits(id);
		gui = new LACGui("LAC id="+id+" gui", this);
	}
	/**
	 * 
	 * @return The local control identifier.
	 */
	public String getId() {
		return id;
	}
	/**
	 * Installerer en ny AlarmUnit. Om AlarmUnit finnes i liste fra før,
	 * betyr det at det er en erstatning, ellers er det en ny installasjon.
	 * Oppdatere database etter installasjon.
	 * 
	 * @param au The alarm unit to install.
	 */
	public void install(AlarmUnit au) {
		if(isAlreadyInstalled(au)){
			removeById(au.getId());
			alarmUnits.add(au);
			//oppdatere installasjonstid for AlarmUnit i database
			//database.addAlarmunit(au, getId(), "1");
		}
		else {
			alarmUnits.add(au);
			//oppdatere installasjonstid for AlarmUnit i database
			//database.addAlarmunit(au, getId(), "1");
		}
		
	}
	
	/**
	 * Sjekker om en alarm allerede er intallert. Typisk om man vil sjekke
	 * om en ny AlarmUnit er en ny ionstallasjon eller en "replacement"
	 * 
	 * @param au AlarmUnit som skal sjekkes om allerede er installert
	 * @return true om alarm er installert, false ellers
	 */
	public boolean isAlreadyInstalled(AlarmUnit au){
		for(AlarmUnit a : alarmUnits) {
			if(a.getId().equals(au.getId())) {
				return true;
			}
		}
		return false;
		//return alarmUnits.contains(au);
	}
	/**
	 * Remove an alarm unit.
	 * @param au The alarm unit to remove.
	 */
	public void remove(AlarmUnit au) {
		alarmUnits.remove(au);
	}
	
	/**
	 * Fjerner AlarmUnit basert på ID
	 * 
	 * @param id AlarmUnit-id til enhet som skal fjernes
	 * @return true hvis suksessfull fjerning, false ellers
	 */
	public boolean removeById(String id){
		AlarmUnit gammel = null;
		for(AlarmUnit a : alarmUnits) {
			if(a.getId().equals(id))
				gammel = a;
		}
		if(gammel != null){
			alarmUnits.remove(gammel);
			return true;
		}
		return false;
	}
	
	/**
	 * Test all alarm units connected to the local control for battery failure.
	 * @return An arraylist with the alarm units that have failing batteries.
	 */
	public ArrayList<AlarmUnit> testBatteries() {
		ArrayList<AlarmUnit> result = new ArrayList<AlarmUnit>();
		for(AlarmUnit au : alarmUnits) {
			if(au.getBattery().low()) {
				result.add(au);
			}
		}
		return result;
	}
	/**
	 * Check all the alarm units connected to the local control for sensor events.
	 * @return An arraylist with the alarm units that have registered sensor events.
	 */
	public ArrayList<AlarmUnit> checkSensors() {
		ArrayList<AlarmUnit> result = new ArrayList<AlarmUnit>();
		for(AlarmUnit au : alarmUnits) {
			if(au.getSensor().check()) {
				result.add(au);
			}
		}
		return result;
	}
	/**
	 * @return An arraylist with the alarm units connected to the local control.
	 */
	public ArrayList<AlarmUnit> getAlarmUnits() {
		return alarmUnits;
	}
	
	/**
	 * Metode som bytter batterier på liste med AlarmUnits
	 * 
	 * @param alarms Liste med AlarmUnits som bør bytte batteri
	 */
	public static void changeBatteries(ArrayList<AlarmUnit> alarms) {
		for(AlarmUnit au : alarms) {
			au.getBattery().replace();
		}
	}
	
	
	
	/**
	 * DETTE ER NETTVERKSDELEN MELLOM MAC OG LAC
	 */
	public void connectionReceived(KTNWorker worker) {
		activeConnection = worker;
    }
    public void messageReceived(KTNWorker worker, String msg) {
    	gui.addMessage("Sjekker alarmer...", "MAC");
    	try {
    		if(msg.equals("checkAlarms")){
    			ArrayList<AlarmUnit> alarm = checkSensors();
    			if(alarm.size()>0){
    				// aktivere alarm, si ifra til MAC
    				activateAlarm(alarm);
    				// reset sensor
    				resetAlarms(alarm);
    			}
    			else
    				gui.addMessage("Ingen alarmer har gått.", "LAC");
    			alarm = testBatteries();
    			replaceBatteries(alarm);
    			
    			
    		}
    	}
    	catch (Exception e) {
    		
    	}
    	activeConnection.disconnect(); // lukke connection med MAC
    }
    public void connectionClosed(KTNWorker worker) {
    	System.out.println("***CONNECTION CLOSED***");
    }
    
    
    public void activateAlarm(ArrayList<AlarmUnit> alarms){
    	// lager feilmelding som skal sendes til MAC, denne inneholder info om alarmene
    	String msg = createAlarmMessage(alarms);
    	gui.addMessage(createAlarmMessageToLAC(alarms), "LAC");
    	try {
    		activeConnection.sendMSG(msg);
    	}
    	catch (Exception e) {
    		
    	}
    	addEventToDB(alarms);	// legger til AlarmEventer i databasen
    }
    
    /**
     * Metode som lager melding som skal sendes til MAC. 
     * Inneholder LACid og info om AlarmUnits som er aktivert.
     * 
     * @return 		et XML-dokument som ser ut som f.eks dette
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
    public String createAlarmMessage(ArrayList<AlarmUnit> alarms) {
    	Element root = new Element("LAC");
		Element id = new Element("LACid");
		id.appendChild(getId());
		root.appendChild(id);
		Element a = new Element("alarmUnits");
		root.appendChild(a);
		Iterator it = alarms.iterator();
		while (it.hasNext()) {
			AlarmUnit au = (AlarmUnit)it.next();
			Element element = serializer.alarmUnitToXml(au);
			a.appendChild(element);
		}
		return new Document(root).toXML();
    }
    
    /**
     * Legger til alarmEvent i datbasen
     * 
     * @param alarms
     */
    public void addEventToDB(ArrayList<AlarmUnit> alarms){
    	for(AlarmUnit au : alarms){
    		database.newAlarmEvent(au);
    	}
    }
    
    public String createAlarmMessageToLAC(ArrayList<AlarmUnit> alarmList){
    	String msg = "Det har gått en alarm!\n\n";
		for(AlarmUnit au : alarmList){
			// legge til romtype og beskrivelse
			msg += "Rom: "+database.getRoomDesc(au)+"\n";
			// legge til alarmid og om den er falsk eller ekte
			msg += "AlarmID: "+au.getId()+", Type: ";
			msg += (au.getSensor().isFalseAlarm() ? "FALSK ALARM" : "EKTE ALARM")+"\n\n";
		}
		
		return msg;
    }
    
    public void resetAlarms(ArrayList<AlarmUnit> alarm){
    	for(AlarmUnit au : alarm) {
			au.getSensor().reset();
		}
    }
    
    /**
     * Registrerer batteribytte i databasen skriver melding i LAC-vindu og skifter batteri
     * 
     * @param alarm	Liste med AlarmUnits som har dårlige batterier
     */
    public void replaceBatteries(ArrayList<AlarmUnit> alarm){
    	if(alarm.size()>0){
			for(AlarmUnit au : alarm){
				database.changeBattery(au);
				au.getBattery().replace();
				gui.addMessage("Batteri på AlarmUnit id=\""+au.getId()+"\" var dårlig. Har blitt skiftet", "OPERATOR");
			}
		}
    	else
    		gui.addMessage("Ingen alarmer har dårlig batteri.", "LAC");
    }
	
	
}
