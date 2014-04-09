import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.io.*;

public class CommServer {
	//private InetAddress ipHost;
	private DatagramSocket dtSocket;
	private InetAddress ClientAddr;
	private Integer ClientPort;
	List<ArrayObject> ResponseList;
	private int iIdMessage;
	
	private float fProb;
	private float fTimeProb;
	private int iSeconds;
	
	private Random generator;

	public CommServer() throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket(Data.PORT);
		ResponseList = new ArrayList<ArrayObject>();
		iIdMessage = 0;
		fProb = 0;
		fTimeProb = 0;
		iSeconds = 0;
		generator = new Random(0);
		
	}
	public CommServer(float fProb) throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket(Data.PORT);
		ResponseList = new ArrayList<ArrayObject>();
		iIdMessage = 0;
		fProb = fProb;
		fTimeProb = 0;
		iSeconds = 0;
		generator = new Random(0);
		
	}
	public CommServer(float fProb, float fTimeProb, int iSeconds) throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket(Data.PORT);
		ResponseList = new ArrayList<ArrayObject>();
		iIdMessage = 0;
		fProb = fProb;
		fTimeProb = fTimeProb;
		iSeconds = iSeconds;
		generator = new Random(0);
		
	}
	
	public int getRequest(Message msRequest) throws IOException {

		byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
		// create packet
		DatagramPacket pkRequest = new DatagramPacket(InBuffer,	InBuffer.length);
		
		while(true){
			while(generator.nextFloat()<(fProb/2)){
				dtSocket.receive(pkRequest);
				if(generator.nextFloat()<fTimeProb){
					try {
						wait(iSeconds*1000);
					} catch (InterruptedException e) {
						System.err.println("Queue Simulator: " + e.getMessage());
					}
				}
			}
		
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
				int i = 0;
				for(i = 0; i < ResponseList.size(); i++){
					int index = it.nextIndex();
					ArrayObject pkArray = it.next();
					if(pkArray.Addr.equals(ClientAddr) && pkArray.Port.equals(ClientPort)){
						if(pkArray.Message.getiIdMessage()==msRequest.getiIdMessage()){
							if(msRequest.getiTypeMessage()==Data.REQUEST){
								// resend previous response
								sendMessage(pkArray.Message);
							}
							else if(msRequest.getiTypeMessage()==Data.ACK){
								// delete acknowled messages
								ResponseList.remove(index);
							}
						}
						else if(pkArray.Message.getiIdMessage()<msRequest.getiIdMessage()){
							// delete previous message if exists
							ResponseList.remove(index);
							
							// Update iIdMessage
							iIdMessage = msRequest.getiIdMessage();
							return Data.OK;
						}
					}
				}
				if(i == ResponseList.size()-1){
					// Update iIdMessage
					System.out.println("return 1");
					iIdMessage = msRequest.getiIdMessage();
					return Data.OK;
				}
			}
			else{
				// Update iIdMessage
				iIdMessage = msRequest.getiIdMessage();
				return Data.OK;
			}
		}
	}
	
	public int sendReply(Message msResponse) {
		
		msResponse.setiTypeMessage(Data.RESPONSE);
		msResponse.setiIdMessage(iIdMessage);
		
		// save the packet
		ArrayObject pkArray = new ArrayObject();
		pkArray.Addr = ClientAddr;
		pkArray.Port = ClientPort;
		pkArray.Message = msResponse;
		ResponseList.add(pkArray);
		
		if(generator.nextFloat()<(fProb/2)){
			return sendMessage(msResponse);
		}
		else return 0;
	}
	
	public int sendMessage(Message msResponse){
		
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