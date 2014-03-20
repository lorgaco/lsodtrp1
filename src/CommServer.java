import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.io.*;

public class CommServer {
	//private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private InetAddress ClientAddr;
	private Integer ClientPort;
	List<ArrayObject> ResponseList;
	private int iIdMessage;

	public CommServer() throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket(Data.PORT);
		ResponseList = new ArrayList<ArrayObject>();
		iIdMessage = 0;
		
	}
	
	public int getRequest(Message msRequest) throws IOException {

		byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
		// create packet
		DatagramPacket pkRequest = new DatagramPacket(InBuffer,	InBuffer.length);
		dtSocket.receive(pkRequest);
		
		ClientAddr = pkRequest.getAddress();
		ClientPort = pkRequest.getPort();		
		
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
		
		// search for duplicates
		if(!ResponseList.isEmpty()){
			ListIterator<ArrayObject> it = ResponseList.listIterator();
			for(int i = 0; i < ResponseList.size(); i++){
				ArrayObject pkArray = it.next();
				if(pkArray.Addr==ClientAddr && pkArray.Port==ClientPort){
					if(pkArray.Message.getiIdMessage()==msRequest.getiIdMessage()){
						//do stuff
					}
				}
			}
		}
		
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
			DatagramPacket pkResponse = new DatagramPacket(baOut.toByteArray(),
															dtOut.size(), ClientAddr, ClientPort);
			
			// save the packet
			ArrayObject pkArray = new ArrayObject();
			pkArray.Addr = ClientAddr;
			pkArray.Port = ClientPort;
			pkArray.Message = msResponse;
			ResponseList.add(pkArray);
			
			// send the packet
			dtSocket.send(pkResponse);
			
		} catch (IOException e) {
			System.err.println("I/O: " + e.getMessage());
		}
		return Data.OK;
		
	}
}

class ArrayObject {
	protected InetAddress Addr;
	protected Integer Port;
	protected Message Message;
}