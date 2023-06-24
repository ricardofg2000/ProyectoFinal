package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Producto;
import start.Log;

import bdm.ConexionBD;

/**
 * La clase ProductosController proporciona métodos para gestionar productos en la base de datos.
 */
public class ProductosController {

    /**
     * Obtiene una lista de todos los productos.
     *
     * @return una lista de productos
     */
    public static List<Producto> listadoProductos() {

        List<Producto> productos = new ArrayList<>();
        ConexionBD.openConnection();
        String sql = "SELECT * FROM Productos";
        ResultSet resultSet = ConexionBD.query(sql);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                String descripcion = resultSet.getString("descripcion");
                String codigoBarras = resultSet.getString("codigoBarras");

                Producto producto = new Producto(id, nombre, precio, descripcion, codigoBarras);
                productos.add(producto);
            }
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener los datos: " + e.getMessage());
        }

        ConexionBD.closeConnection();

        return productos;
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto el producto a agregar
     */
    public static void agregarProducto(Producto producto) {
        try {
            ConexionBD.openConnection();
            String sql = "INSERT INTO Productos (nombre, precio, descripcion, codigoBarras) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setString(3, producto.getDescripcion());
            statement.setString(4, producto.getCodigoBarras());
            statement.executeUpdate();
            ConexionBD.commit();
            statement.close();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al agregar el producto a la base de datos: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Borra un producto de la base de datos.
     *
     * @param id el ID del producto a borrar
     */
    public static void borrarProducto(int id) {
        try {
            ConexionBD.openConnection();
            String sql = "DELETE FROM Productos WHERE id = ?";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            ConexionBD.commit();
            statement.close();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al borrar el producto de la base de datos: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Actualiza los datos de un producto en la base de datos.
     *
     * @param producto el producto con los datos actualizados
     */
    public static void actualizarProducto(Producto producto) {
        try {
            ConexionBD.openConnection();
            String sql = "UPDATE Productos SET nombre = ?, precio = ?, descripcion = ?, codigoBarras = ? WHERE id = ?";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setString(3, producto.getDescripcion());
            statement.setString(4, producto.getCodigoBarras());
            statement.setInt(5, producto.getId());
            statement.executeUpdate();
            ConexionBD.commit();
            statement.close();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al actualizar el producto en base de datos: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Obtiene un producto de la base de datos por su ID.
     *
     * @param id el ID del producto
     * @return el producto correspondiente al ID, o null si no se encuentra
     */
    public static Producto obtenerProductoPorId(int id) {
        Producto producto = null;
        ConexionBD.openConnection();
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement stmt = ConexionBD.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String descripcion = rs.getString("descripcion");
                String codigoBarras = rs.getString("codigo_barras");

                producto = new Producto(productId, nombre, precio, descripcion, codigoBarras);
            }
            rs.close();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener el producto con id " + id + " en base de datos: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }
        return producto;
    }

    /**
     * Verifica si un producto existe en la base de datos.
     *
     * @param nombreProducto el nombre del producto a verificar
     * @return true si el producto existe, false en caso contrario
     */
    public static boolean existeProducto(String nombreProducto) {
        boolean existe = false;

        try {
            ConexionBD.openConnection();

            String sql = "SELECT COUNT(*) FROM productos WHERE nombre = ?";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);
            statement.setString(1, nombreProducto);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                existe = count > 0;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al comprobar la existencia del producto: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return existe;
    }

}
