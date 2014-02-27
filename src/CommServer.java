import java.net.*;
import java.io.*;

public class CommServer {
	private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private int iIdMessage;

	public CommServer(String ServerHostName) throws UnknownHostException, SocketException {
		// host checking
		ipHost = InetAddress.getByName(ServerHostName);
		// socket creation
		dtSocket = new DatagramSocket();
		iIdMessage = 0;
	}
	
	public int getRequest(Message msRequest) {
		
		return Data.OK;
	}
}