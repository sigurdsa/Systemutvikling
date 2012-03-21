package no.ntnu.fp.model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	
	private static Server _server = new Server();
	private ArrayList<Room> _list_rooms;
	private HashMap<CalendarEmployee, Boolean> _list_calendars = new HashMap<CalendarEmployee,Boolean>();
	
	private Server(){
            this._list_rooms = new ArrayList<Room>();
	}

	public static Server getServer(){
		return _server;
	}
	
	public void log_in(String username, String password){
            Connection conexion = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/calendar_system", "root", "ponferrada");
                
                Statement stmt = conexion.createStatement();
                
                String SQL = ("INSERT ");
            }catch(SQLException e){
                System.out.println("Error of MySQL "+ e.getMessage());
            }catch(Exception e){
                System.out.println("Error: "+ e.getMessage());
            }
		
	}
	
	public void log_off(){
		
	}
	
	public void synchronize() throws IOException{
            for(CalendarEmployee ce : this._list_calendars.keySet()){
                if (this._list_calendars.get(ce) == true)
                    ce.search_meeting_modified();
            }
            
	}
	
	
        public void update(CalendarEmployee c){
            for (CalendarEmployee ce : this._list_calendars.keySet()){
                if (ce.equals(c)){
                    this._list_calendars.put(ce, true);
                    return;
                }
            }
		
        }
        
	public void add(CalendarEmployee c){
            if (!this._list_calendars.containsKey(c))
                    this._list_calendars.put(c, false);
	}
	
	public void delete(CalendarEmployee c){
            if (this._list_calendars.containsKey(c))
                    this._list_calendars.remove(c);
	}
        
        
	public Room search_rooms(){
            for (Room r : _list_rooms){
                if (r.getAvailable())
                    return r;
            }
            return null;
        }
        
        public void add_rooms(Room r){
            this._list_rooms.add(r);
        }
        
        public void delete_rooms(Room r){
            this._list_rooms.remove(r);
        }
        
	
}
