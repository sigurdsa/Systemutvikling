/**
 * 25. jan.. 2009
 */
package no.ntnu.fp.net.co;
import java.io.IOException;
import java.net.ConnectException;

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
        while (isRunning) {
            try {
            	Connection conn = aConnection.accept();
            	connectionListener.connectionReceived(conn);
            	sleep(10000); //må koordineres med sleep i maincontrol (skal egentlig ikke være nødvendig men gir mer stabil tilkobling)
            }
            catch (IOException e) {
            	//er det feil under tilkobling så prøver den bare på nytt
            }
            catch (Exception e) {
            	//først og fremst interrupt feil med sleep, ignoreres
            }
        }
    }
    
    public void stopRunning() {
        isRunning = false;
    }
}
