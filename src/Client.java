import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {
	
	private static CommClient ccModule;
	private static String Key;
	
	public static void main(String args [] ) {
		if(args.length<1) System.err.println("Not enough arguments");
		else{
			try {
				ccModule = new CommClient(args[0]);
			} catch (UnknownHostException | SocketException e) {
				System.err.println("NET ERROR: " + e.getMessage());
			}
		}
		
		//TODO store key
		Key="clave";
		
		BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		try {
			String strComand[] = brComand.readLine().split(" ");
			if(strComand.length<1){
				System.err.println("Not enough arguments");
			}
			else{
				String method = strComand[0].toString().toUpperCase();
				if(method.equals("NUEVO")){
					if(strComand.length<3) System.err.println("Not enough arguments");
					else{
						String designation = strComand[1].toString();
						int maximum = Integer.parseInt(strComand[2]);
						int result = nuevo(designation,maximum);
						System.out.println(result);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
	

	//===================================================//
	//                   Client Stubs                    //
	//===================================================//
	
	private static int nuevo(String designation, int maximum){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(designation);
			dtParams.writeInt(maximum);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.NUEVO);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			short sCode = dtResponse.readShort();
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Game created with ID " + sCode);
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int quita(short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeShort(code);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.QUITA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Game deleted");
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int inscribe(String name, String nick){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(name);
			dtParams.writeUTF(nick);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.INSCRIBE);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Registered user");
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int plantilla(){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(Key);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.PLANTILLA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			String[] sNames = dtResponse.readUTF().split(" ");
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Registered users:");
				for(int i=0;i<sNames.length;i++){
					System.out.println(sNames[i]);
				}
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int repertorio(byte minimum){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeByte(minimum);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.REPERTORIO);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			String[] sNames = dtResponse.readUTF().split(" ");
			short[] sCodes = new short[sNames.length];
			for(int i=0;i<sNames.length;i++){
				sCodes[i]= dtResponse.readShort();
			}
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Registered users:");
				for(int i=0;i<sNames.length;i++){
					System.out.println(sNames[i] + ": " + sCodes[i]);
				}
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int juega(String nick, short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(nick);
			dtParams.writeShort(code);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.JUEGA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("User is playing");
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int termina(String nick, short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(nick);
			dtParams.writeShort(code);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.TERMINA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("User stoped playing");
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	private static int lista(short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeShort(code);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.LISTA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			String[] sNames = dtResponse.readUTF().split(" ");
			if(iError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Registered users:");
				for(int i=0;i<sNames.length;i++){
					System.out.println(sNames[i]);
				}
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
}
