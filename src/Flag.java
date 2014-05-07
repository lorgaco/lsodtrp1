import java.io.*;
import java.util.List;
import java.util.ListIterator;


public class Flag {
	public Methods method;

	public Flag() {
		method = new Methods();
	}
	
	public Answer nuevo(Message msRequest, String key_server) {
		
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			String designation = dtParams.readUTF();
			int maximum = dtParams.readInt();
            String key_client = dtParams.readUTF();
            if(key_client.equals(key_server)) {
                sNuevo salida = method.nuevo(designation, maximum);

                answer.setError(salida.error);
                answer.setServer_error(Data.OK);
                answer.setLengthAnswer(1);

                ByteArrayOutputStream baSalida = new ByteArrayOutputStream();
                DataOutputStream dtSalida = new DataOutputStream(baSalida);

                dtSalida.writeShort(salida.code);
                answer.setAnswer(baSalida.toByteArray());
            }
            else {
                answer.setError(Data.AUTENTICATION_FAILED);
                answer.setServer_error(Data.AUTENTICATION_FAILED);
                answer.setLengthAnswer(0);
                answer.setAnswer(null);
            }
			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Data.INTERNAL_ERROR;
		}
	}
	
	public Answer quita(Message msRequest, String key_server) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			short code = dtParams.readShort();
            String key_client = dtParams.readUTF();
            if(key_client.equals(key_server)) {
                int salida = method.quita(code);

                answer.setError(salida);
                answer.setServer_error(Data.OK);
                answer.setLengthAnswer(0);
                answer.setAnswer(null);
            }
            else {
                answer.setError(Data.AUTENTICATION_FAILED);
                answer.setServer_error(Data.AUTENTICATION_FAILED);
                answer.setLengthAnswer(1);
                answer.setAnswer(null);
            }
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
			int salida = method.inscribe(name, alias);

            answer.setError(salida);
            answer.setServer_error(Data.OK);
            answer.setLengthAnswer(0);
            answer.setAnswer(null);

			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
		}
	}
	
	public Answer plantilla(Message msRequest, String key_server) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
        try {
            String key_client = dtParams.readUTF();
            if (key_client.equals(key_server)) {
                List<Jugador> salida = method.plantilla();

                answer.setError(Data.OK);
                answer.setServer_error(Data.OK);
                answer.setLengthAnswer(salida.size());

                ByteArrayOutputStream baSalida = new ByteArrayOutputStream();
                DataOutputStream dtSalida = new DataOutputStream(baSalida);
                ListIterator<Jugador> it = salida.listIterator();
                for(int ii=0; ii<salida.size(); ii++) {
                    Jugador player = it.next();
                    dtSalida.writeUTF(player.name);
                    dtSalida.writeUTF(player.alias);
                }
                answer.setAnswer(baSalida.toByteArray());
            }
            else {
                answer.setError(Data.AUTENTICATION_FAILED);
                answer.setServer_error(Data.AUTENTICATION_FAILED);
                answer.setLengthAnswer(1);
                answer.setAnswer(null);
            }
            return answer;
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            answer.setServer_error(Data.INTERNAL_ERROR);
            return answer;
        }
	}
	
	public Answer repertorio(Message msRequest) {
		Answer answer = new Answer();
		byte [] InBuffer = msRequest.getbyArguments();
		ByteArrayInputStream baParams = new ByteArrayInputStream(InBuffer);
		DataInputStream dtParams = new DataInputStream(baParams);
		try {
			byte minimum = dtParams.readByte();
			List<Juego> salida = method.repertorio(minimum);

            answer.setError(Data.OK);
            answer.setServer_error(Data.OK);
            answer.setLengthAnswer(salida.size());

            ByteArrayOutputStream baSalida = new ByteArrayOutputStream();
            DataOutputStream dtSalida = new DataOutputStream(baSalida);
            ListIterator<Juego> it = salida.listIterator();
            for(int ii=0; ii<salida.size(); ii++) {
                Juego game = it.next();
                dtSalida.writeUTF(game.designation);
                dtSalida.writeShort(game.code);
                dtSalida.writeInt(game.maximum);
            }
            answer.setAnswer(baSalida.toByteArray());

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
			int salida = method.juega(alias, code);

            answer.setError(salida);
            answer.setServer_error(Data.OK);
            answer.setLengthAnswer(0);
            answer.setAnswer(null);

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
			int salida = method.termina(alias, code);

            answer.setError(salida);
            answer.setServer_error(Data.OK);
            answer.setLengthAnswer(0);
            answer.setAnswer(null);

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
            sLista salida = method.lista(code);

            answer.setError(Data.OK);
            answer.setServer_error(Data.OK);
            answer.setLengthAnswer(salida.lista.size());

            ByteArrayOutputStream baSalida = new ByteArrayOutputStream();
            DataOutputStream dtSalida = new DataOutputStream(baSalida);
            dtSalida.writeInt(salida.error);
            ListIterator<Jugador> it = salida.lista.listIterator();
            for(int ii=0; ii<salida.lista.size(); ii++) {
                Jugador player = it.next();
                dtSalida.writeUTF(player.name);
                dtSalida.writeUTF(player.alias);
            }
            answer.setAnswer(baSalida.toByteArray());

			return answer;
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			answer.setServer_error(Data.INTERNAL_ERROR);
			return answer;
			//return Integer.toString(Data.INTERNAL_ERROR);
		}
	}
}
