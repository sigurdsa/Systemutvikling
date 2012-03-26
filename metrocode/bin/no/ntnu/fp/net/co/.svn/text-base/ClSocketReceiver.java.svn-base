package no.ntnu.fp.net.co;

import java.io.IOException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;

/**
 * Helper class for timed receiving. This thread runs until a packet is
 * received, or receive is cancelled using stopReceive().
 */
class ClSocketReceiver extends Thread {

    /** Hold a packet. */
    private KtnDatagram packet = null;
    /** Connection to listen on. */
    private ClSocket connection;
    /** Port to listen on. */
    private int port = -1;

    /**
     * Construct a receiver for a given port.
     * 
     * @param port
     *            Port to listen for packets on.
     */
    public ClSocketReceiver(int port) {
        this.port = port;
    }

    /** Try to receive a packet. */
    public void run() {
        try {
            connection = new ClSocket();
            packet = connection.receive(port);
            connection = null;
        }
        catch (Exception e) {
            Log.writeToLog("Exception while receiving: " + e.getMessage(), "ClSocketReceiver");
            e.printStackTrace();
        }
    }

    /** Get a packet, if one was received. */
    public KtnDatagram getPacket() {
        return packet;
    }

    /** Stop listening for packet. */
    public void stopReceive() {
        try {
            if (connection != null) connection.cancelReceive();
        }
        catch (IOException e) {
            // Ignore.
        }
    }

}
