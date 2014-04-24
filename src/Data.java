
public class Data {
	// socket and message parameters
	public static final int PORT = 4005;
	public static final int MAX_ARGUMENTS_SIZE = 8000;
	public static final int MAX_MESSAGE_SIZE = MAX_ARGUMENTS_SIZE + 16; // 4 ints
	public static final int SOCKET_TIMEOUT = 10000; // in milliseconds
	public static final int SOCKET_RTX_PERIOD = 500; // in milliseconds
	
	// Local response codes
	public static final int OK =0;
	public static final int UNKNOWN_HOST = 1;
	public static final int INTERNAL_ERROR = 2;
	public static final int SERVER_ERROR = 3;
	public static final int NET_ERROR = 4;
	// Remote response errors
	public static final int ALREADY_EXISTS = -1;
	public static final int DOESNT_EXIST = -2;
	public static final int MAX_ACHIEVED = -3;
    public static final int AUTENTICATION_FAILED = -4;
	
	// Message types
	public static final int REQUEST = 0;
	public static final int RESPONSE = 1;
	public static final int ACK = 2;
	
	// Methods
	public static final int NUEVO = 1;
	public static final int QUITA = 2;
	public static final int INSCRIBE = 3;
	public static final int PLANTILLA = 4;
	public static final int REPERTORIO = 5;
	public static final int JUEGA = 6;
	public static final int TERMINA = 7;
	public static final int LISTA = 8;
	
	// File paths
	public static final String PATH_JUEGOS = "/bd/juegos.txt";
	public static final String PATH_JUGADORES = "/bd";
	public static final String PATH_PLAYING = "";

	public static String ErrorToString(int error){
		switch(error) {
		case OK: return "OK";
		case UNKNOWN_HOST: return "UNKNOWN_HOST";
		case INTERNAL_ERROR: return "INTERNAL_ERROR";
		case SERVER_ERROR: return "SERVER_ERROR";
		case NET_ERROR: return "NET_ERROR";
		case ALREADY_EXISTS: return "ALREADY_EXISTS";
		case DOESNT_EXIST: return "DOESNT_EXIST";
		case MAX_ACHIEVED: return "MAX_ACHIEVED";
        case AUTENTICATION_FAILED: return "AUTENTICATION_FAILED";
		default: return "UNKNOWN_ERROR";
		}
	}
	
	public static String PromptToMethod(String Prompt){
		Prompt = Prompt.toUpperCase();
		if(Prompt.equals("N") || Prompt.equals("NU") || Prompt.equals("NUE") || Prompt.equals("NUEV") || Prompt.equals("NUEVO")) 
			return "NUEVO";
		if(Prompt.equals("Q") || Prompt.equals("QU") || Prompt.equals("QUI") || Prompt.equals("QUIT") || Prompt.equals("QUITA"))
			return "QUITA";
		if(Prompt.equals("I") || Prompt.equals("IN") || Prompt.equals("INS") || Prompt.equals("INSC") || Prompt.equals("INSCR")
				|| Prompt.equals("INSCRI") || Prompt.equals("INSCRIB") || Prompt.equals("INSCRIBE"))
			return "INSCRIBE";
		if(Prompt.equals("P") || Prompt.equals("PL") || Prompt.equals("PLA") || Prompt.equals("PLAN") || Prompt.equals("PLANT")
				|| Prompt.equals("PLANTI") || Prompt.equals("PLANTIL") || Prompt.equals("PLANTILL") || Prompt.equals("PLANTILLA"))
			return "PLANTILLA";
		if(Prompt.equals("R") || Prompt.equals("RE") || Prompt.equals("REP") || Prompt.equals("REPE") || Prompt.equals("REPERT")
				|| Prompt.equals("REPERTO") || Prompt.equals("REPERTOR") || Prompt.equals("REPERTORI") || Prompt.equals("REPERTORIO"))
			return "REPERTORIO";
		if(Prompt.equals("J") || Prompt.equals("JU") || Prompt.equals("JUE") || Prompt.equals("JUEG") || Prompt.equals("JUEGA"))
			return "JUEGA";
		if(Prompt.equals("T") || Prompt.equals("TE") || Prompt.equals("TER") || Prompt.equals("TERM") || Prompt.equals("TERMI")
				|| Prompt.equals("TERMIN") || Prompt.equals("TERMINA"))
			return "TERMINA";
		if(Prompt.equals("L") || Prompt.equals("LI") || Prompt.equals("LIS") || Prompt.equals("LIST") || Prompt.equals("LISTA"))
			return "LISTA";
		if(Prompt.equals("F") || Prompt.equals("FI") || Prompt.equals("FIN") || Prompt.equals("FINA") || Prompt.equals("FINAL"))
			return "FINAL";
		return "UNKNOWN";
	}
}
