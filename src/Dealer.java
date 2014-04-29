import java.io.IOException;
import java.net.SocketException;


public class Dealer {
	
	private static CommServer csModule;
	private static Flag flag;
	private static String Key;
	private static float fProb;
	private static float fTimeProb;
	private static int iSeconds;
	
	public static void main(String args [] ) {
			if(args.length<2) System.err.println("Not enough arguments");
			else{
				try {
					if(args[0].equals("-k") || args[1].equals("-K")) {
						Key=args[1];
					}
					else {
						System.err.println("key not provided");
					}
					if(args.length>3) {
						if(args[2].equals("-p") || args[2].equals("-P")) {
							fProb=Float.parseFloat(args[3]);
							if(args.length>6 && (args[4].equals("-t") || args[4].equals("-T"))) {
									fTimeProb=Float.parseFloat(args[5]);
									iSeconds=Integer.parseInt(args[6]);
							}
						}
						else if(args[2].equals("-t") || args[2].equals("-T")) {
							fTimeProb=Float.parseFloat(args[3]);
							iSeconds=Integer.parseInt(args[4]);
							if(args.length>6 && (args[5].equals("-p") || args[5].equals("-P"))) {
                                fProb=Float.parseFloat(args[3]);
							}
						}
                        else{
                            fTimeProb=0;
                            iSeconds=0;
                            fProb=0;
                        }
					}
					
					csModule = new CommServer(fProb/2, fTimeProb, iSeconds);
					flag = new Flag();
				} catch (SocketException e) {
					System.err.println("NET ERROR: " + e.getMessage());
				}
			}
		while(!System.in.equals("exit")) {
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
