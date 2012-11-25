package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Server.Medida;
import Server.Sensor;
import Server.Usuario;
import Server.Vehiculo;

public class GestorBD {
	private Connection conexion;
	private static GestorBD instance;

	protected GestorBD() {

	}

	/**
	 * Obtiene la instancia actual de la base de datos, y si no existe la crea.
	 * 
	 * @return instancia de la base de datos
	 */
	public static GestorBD getInstance() {
		if (instance == null) {
			instance = new GestorBD();
		}
		return instance;
	}

	/**
	 * Metodo que conecta a la BD.
	 */
	public void conectar() {
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:db/ro.s3db");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que desconecta de la BD.
	 */
	public void desconectar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertarUsuario(String login, String pass) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("insert into USUARIO values(?,?)");
		smt.setString(1, login);
		smt.setString(2, pass);
		smt.executeUpdate();
	}

	public void borrarUsuario(String login) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("DELETE FROM USUARIO WHERE LOGIN=?");
		smt.setString(1, login);
		smt.executeUpdate();
	}

	public Usuario getUsuario(String login) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("SELECT * FROM USUARIO where LOGIN=?");
		smt.setString(1, login);
		ResultSet rs = smt.executeQuery();
		return new Usuario(login, rs.getString(2));
	}

	public ArrayList<Usuario> getUsuarios() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		PreparedStatement smt;
		Usuario u;

		try {
			smt = conexion.prepareStatement("SELECT * FROM USUARIO");
			ResultSet rs = smt.executeQuery();
			while (rs.next()) {
				String nick = rs.getString("LOGIN");
				String password = rs.getString("PASSWORD");
				u = new Usuario(nick, password);
				listaUsuarios.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	public void setPassword(String login, String password) {
		PreparedStatement smt;
		try {
			smt = conexion
					.prepareStatement("UPDATE USUARIO SET PASSWORD=? WHERE LOGIN=?");
			smt.setString(1, password);
			smt.setString(2, login);
			smt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertarVehiculo(boolean gps) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("insert into VEHICULO values(?)");
		smt.setBoolean(1, gps);
		smt.executeUpdate();
	}

	public void borrarVehiculo(int id) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("DELETE FROM VEHICULO WHERE ID=?");
		smt.setInt(1, id);
		smt.executeUpdate();
	}

	public Vehiculo getVehiculo(int id) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("SELECT * FROM VEHICULO where ID=?");
		smt.setInt(1, id);
		ResultSet rs = smt.executeQuery();
		return new Vehiculo(id, rs.getBoolean(2));
	}

	public void insertarSensor(String desc, boolean gps) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("insert into SENSOR values(?,?)");
		smt.setString(1, desc);
		smt.setBoolean(2, gps);
		smt.executeUpdate();
	}

	public void borrarSensor(int id) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("DELETE FROM SENSOR WHERE ID=?");
		smt.setInt(1, id);
		smt.executeUpdate();
	}

	public Sensor getSensor(int id) throws SQLException {
		PreparedStatement smt;
		smt = conexion.prepareStatement("SELECT * FROM SENSOR where ID=?");
		smt.setInt(1, id);
		ResultSet rs = smt.executeQuery();
		return new Sensor(id, rs.getString(2), rs.getBoolean(3), getMedidas(id));
	}

	public ArrayList<Sensor> getSensores(int id) {
		ArrayList<Sensor> listaSensores = new ArrayList<Sensor>();
		PreparedStatement smt;
		try {
			smt = conexion
					.prepareStatement("SELECT * FROM SENSOR where ID_VEHICULO=?");
			smt.setInt(1, id);
			ResultSet rs = smt.executeQuery();
			while (rs.next()) {
				int idSensor = rs.getInt("ID");
				String desc = rs.getString("DESC");
				boolean estado = rs.getBoolean("ESTADO");
				listaSensores.add(new Sensor(idSensor, desc, estado,
						getMedidas(idSensor)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaSensores;
	}

	public ArrayList<Medida> getMedidas(int idSensor) {
		ArrayList<Medida> lMedidas = new ArrayList<Medida>();
		PreparedStatement smt;
		try {
			smt = conexion
					.prepareStatement("SELECT * FROM MEDIDA where ID_SENSOR=?");
			smt.setInt(1, idSensor);
			ResultSet rs = smt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				Date fecha = rs.getDate("FECHA");
				String longitud = rs.getString("LONG");
				String lat = rs.getString("LAT");
				int valor = rs.getInt("VALOR");
				lMedidas.add(new Medida(id, fecha, lat, longitud, valor));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lMedidas;

	}

	public static void main(String[] args) {
		GestorBD gestor = GestorBD.getInstance();
		gestor.conectar();
		ArrayList<Sensor> lSensores = gestor.getSensores(1);
		Sensor s = lSensores.get(0);
		gestor.desconectar();
	}

}