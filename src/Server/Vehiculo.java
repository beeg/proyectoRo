package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import DB.GestorBD;
import Util.SocketManager;

public class Vehiculo {
	private int id;
	private int idCell;
	private GPS gps;
	private ServerSocket ss;
	private ArrayList<Usuario> lUsuarios;
	private ArrayList<Sensor> lSensores;
	private ArrayList<Sesion> lSesiones;
	private int numMaxUsuarios;
	private int numActualUsuarios;
	private boolean terminar;
	private VentanaAdministrador vAdmin;

	/**
	 * El constructor de el vehiculo que es el servidor
	 * 
	 * @param id
	 * @param gps
	 * @param idCell
	 * @param puerto
	 */
	public Vehiculo(int id, GPS gps, int idCell, int puerto) {
		this.id = id;
		this.gps = gps;
		this.idCell = idCell;
		numMaxUsuarios = 5;
		numActualUsuarios = 0;
		terminar = false;
		GestorBD bd = GestorBD.getInstance();
		lUsuarios = bd.getUsuarios();
		lSensores = bd.getSensores(id);
		lSesiones = new ArrayList<Sesion>();
		try {
			ss = new ServerSocket(puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * El bucle que ira aceptando mediante el ServerSocket nuevas conexiones. Se
	 * teminara cuando se haga terminar=true y ss.close(), lanzando la
	 * excepcion.
	 * 
	 * @throws IOException
	 */
	public void activarServidor() throws IOException {
		try {
			while (!terminar) {
				SocketManager sM = new SocketManager(ss.accept());
				if (numMaxUsuarios > numActualUsuarios) {
					Sesion s = new Sesion(sM, this);
					sumarUsuariosConectados();
					lSesiones.add(s);
					new Thread(s).start();
					cargarUsuariosVentana();
				} else {
					sM.Escribir("444 El servidor esta lleno \n");
				}
			}
		} catch (IOException e) {

		}
		// Por si hay alguna sesion en curso cuando se cierra el servidor, se
		// cierran todas
		terminarSesiones();
		System.out.println("Servidor offline");
	}

	/**
	 * Terminar todas las sesiones, como va removiendo las sesiones de la lista
	 * en otro metodo, este metodo se aprovecha de eso y lSesiones.size() va
	 * bajando en uno en cada llamada.
	 */
	public void terminarSesiones() {
		while (0 < lSesiones.size()) {
			lSesiones.get(0).terminarSesion();
		}
	}

	/**
	 * Actualizar la lista de usuarios online
	 */
	public void cargarUsuariosVentana() {
		vAdmin.cargarUsuarios();
	}

	/**
	 * Actualizar el label de la ventana de administración que contiene el
	 * numero de usuarios conectados
	 */
	public void sumarUsuariosConectados() {
		numActualUsuarios++;
		vAdmin.actualizarNumAct(numActualUsuarios);
	}

	/**
	 * Se decrementa en uno el numero actual de usuarios y se remueve la sesión
	 * de la lista de sesiones
	 * 
	 * @param numActualUsuarios
	 * @param login
	 */
	public void actualizarListaSesiones(int numActualUsuarios, String login) {
		this.numActualUsuarios = numActualUsuarios;
		vAdmin.actualizarNumAct(numActualUsuarios);
		for (int i = 0; i < lSesiones.size(); i++) {
			if (lSesiones.get(i).getActualUser().getLogin().equals(login)) {
				lSesiones.remove(i);
			}
		}
		vAdmin.cargarUsuarios();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCell() {
		return idCell;
	}

	public void setIdCell(int idCell) {
		this.idCell = idCell;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public ArrayList<Usuario> getlUsuarios() {
		return lUsuarios;
	}

	public void setlUsuarios(ArrayList<Usuario> lUsuarios) {
		this.lUsuarios = lUsuarios;
	}

	public ArrayList<Sensor> getlSensores() {
		return lSensores;
	}

	public void setlSensores(ArrayList<Sensor> lSensores) {
		this.lSensores = lSensores;
	}

	public ServerSocket getSs() {
		return ss;
	}

	public void setSs(ServerSocket ss) {
		this.ss = ss;
	}

	public ArrayList<Sesion> getlSesiones() {
		return lSesiones;
	}

	public void setlSesiones(ArrayList<Sesion> lSesiones) {
		this.lSesiones = lSesiones;
	}

	public int getNumMaxUsuarios() {
		return numMaxUsuarios;
	}

	public void setNumMaxUsuarios(int numMaxUsuarios) {
		this.numMaxUsuarios = numMaxUsuarios;
	}

	public int getNumActualUsuarios() {
		return numActualUsuarios;
	}

	public boolean isTerminar() {
		return terminar;
	}

	public void setTerminar(boolean terminar) {
		this.terminar = terminar;
	}

	public VentanaAdministrador getvAdmin() {
		return vAdmin;
	}

	public void setvAdmin(VentanaAdministrador vAdmin) {
		this.vAdmin = vAdmin;
	}

	public static void main(String[] args) {
		try {
			GestorBD gestor = GestorBD.getInstance();
			gestor.conectar();
			Vehiculo v = gestor.getVehiculo(1);
			gestor.desconectar();
			VentanaAdministrador vA = new VentanaAdministrador(v);
			v.setvAdmin(vA);
			try {
				v.activarServidor();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
