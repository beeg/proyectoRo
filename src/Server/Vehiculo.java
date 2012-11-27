package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import DB.GestorBD;
import Util.SocketManager;

public class Vehiculo extends Thread {
	int id;
	GPS gps;
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
	public Vehiculo(int id, boolean estado, String latitud, String longitud, SocketManager sM) {
		this.sM = sM;
		this.id = id;
		this.gps = new GPS(estado,latitud,longitud);
		GestorBD bd = GestorBD.getInstance();
		bd.conectar();
		lUsuarios = bd.getUsuarios();
		lSensores = bd.getSensores(id);
		bd.desconectar();
		mensajeCliente = "";
		mensajeEnviar = "";
	}
	public void run(){
		activarServidor();
	}
	/**
	 * Este metodo hay que ejecutarlo despues de crear el Servidor, un bucle
	 * hasta que manden SALIR como comando!
	 */
	public void activarServidor() {
		try {
		while (!mensajeCliente.equals("SALIR")) {
				mensajeCliente = sM.Leer();
				gestionarMensaje();
			} 
			sM.Escribir("208 OK Adiós");
		} catch (IOException e) {
			e.printStackTrace();
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
		} else if (comando.equals("ON")) {
			tratarOnSensor(parametro);
		} else if (comando.equals("OFF")) {
			tratarOffSensor(parametro);
		} else if (comando.equals("OFFGPS")){
			tratarOFFGPS();
		} else if(comando.equals("ONGPS")){
			tratarONGPS();
		} else if(comando.equals("GET_VALACT")){
			tratarGetValact(parametro);
		}else if(comando.equals("GET_FOTO")){
			
		}else if(comando.equals("GET_LOC")){
			
		}
	}

	/**
	 * Se encarga de gestionar el login en el momento de insertar el nick del usuario
	 * @param parametro
	 */
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

