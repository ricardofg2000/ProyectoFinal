package bdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

	private static Connection connection = null;
	
    private static final String DB_URL = "jdbc:mysql://database-ricardo.ckscbj98msy3.us-east-1.rds.amazonaws.com:3306/ProyectoProgramacion";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rootroot";

	/**
	 * Abrir una conexión con la BD
	 */
	public static void openConnection() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("ERROR: OPENING. " + e.getMessage());
		}
	}

	/**
	 * Cerrar la conexión con la BD
	 */
	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.err.println("ERROR: CLOSING. " + e.getMessage());
		}
	}

	/**
	 * Retornar un ResultSet con el resultado de ejecutar una Query en la BD (tiempo máximo de espera = 30 segundos)
	 */
	public static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("ERROR: QUERING. " + e.getMessage());
		}
		return rs;
	}
	
	/**
	 * Devolver una PreparedStatement sobre la conexión actual 
	 */
	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);		
	}

	/**
	 * Confirmar una transacción
	 */
	public static void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			System.err.println("ERROR: COMMIT. " + e.getMessage());
		}
	}

	/**
	 * Deshacer una transacción
	 */
	public static void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.err.println("ERROR: ROLLBACK. " + e.getMessage());
		}		
	}

}