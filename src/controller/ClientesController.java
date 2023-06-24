package controller;

import start.Log;
import bdm.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase ClientesController proporciona métodos para administrar clientes en la base de datos.
 */
public class ClientesController {

    /**
     * Valida las credenciales de un usuario.
     *
     * @param usuario    el nombre de usuario
     * @param contrasena la contraseña
     * @return un entero que representa el resultado de la validación:
     *         - 0: credenciales inválidas
     *         - 1: administrador
     *         - 2: cliente normal
     */
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
            Log log = new Log(Log.Tipo.ERROR, "Error al validar las credenciales: " + e.getMessage());
        }

        ConexionBD.closeConnection();
        return valid;
    }

    /**
     * Verifica si existe un usuario en la base de datos.
     *
     * @param usuario el nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
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
            Log log = new Log(Log.Tipo.ERROR, "Error al verificar la existencia del usuario: " + e.getMessage());
        }
        ConexionBD.closeConnection();
        return false;
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param nombre     el nombre del cliente
     * @param usuario    el nombre de usuario del cliente
     * @param contrasena la contraseña del cliente
     * @param telefono   el número de teléfono del cliente
     * @param direccion  la dirección del cliente
     */
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
            Log log = new Log("Cliente creado exitosamente.");
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al verificar la existencia del usuario: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param cliente el objeto Cliente que representa al cliente a crear
     */
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
            Log log = new Log("Cliente creado exitosamente.");
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al crear el cliente: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Elimina un cliente de la base de datos.
     *
     * @param id el ID del cliente a eliminar
     */
    public static void borrarCliente(int id) {
        try {
            ConexionBD.openConnection();
            String sql = "DELETE FROM Cliente WHERE id = ?";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            ConexionBD.commit();
            statement.close();
            Log log = new Log("Cliente eliminado exitosamente.");
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al borrar el cliente: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Obtiene el ID de un cliente a partir de su nombre de usuario.
     *
     * @param usuario el nombre de usuario del cliente
     * @return el ID del cliente, 0 si no se encuentra
     */
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
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener el id del cliente: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }
        return idCliente;
    }

    /**
     * Obtiene una lista de todos los clientes en la base de datos.
     *
     * @return una lista de objetos Cliente
     */
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
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener los datos: " + e.getMessage());
        }

        ConexionBD.closeConnection();

        return clientes;
    }

    /**
     * Actualiza los datos de un cliente en la base de datos.
     *
     * @param cliente el objeto Cliente con los datos actualizados
     */
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
            Log log = new Log(Log.Tipo.ERROR, "Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

}
