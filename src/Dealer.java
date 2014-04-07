import java.io.IOException;
import java.net.SocketException;


public class Dealer {
	
	private static CommServer csModule;
	private static Flag flag;
	
	public static void main(String args [] ) {
		while(!System.in.equals("exit")) {
			if(args.length>1) System.err.println("Not enough arguments");
			else{
				try {
					csModule = new CommServer();
					flag = new Flag();
				} catch (SocketException e) {
					System.err.println("NET ERROR: " + e.getMessage());
				}
			}
		//	BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		
			Message msRequest = new Message();
			try {
				csModule.getRequest(msRequest);
			
				byte [] bArguments = deal(msRequest);
			
				Message msResponse = new Message();
				msResponse.setiIdMethod(msRequest.getiIdMethod());
				msResponse.setbyArguments(bArguments);
				msResponse.setiLengthArgs(bArguments.length);
			
				csModule.sendReply(msResponse);
			
			} catch (IOException e) {
				// 	TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static byte[] deal(Message msRequest) {
		Answer result = new Answer();
		int method = msRequest.getiIdMethod();
		switch(method) {
			case 1:
				result = flag.nuevo(msRequest);
				System.out.println(result);
				break;
			case 2:
				result = flag.quita(msRequest);
				System.out.println(result);
				break;
			case 3:
				result = flag.inscribe(msRequest);
				System.out.println(result);
				break;
			case 4:
				result = flag.plantilla(msRequest);
				System.out.println(result);
				break;
			case 5:
				result = flag.repertorio(msRequest);
				System.out.println(result);
				break;
			case 6:
				result = flag.juega(msRequest);
				System.out.println(result);
				break;
			case 7:
				result = flag.termina(msRequest);
				System.out.println(result);
				break;
			case 8:
				result = flag.lista(msRequest);
				System.out.println(result);
				break;
		}
		return result.toByteStruct();
	}

}
