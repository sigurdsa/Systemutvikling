package no.ntnu.fp.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Helper class implementing asynchronous handling of incoming messages.  Objects
 * that want to receive incoming messages register themselves as MessageListener
 * to this class.  The MessageListener.messageReceived() method will be called
 * each time a new incoming message is received.
 * 
 * @author Thomas &Oslash;sterlie
 * @version 0.1
 */
public class ReceiveWorker extends Thread {

	private Connection aConnection;
	private List messageListenerList;
	
	/**
	 * 
	 * @param aConnection a Connection object that is connected with remote instance
	 */
	public ReceiveWorker(Connection aConnection) {
		this.aConnection = aConnection;
		messageListenerList = new ArrayList();
	}

	/**
	 * Register a new MessageListener object
	 * 
	 * @param listener the MessageListener to be registered
	 */
	public void addMessageListener(MessageListener listener) {
		messageListenerList.add(listener);
	}
	
	/**
	 * Unregister a MessageListener object
	 * 
	 * @param listener the MessageListener to be unregistered
	 */
	public void removeMessageListener(MessageListener listener) {
		messageListenerList.remove(listener);
	}
	
	/**
	 * The worker thread.
	 */
	public void run() {
		try {
			while (true) {
				String message = aConnection.receive();
				Iterator iterator = messageListenerList.iterator();
				while (iterator.hasNext()) {
					MessageListener listener = (MessageListener)iterator.next();
					listener.messageReceived(message);
				}
			}
		} catch(Exception e) {
			e.printStackTrace(); //TODO: better handling of exceptions
		}
	}
	
}