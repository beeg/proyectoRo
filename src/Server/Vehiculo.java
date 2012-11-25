package Server;

public class Vehiculo {
	int id;
	boolean gps;

	public Vehiculo(int id, boolean gps) {
		super();
		this.id = id;
		this.gps = gps;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

}
