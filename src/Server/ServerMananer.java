package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import Util.SocketManager;

public class ServerMananer {
	public static int maxNumUsers=5;
	public static int actualNumUser=0;
	public static boolean terminar=false;
	public static ArrayList<Thread> servidores=new ArrayList<Thread>();
	public void activarEscucha(){
		try {
			ServerSocket ss = new ServerSocket(5888);
			while (!terminar){
				if(maxNumUsers>actualNumUser){
					SocketManager sM = new SocketManager(ss.accept());
					Vehiculo v=new Vehiculo(1,false,sM);
					actualNumUser++;
					//Sin terminar
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
