import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class Flag {
	public Methods method;

	public Flag() {
		method = new Methods();
	}
	
	public Answer nuevo(Message msRequest) {
		
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String designation = dtParams.readUTF();
			int maximum = dtParams.readInt();
			answer = method.nuevo(designation, maximum);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
	}
	
	public Answer quita(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			short code = dtParams.readShort();
			answer = method.quita(code);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
	}
	
	public Answer inscribe(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String name = dtParams.readUTF();
			String alias = dtParams.readUTF();
			answer = method.inscribe(name, alias);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
		}
	}
	
	public Answer plantilla(Message msRequest) {
		Answer answer = new Answer();
		//byte [] InBuffer = msRequest.getbyArguments();
		//ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		//DataInputStream dtParams = new DataInputStream(baParams);
		answer = method.plantilla();
		return answer;
	}
	
	public Answer repertorio(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			byte minimum = dtParams.readByte();
			answer = method.repertorio(minimum);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
		//return null;
	}
	
	public Answer juega(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String alias = dtParams.readUTF();
			short code = dtParams.readShort();
			answer = method.juega(alias, code);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
	}
	
	public Answer termina(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String alias = dtParams.readUTF();
			short code = dtParams.readShort();
			answer = method.termina(alias, code);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
	}
	
	public Answer lista(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			short code = dtParams.readShort();
			answer = method.lista(code);
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Integer.toString(Data.INTERNAL_ERROR);
		}
	}
}
