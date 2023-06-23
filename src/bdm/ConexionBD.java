package bdm;

import start.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

	private static Connection connection = null;
	
    private static String db_URL = null;
    private static String db_USER = null;
    private static String db_PASSWORD = null;

    static {
        
        try (BufferedReader reader = new BufferedReader(new FileReader("src/bdm/dbconfig.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("DB_URL=")) {
                    db_URL = line.substring("DB_URL=".length());
                } else if (line.startsWith("DB_USER=")) {
                	db_USER = line.substring("DB_USER=".length());
                } else if (line.startsWith("DB_PASSWORD=")) {
                	db_PASSWORD = line.substring("DB_PASSWORD=".length());
                }
            }
        } catch (IOException e) {
        	Log log = new Log(Log.Tipo.ERROR, "Error al leer el fichero de configuración de la base de datos: " + e.getMessage());
        }
    }

	public static void openConnection() {
		try {
			connection = DriverManager.getConnection(db_URL, db_USER, db_PASSWORD);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			Log log = new Log(Log.Tipo.ERROR, "ERROR: OPENING. " + e.getMessage());
		}
	}


	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			Log log = new Log(Log.Tipo.ERROR, "ERROR: CLOSING. " + e.getMessage());
		}
	}


	public static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			Log log = new Log(Log.Tipo.ERROR, "ERROR: QUERING. " + e.getMessage());
		}
		return rs;
	}
	

	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);		
	}


	public static void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			Log log = new Log("ERROR: COMMIT. " + e.getMessage());
		}
	}


	public static void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			Log log = new Log(Log.Tipo.ERROR, "ERROR: ROLLBACK. " + e.getMessage());
		}		
	}

}