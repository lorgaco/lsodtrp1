import java.io.*;
import java.net.*;

public class CommClient{
	
	
	private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private DatagramPacket pkRequest;
	//private Integer ClientPort;
	private int iIdMessage;

	public CommClient(String ServerHostName) throws UnknownHostException, SocketException {
		// host checking
		ipHost = InetAddress.getByName(ServerHostName);
		// socket creation
		dtSocket = new DatagramSocket();
		//ClientPort = dtSocket.getLocalPort();
		iIdMessage = 0;
	}
	
	public int doOperation(Message msRequest, Message msResponse) {
        System.out.println("===== CommClient =====");  // PRINT
		msRequest.setiTypeMessage(Data.REQUEST);
		iIdMessage++;
		msRequest.setiIdMessage(iIdMessage);
		
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
			pkRequest = new DatagramPacket(baOut.toByteArray(),dtOut.size(), ipHost, Data.PORT);
			
			// send the packet
			System.out.println("    CommClient -> Send request");  // PRINT
	    	dtSocket.send(pkRequest);
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			return Data.NET_ERROR;
		}
		
		try {
            // Create the input buffer
            byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
            DatagramPacket pkResponse = new DatagramPacket(InBuffer,InBuffer.length);
            ByteArrayInputStream baIn = new ByteArrayInputStream(InBuffer);
            DataInputStream dtIn = new DataInputStream(baIn);

			// Receive the packet
			int maxPkts = (int) Math.floor((float) (Data.SOCKET_TIMEOUT-Data.SOCKET_RTX_PERIOD)/(float) Data.SOCKET_RTX_PERIOD);
			int count = 0;
            do {
                InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
                pkResponse = new DatagramPacket(InBuffer,InBuffer.length);
                baIn = new ByteArrayInputStream(InBuffer);
                dtIn = new DataInputStream(baIn);
				try {
					dtSocket.setSoTimeout(Data.SOCKET_RTX_PERIOD);
	                dtSocket.receive(pkResponse);
					System.out.println("    CommClient -> Package received");  // PRINT
				} catch (SocketTimeoutException ste) {
					System.out.println("    CommClient -> Resend request");  // PRINT
					dtSocket.send(pkRequest);
					count++;
//					System.out.println("maxPkts " + maxPkts + "; count " + count);  // PRINT
					if(count>=maxPkts) {
						System.err.println("Can't reach server");
						return Data.NET_ERROR;
					}
				}
                // extract fields 1
                msResponse.setiTypeMessage(dtIn.readInt());
                msResponse.setiIdMethod(dtIn.readInt());
                msResponse.setiIdMessage(dtIn.readInt());
//                System.out.println("IdMessage Received = " + msResponse.getiIdMessage());  // PRINT
//                System.out.println("IdMethod Received = " + msResponse.getiIdMethod());  // PRINT
//                System.out.println("TypeMessage Received = " + msResponse.getiTypeMessage());  // PRINT

            } while(msResponse.getiIdMessage() != iIdMessage);
			

            // extract fields 2
			msResponse.setiLengthArgs(dtIn.readInt());
			byte[] byArguments = new byte[Data.MAX_ARGUMENTS_SIZE];
			dtIn.read(byArguments, 0, msResponse.getiLengthArgs());
			msResponse.setbyArguments(byArguments);
			
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			//timer.cancel();
			return Data.NET_ERROR;
		}
		
		try {
			Message msAck = new Message();
			msAck.setiTypeMessage(Data.ACK);
			msAck.setiIdMessage(msRequest.getiIdMessage());
			msAck.setiIdMethod(msRequest.getiIdMethod());
			msAck.setiLengthArgs(0);
			
			ByteArrayOutputStream baOut = new ByteArrayOutputStream();
			DataOutputStream dtOut = new DataOutputStream(baOut);
			
			// fill fields
			dtOut.writeInt(msAck.getiTypeMessage());
			dtOut.writeInt(msAck.getiIdMethod());
			dtOut.writeInt(msAck.getiIdMessage());
			dtOut.writeInt(msAck.getiLengthArgs());
			dtOut.close();
						
			// create packet
			pkRequest = new DatagramPacket(baOut.toByteArray(),dtOut.size(), ipHost, Data.PORT);
						
			// send the packet
			dtSocket.send(pkRequest);
			System.out.println("    CommClient -> Send ACK");  // PRINT
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			return Data.NET_ERROR;
		}
		
//		System.out.println("sale");  // PRINT
        System.out.println("=== End CommClient ===");  // PRINT
		return Data.OK;
	}

}