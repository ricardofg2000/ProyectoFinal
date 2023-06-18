package controller;

import bdm.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteController {

	public static boolean validarCredenciales(String usuario, String contrasena) {
		boolean valid = false;
		ConexionBD.openConnection();
		String sql = "SELECT * FROM Cliente WHERE usuario = ? AND contrasena = ?";

		try {
			PreparedStatement statement = ConexionBD.prepareStatement(sql);
			statement.setString(1, usuario);
			statement.setString(2, contrasena);
			ResultSet resultSet = statement.executeQuery();
			valid = resultSet.next();
		} catch (SQLException e) {
			System.err.println("Error al validar las credenciales: " + e.getMessage());
		}

		ConexionBD.closeConnection();
		return valid;
	}

	public static boolean existeUsuario(String usuario) {
		ConexionBD.openConnection();
		String sql = "SELECT COUNT(*) FROM Cliente WHERE usuario = ?";
		try {
			PreparedStatement statement = ConexionBD.prepareStatement(sql);
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			System.err.println("Error al verificar la existencia del usuario: " + e.getMessage());
		}
		ConexionBD.closeConnection();
		return false;
	}

	public static void crearCliente(String nombre, String usuario, String contrasena, String telefono,
			String direccion) {
		ConexionBD.openConnection();
		try {
			String sql = "INSERT INTO Cliente (nombre, usuario, contrasena, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement statement = ConexionBD.prepareStatement(sql);
			statement.setString(1, nombre);
			statement.setString(2, usuario);
			statement.setString(3, contrasena);
			statement.setString(4, telefono);
			statement.setString(5, direccion);
			statement.executeUpdate();

			ConexionBD.commit();
			System.out.println("Cliente creado exitosamente.");
		} catch (SQLException e) {
			System.err.println("Error al crear el cliente: " + e.getMessage());
			ConexionBD.rollback();
		} finally {
			ConexionBD.closeConnection();
		}
	}

	public static int obtenerIdCliente(String usuario) {
		int idCliente = 0;

		ConexionBD.openConnection();

		try {
			String sql = "SELECT id FROM Cliente WHERE usuario = ?";
			PreparedStatement statement = ConexionBD.prepareStatement(sql);
			statement.setString(1, usuario);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				idCliente = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexionBD.closeConnection();
		}
		System.out.println(idCliente);//Borrar
		return idCliente;
	}

}
