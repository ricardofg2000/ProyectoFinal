package bdm;

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
	
    //private static final String DB_URL = "jdbc:mysql://database-ricardo.ckscbj98msy3.us-east-1.rds.amazonaws.com:3306/ProyectoProgramacion";
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/bdm/dbconfig.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("DB_URL=")) {
                    dbUrl = line.substring("DB_URL=".length());
                } else if (line.startsWith("DB_USER=")) {
                    dbUser = line.substring("DB_USER=".length());
                } else if (line.startsWith("DB_PASSWORD=")) {
                    dbPassword = line.substring("DB_PASSWORD=".length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DB_URL = dbUrl;
        DB_USER = dbUser;
        DB_PASSWORD = dbPassword;
    }

	public static void openConnection() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("ERROR: OPENING. " + e.getMessage());
		}
	}


	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.err.println("ERROR: CLOSING. " + e.getMessage());
		}
	}


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
	

	public static PreparedStatement prepareStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);		
	}


	public static void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			System.err.println("ERROR: COMMIT. " + e.getMessage());
		}
	}


	public static void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.err.println("ERROR: ROLLBACK. " + e.getMessage());
		}		
	}

}