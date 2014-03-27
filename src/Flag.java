import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;


public class Flag {
	public Methods method;

	public Flag() {
		method = new Methods();
	}
	
	public int nuevo(Message msRequest) {
		
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String designation = dtParams.readUTF();
			int maximum = dtParams.readInt();
			int resul = method.nuevo(designation, maximum);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	public int quita(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			short code = dtParams.readShort();
			int resul = method.quita(code);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	public int inscribe(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String name = dtParams.readUTF();
			String alias = dtParams.readUTF();
			int resul = method.inscribe(name, alias);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	public String plantilla(Message msRequest) {
		//byte [] InBuffer = msRequest.getbyArguments();
		//ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		//DataInputStream dtParams = new DataInputStream(baParams);
		String resul = method.plantilla();
		return resul;
	}
	
	public String repertorio(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			byte minimum = dtParams.readByte();
			String resul = method.repertorio(minimum);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			//return Data.INTERNAL_ERROR;
		}
		return null;
	}
	
	public int juega(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String alias = dtParams.readUTF();
			short code = dtParams.readShort();
			int resul = method.juega(alias, code);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	public int termina(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String alias = dtParams.readUTF();
			short code = dtParams.readShort();
			int resul = method.termina(alias, code);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
	
	public int lista(Message msRequest) {
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			short code = dtParams.readShort();
			int resul = method.lista(code);
			return resul;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			return Data.INTERNAL_ERROR;
		}
	}
}
