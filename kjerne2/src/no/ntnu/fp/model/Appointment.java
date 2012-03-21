package no.ntnu.fp.model;


public class Appointment extends SuperClass{

    
    public Appointment(int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description){
       this.date = _date;
       this.month = _month;
       this.year = _year;
       this.week = _week;
       this.minute = _minute;
       this.hour = _hour;
       this.name = _name;
       this.description = _description;
    }
    
    public void change(int _date, int _month, int _year, int _week, int _minute, int _hour, String _name, String _description){
       this.date = _date;
       this.month = _month;
       this.year = _year;
       this.week = _week;
       this.minute = _minute;
       this.hour = _hour;
       this.name = _name;
       this.description = _description;        
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
    
}
