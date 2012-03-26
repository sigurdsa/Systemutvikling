package no.ntnu.fp.storage;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import org.apache.derby.tools.sysinfo;



import no.ntnu.fp.su.*;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class DBStorage {
	
	//private static DbStorage _instance;
	
	private Connection con;
	
	public DBStorage(){
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			this.con = DriverManager.getConnection("jdbc:derby://algorit.me/lac;user=metro;password=code");
		} catch (Exception e) {
		      System.err.println("Exception: "+e.getMessage());
		}
		
	}
	
	public ArrayList<String[]> getAllAlarmEvents() throws SQLException{
		Statement sta = this.con.createStatement();
		ResultSet res = sta.executeQuery("SELECT * FROM alarmevent");
		ArrayList<String[]> ar = new ArrayList<String[]>();
	
		while(res.next()){
			String[] a = {res.getString("id"), res.getString("eventdatetime"), res.getString("isreal")};
			ar.add(a);
		}
		return ar;
	}
	
	public void XMLStatusWriter(Document xmlDocument) {
		String id;
		Element LACElement = xmlDocument.getRootElement();
		Element idElement = LACElement.getFirstChildElement("LACid");
		
		id = idElement.getValue();
		LocalControl aLAC = new LocalControl(id);
		Element auList = LACElement.getFirstChildElement("alarmUnits");
		Elements alarmUnitElements = auList.getChildElements("alarmUnit");
		
		for (int i = 0; i < alarmUnitElements.size(); i++) {
			String aunID, sensorstatus, batterystatus;
			Element auElement = alarmUnitElements.get(i);
			Element auID = auElement.getFirstChildElement(id);
			Element auSensorstatus = auElement.getFirstChildElement("sensorStatus");
			Element auBatterystatus = auElement.getFirstChildElement("batteryStatus");
			aunID = auID.getValue();
			sensorstatus = auSensorstatus.getValue();
			batterystatus = auSensorstatus.getValue();
			
			if (sensorstatus.equals("ALARM!")) {
				// skriv til sensorchechevent
			}
			
			if (batterystatus.equals("LAVT BATTERI")){
				//skriv til batterycheckevent
			}
			
		}
	
		
	}
	
	public ArrayList<String[]> generateAlarmunitLog(String[] alarmunitID){
		ArrayList<String[]> alarmEvents = new ArrayList<String[]>();
		for (int i=0; i< alarmunitID.length; i++){
			try {
				Statement sta = this.con.createStatement();
				ResultSet res = sta.executeQuery("SELECT eventdatetime, isreal FROM lac.ALARMEVENT WHERE lac.alarmevent.alarmunitid ="+alarmunitID[i]);
				while(res.next()){
					String[] event = new String[3];
					event[0] = alarmunitID[i];
					event[1] = res.getString("eventdatetime");
					event[2] = res.getString("isreal");
					alarmEvents.add(event);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return alarmEvents;
	}
	 

	
	public ArrayList<String[]> generateLacLog (String[] lacID){
		ArrayList<String[]> alarmEvents = new ArrayList<String[]>();
		for (int i=0; i<lacID.length; i++){
			try {
				Statement sta = this.con.createStatement();
				ResultSet res = sta.executeQuery("select eventdatetime, isreal, alarmunitid from lac.alarmevent, lac.alarmunit where lac.alarmunit.lacid="+lacID[i]+" and lac.alarmevent.alarmunitid = lac.alarmunit.id");
				while(res.next()){
					String[] event = new String[7];
					event[0] = lacID[i];
					event[1] = res.getString("alarmunitid");
					event[2] = res.getString("eventdatetime");
					event[3] = res.getString("isreal");
					event[4] = String.valueOf(this.alarmUnitMTTF(Integer.parseInt(event[1])));
					event[5] = String.valueOf(this.batteryMTTF(Integer.parseInt(event[1])));
					ResultSet insdate = sta.executeQuery("select installdatetime from lac.alarmunit where lac.alarmunit.id="+event[1]);
					insdate.next();
					event[6] = insdate.getString("installdatetime");
					
					
					alarmEvents.add(event);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return alarmEvents;
	}
	public ArrayList<String[]> generateBatteryLog(String[] batteryID){
		
		ArrayList<String[]> batteries = new ArrayList<String[]>();
		
		for (int i=0; i<batteryID.length; i++){
			try {
				Statement sta = this.con.createStatement();
				ResultSet res = sta.executeQuery("select INSTALLTIMESTAMP, AUID from lac.BATTERY where lac.battery.id="+batteryID[i]+"");
				while(res.next()){
					String[] battery = new String[3];
					battery[0] = batteryID[i];
					battery[1] = res.getString("INSTALLTIMESTAMP");
					battery[2] = res.getString("AUID");
					batteries.add(battery);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	
	return batteries;
	
	
	}
	
	
	
	public ArrayList<LACinfo> getLACs(){
		ArrayList<LACinfo> lacs = new ArrayList<LACinfo>();
		String id, remoteAddress;
		int remotePort;
		try {
			Statement sta = this.con.createStatement();
			ResultSet res = sta.executeQuery("SELECT * FROM lac.lac");
			while(res.next()){
				id = res.getString("ID");
				remoteAddress = res.getString("HOSTNAME");
				remotePort = res.getInt("PORT");
				lacs.add(new LACinfo(id, remoteAddress, remotePort));
			}
			res.close();
			sta.close();
			
			}
		catch (SQLException e){
			e.printStackTrace();
		}
		return lacs;
	}
	
	public boolean addLAC(LACinfo lac){
		// Returnerer true hvis LAC ikke finnes i databasen, og legge den til i databasen. 
		// Returnerer false hvis den allerede finnes
		try {
			Statement sta = this.con.createStatement();
			int lacid = Integer.parseInt(lac.getId());
			ResultSet res = sta.executeQuery("SELECT * FROM LAC.LAC WHERE id="+lacid+"");
			
			if(res.next()){
				return false;}
			
			
			sta.executeUpdate("INSERT INTO LAC.LAC(ID, HOSTNAME, PORT) VALUES("+lacid+",'"+lac.getRemoteAddress()+"' , "+lac.getRemotePort()+")");
			return true;
			

		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return true;
		
		
		
	}
		// addRoom legger til et nytt room med gitt id og description
	public void addRoom (String name, String description){
		try {
			Statement sta = this.con.createStatement();
			java.util.Date today = new java.util.Date();
		    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
			
		    sta.executeUpdate("INSERT INTO LAC.ROOM(NAME, DESCRIPTION, INCLUDEDATETIME) VALUES('"+name+"','"+description+"','"+ts+"')");
		    
			
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}

	}
	
		// addAlarmunit legger til en ny alarmunit i tabellen alarmunit og nytt battery i tabellen battery
	public void addAlarmunit (AlarmUnit au, String lacid, String roomid){
		String id = au.getId(); 
		changeBattery(au);
		
		try {
			java.util.Date today = new java.util.Date();
		    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
			Statement sta = this.con.createStatement();

			sta.executeUpdate("INSERT INTO LAC.ALARMUNIT(ID, STATUS, INSTALLDATETIME, ROOMID, LACID) VALUES("+id+",'OK','"+ts+"',"+roomid+","+lacid+")");

			//String aa= "INSERT INTO LAC.ALARMUNIT(ID, STATUS, INSTALLDATETIME,BATTERYID,ROOMID,LACID) VALUES("+id+",'OK','"+ts+"',"+batteryid+","+roomid+","+lacid+")";
			//sta.executeUpdate("INSERT INTO LAC.ALARMUNIT(ID, STATUS, INSTALLDATETIME,BATTERYID,ROOMID,LACID) VALUES("+id+",'OK','"+ts+"',"+batteryid+","+roomid+","+lacid+")");

			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void deleteAlarmUnit(String deleteid){
		
		try{
			Statement sta = this.con.createStatement();
			sta.executeUpdate("DELETE FROM LAC.BATTERY WHERE AUID = "+deleteid+"");
			sta.executeUpdate("DELETE FROM LAC.ALARMEVENT WHERE ALARMUNITID = "+deleteid+"");
			sta.executeUpdate("DELETE FROM LAC.ALARMUNIT WHERE ID = "+deleteid+"");
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	
		// newAlarmEvent lager ny alarmevent for gitt alarmunit

		// changeBattery legger til nytt batteri i battery-tabellen og oppdaterer alarmunit med den nye batteryid`en.
	

	
public void changeBattery(AlarmUnit au) {
		

		String id = au.getId();
		java.util.Date today = new java.util.Date();
	    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
	    
		try {
			Statement sta = this.con.createStatement();
			
		sta.executeUpdate("INSERT INTO LAC.BATTERY(INSTALLTIMESTAMP, auid) VALUES('"+ts+"',"+id+")");
		
	}
		
		catch (SQLException e){
			e.printStackTrace();
		}
}

		// updateRoom oppdaterer en gitt roomid med den nye description
	public void newAlarmEvent(AlarmUnit au){
		Statement sta;
		try {
			java.util.Date today = new java.util.Date();
		    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
		    
		    int isReal = au.getSensor().isFalseAlarm() ? 0 : 1;
		    
			sta = this.con.createStatement();
			sta.executeUpdate("INSERT INTO LAC.ALARMEVENT(EVENTDATETIME, ISREAL, ALARMUNITID) VALUES('"+ts+"',"+isReal+" ,"+au.getId()+")");
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/*
	public int highestBatteryID(){
		try {
			Statement sta = this.con.createStatement();
			int id = sta.executeUpdate("SELECT MAX(ID) FROM LAC.BATTERY");
			return id;
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	*/
	
	public ArrayList<AlarmUnit> getAlarmunits(String lacid){
		
		ArrayList<AlarmUnit> alarmunits = new ArrayList<AlarmUnit>();
		
		try { Statement sta = this.con.createStatement();
		
			ResultSet rs = sta.executeQuery("SELECT ID FROM LAC.ALARMUNIT WHERE LACID = "+lacid+"");
				while(rs.next()){
				AlarmUnit au = new AlarmUnit(rs.getString("ID"));
				alarmunits.add(au);
				}
			
			
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		return alarmunits;
	}
	
	public String getRoomDesc(AlarmUnit au){
		
		String desc = "ingen description";
		String auid = au.getId();
		String roomid = "ingen description";
		String name = "";
		
		try{
			Statement sta = this.con.createStatement();
			
			ResultSet rs = sta.executeQuery("SELECT ROOMID FROM LAC.ALARMUNIT WHERE ID = "+auid+"");
			rs.next();
			roomid = rs.getString("ROOMID");
			
			
		    ResultSet description = sta.executeQuery("SELECT NAME, DESCRIPTION FROM LAC.ROOM WHERE ID = "+roomid+"");
		    description.next();
		    desc = description.getString("DESCRIPTION");
		    name = description.getString("NAME");
	    
		
		}
		catch(SQLException e){
			e.getMessage();
			e.printStackTrace();
		}
		
		return name+" - "+desc;
		
	}
	
	public void changeRoomDescription(String id, String desc){
		
		try{
		
		Statement sta = this.con.createStatement();
		
		sta.executeUpdate("UPDATE LAC.ROOM SET DESCRIPTION = '"+desc+"' WHERE ID = "+id+"");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
	
	public String getRooms(){
		
		String rooms = "";
		
		try{
			Statement sta = this.con.createStatement();
			
			ResultSet rs = sta.executeQuery("SELECT ID,NAME FROM LAC.ROOM");
			while(rs.next()){
				
				rooms += rs.getString("ID")+" - "+rs.getString("NAME")+"\n";
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return rooms;
		
		
	}
	
	public String getRoomName(String auid){
		String romnavn = "";
		try{
			Statement sta = this.con.createStatement();
			ResultSet rs = sta.executeQuery("SELECT LAC.ROOM.NAME FROM LAC.ROOM,LAC.ALARMUNIT WHERE LAC.ALARMUNIT.ID = "+auid+" AND LAC.ROOM.ID = LAC.ALARMUNIT.ROOMID ");
			
			rs.next();
			romnavn = rs.getString(1);	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return romnavn;
		
	}
	
	public int getHighestAuId(){
		
		int max = 0;
		
		try{
			Statement sta = this.con.createStatement();
			
			ResultSet rs = sta.executeQuery("SELECT MAX(ID) AS ID FROM LAC.ALARMUNIT");
			
			//finn høyeste verdi i rs og sett det til max variabelen
			
			if(rs.next()){
		    max = rs.getInt("ID");  
			}	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return max;
		
	}
	
	public long alarmUnitMTTF(int auID){
		long INS = 0;
		long now = 0;
		long oppetid = 0;
		int failures = 1;
		
		try {
		Statement sta = this.con.createStatement();
		
		ResultSet fails = sta.executeQuery("SELECT COUNT(ALARMUNITID) FROM LAC.ALARMEVENT WHERE alarmunitid ="+auID+" AND isreal = 0");
		fails.next();
		failures = fails.getInt(1) > 0 ? fails.getInt(1) : 1; // needed to avoid division by zero.
		
		
		ResultSet insDate = sta.executeQuery("SELECT INSTALLDATETIME FROM LAC.ALARMUNIT WHERE ID = "+auID+"");
		insDate.next();
		//System.out.println(insDate.getRow());
		Timestamp tsINS = Timestamp.valueOf((insDate.getString(1)));
		// installasjionstid
		INS = tsINS.getTime();
		
		// Tid NÅ
		java.util.Date today = new java.util.Date();
	    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
	    now = ts.getTime();
		
	    // oppetid i sekunder
	    oppetid = ((now-INS)/1000);
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
		return oppetid/failures;
		
	}
	
	public long batteryMTTF(int auID){
		long INS = 0;
		long now = 0;
		long uptime = 0;
		long count = 0;
		// select count(lac.battery.id) as count from lac.battery where lac.battery.auid=44
		try{
		Statement sta = this.con.createStatement();
		ResultSet res = sta.executeQuery("select count(lac.battery.id) as count from lac.battery where lac.battery.auid="+auID);
		res.next();
		count = res.getLong("count");
		res.close();
		
		ResultSet res2 = sta.executeQuery("select lac.alarmunit.installdatetime from lac.alarmunit where id="+auID);
		res2.next();
		Timestamp tsINS = Timestamp.valueOf((res2.getString(1)));
		INS = tsINS.getTime();
		
		// Tid NÅ
		java.util.Date today = new java.util.Date();
	    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
	    now = ts.getTime();
	    
	    uptime = ((now-INS)/1000);
		
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return (uptime/count);
	}
	
	
	public static void main(String args[]){
		DBStorage db = new DBStorage();
		AlarmUnit au = new AlarmUnit("48");
		//db.newAlarmEvent(au);
		//LACinfo la = new LACinfo("3", "ntnu.no", 2000);	
		
		//db.addAlarmunit(au, "3", "2");
	
		
		//db.addAlarmunit(au, "1", "1");
		
//		java.util.Date today = new java.util.Date();
//	    java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());
//	    Timestamp ts2 = Timestamp.valueOf("2009-04-30 11:25:00");
//	    
//	    long a = ts2.getTime();
//	    long b = ts.getTime();
//	    System.out.println(((b-a)/1000));
		/*ArrayList<AlarmUnit> alarmunits = new ArrayList<AlarmUnit>();
		alarmunits = db.getAlarmunits("2");
		System.out.println(db.getRoomDesc(alarmunits.get(1)));
		for (int i=0; i<alarmunits.size(); i++){*/
			//System.out.println(alarmunits.get(i).getId());
		//	System.out.println(db.getRoomDesc(alarmunits.get(i)));
			
		//} 
		
		/*
		 * hvordan hente rapportdata fra en eller flere alarmunits
		 * 
		String[] a = {"4","5","6"};
		ArrayList<String[]> b = db.generateAlarmunitLog(a);
		
		for(int i = 0; i<b.size(); i++){
			String[] lol = b.get(i);
			System.out.println("alarmunitID:"+lol[0]+" time:"+lol[1]+" isreal: "+lol[2]);
			
		}
		*/
		/*
		 * hvordan hente ut rapportdata for en eller flere lacz:
		 */
		
		
		String[] a = {"1","3"};
		ArrayList<String[]> b = db.generateLacLog(a);
		
		for(int i = 0; i<b.size(); i++){
			String[] lol = b.get(i);

			System.out.println("lacID: "+lol[0]);
			System.out.println("AlarmunitID: "+lol[1]);
			System.out.println("time: "+lol[2]);
			System.out.println("EKTE?: "+lol[3]);
			System.out.println("AU MTTF:"+lol[4]);
			System.out.println("BATT MTTF:"+lol[5]);
			System.out.println("AU INSTALLDATE:"+lol[6]);
			System.out.println("-----");
			
			
		}
		/*
		String[] ss= new String[5];
		ss[0]="37";
		ss[1]="38";
		ss[2]="39";
		ss[3]="45";
		ss[4]="47";
		
		ArrayList<String[]> b = db.generateBatteryLog(ss);
		for(int i = 0; i<b.size(); i++){
			String[] lol = b.get(i);
			System.out.println("\n\n\n-----");
			System.out.println("batteryID: "+lol[0]);
			System.out.println("installtimestamp: "+lol[1]);
			System.out.println("auid: "+lol[2]);
		}*/
		
		//db.deleteAlarmUnit("2");
		//System.out.println(db.getRoomName("1"));
		
	}
	
	
	
	

	
	

}
