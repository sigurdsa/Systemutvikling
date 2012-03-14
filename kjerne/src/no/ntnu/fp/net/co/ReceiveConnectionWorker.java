/**
 * 25. jan.. 2009
 */
package no.ntnu.fp.net.co;

/**
 * @author Marius Bjerke
 */
public class ReceiveConnectionWorker extends Thread {

    private boolean isRunning;
    private Connection aConnection;
    private ConnectionListener connectionListener;

    /**
     * @param aConnection
     *            a Connection object that is connected with remote instance
     */
    public ReceiveConnectionWorker(Connection aConnection, ConnectionListener listener) {
        isRunning = false;
        this.aConnection = aConnection;
        connectionListener = listener;
    }

    /**
     * The worker thread.
     */
    public void run() {
        isRunning = true;
        try {
            while (isRunning) {
                Connection conn = aConnection.accept();
                connectionListener.connectionReceived(conn);
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // TODO: better handling of exceptions
        }
    }
    
    public void stopRunning() {
        isRunning = false;
    }

    /**
     * @author Thomas &Oslash;sterlie <br>
     * <br>
     *         This interface is provided by the core "Fellesprosjekt"
     *         development team. This class is not needed to solve the KTN
     *         excerise, but might be used by the "fellesprosjekt" application.
     */
    public interface ConnectionListener {

        public void connectionReceived(Connection connection);

    }
}
