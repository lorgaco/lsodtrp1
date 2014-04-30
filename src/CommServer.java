import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.io.*;

import static java.lang.Thread.sleep;

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

	public CommServer(float fProb, float fTimeProb, int iSeconds) throws SocketException {
		// socket creation
		dtSocket = new DatagramSocket(Data.PORT);
		ResponseList = new ArrayList<ArrayObject>();
		iIdMessage = 0;
		this.fProb = fProb/100;
		this.fTimeProb = fTimeProb/100;
		this.iSeconds = iSeconds;
		generator = new Random(0);
		
	}
	
	public int getRequest(Message msRequest) throws IOException {

		byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
		// create packet
		DatagramPacket pkRequest = new DatagramPacket(InBuffer,	InBuffer.length);
		
		while(true){
            boolean received = false;


            while(!received) {
                float aux_random = generator.nextFloat();
//                System.out.println("rand tirar = " + aux_random + "; fProb = " + fProb);  // PRINT
                if(aux_random < fProb) {
                    System.out.println("    CommServer -> Package thrown => lost");  // PRINT
                    dtSocket.receive(pkRequest);
                }
                float aux_random2 = generator.nextFloat();
//                System.out.println("rand sleep = " + aux_random2 + "; fProb = " + fTimeProb);  // PRINT
                if(aux_random2 < fTimeProb) {
                    System.out.println("    CommServer -> Package delayed");  // PRINT
                    dtSocket.receive(pkRequest);
                    received = true;
                    try {
                        sleep(iSeconds * 1000);
                    } catch (Exception e) {
                        System.err.println("Queue Simulator: " + e.getMessage());
                    }
                }
                if(aux_random > fProb || aux_random2 > fTimeProb) {
                    dtSocket.receive(pkRequest);
                    received = true;
                }
            }
/*
            while(aux_random < fProb || entra){
                if(!entra) System.out.println("tira");  // PRINT
                entra = false;
				dtSocket.receive(pkRequest);
				aux_random = generator.nextFloat();
				System.out.println("rand sleep = " + aux_random + "; fProb = " + fTimeProb);  // PRINT
				if(aux_random < fTimeProb){
                    System.out.println("sleep");  // PRINT
					try {
						sleep(iSeconds * 1000);
					} catch (InterruptedException e) {
						System.err.println("Queue Simulator: " + e.getMessage());
					}
				}
                aux_random = generator.nextFloat();
                System.out.println("rand tirar = " + aux_random + "; fProb = " + fProb);  // PRINT
			}
*/
            System.out.println("    CommServer -> Package received");  // PRINT
		
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

			System.out.println("    CommServer -> Message request type = " + msRequest.getiTypeMessage());  // PRINT
		
			// search for duplicates
			if(!ResponseList.isEmpty()){
//				System.out.println("search");  // PRINT
				ListIterator<ArrayObject> it = ResponseList.listIterator();
				int i = 0;
				for(i = 0; i < ResponseList.size(); i++){
//					System.out.println("busca");  // PRINT
					int index = it.nextIndex();
					ArrayObject pkArray = it.next();
					if(pkArray.Addr.equals(ClientAddr) && pkArray.Port.equals(ClientPort)){
						if(pkArray.Message.getiIdMessage()==msRequest.getiIdMessage()){
							if(msRequest.getiTypeMessage()==Data.REQUEST){
								// resend previous response
//								System.out.println("Resend, IdMessage = " + pkArray.Message.getiIdMessage());  // PRINT
//                                System.out.println("Resend, IdMethod = " + pkArray.Message.getiIdMethod());  // PRINT
//                                System.out.println("Resend, TypeMessage = " + pkArray.Message.getiTypeMessage());  // PRINT
								sendMessage(pkArray.Message);
							}
							else if(msRequest.getiTypeMessage()==Data.ACK){
								// delete acknowled messages
								System.out.println("    CommServer -> Search -> Delete by ACK");  // PRINT
								ResponseList.remove(index);
							}
						}
						else if(pkArray.Message.getiIdMessage()<msRequest.getiIdMessage()){
							// delete previous message if exists
							System.out.println("    CommServer -> Search -> Delete by higher id and return");  // PRINT
							ResponseList.remove(index);
							
							// Update iIdMessage
							iIdMessage = msRequest.getiIdMessage();
							return Data.OK;
						}
					}
				}
				if(i == ResponseList.size()-1){
					// Update iIdMessage
					System.out.println("    CommServer -> Search -> Not found in the list and return");  // PRINT
					iIdMessage = msRequest.getiIdMessage();
					return Data.OK;
				}
			}
			else{
				// Update iIdMessage
				System.out.println("    CommServer -> Search -> Empty list and return");  // PRINT
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
		
		float aux_random = generator.nextFloat();
//		System.out.println("Reply rand " + aux_random + "; fProb " + fProb);  // PRINT
		if(aux_random>(fProb)){
			System.out.println("    CommServer -> Send response");  // PRINT
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

            // extract fields todo delete this, just for debugging
//            System.out.println("IdMessage Sent = " + msResponse.getiIdMessage());  // PRINT
//            System.out.println("IdMethod Sent = " + msResponse.getiIdMethod());  // PRINT
//            System.out.println("TypeMessage Sent = " + msResponse.getiTypeMessage());  // PRINT
			
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
