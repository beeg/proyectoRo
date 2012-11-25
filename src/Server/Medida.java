package Server;

import java.util.Date;

public class Medida {
	private int id;
	private Date date;
	private String latitud;
	private String longitud;
	int valor;

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
		return "date" + ";" + latitud + "-" + longitud + ";" + valor;
	}

}
