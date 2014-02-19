import java.io.*;
import java.net.*;

public class CommClient {
	
	private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private int iIdMessage;

	public CommClient(String ServerHostName) throws UnknownHostException, SocketException {
		// host checking
		ipHost = InetAddress.getByName(ServerHostName);
		// socket creation
		dtSocket = new DatagramSocket();
		iIdMessage = 0;
	}
	
	public int doOperation(Message msRequest, Message msResponse) {
		msRequest.setiTypeMessage(Data.REQUEST);
		msRequest.setiIdMessage(iIdMessage++);
		
		try {
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			DataOutputStream dtOut = new DataOutputStream(baOut);
			
			// fill fields
			dtOut.writeInt(msRequest.getiTypeMessage());
			dtOut.writeInt(msRequest.getiIdMethod());
			dtOut.writeInt(msRequest.getiIdMessage());
			dtOut.writeInt(msRequest.getiLengthArgs());
			dtOut.write(msRequest.getbyArguments(),0,msRequest.getiLengthArgs());
			dtOut.close();
			
			// create packet
			DatagramPacket pkRequest = new DatagramPacket(baOut.toByteArray(),
															dtOut.size(), ipHost, Data.PORT);
			// send the packet
			dtSocket.send(pkRequest);
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
		}
		
		try {
			// Create the input buffer
			byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE] ;
			DatagramPacket pkResponse = new DatagramPacket(InBuffer,InBuffer.length);
			
			// Receive the packet
			dtSocket.receive(pkResponse);
			
			ByteArrayInputStream baIn = new ByteArrayInputStream(InBuffer);
			DataInputStream dtIn = new DataInputStream(baIn);
			
			// extract fields
			msResponse.setiTypeMessage(dtIn.readInt());
			msResponse.setiIdMethod(dtIn.readInt());
			msResponse.setiIdMessage(dtIn.readInt());
			msResponse.setiLengthArgs(dtIn.readInt());
			byte[] byArguments = new byte[Data.MAX_ARGUMENTS_SIZE];
			dtIn.read(byArguments, 0, msResponse.getiLengthArgs());
			msResponse.setbyArguments(byArguments);
			
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
		}
		
		return Data.OK;
	}
	
}