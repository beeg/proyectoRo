package Client;

import java.io.IOException;

import Util.SocketManager;

public class Cliente {
	SocketManager sm;
	String mensajeServer;
	String login;
	String pass;

	public Cliente(SocketManager sm) {
		this.sm = sm;
	}

	/**
	 * El metodo de logeo pasas login y pass
	 */
	public void conectar() {
		login = "admin";
		pass = "admin";
		try {
			sm.Escribir("USER " + login + '\n');
			mensajeServer = sm.Leer();
			System.out.println(mensajeServer);
			if (mensajeServer.equals("201 OK Bienvenido " + login + ".")) {
				sm.Escribir("PASS " + pass + '\n');
				mensajeServer = sm.Leer();
				System.out.println(mensajeServer);
			} else {
				System.out.println(mensajeServer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para mandar el comando LISTSENSOR
	 */
	public void listSensor() {
		try {
			sm.Escribir("LISTSENSOR" + '\n');
			String mensajeLeido = sm.Leer();
			while (!mensajeLeido.contains("212")) {
				System.out.println(mensajeLeido);
				mensajeLeido = sm.Leer();
			}
			System.out.println(mensajeLeido);

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		try {
			SocketManager sm = new SocketManager("127.0.0.1", 5888);
			Cliente c = new Cliente(sm);
			c.conectar();
			c.listSensor();
			c.historico(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
