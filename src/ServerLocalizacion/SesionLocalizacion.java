package ServerLocalizacion;

import java.io.IOException;
import java.sql.SQLException;

import DB.GestorBD;
import Server.Usuario;
import Util.SocketManager;

public class SesionLocalizacion implements Runnable {
	SocketManager sM;
	Usuario actualUser;
	boolean terminar;
	String mensajeCliente;
	String mensajeEnviar;
	int estado;
	ServidorLocalizacion server;

	public SesionLocalizacion(SocketManager sM, ServidorLocalizacion server) {
		this.sM = sM;
		actualUser = new Usuario("Desconocido", "");
		estado = 0;
		this.server = server;
		terminar=false;
		mensajeCliente="";
		mensajeEnviar="";
	}

	@Override
	public void run() {
		activarSesion();
	}

	public void activarSesion() {
		while (!mensajeCliente.contains("SALIR") && !terminar) {
			try {
				mensajeCliente = sM.Leer();
				gestionarMensaje();
			} catch (IOException e) {
			}
		}
		try {
			/*
			 * Si no se ha terminado desde la administración del servidor hay
			 * que mandar un mensaje y cerrar el socketmanager. También reducir
			 * el número de usuarios conectados en en el servidor.
			 */
			if (!terminar) {
				sM.Escribir("208 OK Adiós\n");
				sM.CerrarStreams();
				sM.CerrarSocket();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Termino la sesion del usuario "
				+ actualUser.getLogin() + " en el servidor de localización");
	}

	/**
	 * Cada vez que llega un mensaje pasa por este gestor que es donde se
	 * encargara de gestionar el mensaje dependiendo del estado actual y el
	 * mensaje recibido
	 */
	public void gestionarMensaje() {
		String[] separado = mensajeCliente.split(" ");
		String comando = separado[0];
		String parametro;
		if (separado.length != 2)
			parametro = "";
		else
			parametro = separado[1];
		switch (estado) {
		case 0:
			if (comando.equals("USER")) {
				tratarUser(parametro);
			}
			break;
		case 1:
			if (comando.equals("PASS")) {
				tratarPass(parametro);
			}
			break;
		case 2:
			if (comando.equals("GET_COOR")) {
				tratarGetCorr(parametro);
			}
			break;

		}
	}

	/**
	 * Se encarga de gestionar el login en el momento de insertar el nick del
	 * usuario
	 * 
	 * @param parametro
	 */
	public void tratarUser(String parametro) {
		@SuppressWarnings("unused")
		boolean encontrado = false;
		GestorBD gestor = GestorBD.getInstance();
		gestor.conectar();
		try {
			actualUser = gestor.getUsuario(parametro);
			encontrado = true;
		} catch (SQLException e1) {
			System.out.println("Usuario no encontrado");
		}
		if (encontrado) {
			mensajeEnviar = "201 OK Bienvenido " + parametro + ".\n";
			estado = 1;
		} else {
			mensajeEnviar = "401 ERR Falta el nombre de usuario \n";
		}
		try {
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {
			System.out.println("Error al devolver USER");
		}
		gestor.desconectar();
	}

	/**
	 * Gestiona el tratamiento de la contrasena insertada por el usuario. En
	 * caso de que esta sea correcta, le permite el acceso al sistema. Por el
	 * contrario, si no coincidiese, volveria al inicio
	 * 
	 * @param parametro
	 */
	public void tratarPass(String parametro) {
		if (actualUser.getPassword().equals(parametro)) {
			mensajeEnviar = "202 OK Bienvenido al sistema \n";
			estado = 2;
		} else if (parametro.equals("")) {
			mensajeEnviar = "403 ERR Falta la clave \n";
			actualUser = null;
		} else {
			System.out.println(actualUser.getPassword());
			System.out.println(parametro);
			mensajeEnviar = "402 OK La clave es incorrecta \n";
			actualUser = null;
		}
		try {
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {
			System.out.println("Error al devolver PASS");
		}
	}

	public void tratarGetCorr(String parametro) {
		System.out.println("paso por aquí?");
		try {
			if (parametro.equals("")) {
				mensajeEnviar = "418 ERR Falta parámetro cell_id\n";
			} else {
				GestorBD gestor = GestorBD.getInstance();
				gestor.conectar();
				try {
					Celda c = gestor.getCelda(Integer.parseInt(parametro));
					gestor.desconectar();
					mensajeEnviar = "114 OK " + c.toString() + "\n";
				} catch (NumberFormatException | SQLException e) {
					mensajeEnviar = "417 ERR Celda desconocida\n";
				}
			}
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {

		}
	}

	public SocketManager getsM() {
		return sM;
	}

	public void setsM(SocketManager sM) {
		this.sM = sM;
	}

	public Usuario getActualUser() {
		return actualUser;
	}

	public void setActualUser(Usuario actualUser) {
		this.actualUser = actualUser;
	}

	public boolean isTerminar() {
		return terminar;
	}

	public void setTerminar(boolean terminar) {
		this.terminar = terminar;
	}

	public String getMensajeCliente() {
		return mensajeCliente;
	}

	public void setMensajeCliente(String mensajeCliente) {
		this.mensajeCliente = mensajeCliente;
	}

	public String getMensajeEnviar() {
		return mensajeEnviar;
	}

	public void setMensajeEnviar(String mensajeEnviar) {
		this.mensajeEnviar = mensajeEnviar;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public ServidorLocalizacion getServer() {
		return server;
	}

	public void setServer(ServidorLocalizacion server) {
		this.server = server;
	}

}
