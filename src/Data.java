
public class Data {
	// socket and message parameters
	public static final int PORT = 4000;
	public static final int MAX_ARGUMENTS_SIZE = 8000;
	public static final int MAX_MESSAGE_SIZE = MAX_ARGUMENTS_SIZE + 16; // 4 ints
	
	// Local response codes
	public static final int OK =0;
	public static final int UNKNOWN_HOST = 1;
	
	// Message types
	public static final int REQUEST = 0;
	public static final int RESPONSE = 1;
}
