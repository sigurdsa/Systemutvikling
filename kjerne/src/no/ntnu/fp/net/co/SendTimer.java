package no.ntnu.fp.net.co;

import java.io.IOException;
import java.util.TimerTask;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.co.AbstractConnection;

/**
 * A helper class used when sending datagrams and waiting for ack. The class
 * specifies a TimerTask used in the Timer in the send-method of an
 * Connection-implementation. The class will resend a datagram using a socket at
 * timeout. The datagram and socket are specified in the constructor.
 * 
 * @see AbstractConnection#simplySendPacket(KtnDatagram)
 * @see java.util.TimerTask
 * @see java.util.Timer
 * @author Stein Jakob Nordbø
 */
public class SendTimer extends TimerTask {

    private ClSocket socket;
    private KtnDatagram packet;
    private int tries;

    /**
     * Simple constructor.
     * 
     * @param socket
     *            - the socket in which the datagram is to be send if timeout
     * @param packet
     *            - the packet to send if timeout
     */
    public SendTimer(ClSocket socket, KtnDatagram packet) {
        this.socket = socket;
        this.packet = packet;
        tries = 0;
    }

    /**
     * Perform a packet (re)send. The method will be called upon a timeout from
     * the assosiated timer. If called, it means that a correct ack is not
     * received within the desired interval, and the datagram is resend on the
     * same socket.
     */
    public void run() {
        try {
            Log.writeToLog(packet, "Sending this datagram (try: " + ++tries + ")", "SendTimer");
            
            socket.send(packet);
        }
        catch (ClException e) {
            Log.writeToLog("ERROR: Could not establish a connection to " + packet.getDest_addr()
                    + ":" + packet.getDest_port(), "SendTimer");
        }
        catch (IOException e) {
            Log.writeToLog("ERROR: Could not establish a connection to " + packet.getDest_addr()
                    + ":" + packet.getDest_port(), "SendTimer");
        }
    }
}
