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
public class MACGui extends JFrame {

    private JTextArea messages;
    private JList loggedOn;
    private JPanel hoved;
    private MainControl mac;
    private DBStorage database = new DBStorage();
    private LogGenerator logg = new LogGenerator();

    public MACGui(String title, MainControl mac) {
        super(title);
        this.mac = mac;
        this.setSize(600, 600);
        this.placeComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        messages.setEditable(false);
        
        updateUserList(mac.getLACs());
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
        hoved.add(new JPanel().add(loggedOn = new JList()), BorderLayout.EAST);
        loggedOn.setPreferredSize(new Dimension(100, 400));
        loggedOn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        messages.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Meny");
        MenuItem item = new MenuItem("Legg til LAC");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	String lacId = JOptionPane.showInputDialog(MACGui.this,
                        "Skriv inn LAC-id:", "LAC-id",
                        JOptionPane.PLAIN_MESSAGE);
            	String lacAddress = JOptionPane.showInputDialog(MACGui.this,
                        "Skriv adresse til LAC:", "LAC-id",
                        JOptionPane.PLAIN_MESSAGE);
            	String lacPort = JOptionPane.showInputDialog(MACGui.this,
                        "Skriv portnr til LAC:", "LAC-id",
                        JOptionPane.PLAIN_MESSAGE);
            	LACinfo info = new LACinfo(lacId,lacAddress,Integer.parseInt(lacPort));
            	if(database.addLAC(info)){
            		mac.addLAC(info);
            		addMessage("Ny LAC med id=\""+lacId+"\" har blitt lagt til", "OPERATOR");
            		updateUserList(mac.getLACs());
            	}
            	addMessage("LAC med id=\""+lacId+"\" er allerede i listen", "MAC");
            }
        });
        menu.add(item);
        
        item = new MenuItem("Avslutt");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        menu.add(item);
        menubar.add(menu);
        
        menu = new Menu("Sjekk");
        item = new MenuItem("Start testing");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	mac.startLACCheck();
            }
        });
        menu.add(item);
        
        item = new MenuItem("Pause testing");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	mac.pauseLACCheck();
            }
        });
        menu.add(item);
        
        menubar.add(menu);
        
        menu = new Menu("Logg");
        item = new MenuItem("Lag logg");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	String lacs = "";
            	ArrayList<LACinfo> info = mac.getLACs();
            	for(LACinfo lac : info){
            		lacs += "LAC id: "+lac.getId()+"\n";
            	}
            	String lacId = JOptionPane.showInputDialog(MACGui.this,
                        "Skriv inn id på LAC du vl ha logg fra: (separert med komma)\n"+
                        "\nTilgjengelige LAC'er er:\n"+lacs, "LAC-id",
                        JOptionPane.PLAIN_MESSAGE);
            	String[] lacLog = lacId.split(",");
            	logg.LAClog(lacLog);
            	addMessage("Logg er laget. Finn den selv!", "OPERATOR");
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
    public void updateUserList(ArrayList<LACinfo> info) {
        String[] liste = new String[info.size()];
        int i = 0;
    	for(LACinfo lac : info){
    		liste[i++] = "LAC id: "+lac.getId();
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