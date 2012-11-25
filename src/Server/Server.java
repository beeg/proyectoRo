package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import DB.GestorBD;
import Util.SocketManager;

public class Server {
	int id;
	Usuario actualUser;
	SocketManager sM;
	String mensajeEnviar;
	String mensajeCliente;
	ArrayList<Usuario> lUsuarios;
	ArrayList<Sensor> lSensores;

	/**
	 * Es el servidor/vehiculo con su id y su SocketManager
	 * @param id
	 * @param sM
	 */
	public Server(int id, SocketManager sM) {
		this.sM = sM;
		this.id = id;
		GestorBD bd = GestorBD.getInstance();
		bd.conectar();
		lUsuarios = bd.getUsuarios();
		lSensores = bd.getSensores(id);
		bd.desconectar();
		mensajeCliente = "";
		mensajeEnviar = "";
	}

	/**
	 * Este metodo hay que ejecutarlo despues de crear el Servidor, un bucle
	 * hasta que manden SALIR como comando!
	 */
	public void activarServidor() {
		while (!mensajeCliente.equals("SALIR")) {
			try {
				mensajeCliente = sM.Leer();
				gestionarMensaje();
			} catch (IOException e) {
			}

		}
	}

	/**
	 * Dependiendo de que llega por mensaje hace una cosa o otra, separo el mensaje en dos
	 * porque puede ser "USER aritz", el primero el comando el segundo el dato y dependiendo
	 * del comando llamo a su metodo
	 */
	public void gestionarMensaje() {

		String[] separado = mensajeCliente.split(" ");
		String comando = separado[0];
		String parametro;
		if (separado.length != 2)
			parametro = "";
		else
			parametro = separado[1];
		if (comando.equals("USER")) {
			tratarUser(parametro);
		} else if (comando.equals("PASS")) {
			tratarPass(parametro);
		} else if (comando.equals("LISTSENSOR")) {
			tratarListSensor();
		} else if (comando.equals("HISTORICO")) {
			tratarHistorico(parametro);
		}
	}

	public void tratarUser(String parametro) {
		Usuario u;
		boolean encontrado = false;
		for (int i = 0; i < lUsuarios.size() && !encontrado; i++) {
			u = lUsuarios.get(i);
			if (u.getLogin().equals(parametro)) {
				encontrado = true;
				actualUser = u;
			}
		}
		if (encontrado) {
			mensajeEnviar = "201 OK Bienvenido " + parametro + ".\n";
		} else {
			mensajeEnviar = "401 ERR Falta el nombre de usuario \n";
		}
		try {
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {
			System.out.println("Error al devolver USER");
		}

	}

	public void tratarPass(String parametro) {
		if (actualUser.getPassword().equals(parametro)) {
			mensajeEnviar = "202 OK Bienvenido al sistema \n";
		} else if (parametro.equals("")) {
			mensajeEnviar = "403 ERR Falta la clave";
			actualUser = null;
		} else {
			mensajeEnviar = "402 OK La clave es incorrecta \n";
			actualUser = null;
		}
		try {
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {
			System.out.println("Error al devolver PASS");
		}
	}

	public void tratarListSensor() {
		String inicio = "112 OK Lista de sensores\n";
		String fin = "212 OK Lista finalizada\n";
		try {
			sM.Escribir(inicio);
			String dSensor;
			for (Sensor sensor : lSensores) {
				dSensor = sensor.toString() + '\n';
				sM.Escribir(dSensor);
			}
			sM.Escribir(fin);
		} catch (IOException e) {
			System.out.println("Error al devolver LISTSENSOR");
		}

	}

	public void tratarHistorico(String parametro) {
		try {
			if (parametro.equals("")) {
				mensajeEnviar = "415 ERR Falta parámetro id_sensor";
				sM.Escribir(mensajeEnviar);
			} else {
				try {
					int idSensor = Integer.parseInt(parametro);
					boolean encontrado = false;
					Sensor sensor = null;
					for (int i = 0; i < lSensores.size() && !encontrado; i++) {
						sensor = lSensores.get(i);
						if (sensor.getId() == idSensor) {
							encontrado = true;
						}
					}
					if (encontrado) {
						String inicio = "113 OK Lista de medidas\n";
						String fin = "212 OK Lista finalizada\n";
						sM.Escribir(inicio);
						for (Medida m : sensor.getMedidas()) {
							sM.Escribir(m.toString() + '\n');
						}
						sM.Escribir(fin);
					} else {
						mensajeEnviar = "414 ERR Sensor desconocido";
						sM.Escribir(mensajeEnviar);
					}
				} catch (NumberFormatException e) {
					mensajeEnviar = "414 ERR Sensor desconocido";
					sM.Escribir(mensajeEnviar);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al enviar HISTORICO");
		}

	}

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(5888);
			SocketManager sM = new SocketManager(ss.accept());
			Server s = new Server(1, sM);
			s.activarServidor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
