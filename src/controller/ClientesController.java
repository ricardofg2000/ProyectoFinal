package controller;

import bdm.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClientesController {

	public static int validarCredenciales(String usuario, String contrasena) {
	    int valid = 0;
	    ConexionBD.openConnection();
	    String sql = "SELECT * FROM Cliente WHERE usuario = ? AND contrasena = ?";

	    try {
	        PreparedStatement statement = ConexionBD.prepareStatement(sql);
	        statement.setString(1, usuario);
	        statement.setString(2, contrasena);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	            String rol = resultSet.getString("rol");
	            if (rol.equals("admin")) {
	                valid = 1;
	            } else if (rol.equals("cliente")) {
	                valid = 2;
	            }
	        }
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
	
	public static void crearCliente(Cliente cliente) {
	    ConexionBD.openConnection();
	    try {
	        String sql = "INSERT INTO Cliente (nombre, direccion, telefono, usuario, contrasena) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement statement = ConexionBD.prepareStatement(sql);
	        statement.setString(1, cliente.getNombre());
	        statement.setString(2, cliente.getDireccion());
	        statement.setString(3, cliente.getTelefono());
	        statement.setString(4, cliente.getUsuario());
	        statement.setString(5, cliente.getContrasena());

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

	public static void borrarCliente(int id) {
	    try {
	        ConexionBD.openConnection();
	        String sql = "DELETE FROM Cliente WHERE id = ?";
	        PreparedStatement statement = ConexionBD.prepareStatement(sql);
	        statement.setInt(1, id);
	        statement.executeUpdate();
	        ConexionBD.commit();
	        statement.close();
	        System.out.println("Cliente eliminado exitosamente.");
	    } catch (SQLException e) {
	        e.printStackTrace();
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
		return idCliente;
	}

	public static List<Cliente> listadoClientes() {
	    List<Cliente> clientes = new ArrayList<>();
	    ConexionBD.openConnection();
	    String sql = "SELECT * FROM Cliente";
	    ResultSet resultSet = ConexionBD.query(sql);

	    try {
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String nombre = resultSet.getString("nombre");
	            String direccion = resultSet.getString("direccion");
	            String telefono = resultSet.getString("telefono");
	            String usuario = resultSet.getString("usuario");
	            String contrasena = resultSet.getString("contrasena");
	            String rol = resultSet.getString("rol");

	            Cliente cliente = new Cliente(id, usuario, contrasena, nombre, telefono, direccion, rol);
	            clientes.add(cliente);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al obtener los datos: " + e.getMessage());
	    }

	    ConexionBD.closeConnection();

	    return clientes;
	}
	
	public static void actualizarCliente(Cliente cliente) {
	    try {
	        ConexionBD.openConnection();
	        String sql = "UPDATE Cliente SET nombre = ?, direccion = ?, telefono = ?, usuario = ?, contrasena = ? WHERE id = ?";
	        PreparedStatement statement = ConexionBD.prepareStatement(sql);
	        statement.setString(1, cliente.getNombre());
	        statement.setString(2, cliente.getDireccion());
	        statement.setString(3, cliente.getTelefono());
	        statement.setString(4, cliente.getUsuario());
	        statement.setString(5, cliente.getContrasena());
	        statement.setInt(6, cliente.getId());
	        statement.executeUpdate();
	        ConexionBD.commit();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        ConexionBD.rollback();
	    } finally {
	        ConexionBD.closeConnection();
	    }
	}


}
