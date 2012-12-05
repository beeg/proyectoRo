package Server;

import java.util.ArrayList;
import java.util.Date;

import DB.GestorBD;

public class Medida {
	private int id;
	private Date date;
	private String latitud;
	private String longitud;
	private int valor;

	public Medida(int id, Date date, String latitud, String longitud, int valor) {
		super();
		this.id = id;
		this.date = date;
		this.longitud = longitud;
		this.latitud = latitud;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String toString() {
		String dia = (new Integer(date.getDate())).toString(); System.out.println(dia);
		String mes = (new Integer(date.getMonth()+1)).toString(); System.out.println(mes);
		String anyo = (new Integer(date.getYear()+1900)).toString(); System.out.println(anyo);
		return dia+"/"+mes+"/"+ anyo + ";" + "hora"/*(new Integer(date.getHours())).toString()+ ":" + (new Integer(date.getMinutes())).toString() + ":" +(new Integer(date.getSeconds())).toString()*/+ ";" + latitud + "-" + longitud + ";" + valor;
	}
	
	public static void main(String[] args)	{
		Date date = new Date();
		GestorBD g = GestorBD.getInstance();
		g.conectar();
		ArrayList<Medida> m=g.getMedidas(1);
		g.desconectar();
		System.out.println(m.get(0).getDate());
	}

}
