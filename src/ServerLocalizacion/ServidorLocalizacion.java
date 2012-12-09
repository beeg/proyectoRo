package ServerLocalizacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import Util.SocketManager;

public class ServidorLocalizacion {
	private ServerSocket ss;
	private ArrayList<SesionLocalizacion> lSesiones;

	public ServidorLocalizacion() {
		lSesiones = new ArrayList<SesionLocalizacion>();
		try {
			ss = new ServerSocket(5889);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Activa el servidor de localizacion
	 */
	public void activarServidor() {
		try {
			while (true) {
				SocketManager sm = new SocketManager(ss.accept());
				SesionLocalizacion s = new SesionLocalizacion(sm, this);
				lSesiones.add(s);
				new Thread(s).start();
			}
		} catch (IOException e) {
			System.out.println("Error en el servidor de localización");
		}
	}

	public ServerSocket getSs() {
		return ss;
	}

	public void setSs(ServerSocket ss) {
		this.ss = ss;
	}

	public ArrayList<SesionLocalizacion> getlSesiones() {
		return lSesiones;
	}

	public void setlSesiones(ArrayList<SesionLocalizacion> lSesiones) {
		this.lSesiones = lSesiones;
	}

	public static void main(String[] args) {
		new ServidorLocalizacion().activarServidor();
	}

}
