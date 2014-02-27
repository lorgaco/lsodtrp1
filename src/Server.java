import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;


public class Server {
	
	private static CommServer csModule;
	
	public static void main(String args [] ) {
		if(args.length>1) System.err.println("Not enough arguments");
		else{
			try {
				csModule = new CommServer();
			} catch (SocketException e) {
				System.err.println("NET ERROR: " + e.getMessage());
			}
		}
		//BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		
		Message msRequest = new Message();
		try {
			csModule.getRequest(msRequest);
			
			Message msResponse = new Message();
			int requestIdMethod = msRequest.getiIdMethod();
			byte [] bArguments = new byte[128];
			msResponse.setiIdMethod(requestIdMethod);
			msResponse.setbyArguments(bArguments);
			msResponse.setiLengthArgs(bArguments.length);
			
			csModule.sendReply(msResponse);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
