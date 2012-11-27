package Server;

public class GPS {
	
	private boolean estado;
	private String latitud;
	private String longitud;
	
	public GPS (boolean estado, String latitud, String longitud)	{
		this.estado=estado;
		this.latitud=latitud;
		this.longitud=longitud;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	

}
