/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.fp.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author raicar
 */
public class Meeting extends SuperClass{

    private ArrayList<Employee> _list_attending = new ArrayList<Employee>();
    private ArrayList<Employee> _list_denying = new ArrayList<Employee>();
    private ArrayList<Employee> _list_waiting = new ArrayList<Employee>();
    
    private Room room;
    private Employee moderator;
    private ArrayList<Employee> _list_invited_employees = new ArrayList<Employee>();
    
    public Meeting(int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description, Room _r, Employee _moderator, ArrayList<Employee> list){
       this.date = _date;
       this.month = _month;
       this.year = _year;
       this.week = _week;
       this.minute = _minute;
       this.hour = _hour;
       this.name = _name;
       this.description = _description;
       this._list_invited_employees = list;
       this.room = _r;
       this.moderator = _moderator;
    }
    
    public ArrayList<Employee> getAttendList(){
        return this._list_attending;
    }

    public ArrayList<Employee> getdDenyList(){
        return this._list_denying;
    }
    
    public ArrayList<Employee> getWaitList(){
        return this._list_waiting;
    }
    
    public ArrayList<Employee> getInvitedList(){
        return this._list_invited_employees;
    }
    
    public Employee getModerator(){
        return this.moderator;
    }
    
    public void set_room(Room r){
        this.room = r;
    }
    
    public void show_status(){
        System.out.println("\n\tNumber of attended Employees: "+ _list_attending.size()+
                "\n\tNumber of denied Employees: "+ _list_denying.size() + 
                "\n\tNumber of waited Employees: "+ _list_waiting.size() +
                "\n\tNumber of invited Employees: "+ _list_invited_employees.size());
    }
    
    public void add_employee(Employee e, int op){
        if (op == 1) //Attend
            this._list_attending.add(e);
        else if (op == 2) //Deny
            this._list_denying.add(e);
        else if (op == 3) //Wait
            this._list_waiting.add(e);
        else if (op == 4) //Invite
            this._list_invited_employees.add(e);
    }
    
    public void delete_employee(Employee e, int op){
        if (op == 0) //Attend
            this._list_attending.remove(e);
        else if (op == 1) //Deny
            this._list_denying.remove(e);
        else if (op == 2) //Wait
            this._list_waiting.remove(e);
        else if (op == 3) //Invite
            this._list_invited_employees.remove(e);
    }

    public void change(int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description, Room _r, Employee _moderator, ArrayList<Employee> list) {
       this.date = _date;
       this.month = _month;
       this.year = _year;
       this.week = _week;
       this.minute = _minute;
       this.hour = _hour;
       this.name = _name;
       this.description = _description;
       this._list_invited_employees = list;
       this.room = _r;
       this.moderator = _moderator;
    }
    
    @Override
    public String toString(){
        return "\nMeeting: "+
                "\nDate: "+ this.date+"/"+this.month+"/"+this.year+
                "\nHour: "+this.hour+":"+this.minute+
                "\nWeek: "+this.week+
                "\nName: "+this.name+
                "\nDescription "+this.description;
    }
    
    public void call_employee() throws IOException{
        for (Employee e : this._list_invited_employees){
            e.answer_meeting();
        }
    }
    
}
