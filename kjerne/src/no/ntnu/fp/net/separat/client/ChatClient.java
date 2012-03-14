/*
 * Created on 30.jan.2004
 *
 */
package no.ntnu.fp.net.separat.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.admin.Settings;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;
import no.ntnu.fp.net.co.SimpleConnection;

/**
 * @author Geir Vevle
 * 
 * This chat client GUI is made just as an experiment. Also used as a demo
 * program in TTM4100 on NTNU 2005.
 */
public class ChatClient {

    private static int portUsed = 0;

    private String username = "default";

    private Gui gui;

    private int port_to_server = 4444;

    private String addressServer = "localhost";

    private int thisPort = 5555;

    private RecieveThread recieveThread;

    private Connection connection;

    private static boolean SIMPLE_CONNECTION = true;

    public ChatClient(String address, int port) {
        port_to_server = port;
        addressServer= address;
        if (SIMPLE_CONNECTION) {
            connection = new SimpleConnection(thisPort);
        } else {
            connection = new ConnectionImpl(thisPort);
        }
        //	  this.username = JOptionPane.showInputDialog(gui,"Skriv inn
        // navn:");
        gui = new Gui("Chat klient laget av Geir", this);
        this.login(username);

        gui.setDefaultCloseOperation(Gui.EXIT_ON_CLOSE);
    }

    private class RecieveThread extends Thread {
        public boolean run = true;

        public void run() {
            run = true;
            while (run) {
                try {
                    ChatClient.this.recieve(ChatClient.this.connection
                            .receive());
                } catch (ConnectException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String message) {
        send(this.username + ": " + message);
    }

    public void login(String username) {
        String message;
        System.out.println("Logger inn " + username);
        try {
            connection.connect(InetAddress.getByName(addressServer),
                    port_to_server);
            connection.send("Hello:" + username);
            recieveThread = new RecieveThread();
            recieveThread.start();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void recieve(String message) {
        if (message.substring(0, 1).equals("[")
                && message.substring(message.length() - 1, message.length())
                        .equals("]")) {
            String[] list = message.substring(1, message.length() - 1).split(
                    ", ");
            gui.updateUserList(list);
        } else if (!message.substring(0, 1).equals("/")) {
            gui.addMessage(message.substring(message.indexOf(":")), message
                    .substring(0, message.indexOf(":")));
        }
    }

    public void send(String data) {
        try {
            connection.send(data);
        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void disconnect() {
        recieveThread.run = false;
	// XXX: Geir: Do not call suspend() on the receive thread, as this
	// stops all receives, including waiting for ACK on the packet that
	// is sent (username+"is closing") and the FIN...
	// --SJ 2006-01-01
	//recieveThread.suspend();
        send(username + " is closing");
        try {
            connection.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String address;
        int port;
        Log.setLogName("Klienten");
        Settings settings = new Settings();
        address = settings.getServerAddress();
        port = settings.getServerPort();
        SIMPLE_CONNECTION = settings.useSimpleConnection();
        
        if (SIMPLE_CONNECTION){
            System.out.println("Using SimpleConnection");
        }
        ChatClient client = new ChatClient(address,port);
    }

    /**
     * @param username
     *            The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
