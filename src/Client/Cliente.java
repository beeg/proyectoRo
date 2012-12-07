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

	public String userLogin(String user) {
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

	public String passLogin(String pass) {
		try {
			sm.Escribir("PASS " + pass + '\n');
			mensajeServer = sm.Leer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mensajeServer;
	}

	/**
	 * Metodo para mandar el comando LISTSENSOR
	 */
<<<<<<< HEAD
	public ArrayList<String> listSensor() throws IOException{
		ArrayList<String>listaMensajes=new ArrayList<String>();
		sm.Escribir("LISTSENSOR" + '\n');
		String mensajeLeido = sm.Leer();
		listaMensajes.add(mensajeLeido);
		while (!mensajeLeido.contains("212")) {
			mensajeLeido = sm.Leer();
=======
	public ArrayList<String> listSensor() {
		ArrayList<String> listaMensajes = new ArrayList<String>();
		try {
			sm.Escribir("LISTSENSOR" + '\n');
			String mensajeLeido = sm.Leer();
>>>>>>> bc825ac91130065c78b42144ec27a133d6fc71b4
			listaMensajes.add(mensajeLeido);
		}
		return listaMensajes;
	}
<<<<<<< HEAD
	
	public String getValAct(String id_sensor) throws IOException{
		System.out.println("CLIENTE VALACT");
		String mensajeLeido="";		
		sm.Escribir("GET_VALACT " + id_sensor + '\n');	
		System.out.println("FIN ESCRIBIR CLIENTE VALACT");
		mensajeLeido = sm.Leer();
		System.out.println("Valact leidooooooo");
=======

	public String getValAct(String id_sensor) {
		String mensajeLeido = "";
		try {
			sm.Escribir("GET_VALACT" + id_sensor + '\n');
			mensajeLeido = sm.Leer();
		} catch (IOException e) {
			e.printStackTrace();
		}
>>>>>>> bc825ac91130065c78b42144ec27a133d6fc71b4
		return mensajeLeido;
	}

	/**
	 * Metodo para recuperar todas las medidas de un sensor. HISTORIDO id_sensor
	 * 
	 * @param idSensor
	 */
	public ArrayList<String> historico(String idSensor) throws IOException{
		ArrayList<String> medidas = new ArrayList<String>();
<<<<<<< HEAD
		sm.Escribir("HISTORICO " + idSensor + '\n');
		String primerMensaje = sm.Leer();
		medidas.add(primerMensaje);
		System.out.println(primerMensaje);
		if (primerMensaje.contains("113")) {
			String mensajeLeido = sm.Leer();				
			while (!mensajeLeido.contains("212")) {
				medidas.add(mensajeLeido);
				System.out.println(mensajeLeido);
				mensajeLeido = sm.Leer();
=======
		try {
			sm.Escribir("HISTORICO " + idSensor + '\n');
			String primerMensaje = sm.Leer();
			medidas.add(primerMensaje);
			System.out.println(primerMensaje);
			if (primerMensaje.contains("113")) {
				String mensajeLeido = sm.Leer();
				while (!mensajeLeido.contains("212")) {
					medidas.add(mensajeLeido);
					System.out.println(mensajeLeido);
					mensajeLeido = sm.Leer();
>>>>>>> bc825ac91130065c78b42144ec27a133d6fc71b4
				}
				System.out.println(mensajeLeido);
				medidas.add(mensajeLeido);
			}
		return medidas;
	}

	/**
	 * Metodo para activar un sensor. ON id_sensor
	 * 
	 * @param idSensor
	 */
<<<<<<< HEAD
	public String onSensor(String idSensor) throws IOException{
		String primerMensaje="";
		sm.Escribir("ON " + idSensor + '\n');
		primerMensaje = sm.Leer();
		System.out.println(primerMensaje);
=======
	public String onSensor(String idSensor) {
		String primerMensaje = "";
		try {
			sm.Escribir("ON " + idSensor + '\n');
			primerMensaje = sm.Leer();
			System.out.println(primerMensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
>>>>>>> bc825ac91130065c78b42144ec27a133d6fc71b4
		return primerMensaje;
	}

	/**
	 * Metodo para desactivar un sensor. OFF id_sensor
	 * 
	 * @param idSensor
	 */
<<<<<<< HEAD
	public String offSensor(String idSensor) throws IOException{
		String primerMensaje ="";
		sm.Escribir("OFF " + idSensor + '\n');
		primerMensaje = sm.Leer();
		System.out.println(primerMensaje);
		return primerMensaje;
	}
	
	public String ONGPS() throws IOException{
		String resultado="";
		sm.Escribir("ONGPS\n");
		resultado=sm.Leer();
		System.out.println(resultado);
		return resultado;
	}
	
	public String OFFGPS() throws IOException{
		String resultado="";
		sm.Escribir("OFFGPS \n");
		resultado=sm.Leer();
=======
	public String offSensor(String idSensor) {
		String primerMensaje = "";
		try {
			sm.Escribir("OFF " + idSensor + '\n');
			primerMensaje = sm.Leer();
			System.out.println(primerMensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return primerMensaje;
	}

	public String ONGPS() {
		String resultado = "";
		try {
			sm.Escribir("ONGPS\n");
			resultado = sm.Leer();
			System.out.println(resultado);
		} catch (IOException e) {
			System.out.println("Error en GPS en cliente");
		}
		return resultado;
	}

	public String OFFGPS() {
		String resultado = "";
		try {
			sm.Escribir("OFFGPS \n");
			resultado = sm.Leer();
>>>>>>> bc825ac91130065c78b42144ec27a133d6fc71b4
			System.out.println(resultado);
		return resultado;
	}

	public void SALIR() throws IOException {
		sm.Escribir("SALIR \n");
		mensajeServer = sm.Leer();
		if (mensajeServer.contains("208")) {
			sm.CerrarStreams();
			sm.CerrarSocket();
		}
	}

	public static void main(String[] args) {
		try {
			SocketManager sm = new SocketManager("127.0.0.1", 5888);
			Cliente c = new Cliente(sm);
			/*
			 * c.conectar(); System.out.println("ListSensor Inicio");
			 * c.listSensor(); System.out.println("ListSensor Fin");
			 * System.out.println("Historico Inicio"); c.historico(1);
			 * System.out.println("Historico Fin");
			 * System.out.println("Historico Inicio"); c.historico(3);
			 * System.out.println("Historico Fin");
			 * System.out.println("SensorOn inicio"); c.onSensor(2);
			 * System.out.println("SensorOn fin");
			 * System.out.println("SensorOff inicio"); c.offSensor(1);
			 * System.out.println("SensorOff fin");
			 * System.out.println("GPSOff inicio"); c.OFFGPS();
			 * System.out.println("GPSOff fin");
			 * System.out.println("GPSOn inicio"); c.ONGPS();
			 * System.out.println("GPSOff inicio");
			 */
			c.userLogin("hola");
			System.out.println("Terminado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
