package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import DB.GestorBD;
import Util.SocketManager;

public class Vehiculo extends Thread {
	int id;
	boolean estado;
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
	public Vehiculo(int id,boolean estado, SocketManager sM) {
		this.sM = sM;
		this.id = id;
		this.estado=estado;
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

	public void tratarONGPS(){
		if(estado){
			mensajeEnviar = "419 ERR GPS en estado ON.\n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}else{
			estado=true;
			mensajeEnviar="205 OK GPS activado. \n";
			try {
				sM.Escribir(mensajeEnviar);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void tratarOFFGPS(){
		if(estado){
			estado=false;
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
	public void tratarGetFoto(){
		try {
		if(this.estado){	
				sM.Escribir("206 OK lo que serían los bytes...\n");
				if(sM.Leer().equals("GET_LOC")){
					
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
