package no.ntnu.fp.model;

import java.util.Comparator;

	
	public class CustomComparator implements Comparator<Meeting> {
	  
	    public int compare(Meeting m1, Meeting m2) {
	        return m1.getStartTime().compareTo(m2.getStartTime());
	    }
	}

