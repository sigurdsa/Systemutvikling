/*
 * Created on 28.jan.2004
 *
 */
package no.ntnu.fp.net.separat.client;

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
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Geir Vevle
 * 
 * This chat client GUI is made just as an experiment. Also used as a demo program in TTM4100 on NTNU 2005.
 */
public class Gui extends JFrame {

    private JTextArea messages;

    private JTextField inputFelt;

    private JList loggedOn;

    private JPanel hoved;

    private ChatClient target;

    public Gui(String title, ChatClient target) {
        super(title);
        this.setSize(500, 400);
        this.placeComponents();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Gui.this.target.disconnect();
                System.exit(0);
            }
        });
        this.setVisible(true);
        this.target = target;
        
        messages.setEditable(false);
        inputFelt.requestDefaultFocus();
        inputFelt.requestFocus();
    }

    private void placeComponents() {
        this.getContentPane().add(hoved = new JPanel(new BorderLayout()));
        hoved.add(new JPanel().add(messages = new JTextArea()),
                BorderLayout.CENTER);
        hoved.add(new JPanel().add(loggedOn = new JList()), BorderLayout.EAST);
        hoved.add(new JPanel().add(inputFelt = new JTextField()),
                BorderLayout.SOUTH);
        loggedOn.setPreferredSize(new Dimension(100, 400));
        loggedOn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        messages.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputFelt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loggedOn.setListData(new Vector());
        inputFelt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    sendMessage(inputFelt.getText());
                    inputFelt.setText("");
                }
            }
        });

        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Settings");
        MenuItem item = new MenuItem("Change nick");
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(Gui.this,
                        "Enter new nick:", "Change nick",
                        JOptionPane.PLAIN_MESSAGE);
                Gui.this.target.send("/newName: " + newName);
                Gui.this.target.setUsername(newName);
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
    public void updateUserList(String[] liste) {
        loggedOn.setListData(liste);
    }

    /**
     * En metode som kaller sendMessage i hovedmodulen for å sende melding. Den
     * kalles når man skriver inn tekst og trykker enter.
     * 
     * @param message
     */
    private void sendMessage(String message) {
        target.sendMessage(message);
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