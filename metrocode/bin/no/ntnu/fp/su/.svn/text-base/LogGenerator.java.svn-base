package no.ntnu.fp.su;

import no.ntnu.fp.su.*;
import no.ntnu.fp.storage.*;

import java.io.*;
import java.util.ArrayList;

public class LogGenerator {
	DBStorage db;
	
	public LogGenerator(){
		db = new DBStorage();

	}
	
	public void LAClog(String[] lacID){
		ArrayList<String[]> lacs = db.generateLacLog(lacID);
		try {
			FileWriter fstream;
			fstream = new FileWriter("LAClog.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Her er en liste over AlarmEvents:\n");
			out.write("-------------------------------------------------------------------------\n\n\n");
			for(int i = 0; i<lacs.size(); i++){
				String[] lol = lacs.get(i);
				out.write("---------------------------------\n");
				out.write("lacID: "+lol[0]+"\n");
				out.write("AlarmunitID: "+lol[1]+"\n");
				out.write("time: "+lol[2]+"\n");
				String a;
				a = lol[3].equals("1") ? "Ja" : "Nei";
				out.write("Ekte alarm: "+a+"\n");
				out.write("AlarmUnit MTTF:"+lol[4]+"\n");
				out.write("Battery MTTF:"+lol[5]+" \n");
				out.write("AlarmUnit Install time:"+lol[6]);
				out.write("----------------------------------");
				out.write("\n\n");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{

			
		}

	}
	
	public static void main(String[] args){
		LogGenerator lg = new LogGenerator();
		String[] lacIDs = {"1"};
		lg.LAClog(lacIDs);
	}
	
	

}
