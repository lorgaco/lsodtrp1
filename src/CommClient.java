import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class CommClient{
	
	
	private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private DatagramPacket pkRequest;
	Timer timer;
	//private Integer ClientPort;
	private int iIdMessage;

	public CommClient(String ServerHostName) throws UnknownHostException, SocketException {
		// host checking
		ipHost = InetAddress.getByName(ServerHostName);
		// socket creation
		dtSocket = new DatagramSocket();
		dtSocket.setSoTimeout(Data.SOCKET_TIMEOUT);
		//ClientPort = dtSocket.getLocalPort();
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
			pkRequest = new DatagramPacket(baOut.toByteArray(),dtOut.size(), ipHost, Data.PORT);
			
			// send the packet
	    	dtSocket.send(pkRequest);
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			return Data.NET_ERROR;
		}
		
		try {
			// Create the input buffer
			byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE] ;
			DatagramPacket pkResponse = new DatagramPacket(InBuffer,InBuffer.length);
            ByteArrayInputStream baIn = new ByteArrayInputStream(InBuffer);
            DataInputStream dtIn = new DataInputStream(baIn);
			
			// Receive the packet
			timer = new Timer();
			timer.schedule(new Wait(dtSocket, pkRequest), Data.SOCKET_RTX_PERIOD, Data.SOCKET_RTX_PERIOD); //schedule the task to be run at SOCKET_RTX_PERIOD ms time
            do {
                dtSocket.receive(pkResponse);
                // extract fields 1
                msResponse.setiTypeMessage(dtIn.readInt());
                msResponse.setiIdMethod(dtIn.readInt());
                msResponse.setiIdMessage(dtIn.readInt());
            }while(msResponse.getiIdMessage()!=iIdMessage-1);
			timer.cancel();

            // extract fields 2
			msResponse.setiLengthArgs(dtIn.readInt());
			byte[] byArguments = new byte[Data.MAX_ARGUMENTS_SIZE];
			dtIn.read(byArguments, 0, msResponse.getiLengthArgs());
			msResponse.setbyArguments(byArguments);
			
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			timer.cancel();
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
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
			timer.cancel();
			return Data.NET_ERROR;
		}
		
		return Data.OK;
	}

}

class Wait extends TimerTask{
	
	
	private DatagramSocket Socket;
	private DatagramPacket Packet;
	int count;
	int maxPkts;
    Wait(DatagramSocket dtSocket, DatagramPacket pkRequest)
    {
    	Socket = dtSocket;
    	Packet = pkRequest;
    	count = 0;
    	maxPkts = (int) Math.floor((float) (Data.SOCKET_TIMEOUT-Data.SOCKET_RTX_PERIOD)/(float) Data.SOCKET_RTX_PERIOD);
    }

    public void run()
    {
    	try {
    		if(count >= maxPkts) {
    			System.err.println("Can't reach server");
                this.cancel();
    		} else {
    			System.out.println("envia");
				Socket.send(Packet);
				count++;
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}