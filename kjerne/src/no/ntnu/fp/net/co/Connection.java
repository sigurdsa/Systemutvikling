package no.ntnu.fp.net.co;

import java.net.InetAddress;

/**
 * Interface that defines the methods a Connection implementation must support.
 * 
 * @author Thomas &Oslash;sterlie
 * @version 0.1
 */
public interface Connection {

    /**
     * Connects to a remote instance.
     * 
     * @param remoteAddress
     *            address of remote instance
     * @param remotePort
     *            port remote instance is listening to
     * @throws java.io.IOException
     *             if an I/O error occurs during the connection
     * @throws java.net.SocketTimeoutException
     *             if timeout expires before connection is completed.
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws java.io.IOException,
            java.net.SocketTimeoutException;

    /**
     * Listens for a connection to be made to this Connection and accepts it.
     * 
     * @throws java.io.IOException
     *             if an I/O error occurs when waiting for a connection
     * @throws java.net.SocketTimeoutException
     *             if the timeout has been reached
     */
    public Connection accept() throws java.io.IOException, java.net.SocketTimeoutException;

    /**
     * Send a message to a remote instance.
     * 
     * @param msg
     *            the message to be sent
     * @throws java.net.ConnectException
     *             if no remote instance is connected
     * @throws java.io.IOException
     *             if an I/O error occurs when sending
     */
    public void send(String msg) throws java.net.ConnectException, java.io.IOException;

    /**
     * Synchronous receive of message from remote instance. Blocks until a
     * message is received.
     * 
     * @return the received message
     * @throws java.net.ConnectException
     *             if no remote instance is connected
     * @throws java.io.IOException
     *             if an I/O error occurs when sending
     */
    public String receive() throws java.net.ConnectException, java.io.IOException;

    /**
     * Closes this connection
     * 
     * @throws java.io.IOException
     *             if an I/O error occurs when closing this connection
     */
    public void close() throws java.io.IOException;

}
