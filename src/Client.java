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
		if(args.length<1) {
			System.err.println("Not enough arguments");
			return;
		}
		else{
			try {
				ccModule = new CommClient(args[0]);
				if(args.length>2) {
					if(args[1].equals("-k") || args[1].equals("-K")) {
						Key=args[2];
					}
				}
			} catch (UnknownHostException | SocketException e) {
				System.err.println("NET ERROR: " + e.getMessage());
			}
		}
		
		BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("Escriba comando");
			try {
				String strComand[] = brComand.readLine().split(" ");
				if(strComand.length<1){
					System.err.println("Not enough arguments");
					break;
				}
				else{
					String method = Data.PromptToMethod(strComand[0].toString());
					if(method.equals("NUEVO")){
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String designation = strComand[1].toString();
							for(int i = 2; i < strComand.length-1; i++) {
								designation = designation + " " + strComand[i].toString();
							}
							int maximum = Integer.parseInt(strComand[strComand.length-1]);
							nuevo(designation,maximum);
						}
					}
					else if(method.equals("QUITA")){
						if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							short code = Short.parseShort(strComand[1].toString());
							quita(code);
						}
					}
					else if(method.equals("INSCRIBE")){
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String name = strComand[1].toString();
							String nick = strComand[2].toString();
							inscribe(name, nick);
						}
					}
					else if(method.equals("PLANTILLA")){
							plantilla();
					}
					else if(method.equals("REPERTORIO")){
						if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							byte min = Byte.parseByte(strComand[1].toString());
							repertorio(min);
						}
					}
					else if(method.equals("JUEGA")){
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String nick = strComand[1].toString();
							short code = Short.parseShort(strComand[2].toString());
							juega(nick, code);
						}
					}
					else if(method.equals("TERMINA")){
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String nick = strComand[1].toString();
							short code = Short.parseShort(strComand[2].toString());
							termina(nick, code);
						}
					}
					else if(method.equals("LISTA")){
						if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							short code = Short.parseShort(strComand[1].toString());
							lista(code);
						}
					}
					else if(method.equals("FINAL")){
						break;
					}
					else if(method.equals("UNKNOWN")){
						System.out.println("Comando incorrecto");
					}
				}
			} catch (IOException e) {
				System.err.println("ERROR: " + e.getMessage());
				break;
			}
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Juego creado con id " + sResponse);
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Juego eliminado");
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Inscrito");
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
		//try {
			//dtParams.writeUTF(Key);
		//} catch (IOException e) {
			//System.err.println("ERROR: " + e.getMessage());
			//return Data.INTERNAL_ERROR;
		//}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.PLANTILLA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				String[] aResponse = sResponse.split(",|]|[");
				for(int ii=0; ii<aResponse.length; ii++){
					System.out.println(aResponse[ii]);
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				String[] aResponse = sResponse.split(",|]|[");
				for(int ii=0; ii<aResponse.length; ii++){
					System.out.println(aResponse[ii]);
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("jugando");
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				System.out.println("Desconectado");
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
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
		ByteArrayInputStream baResponse = new ByteArrayInputStream(byResponse);
		DataInputStream dtResponse = new DataInputStream(baResponse);
		
		try {
			int iError = dtResponse.readInt();
			int iServerError = dtResponse.readInt();
			String sResponse = dtResponse.readUTF();
			if(iError!=Data.OK  || iServerError!=Data.OK) {
				System.err.println("SERVER ERROR: " + iServerError);
				System.err.println("METHOD ERROR: " + iError);
				return Data.SERVER_ERROR;
			}
			else {
				String[] aResponse = sResponse.split(",|]|[");
				for(int ii=0; ii<aResponse.length; ii++){
					System.out.println(aResponse[ii]);
				}
				return status;
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
}
