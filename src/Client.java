import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


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
                        System.out.println("Admin key: " + Key);  // PRINT
					}
					else {
						Key = null;
						System.out.println("No admin key");  // PRINT
					}
				}
				else {
					Key = null;
					System.out.println("No admin key");  // PRINT
				}
			} catch (Exception e) {
				System.err.println("NET ERROR: " + e.getMessage());
			}
		}
		
		BufferedReader brComand = new BufferedReader(new InputStreamReader(System.in));
		while(true){
            System.out.println(" ");  // PRINT
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
                        System.out.println("NUEVO");
                        if(Key == null) System.out.println("No Key provided. This operation can only be done by Admin");
						else if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String designation = strComand[1].toString();
							for(int i = 2; i < strComand.length-1; i++) {
								designation = designation + " " + strComand[i].toString();
							}
							if(designation.length() > 30) System.err.println("FORMAT ERROR > 30 characters");
							else {
								try {
									int maximum = Integer.parseInt(strComand[strComand.length-1]);
									sNuevo resultado = nuevo(designation,maximum);
                                    if(resultado.error!=Data.OK) System.err.println("METHOD ERROR: " + Data.ErrorToString(resultado.error));
                                    else System.out.println("Juego creado con id " + resultado.code);
								} catch (Exception e) {
									System.err.println("FORMAT ERROR: " + e.getMessage());
								}
							}
						}
					}
					else if(method.equals("QUITA")){
                        System.out.println("QUITA");
                        if(Key == null) System.out.println("No Key provided. This operation can only be done by Admin");
						else if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							try {
								short code = Short.parseShort(strComand[1].toString());
								int resultado = quita(code);
                                if(resultado!=Data.OK) System.err.println("METHOD ERROR: " + Data.ErrorToString(resultado));
                                else System.out.println("Juego eliminado");
							} catch (Exception e) {
								System.err.println("FORMAT ERROR: " + e.getMessage());
							}
						}
					}
					else if(method.equals("INSCRIBE")){
                        System.out.println("INSCRIBE");
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
                            String name = strComand[1].toString();
                            for(int i = 2; i < strComand.length-1; i++) {
                                name = name + " " + strComand[i].toString();
                            }
							if(name.length() > 48) System.err.println("FORMAT ERROR > 48 characters");
							else {
                            	String alias = strComand[strComand.length-1].toString();
								if(alias.length() > 8) System.err.println("FORMAT ERROR > 8 characters");
								else {
									int resultado = inscribe(name, alias);
                                    if(resultado!=Data.OK) System.err.println("METHOD ERROR: " + Data.ErrorToString(resultado));
                                    else System.out.println("Inscrito");
								}
							}
						}
					}
					else if(method.equals("PLANTILLA")){
                        System.out.println("PLANTILLA");
                        if(Key == null) System.out.println("No Key provided. This operation can only be done by Admin");
                        else {
                            List<Jugador> resultado = plantilla();
                            ListIterator<Jugador> it = resultado.listIterator();
                            for(int ii=0; ii<resultado.size(); ii++) {
                                Jugador player = it.next();
                                System.out.println(player.name + " (" + player.alias + ").");
                            }
                        }
					}
					else if(method.equals("REPERTORIO")){
                        System.out.println("REPERTORIO");
						if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							try {
								byte min = Byte.parseByte(strComand[1].toString());
                                List<Juego> resultado = repertorio(min);
                                ListIterator<Juego> it = resultado.listIterator();
                                for(int ii=0; ii<resultado.size(); ii++) {
                                    Juego game = it.next();
                                    System.out.println(game.designation + " (" + game.code + "): Max=" + game.maximum + ".");
                                }
							} catch (Exception e) {
								System.err.println("FORMAT ERROR: " + e.getMessage());
							}
						}
					}
					else if(method.equals("JUEGA")){
                        System.out.println("JUEGA");
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String nick = strComand[1].toString();
							try {
								short code = Short.parseShort(strComand[2].toString());
								int resultado = juega(nick, code);
                                if(resultado!=Data.OK) System.err.println("METHOD ERROR: " + Data.ErrorToString(resultado));
                                else System.out.println("Jugando");
							} catch (Exception e) {
								System.err.println("FORMAT ERROR: " + e.getMessage());
							}
						}
					}
					else if(method.equals("TERMINA")){
                        System.out.println("TERMINA");
						if(strComand.length<3) System.err.println("Not enough arguments");
						else{
							String nick = strComand[1].toString();
							try {
								short code = Short.parseShort(strComand[2].toString());
								int resultado = termina(nick, code);
                                if(resultado!=Data.OK) System.err.println("METHOD ERROR: " + Data.ErrorToString(resultado));
                                else System.out.println("Desconectado");
							} catch (Exception e) {
								System.err.println("FORMAT ERROR: " + e.getMessage());
							}
						}
					}
					else if(method.equals("LISTA")){
                        System.out.println("LISTA");
						if(strComand.length<2) System.err.println("Not enough arguments");
						else{
							try {
								short code = Short.parseShort(strComand[1].toString());
								List<Jugador> resultado = lista(code);
                                ListIterator<Jugador> it = resultado.listIterator();
                                for(int ii=0; ii<resultado.size(); ii++) {
                                    Jugador player = it.next();
                                    System.out.println(player.name + " (" + player.alias + ").");
                                }
							} catch (Exception e) {
								System.err.println("FORMAT ERROR: " + e.getMessage());
							}
						}
					}
					else if(method.equals("FINAL")){
                        System.out.println("FINAL");
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
	
	private static sNuevo nuevo(String designation, int maximum){
        sNuevo out = new sNuevo();
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(designation);
			dtParams.writeInt(maximum);
            dtParams.writeUTF(Key);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
            out.code = 0;
            out.error = Data.INTERNAL_ERROR;
            return out;
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.NUEVO);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) {
            out.code = 0;
            out.error = Data.NET_ERROR;
            return out;
        }
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            out.code = 0;
            out.error = Data.SERVER_ERROR;
            return out;
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                out.code = dtResponse.readShort();
                out.error = Data.NET_ERROR;
                return out;
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                out.code = 0;
                out.error = Data.INTERNAL_ERROR;
                return out;
            }
        }
	}
	
	private static int quita(short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeShort(code);
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
		msRequest.setiIdMethod(Data.QUITA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) return Data.NET_ERROR;
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return Data.SERVER_ERROR;
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                return dtResponse.readInt();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return Data.INTERNAL_ERROR;
            }
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
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return Data.SERVER_ERROR;
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                return dtResponse.readInt();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return Data.INTERNAL_ERROR;
            }
        }
	}
	
	private static List<Jugador> plantilla(){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeUTF(Key);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return new ArrayList<Jugador>();
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.PLANTILLA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) {
            System.err.println("ERROR: NET ERROR");
            return new ArrayList<Jugador>();
        }
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return new ArrayList<Jugador>();
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                List<Jugador> players = new ArrayList<Jugador>();
                for (int ii = 0; ii < response.getLengthAnswer(); ii++) {
                    Jugador player = new Jugador();
                    player.name = dtResponse.readUTF();
                    player.alias = dtResponse.readUTF();
                    players.add(player);
                }
                return players;
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return new ArrayList<Jugador>();
            }
        }
	}
	
	private static List<Juego> repertorio(byte minimum){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeByte(minimum);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return new ArrayList<Juego>();
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.REPERTORIO);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) {
            System.err.println("ERROR: NET ERROR");
            return new ArrayList<Juego>();
        }
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
        Answer response = new Answer(byResponse);

        ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
        DataInputStream dtResponse = new DataInputStream(baResponse);

        try {
            List<Juego> games = new ArrayList<Juego>();
            for(int ii=0; ii<response.getLengthAnswer(); ii++){
                Juego game = new Juego();
                game.designation = dtResponse.readUTF();
                game.code = dtResponse.readByte();
                game.maximum = dtResponse.readInt();
                games.add(game);
            }
            return games;
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            return new ArrayList<Juego>();
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
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return Data.SERVER_ERROR;
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                return dtResponse.readInt();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return Data.INTERNAL_ERROR;
            }
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
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return Data.SERVER_ERROR;
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                return dtResponse.readInt();
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return Data.INTERNAL_ERROR;
            }
        }
	}
	
	private static List<Jugador> lista(short code){
		
		//================Arguments Packaging================//
		ByteArrayOutputStream baParams = new ByteArrayOutputStream();
		DataOutputStream dtParams = new DataOutputStream(baParams);
		try {
			dtParams.writeShort(code);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return new ArrayList<Jugador>();
		}

		//================Message Sending================//
		Message msRequest = new Message();
		byte[] byParams = baParams.toByteArray();
		msRequest.setbyArguments(byParams);
		msRequest.setiLengthArgs(byParams.length);
		msRequest.setiIdMethod(Data.LISTA);
		
		Message msResponse = new Message();
		
		int status = ccModule.doOperation(msRequest, msResponse);
		if(status==Data.NET_ERROR) {
            System.err.println("ERROR: NET ERROR");
            return new ArrayList<Jugador>();
        }
		
		//================Response Processing================//
		byte[] byResponse = msResponse.getbyArguments();
        Answer response = new Answer(byResponse);

        if(response.getServer_error()!=Data.OK) {
            System.err.println("SERVER ERROR: " + Data.ErrorToString(response.getServer_error()));
            return new ArrayList<Jugador>();
        }
        else {
            ByteArrayInputStream baResponse = new ByteArrayInputStream(response.getAnswer());
            DataInputStream dtResponse = new DataInputStream(baResponse);

            try {
                List<Jugador> players = new ArrayList<Jugador>();
                for (int ii = 0; ii < response.getLengthAnswer(); ii++) {
                    Jugador player = new Jugador();
                    player.name = dtResponse.readUTF();
                    player.alias = dtResponse.readUTF();
                    players.add(player);
                }
                return players;
            } catch (IOException e) {
                System.err.println("ERROR: " + e.getMessage());
                return new ArrayList<Jugador>();
            }
        }
	}
}
