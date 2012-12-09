package Client;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Util.SocketManager;

public class Cliente {
	private SocketManager sm;
	private String mensajeServer;
	private String login;
	private String pass;

	/**
	 * Constructor que recibe un SocketManager por parametro
	 * @param sm
	 */
	public Cliente(SocketManager sm) {
		this.sm = sm;
	}

	/**
	 * Comando USER
	 * Envía al servidor el nick del usuario y devuelve la respuesta del servidor
	 * @param user
	 * @return
	 */
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

	/**
	 * Comando PASS
	 * Envía al servidor la contrasenya del usuario y devuelve la respuesta del servidor
	 * @param pass
	 * @return
	 */
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
	 * Comando LISTSENSOR
	 * Pide al servidor la lista de los sensores del vehiculo conectado.
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> listSensor() throws IOException {
		ArrayList<String> listaMensajes = new ArrayList<String>();
		sm.Escribir("LISTSENSOR" + '\n');
		String mensajeLeido = sm.Leer();
		listaMensajes.add(mensajeLeido);
		while (!mensajeLeido.contains("212")) {
			mensajeLeido = sm.Leer();
			listaMensajes.add(mensajeLeido);
		}
		return listaMensajes;
	}

	/**
	 * Comando GET_VALACT
	 * Solicita al servidor la ultima medida del sensor indicado por parametro
	 * @param id_sensor
	 * @return
	 * @throws IOException
	 */
	public String getValAct(String id_sensor) throws IOException {
		String mensajeLeido = "";
		sm.Escribir("GET_VALACT " + id_sensor + '\n');
		mensajeLeido = sm.Leer();
		return mensajeLeido;
	}

	/**
	 * Comando HISTORICO
	 * Solicita al servidor todas las medidas de un determinado sensor, este especificado
	 * por parametro
	 * @param idSensor
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> historico(String idSensor) throws IOException {
		ArrayList<String> medidas = new ArrayList<String>();
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
			}
			System.out.println(mensajeLeido);
			medidas.add(mensajeLeido);
		}
		return medidas;
	}

	/**
	 * Comando ON id_sensor
	 * Solicita al servidor la activación de un determinado sensor
	 * @param idSensor
	 * @return
	 * @throws IOException
	 */
	public String onSensor(String idSensor) throws IOException {
		String primerMensaje = "";
		sm.Escribir("ON " + idSensor + '\n');
		primerMensaje = sm.Leer();
		System.out.println(primerMensaje);
		return primerMensaje;
	}

	/**
	 * Comando OFF id_sensor
	 * Solicita al servidor la desactivación de un determinado sensor, este especificado
	 * por parametro
	 * @param idSensor
	 * @return
	 * @throws IOException
	 */
	public String offSensor(String idSensor) throws IOException {
		String primerMensaje = "";
		sm.Escribir("OFF " + idSensor + '\n');
		primerMensaje = sm.Leer();
		System.out.println(primerMensaje);
		return primerMensaje;
	}

	/**
	 * Comando ONGPS
	 * Solicita al servidor la activación del GPS del vehiculo
	 * @return
	 * @throws IOException
	 */
	public String ONGPS() throws IOException {
		String resultado = "";
		sm.Escribir("ONGPS\n");
		resultado = sm.Leer();
		System.out.println(resultado);
		return resultado;
	}

	/**
	 * Comando OFFGPS
	 * Solicita al servidor la desactivacion del GPS del vehiculo
	 * @return
	 * @throws IOException
	 */
	public String OFFGPS() throws IOException {
		String resultado = "";
		sm.Escribir("OFFGPS\n");
		resultado = sm.Leer();
		System.out.println(resultado);
		return resultado;
	}

	/**
	 * Comando GET_FOTO
	 * Solicita al servidor una foto geolocalizada
	 * @return
	 * @throws IOException
	 */
	public ImageIcon getFoto() throws IOException {
		ImageIcon img = null;
		String mensajeLeido = "";
		sm.Escribir("GET_FOTO\n");
		mensajeLeido = sm.Leer();
		if (mensajeLeido.contains("206")) {
			byte[] buffer;
			buffer = sm.LeerBytes();
			img = new ImageIcon(buffer);
		}
		return img;
	}

	/**
	 * Comando GET_LOC
	 * Solicita al servidor las coordenadas actuales del vehiculo
	 * @return
	 * @throws IOException
	 */
	public String getLoc() throws IOException {
		String mensajeLeido = "";
		sm.Escribir("GET_LOC\n");
		mensajeLeido = sm.Leer();
		System.out.println(mensajeLeido);
		return mensajeLeido;
	}

	/**
	 * Comando SALIR
	 * Permite salir del sistema
	 * @throws IOException
	 */
	public void SALIR() throws IOException {
		sm.Escribir("SALIR\n");
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
