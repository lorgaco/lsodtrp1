import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {
	
	private static CommClient ccModule;
	
	public static void main(String args [] ) {
		if(args.length<1) System.err.println("Not enough arguments");
		else{
			try {
				ccModule = new CommClient(args[0]);
			} catch (UnknownHostException | SocketException e) {
				System.err.println("NET ERROR: " + e.getMessage());
			}
		}
		BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		try {
			String strComand[] = brComand.readLine().split(" ");
			if(strComand.length<1){
				System.err.println("Not enough arguments");
			}
			else{
				String method = args[0].toString().toUpperCase();
				if(method.equals("NUEVO")){
					if(args.length<3) System.err.println("Not enough arguments");
					else{
						String designation = args[1].toString();
						int maximum = Integer.parseInt(args[2]);
						int result = nuevo(designation,maximum);
						System.out.println(result);
					}
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
	
	private static int nuevo(String designation, int maximum){
		System.out.println(designation + " " + maximum);
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(designation);
			dtParams.writeInt(maximum);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}

		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.NUEVO);
		
		Message msResponse = new Message();
		
		int status=ccModule.doOperation(msRequest, msResponse);
		
		System.out.println("implement msResponse");
		
		return status;
	}

}
