import java.net.*;
import java.io.*;

public class EmisorUDP {
	public static void main(String args [] ) {
		// Los argumentos dan:
		// el nombre de la máquina receptora y 2 mensajes
		if (args.length != 3) {
			System.err.println("Uso: java EmisorUDP maquina msj1 msj2");
		}
		else try{
			// Crea su socket
			DatagramSocket elSocket = new DatagramSocket();
			// Construye la dirección del socket del receptor
			InetAddress maquina = InetAddress.getByName(args[0]);
			int puerto = 4000;
			// Crea el primer mensaje
			byte [] cadena = args[1].getBytes();
			DatagramPacket mensaje = new DatagramPacket(cadena,
					args[1].length(), maquina, puerto);
			// Envía el primer mensaje
			elSocket.send(mensaje);
			// Crea el segundo mensaje
			cadena = args[2].getBytes();
			mensaje.setData(cadena);
			mensaje.setLength(args[2].length());
			// Envía el segundo mensaje
			elSocket.send(mensaje);
			// Cierra su socket
			elSocket.close();
		} catch(UnknownHostException e) {
			System.err.println("Desconocido: " + e.getMessage());
		} catch(SocketException e) {
			System.err.println("Socket: " + e.getMessage());
		} catch(IOException e) {
			System.err.println("E/S: " + e.getMessage());
		}
	}
}