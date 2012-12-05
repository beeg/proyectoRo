package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

import DB.GestorBD;
import Util.SocketManager;

public class Vehiculo {
	private int id;
	private int idCell;
	private GPS gps;
	private ServerSocket ss;
	private ArrayList<Usuario> lUsuarios;
	private ArrayList<Sensor> lSensores;
	private ArrayList<Sesion>lSesiones;
	private int numMaxUsuarios;
	private int numActualUsuarios;
	private boolean terminar;
	private VentanaAdministrador vAdmin;
	
	/**
	 * Es el servidor/vehiculo con su id y su SocketManager
	 * @param id
	 * @param sM
	 * @throws IOException 
	 */
	public Vehiculo(int id, GPS gps,int idCell, int puerto) {
		this.id = id;
		this.gps = gps;
		this.idCell=idCell;
		numMaxUsuarios=5;
		numActualUsuarios=0;
		terminar=false;
		GestorBD bd = GestorBD.getInstance();
		lUsuarios = bd.getUsuarios();
		lSensores = bd.getSensores(id);
		lSesiones=new ArrayList<Sesion>();
		try {
			ss=new ServerSocket(puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void activarServidor() throws IOException{
		try{
		while(!terminar){
			if(numMaxUsuarios>numActualUsuarios){
				SocketManager sM = new SocketManager(ss.accept());
				Sesion s=new Sesion(sM,this);
				sumarUsuariosConectados();
				lSesiones.add(s);
				new Thread(s).start();
				cargarUsuariosVentana();
			}
		}
		}catch(IOException e){
			
		}
		terminarSesiones();
		System.out.println("Termino servidor");
	}
	public void terminarSesiones(){
		for(int i=0;i<lSesiones.size();i++){
			Sesion s=lSesiones.get(i);
			s.terminarSesion();
			try{
				s.getsM().CerrarStreams();
				s.getsM().CerrarSocket();
			}catch(IOException e){
				System.out.println("Error al cerrar todas las sesiones");
			}
		}
	}
	public void cargarUsuariosVentana(){
		vAdmin.cargarUsuarios();
	}
	public void sumarUsuariosConectados(){
		numActualUsuarios++;
		vAdmin.actualizarNumAct(numActualUsuarios);
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
	public void setNumActualUsuarios(int numActualUsuarios, String login) {
		this.numActualUsuarios = numActualUsuarios;
		vAdmin.actualizarNumAct(numActualUsuarios);
		for (int i=0;i<lSesiones.size();i++){
			if(lSesiones.get(i).getActualUser().getLogin().equals(login)){
				lSesiones.remove(i);
			}
		}
		vAdmin.cargarUsuarios();
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
				GestorBD gestor=GestorBD.getInstance();
				gestor.conectar();
				Vehiculo v =gestor.getVehiculo(1);
				gestor.desconectar();
				VentanaAdministrador vA=new VentanaAdministrador(v);
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
