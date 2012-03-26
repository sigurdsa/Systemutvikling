package no.ntnu.fp.net.co;

import java.net.InetAddress;

public class TestWorkerServer implements  KTNListener{
	private KTNWorker connection;
	private KTNWorker activeConnection;
	private int nr;
	
	public static void main(String[] args) {
		new TestWorkerServer();
	}
	public TestWorkerServer() {
		nr = 0;
		connection = new KTNWorker(true, this, 5555);
	}
    public void connectionReceived(KTNWorker worker) {
    	activeConnection = worker;
    }
    public void messageReceived(KTNWorker worker, String msg) {
    	try {
    		nr = nr + 2;
    		activeConnection.sendMSG("Server says: " + nr);
    		if (nr > 19) {
    			//activeConnection.disconnect(); //disconnect fra begge sider om denne er på
    		}
    	}
    	catch (Exception e) {
    		System.out.println("----------------------------------------------------------ERROR ENCOUNTERED: " + e + " when receiving: " + msg);
    	}
    }
    public void connectionClosed(KTNWorker worker) {
    	activeConnection = null;
    }
}
