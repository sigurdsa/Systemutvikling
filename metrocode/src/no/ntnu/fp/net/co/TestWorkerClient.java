package no.ntnu.fp.net.co;
import java.net.InetAddress;

public class TestWorkerClient implements KTNListener {
	private KTNWorker connection;
	private int nr;
	
	public static void main(String[] args) {
		new TestWorkerClient();
	}
	public TestWorkerClient() {
		nr = 1;
		connection = new KTNWorker(false, this, 4001);
		try {
			connection.connect(InetAddress.getLocalHost(), 5555);
			connection.sendMSG("Client says: " + nr);
		}
		catch (Exception e) {
			System.out.println("-----------------------------ERROR ENCOUNTERED: " + e);
		}
	}
    public void connectionReceived(KTNWorker worker) {
    	//klient ignorerer dette
    }
    public void messageReceived(KTNWorker worker, String msg) {
    	if (msg.equals("Server says: 20")) {
    		connection.disconnect();
    	}
    	else {
    		nr = nr + 2;
    		try {
    			connection.sendMSG("Client says: " + nr);
    		}
    		catch (Exception e) {
    			System.out.println("-----------------------------ERROR ENCOUNTERED: " + e);
    		}
    	}
    }
    public void connectionClosed(KTNWorker worker) {
    	
    }
}
