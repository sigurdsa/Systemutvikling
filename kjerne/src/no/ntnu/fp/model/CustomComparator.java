package no.ntnu.fp.model;

import java.util.Comparator;

	
	public class CustomComparator implements Comparator<AbstractAppointment> {
	  
	    public int compare(AbstractAppointment a1, AbstractAppointment a2) {
	        return a1.getStartTime().compareTo(a2.getStartTime());
	    }

	
	}

