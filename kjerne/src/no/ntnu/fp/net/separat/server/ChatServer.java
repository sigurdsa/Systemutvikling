/*
 * Created on 02.feb.2004
 *
 */
package no.ntnu.fp.net.separat.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.admin.Settings;
import no.ntnu.fp.net.co.Connection;
import no.ntnu.fp.net.co.ConnectionImpl;
import no.ntnu.fp.net.co.SimpleConnection;
import no.ntnu.fp.net.separat.client.ChatClient;

/**
 * @author Geir Vevle
 * 
 * This is a Chat srver made only as a experiment.
 */
public class ChatServer extends JFrame {

    public static boolean SIMPLE_CONNECTION = false;

    private Connection server;

    private String addressServer = "localhost";

    private ArrayList users;

    private int listenPort = 4444;

    private boolean run = true;

    private InetAddress localAddress;

    private JList userlist = new JList();

    private static boolean debug = true;

    //Lagrer info om hver og en bruker
    private class User {
        public String name;
        private RecieveThread recieveThread;
        public Connection conn;

        public User(String name, Connection conn) {
            this.name = name;
            this.conn = conn;
            recieveThread = new RecieveThread();
            recieveThread.start();
        }

        private class RecieveThread extends Thread {
            public boolean run = true;

            public void run() {
                run = true;
                while (run) {
                    try {
                        User.this.recieve(User.this.conn.receive());
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
		    catch (EOFException e) {
		      DBG("User.run(): Disconnect was requested.");
		      run = false;
		      try { conn.close(); }
		      catch (IOException ioe) {
			System.err.println("Chat server: IOException while" +
					   "closing connection: " +
					   ioe.getMessage());
		      }
		      if (!ChatServer.this.users.remove(User.this))
			DBG("User.run(): Unable to remove 'this' from list " +
			    "of users - expect errors!");
		      ChatServer.this.broadcast("***: " + User.this.name + " disconnected.");
              ChatServer.this.
			broadcast(ChatServer.this.getUsers().toString());
		    } catch (IOException e) {
		      DBG("User.run(): Error: " + e.getMessage());
                    }
                }
            }
        }

        private void recieve(String mess) {
            if (mess.length() == 0) {

            } else if (mess.equals(new String(name + " is closing"))) {

//                recieveThread.run = false;
//                recieveThread = null;
//                try {
//                    conn.close();
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                ChatServer.this.users.remove(this);
//                ChatServer.this
//                        .broadcast(ChatServer.this.getUsers().toString());
//                try {
//                    this.finalize();
//                } catch (Throwable e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
		} else if (mess.substring(0, 1).equals("/")) {
                if (mess.substring(1, 9).equals("newName:")) {
                    String oldName = name;
                    name = mess.substring(10, mess.length());
                    ChatServer.this.broadcast(ChatServer.this.getUsers()
                            .toString());
                    ChatServer.this.broadcast("**: " + oldName + " changed nick to " + name + ".");
                }
            } else {
                ChatServer.this.broadcast(mess);
            }
        }

        private void send(String mess) {
            try {
                conn.send(mess);
            } catch (ConnectException e) {
	      DBG("User.send(): ConnectException: '" + e.getMessage() +
		  "' while sending message '" + mess + "'");
	    } catch (EOFException exp) {
	      DBG("User.send(): Disconnect requested.");
	      this.recieveThread.run = false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private synchronized void broadcast(String mess) {
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            User element = (User) iter.next();
            element.send(mess);
        }
    }

    public ChatServer(int port) {
        listenPort = port;
        setTitle("Server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        getContentPane().add(userlist);
        setVisible(true);

    }

    public void startServer() {
        try {
            localAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        users = new ArrayList();

        if (SIMPLE_CONNECTION)
            server = new SimpleConnection(listenPort);
        else
            server = new ConnectionImpl(listenPort);

        Thread listener = new Thread() {

            private Connection newConn;

            private String message = "";

            public void run() {
                while (true) {
                    try {
                        DBG("Server lytter på:" + listenPort);
                        newConn = server.accept();
                        message = newConn.receive();

                        if (message.substring(0, 6).equals("Hello:")) {
                            User newUser;
                            DBG("Fikk inn connection fra: "
                                    + message.substring(6, message.length()));
                            users.add(newUser = new User(message.substring(6,
                                    message.length()), newConn));
                            broadcast("*: " + message.substring(6, message.length()) + " joined.");
                            broadcast(getUsers().toString());
                        }
                    } catch (SocketTimeoutException e) {
		      DBG("startServer(): Noe gikk galt, forsøk igjen.");
		      e.printStackTrace();
                    } catch (IOException e) {
		      DBG("startServer(): Noe gikk galt, forsøk igjen.");
		      e.printStackTrace();
                    }
                }
            }
        };
        listener.start();
    }

    private void newMessage(String message, String from) {
        for (int i = 0; i < users.size(); i++) {

        }
    }

    private ArrayList getUsers() {
        ArrayList userList = new ArrayList();
        for (int i = 0; i < users.size(); i++) {
            userList.add(((User) users.get(i)).name);
        }
        userlist.setListData(userList.toArray());
        return userList;
    }

    public static void main(String[] args) {
        String address;
        int port;
        Log.setLogName("Server");
        Settings settings = new Settings();
        port = settings.getServerPort();
        SIMPLE_CONNECTION = settings.useSimpleConnection();
        if (SIMPLE_CONNECTION){
            DBG("Using SimpleConnection");
        }
        ChatServer server = new ChatServer(port);
        server.startServer();
    }

  /** Write debug message to stdout. */
  private static void DBG(String msg) {
    if (debug)
      System.out.println("ChatServer: " + msg);
  }
}
