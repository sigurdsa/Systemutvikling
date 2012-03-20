package no.ntnu.fp.model;

public class Dato {

        private int day, month, year;
           
        public Dato(int day, int month, int year) {
                check(day, month, year);
            this.day = day;
            this.month = month;
            this.year = year;
        }
        public Dato(String dato){
                String[] datoTabell = dato.split("-");
                if(datoTabell.length == 3 ){
//                      if(datoTabell[0] != "dd" && datoTabell[1] != "mm" && datoTabell[2] != "yyyy" && datoTabell[0] != "" && datoTabell[1] != "" && datoTabell[2] != ""){
                                check(Integer.parseInt(datoTabell[0]), Integer.parseInt(datoTabell[1]), Integer.parseInt(datoTabell[2]));
                                this.day = Integer.parseInt(datoTabell[0]);
                                this.month = Integer.parseInt(datoTabell[1]);
                                this.year = Integer.parseInt(datoTabell[2]);
//                      }
                } else{
                        throw new IllegalArgumentException();
                }
        }
       
        private boolean isLeapYear(int year) {
            return (year % 4 == 0 && (year % 400 == 0 || year % 100 != 0));
        }
        private int numberOfDays(int month, int year) {
            switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
            case 4: case 6: case 9: case 11: return 30;
            case 2: return (isLeapYear(year) ? 29 : 28);
            }    
            return -1;
        }
        private void check(int day, int month, int year) {
                if (day < 1 || day > numberOfDays(month, year)) {
                        throw new IllegalArgumentException("day is illegal: " + day);
            }
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("month is illegal: " + day);
            }
        }
             
        public int getDay() {
            return day;
        }
        public void setDay(int day) {
            check(day, month, year);
            this.day = day;
        }
        public int getMonth() {
            return month;
        }
        public void setMonth(int month) {
                check(day, month, year);
            this.month = month;
        }
        public int getYear() {
            return year;
        }
        public void setYear(int year) {
                check(day, month, year);
            this.year = year;
        }
        public String toString(){
                return day+"-"+month+"-"+year;
        }
}

