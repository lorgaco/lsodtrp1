import java.net.*;
import java.io.*;

public class ReceptorUDP {
	public static void main(String args [] ) {
		// Sin argumentos
		if (args.length != 0) {
			System.err.println("Uso: java ReceptorUDP");
		}
		else try{
			// Crea su socket
			DatagramSocket elSocket = new DatagramSocket(4000);
			// Crea el espacio para los mensajes
			byte [] cadena = new byte[1000] ;
			DatagramPacket mensaje1 =
					new DatagramPacket(cadena, cadena.length);
			// Recibe y muestra el primer mensaje
			elSocket.receive(mensaje1);
			System.out.println("Mensaje Recibido: (" +
					new String(mensaje1.getData(), 0, mensaje1.getLength()) +
					") longitud = " + mensaje1.getLength());
			DatagramPacket mensaje2 =
					new DatagramPacket(cadena, cadena.length);
			// Recibe y muestra el segundo mensaje
			elSocket.receive(mensaje2);
			System.out.println("Mensaje Recibido: (" +
					new String(mensaje2.getData(), 0, mensaje2.getLength()) +
					") longitud = " + mensaje2.getLength());
		} catch(SocketException e) {
			System.err.println("Socket: " + e.getMessage());
		} catch(IOException e) {
			System.err.println("E/S: " + e.getMessage());
		}
	}
}