	/**
	 * Gestiona el tratamiento de la contrasena insertada por el usuario. En caso de que esta
	 * sea correcta, le permite el acceso al sistema. Por el contrario, si no coincidiese,
	 * volveria al inicio
	 * @param parametro
	 */
	public void tratarPass(String parametro) {
		if (actualUser.getPassword().equals(parametro)) {
			mensajeEnviar = "202 OK Bienvenido al sistema \n";
		} else if (parametro.equals("")) {
			mensajeEnviar = "403 ERR Falta la clave \n";
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

	/**
	 * Devuelve una lista con todos los sensores disponibles del vehiculo
	 */
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

	/**
	 * Devuelve una lista con todos los detalles de las medidas tomadas por dicho sensor
	 * seleccionado por parametro
	 * @param parametro
	 */
	public void tratarHistorico(String parametro) {
		try {
			if (parametro.equals("")) {
				mensajeEnviar = "415 ERR Falta parámetro id_sensor\n";
				sM.Escribir(mensajeEnviar);
			} else {
				try {
					int idSensor = Integer.parseInt(parametro);
					boolean encontrado = false;
					Sensor sensor = null;
					System.out.println(lSensores);
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
						mensajeEnviar = "414 ERR Sensor desconocido\n";
						sM.Escribir(mensajeEnviar);
					}
				} catch (NumberFormatException e) {
					mensajeEnviar = "414 ERR Sensor desconocido\n";
					sM.Escribir(mensajeEnviar);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al enviar HISTORICO");
		}
	}
	
	/**
	 * Activa el estado del sensor a 'ON', en caso de que estuviese descativado, sino
	 * devuelve un mensaje de error
	 * @param parametro
	 */
	public void tratarOnSensor(String parametro) {
		try	{
			boolean encontrado=false;
			Sensor s=null;
			try	{
				int idSensor=Integer.parseInt(parametro);
			
				for(int i=0; i<lSensores.size() && !encontrado; i++)	{
					if(lSensores.get(i).id==idSensor)	{
						encontrado=true;
						s=lSensores.get(i);
					}
				}
			} catch (NumberFormatException e) {
				mensajeEnviar = "417 ERR Sensor no existe.\n";
				sM.Escribir(mensajeEnviar);	
			}
			
			if(encontrado)	{
				if(s.isEstado())	{ //ON
					mensajeEnviar = "418 ERR Sensor en estado ON.\n";
					sM.Escribir(mensajeEnviar);	
				} else	{	//OFF
					s.setEstado(true);
					//actualizar en BD
					mensajeEnviar = "203 OK Sensor activo.\n";
					sM.Escribir(mensajeEnviar);	
				}
			} else	{
				mensajeEnviar = "417 ERR Sensor no existe.\n";
				sM.Escribir(mensajeEnviar);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Desactiva el estado del sensor, en caso de que estuviese activado, sino devuelve
	 * un mensaje de error
	 * @param parametro
	 */
	public void tratarOffSensor(String parametro) {
		try	{
			boolean encontrado=false;
			Sensor s=null;
			try	{
				int idSensor=Integer.parseInt(parametro);
			
				for(int i=0; i<lSensores.size() && !encontrado; i++)	{
					if(lSensores.get(i).id==idSensor)	{
						encontrado=true;
						s=lSensores.get(i);
					}
				}
			} catch (NumberFormatException e) {
				mensajeEnviar = "417 ERR Sensor no existe.\n";
				sM.Escribir(mensajeEnviar);	
			}
			
			if(encontrado)	{
				if(s.isEstado())	{ //OFF
					mensajeEnviar = "419 ERR Sensor en estado OFF.\n";
					sM.Escribir(mensajeEnviar);	
				} else	{	//OFF
					s.setEstado(false);
					//actualizar en BD
					mensajeEnviar = "204 OK Sensor desactivado.\n";
					sM.Escribir(mensajeEnviar);	
				}
			} else	{
				mensajeEnviar = "417 ERR Sensor no existe.\n";
				sM.Escribir(mensajeEnviar);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Activa el GPS del vehiculo, en caso de que estuviese desactivado, sino devuelve
	 * un mensaje de error
	 */
	public void tratarONGPS(){
		if(gps.isEstado()){
			mensajeEnviar = "419 ERR GPS en estado ON.\n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else{
			gps.setEstado(true);
			mensajeEnviar="205 OK GPS activado. \n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Desactiva el GPS del vehiculo, en caso de que estuviese activado, sino devuelve un
	 * mensaje de error
	 */
	public void tratarOFFGPS(){
		if(gps.isEstado()){
			gps.setEstado(false);
			mensajeEnviar = "206 OK GPS desactivado.\n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else{
			mensajeEnviar="420 ERR GPS en estado OFF \n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permite obtener el valor actual del sensor seleccionado por parametro. En caso de que
	 * dicho sensor estuviese desactivado, devuelve un mensaje de error.
	 * @param parametro
	 */
	public void tratarGetValact(String parametro){
		try {
			if (parametro.equals("")) {
				mensajeEnviar = "415 ERR Falta parámetro id_sensor\n";
				sM.Escribir(mensajeEnviar);
			} else {
				try {
					int idSensor = Integer.parseInt(parametro);
					boolean encontrado = false;
					Sensor sensor = null;
					System.out.println(lSensores);
					for (int i = 0; i < lSensores.size() && !encontrado; i++) {
						sensor = lSensores.get(i);
						if (sensor.getId() == idSensor) {
							encontrado = true;
						}
					}
					if (encontrado) {
						if(sensor.isEstado()){
							//preguntar qué medida hay que mandar.
						}else{
							mensajeEnviar="416 Sensor en OFF\n";
							sM.Escribir(mensajeEnviar);
						}
					} else {
						mensajeEnviar = "414 ERR Sensor desconocido\n";
						sM.Escribir(mensajeEnviar);
					}
				} catch (NumberFormatException e) {
					mensajeEnviar = "414 ERR Sensor desconocido\n";
					sM.Escribir(mensajeEnviar);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al enviar HISTORICO");
		}
	}
	
	/**
	 * Permite al usuario recibir una "foto", transmision de bytes, en caso de que el GPS
	 * se encuentre activado. Posteriormente, debera ejecutar el comando "GET_LOC" para
	 * que el proceso sea correcto. 
	 */
	public void tratarGetFoto(){
		try {
		if(gps.isEstado()){	
				sM.Escribir("206 OK lo que serían los bytes...\n");
				if(sM.Leer().equals("GET_LOC")){
					tratarGetLoc();
				}else{
					sM.Escribir("No has enviado GET_LOC debes enviar GET_LOC\n");
				}
		}
		else{
				sM.Escribir("420 ERR GPS en estado OFF\n");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Devuelve las coordenadas geograficas del GPS del vehiculo
	 */
	public void tratarGetLoc(){
		try {	
		sM.Escribir("114 OK "+gps.getLatitud()+"-"+gps.getLongitud()+"\n");		
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(5888);
			SocketManager sM = new SocketManager(ss.accept());
			GestorBD gestor=GestorBD.getInstance();
			gestor.conectar();
			Vehiculo s =gestor.getVehiculo(1, sM);
			gestor.desconectar();
			System.out.println("Servidor activo");
			s.activarServidor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
