package Server;

import java.util.ArrayList;

import DB.GestorBD;

public class Sensor {
	int id;
	String desc;
	boolean estado;
	ArrayList<Medida> medidas;

	public Sensor(int id, String desc, boolean estado, ArrayList<Medida> medidas) {
		super();
		this.id = id;
		this.desc = desc;
		this.estado = estado;
		this.medidas = medidas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public ArrayList<Medida> getMedidas() {
		return medidas;
	}

	public void setMedidas(ArrayList<Medida> medidas) {
		this.medidas = medidas;
	}

	public String toString() {
		return getId() + ";" + getDesc() + ";" + isEstado();
	}

}
