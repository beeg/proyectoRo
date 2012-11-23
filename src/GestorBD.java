import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
			System.out.println("Conectting");
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager
					.getConnection("jdbc:sqlite:files/Reversi.s3db"); // Conectar
																		// con X
																		// base
																		// de
																		// datos
																		// a Y
																		// archivo,
																		// por
																		// eso
			// jdbc:sqlite:

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
			System.out.println("Disconnecting");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}