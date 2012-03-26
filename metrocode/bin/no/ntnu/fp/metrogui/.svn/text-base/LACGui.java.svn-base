/*
 * Created on 28.jan.2004
 *
 */
package no.ntnu.fp.metrogui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ntnu.fp.storage.DBStorage;
import no.ntnu.fp.su.*;

/**
 * @author Christian Askeland
 * 
 */
public class LACGui extends JFrame {

    private JTextArea messages;

    private JList loggedOn;

    private JPanel hoved;

    private LocalControl lac;
    
    private DBStorage database = new DBStorage();

    public LACGui(String title, LocalControl lac) {
        super(title);
        this.lac = lac;
        this.setSize(600, 600);
        this.placeComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	// Avslutter systemet brutalt
                System.exit(0);
            }
        });
        this.setVisible(true);
        
        messages.setEditable(false);
        
        updateUserList(lac.getAlarmUnits());
    }

    private void placeComponents() {
        this.getContentPane().add(hoved = new JPanel(new BorderLayout()));
        hoved.add(new JPanel().add(
        		new JScrollPane(
        				messages = new JTextArea()
        			)
        		),
                BorderLayout.CENTER
            );
        hoved.add(new JPanel().add(
        		new JScrollPane(
        				loggedOn = new JList()
        			)		
        		), 
        		BorderLayout.EAST);
        loggedOn.setPreferredSize(new Dimension(100, 400));
        //messages.setPreferredSize(new Dimension(500, 400));
        loggedOn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        messages.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loggedOn.setListData(new Vector());
        

        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Meny");
        MenuItem item = new MenuItem("Send penger til Sudan");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showConfirmDialog(LACGui.this,
                        "Bekreft overførsel!", "Hva faen?",
                        JOptionPane.OK_OPTION);            	
            }
        });
        menu.add(item);
        
        item = new MenuItem("Sjekk sensorer");
        item.addActionListener(new AbstractAction() {
        	public void actionPerformed(ActionEvent e) {
            	ArrayList<AlarmUnit> alarms = lac.checkSensors();
            	if(alarms.size()>0){
	            	String s = lac.createAlarmMessageToLAC(alarms);
	            	lac.addEventToDB(alarms);
	            	lac.resetAlarms(alarms);
	            	addMessage(s, "LAC");
            	}
            	else {
            		addMessage("Ingen alarmer har gått.", "LAC");
            	}
            }
        });
        menu.add(item);
        
        item = new MenuItem("Sjekk batteri");
        item.addActionListener(new AbstractAction() {
        	public void actionPerformed(ActionEvent e) {
            	ArrayList<AlarmUnit> alarms = lac.testBatteries();
            	lac.replaceBatteries(alarms);
            }
        });
        menu.add(item);
        
        item = new MenuItem("Avslutt");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	// Avslutter systemet brutalt
            	System.exit(0);
            }
        });
        menu.add(item);
        menubar.add(menu);
        //this.setMenuBar(menubar);
        
        
        menu = new Menu("Legg til");
        item = new MenuItem("Ny AlarmUnit");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	// Hent id til ny AlarmUnit
            	int auId = database.getHighestAuId();
            	auId++;
            	
            	// Hent id til rom
            	String rooms = database.getRooms();
            	String roomId = JOptionPane.showInputDialog(LACGui.this,
                        "Skriv romid:\n"+rooms, "Romid",
                        JOptionPane.PLAIN_MESSAGE);            	
            	
            	AlarmUnit au = new AlarmUnit(""+auId);
            	
            	// installer ny AlarmUnit i LAC
            	lac.install(au);
            	
            	// oppdatere databasen
            	database.addAlarmunit(au, lac.getId(), roomId);
            	
            	// legge til beskjded i gui
            	addMessage("Ny AlarmUnit, id=\""+auId+"\" har blitt installert i rom: "+roomId, "OPERATOR");
            	
            	 updateUserList(lac.getAlarmUnits());
            }
        });
        menu.add(item);
        item = new MenuItem("Nytt rom");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	String romNavn = JOptionPane.showInputDialog(LACGui.this,
                        "Skriv romnavn:", "Romnavn",
                        JOptionPane.PLAIN_MESSAGE);
            	String romDesc = JOptionPane.showInputDialog(LACGui.this,
                        "Skriv rombeskrivelse:", "Rombeskrivelse",
                        JOptionPane.PLAIN_MESSAGE); 
            	database.addRoom(romNavn, romDesc);
            	addMessage(romNavn+" lagt til i databasen", "OPERATOR");
            }
        });
        menu.add(item);
        menubar.add(menu);
        //this.setMenuBar(menubar);
        
        menu = new Menu("Endre");
        item = new MenuItem("Rombeskrivelse");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	// Hent id til rom
            	String rooms = database.getRooms();
            	String roomId = JOptionPane.showInputDialog(LACGui.this,
                        "Velg rom du vil endre:\n"+rooms, "Velg rom",
                        JOptionPane.PLAIN_MESSAGE);            	
            	String romDesc = JOptionPane.showInputDialog(LACGui.this,
                        "Skriv rombeskrivelse:", "Rombeskrivelse",
                        JOptionPane.PLAIN_MESSAGE); 
            	database.changeRoomDescription(roomId,romDesc);
            	addMessage("Rom nr.:"+roomId+" har endret beskrivelse", "OPERATOR");
            }
        }); 
        menu.add(item);
        menubar.add(menu);
        
        menu = new Menu("Fjern");
        item = new MenuItem("Slett AlarmUnit");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	String units = "";
            	ArrayList<AlarmUnit> alarms = lac.getAlarmUnits();
            	for(AlarmUnit au : alarms){
                	units += au.getId() +" - "+ database.getRoomName(au.getId())+"\n";
                }
            	String auId = JOptionPane.showInputDialog(LACGui.this,
                        "Velg AlarmUnit du vil slette:\n"+units, "Slett AlarmUnit",
                        JOptionPane.PLAIN_MESSAGE);
            	lac.removeById(auId);
            	database.deleteAlarmUnit(auId);
            	updateUserList(lac.getAlarmUnits());
            }
        }); 
        menu.add(item);
        menubar.add(menu);
        
        menu = new Menu("Hjelp");
        item = new MenuItem("YouKok.com");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	
            }
        }); 
        menu.add(item);
        menubar.add(menu);
        
        this.setMenuBar(menubar);
    }

    /**
     * tar inn en array list av String objekter og viser den til høyre.
     * 
     * @param liste
     */
    public void updateUserList(ArrayList<AlarmUnit> alarms) {
        String[] liste = new String[alarms.size()];
    	int i = 0;
        for(AlarmUnit au : alarms){
        	liste[i++] = au.getId() +" - "+ database.getRoomName(au.getId());
        }
    	loggedOn.setListData(liste);
    }

    /**
     * En metode som kaller sendMessage i hovedmodulen for å sende melding. Den
     * kalles når man skriver inn tekst og trykker enter.
     * 
     * @param message
     */
    private void sendMessage(String message) {
        //target.sendMessage(message);
    }

    /**
     * Tar inn en melding og viser den i chat vinduet.
     * 
     * @param message
     *            En melding
     * @param from
     *            Sender av meldingen
     */
    public void addMessage(String message, String from) {
        messages.append(from + ":\t" + message + "\n");
    }
}