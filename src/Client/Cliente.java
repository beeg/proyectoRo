package Client;

import java.io.IOException;
import java.util.ArrayList;

import Util.SocketManager;

public class Cliente {
	private SocketManager sm;
	private String mensajeServer;
	private String login;
	private String pass;

	public Cliente(SocketManager sm) {
		this.sm = sm;
	}

	public String userLogin(String user){
		try {
			System.out.println(user);
			sm.Escribir("USER " + user + '\n');
			mensajeServer = sm.Leer();
			System.out.println(mensajeServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mensajeServer;
	}
	public String passLogin(String pass){
		try{
		sm.Escribir("PASS " + pass + '\n');
		mensajeServer = sm.Leer();
		}catch(IOException e){
			e.printStackTrace();
		}
		return mensajeServer;
	}
	/**
	 * Metodo para mandar el comando LISTSENSOR
	 */
	public ArrayList<String> listSensor() {
		ArrayList<String>listaMensajes=new ArrayList<String>();
		try {
			sm.Escribir("LISTSENSOR" + '\n');
			String mensajeLeido = sm.Leer();
			listaMensajes.add(mensajeLeido);
			while (!mensajeLeido.contains("212")) {
				mensajeLeido = sm.Leer();
				listaMensajes.add(mensajeLeido);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaMensajes;
	}

	/**
	 * Metodo para recuperar todas las medidas de un sensor. HISTORIDO id_sensor
	 * @param idSensor
	 */
	public void historico(int idSensor) {
		try {
			sm.Escribir("HISTORICO " + idSensor + '\n');
			String primerMensaje = sm.Leer();
			System.out.println(primerMensaje);
			if (primerMensaje.contains("113")) {
				String mensajeLeido = sm.Leer();
				while (!mensajeLeido.contains("212")) {
					System.out.println(mensajeLeido);
					mensajeLeido = sm.Leer();
				}
				System.out.println(mensajeLeido);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para activar un sensor. ON id_sensor
	 * @param idSensor
	 */
	public void onSensor(int idSensor) {
		try {
			sm.Escribir("ON " + idSensor + '\n');
			String primerMensaje = sm.Leer();
			System.out.println(primerMensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para desactivar un sensor. OFF id_sensor
	 * @param idSensor
	 */
	public void offSensor(int idSensor) {
		try {
			sm.Escribir("OFF " + idSensor + '\n');
			String primerMensaje = sm.Leer();
			System.out.println(primerMensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ONGPS(){
		try {
			sm.Escribir("ONGPS\n");
			System.out.println(sm.Leer());
		} catch (IOException e) {
			System.out.println("Error en GPS en cliente");
		}
	}
	
	public void OFFGPS(){
		try {
			sm.Escribir("OFFGPS \n");
			System.out.println(sm.Leer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void SALIR(){
		try{
		sm.Escribir("SALIR \n");
		mensajeServer=sm.Leer();
		if(mensajeServer.contains("208")){
			sm.CerrarStreams();
			sm.CerrarSocket();
		}
		}catch(IOException e){
			
		}
	}
	public static void main(String[] args) {
		try {
			SocketManager sm = new SocketManager("127.0.0.1", 5888);
			Cliente c = new Cliente(sm);
			/*c.conectar();
			System.out.println("ListSensor Inicio");
			c.listSensor();
			System.out.println("ListSensor Fin");
			System.out.println("Historico Inicio");
			c.historico(1);
			System.out.println("Historico Fin");
			System.out.println("Historico Inicio");
			c.historico(3);
			System.out.println("Historico Fin");
			System.out.println("SensorOn inicio");
			c.onSensor(2);
			System.out.println("SensorOn fin");
			System.out.println("SensorOff inicio");
			c.offSensor(1);
			System.out.println("SensorOff fin");
			System.out.println("GPSOff inicio");
			c.OFFGPS();
			System.out.println("GPSOff fin");
			System.out.println("GPSOn inicio");
			c.ONGPS();
			System.out.println("GPSOff inicio");
			*/
			c.userLogin("hola");
			System.out.println("Terminado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
