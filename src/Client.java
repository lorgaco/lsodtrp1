import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
	public static void main(String args [] ) {
		if(args.length<1) System.err.println("Not enough arguments");
		else{
			System.out.println("createCommServer");
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

		Message msg = new Message();
		byte[] byParams = baParams.toByteArray();
		msg.setbyArguments(byParams);
		msg.setiLengthArgs(byParams.length);
		msg.setiIdMethod(Data.NUEVO);
		
		
		
		return Data.OK;
	}

}
