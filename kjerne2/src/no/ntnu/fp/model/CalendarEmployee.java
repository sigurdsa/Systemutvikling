package no.ntnu.fp.model;

import java.io.IOException;
import java.util.HashMap;

public class CalendarEmployee {
	
	private HashMap<SuperClass, Boolean> _list_meetingAppointment = new HashMap<SuperClass, Boolean>();
	
	public CalendarEmployee(){
		
	}
	
	public void addA(Appointment a){
            if (!this._list_meetingAppointment.containsKey(a))
                this._list_meetingAppointment.put(a, false);
	}
	
	public void deleteA(Appointment a){
            if (this._list_meetingAppointment.containsKey(a))
                this._list_meetingAppointment.remove(a);
	}

        public void addM(Meeting m){
            if (!this._list_meetingAppointment.containsKey(m)){
                this._list_meetingAppointment.put(m, true);
                
                Server s = Server.getServer();
                s.update(this);
                
            }
            
        }
        
        public void deleteM(Meeting m){
            if (this._list_meetingAppointment.containsKey(m)){
                this._list_meetingAppointment.remove(m);
                
                
            }
        }

        public void modify(Meeting m) {
            if (this._list_meetingAppointment.containsKey(m)){
                this._list_meetingAppointment.put(m, true);
                
                Server s = Server.getServer();
                s.update(this);
            }
        }
        
        public void search_meeting_modified() throws IOException{
            for(SuperClass s : this._list_meetingAppointment.keySet()){
                if (this._list_meetingAppointment.get(s) == true){
                    Meeting m = (Meeting)s;
                    m.call_employee();
                }
            }
        }
        
        public void show(){
            System.out.println("\nAppointments and Meeting:");
            for(SuperClass s : this._list_meetingAppointment.keySet()){
                System.out.println("\nDate: "+ s.date+"/"+s.month+"/"+s.year+
                        "\nHour: "+s.hour+":"+s.minute+
                        "\nWeek: "+s.week+
                        "\nName: "+s.name+
                        "\nDescription "+s.description);
            }
        }
        
        
}
