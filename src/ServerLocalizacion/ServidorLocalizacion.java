package ServerLocalizacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import Server.Usuario;
import Util.SocketManager;

public class ServidorLocalizacion {
	private SocketManager sM;
	private String mensajeCliente;
	private Usuario actualUser;
	private ArrayList<Usuario>lUsuarios;
	private ArrayList<Celda> lCeldas;
	private String mensajeEnviar;
	//private static String[]arrayComandos={"USER","PASS","GET_COOR","SALIR"};
	public ServidorLocalizacion(SocketManager sM){
		this.sM=sM;
		mensajeCliente="";
		mensajeEnviar="";
	}
	public void activarServidor(){
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
	public void gestionarMensaje(){
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
	
	public void tratarGetCoor(String parametro) {
		try {
		if(parametro.equals(""))	{
			mensajeEnviar="418 ERR Falta parametro cell_id.\n";
		}	else	{
		try	{
		boolean encontrado=false;
		int idCelda=Integer.parseInt(parametro);
		Celda celda=null;
		
		for(int i=0;i<lCeldas.size()&&!encontrado;i++)	{
			if(lCeldas.get(i).getId()==idCelda)	{
				encontrado=true;
				celda=lCeldas.get(i);
			}
		}
		
		if(encontrado)	{
			mensajeEnviar="114 OK "+celda.getLatitud()+"-"+celda.getLongitud()+"\n";
		}	else	{
			mensajeEnviar="417 ERR Celda desconocida.\n";
		} }  catch (NumberFormatException e) {
			mensajeEnviar = "417 ERR Celda desconocida\n";
			sM.Escribir(mensajeEnviar);
		}
		}
			sM.Escribir(mensajeEnviar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[]args){
		try {
			ServerSocket ss=new ServerSocket(5889);
			SocketManager sM=new SocketManager(ss.accept());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
