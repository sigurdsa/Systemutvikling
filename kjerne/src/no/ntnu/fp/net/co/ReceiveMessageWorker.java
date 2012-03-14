package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.co.Connection;

/**
 * Helper class implementing asynchronous handling of incoming messages. Objects
 * that want to receive incoming messages register themselves as MessageListener
 * to this class. The MessageListener.messageReceived() method will be called
 * each time a new incoming message is received. <br>
 * <br>
 * This interface is provided by the core "Fellesprosjekt" development team.
 * This class is not needed to solve the KTN excerise, but might be used by the
 * "fellesprosjekt" application.
 * 
 * @author Thomas &Oslash;sterlie
 * @version 0.1
 */
public class ReceiveMessageWorker extends Thread {

    private boolean isRunning;
    private Connection aConnection;
    private List<MessageListener> messageListenerList;

    /**
     * @param aConnection
     *            a Connection object that is connected with remote instance
     */
    public ReceiveMessageWorker(Connection aConnection) {
        isRunning = false;
        this.aConnection = aConnection;
        messageListenerList = new ArrayList<MessageListener>();
    }

    /**
     * Register a new MessageListener object
     * 
     * @param listener
     *            the MessageListener to be registered
     */
    public void addMessageListener(MessageListener listener) {
        messageListenerList.add(listener);
    }

    /**
     * Unregister a MessageListener object
     * 
     * @param listener
     *            the MessageListener to be unregistered
     */
    public void removeMessageListener(MessageListener listener) {
        messageListenerList.remove(listener);
    }

    /**
     * The worker thread.
     */
    public void run() {
        isRunning = true;
        int counter = 0;
        try {
            while (isRunning) {
                String message = aConnection.receive();
                Iterator<MessageListener> iterator = messageListenerList.iterator();
                while (iterator.hasNext()) {
                    MessageListener listener = iterator.next();
                    listener.messageReceived(message);
                }
            }
        }
        catch (EOFException e) {
            try {
                aConnection.close();
                isRunning = false;
                for (MessageListener ml : messageListenerList) {
                    ml.connectionClosed(aConnection);
                }
            }
            catch (IOException e2) {
                // TODO: better handle exception
            }
        }
        catch (IOException e) {
            // TODO: better handle exception
        }
    }

    /**
     * @author Thomas &Oslash;sterlie <br>
     * <br>
     *         This interface is provided by the core "Fellesprosjekt"
     *         development team. This class is not needed to solve the KTN
     *         excerise, but might be used by the "fellesprosjekt" application.
     */
    public interface MessageListener {

        public void messageReceived(String message);

        public void connectionClosed(Connection conn);

    }
}
