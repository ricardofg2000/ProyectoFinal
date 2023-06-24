package controller;

import bdm.ConexionBD;
import start.Log;
import model.CarritoDeCompra;
import model.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * La clase CarritoDeCompraController se encarga de gestionar las operaciones relacionadas con el carrito de compra.
 */
public class CarritoDeCompraController {

    /**
     * Agrega un producto al carrito de compra de un usuario.
     *
     * @param usuario    el nombre de usuario del cliente.
     * @param idProducto el ID del producto a agregar.
     * @param cantidad   la cantidad del producto a agregar.
     */
    public static void agregarProductoAlCarrito(String usuario, int idProducto, int cantidad) {
        ConexionBD.openConnection();

        try {
            String selectSql = "SELECT * FROM carritodecompra "
                    + "INNER JOIN cliente ON carritodecompra.id_cliente = cliente.id "
                    + "WHERE cliente.usuario = ? AND carritodecompra.producto_id = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(selectSql);
            selectStatement.setString(1, usuario);
            selectStatement.setInt(2, idProducto);

            ResultSet resultSet = selectStatement.executeQuery();

            // Si existe un carrito de compra de ese cliente
            if (resultSet.next()) {
                int nuevaCantidad = cantidad;

                String updateSql = "UPDATE carritodecompra "
                        + "INNER JOIN Cliente ON carritodecompra.id_cliente = Cliente.id "
                        + "SET carritodecompra.cantidad = ? "
                        + "WHERE cliente.usuario = ? AND carritodecompra.producto_id = ?";
                PreparedStatement updateStatement = ConexionBD.prepareStatement(updateSql);
                updateStatement.setInt(1, nuevaCantidad);
                updateStatement.setString(2, usuario);
                updateStatement.setInt(3, idProducto);

                updateStatement.executeUpdate();

                Log log = new Log("Cantidad actualizada en el carrito correctamente.");

                // Si ese cliente no tiene carrito
            } else {
                // Consulta para obtener el id_cliente
                String selectClienteSql = "SELECT id FROM Cliente WHERE usuario = ?";
                PreparedStatement selectClienteStatement = ConexionBD.prepareStatement(selectClienteSql);
                selectClienteStatement.setString(1, usuario);
                ResultSet clienteResultSet = selectClienteStatement.executeQuery();

                int idCliente = 0;
                if (clienteResultSet.next()) {
                    idCliente = clienteResultSet.getInt("id");
                }

                String insertSql = "INSERT INTO carritodecompra (producto_id, cantidad, id_cliente) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = ConexionBD.prepareStatement(insertSql);
                insertStatement.setInt(1, idProducto);
                insertStatement.setInt(2, cantidad);
                insertStatement.setInt(3, idCliente);

                insertStatement.executeUpdate();

                Log log = new Log("Producto agregado al carrito correctamente.");

            }

            ConexionBD.commit();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al agregar el producto al carrito: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }

    /**
     * Obtiene la cantidad de un producto en el carrito de compra de un usuario.
     *
     * @param usuario    el nombre de usuario del cliente.
     * @param idProducto el ID del producto.
     * @return la cantidad del producto en el carrito.
     */
    public static String obtenerCantidadProducto(String usuario, int idProducto) {
        ConexionBD.openConnection();

        String cantidad = "";

        try {
            String selectSql = "SELECT carritodecompra.cantidad " + "FROM carritodecompra "
                    + "INNER JOIN Cliente ON carritodecompra.id_cliente = cliente.id "
                    + "WHERE cliente.usuario = ? AND carritodecompra.producto_id = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(selectSql);
            selectStatement.setString(1, usuario);
            selectStatement.setInt(2, idProducto);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                cantidad = resultSet.getString("cantidad");
            }
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR,
                    "Error al obtener la cantidad del producto en el carrito: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return cantidad;
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param productoId el ID del producto.
     * @return el producto correspondiente al ID.
     */
    public static Producto obtenerProducto(int productoId) {
        ConexionBD.openConnection();
        Producto producto = null;

        try {
            String sql = "SELECT * FROM productos WHERE id = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(sql);
            selectStatement.setInt(1, productoId);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                String descripcion = resultSet.getString("descripcion");
                String codigoBarras = resultSet.getString("codigoBarras");

                producto = new Producto(id, nombre, precio, descripcion, codigoBarras);
            }
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener el producto: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return producto;
    }

    /**
     * Obtiene la lista de productos en el carrito de compra de un usuario.
     *
     * @param usuario el nombre de usuario del cliente.
     * @return la lista de productos en el carrito.
     */
    public static List<Producto> obtenerProductosCarrito(String usuario) {
        ConexionBD.openConnection();
        List<Producto> productos = new ArrayList<>();

        try {
            String sql = "SELECT c.producto_id, cl.id " + "FROM carritodecompra c "
                    + "INNER JOIN cliente cl ON c.id_cliente = cl.id " + "WHERE cl.usuario = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(sql);
            selectStatement.setString(1, usuario);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int productoId = resultSet.getInt("producto_id");
                Producto producto = obtenerProducto(productoId);
                productos.add(producto);
            }
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener los productos del carrito: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return productos;
    }

    /**
     * Borra el carrito de compra de un usuario.
     *
     * @param usuario el nombre de usuario del cliente.
     */
    public static void borrarCarrito(String usuario) {
        ConexionBD.openConnection();

        try {
            // Obtener el idCliente usando el usuario
            String clienteSql = "SELECT id FROM cliente WHERE usuario = ?";
            PreparedStatement clienteStatement = ConexionBD.prepareStatement(clienteSql);
            clienteStatement.setString(1, usuario);
            ResultSet clienteResult = clienteStatement.executeQuery();

            int idCliente = -1;

            if (clienteResult.next()) {
                idCliente = clienteResult.getInt("id");
            } else {
                Log log = new Log("No se encontró un cliente con el usuario especificado.");
                return;
            }

            // Borrar el carrito asociado al idCliente
            String carritoSql = "DELETE FROM carritodecompra WHERE id_cliente = ?";
            PreparedStatement carritoStatement = ConexionBD.prepareStatement(carritoSql);
            carritoStatement.setInt(1, idCliente);
            carritoStatement.executeUpdate();

            ConexionBD.commit();
            Log log = new Log("Carrito borrado correctamente.");
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al borrar el carrito: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }
}
