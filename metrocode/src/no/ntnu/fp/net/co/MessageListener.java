package no.ntnu.fp.net.co;
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
