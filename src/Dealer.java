import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Dealer {
	
	public void deal(Message msRequest) {
		int method = msRequest.getiIdMethod();
		switch(method) {
			case 1:
				int result = nuevo(msRequest);
				System.out.println(result);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
		}
	}
	
	private int nuevo(Message msRequest) {
		
		byte [] InBuffer = new byte[Data.MAX_MESSAGE_SIZE];
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			dtParams.readUTF();
			dtParams.readInt();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
		return 0;
	}
}
