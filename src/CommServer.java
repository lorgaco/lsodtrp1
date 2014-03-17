import java.net.*;
import java.io.*;

public class CommServer {
	private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private int iIdMessage;

	public CommServer() throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket();
		iIdMessage = 0;
		
	}
	
	public int getRequest(Message msRequest) throws IOException {

		byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
		// create packet
		DatagramPacket pkRequest = new DatagramPacket(InBuffer,	InBuffer.length);
		dtSocket.receive(pkRequest);
		
		ByteArrayInputStream baIn = new ByteArrayInputStream(InBuffer);
		DataInputStream dtIn = new DataInputStream(baIn);
		
		// extract fields
		msRequest.setiTypeMessage(dtIn.readInt());
		msRequest.setiIdMethod(dtIn.readInt());
		msRequest.setiIdMessage(dtIn.readInt());
		msRequest.setiLengthArgs(dtIn.readInt());
		byte[] byArguments = new byte[Data.MAX_ARGUMENTS_SIZE];
		dtIn.read(byArguments, 0, msRequest.getiLengthArgs());
		msRequest.setbyArguments(byArguments);
		
		// Update iIdMessage
		iIdMessage = msRequest.getiIdMessage();

		return Data.OK;
	}
	
	public int sendReply(Message msResponse) {
		
		msResponse.setiTypeMessage(Data.RESPONSE);
		msResponse.setiIdMessage(iIdMessage);
		
		try {
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			DataOutputStream dtOut = new DataOutputStream(baOut);
			
			// fill fields
			dtOut.writeInt(msResponse.getiTypeMessage());
			dtOut.writeInt(msResponse.getiIdMethod());
			dtOut.writeInt(msResponse.getiIdMessage());
			dtOut.writeInt(msResponse.getiLengthArgs());
			dtOut.write(msResponse.getbyArguments(),0,msResponse.getiLengthArgs());
			dtOut.close();
			
			// create packet
			DatagramPacket pkRequest = new DatagramPacket(baOut.toByteArray(),
															dtOut.size(), ipHost, Data.PORT);
			// send the packet
			dtSocket.send(pkRequest);
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
		}
		return Data.OK;
		
	}
}