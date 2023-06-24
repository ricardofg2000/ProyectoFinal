package controller;

import bdm.ConexionBD;
import start.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La clase PedidosController proporciona métodos para agregar pedidos y obtener información relacionada con los pedidos.
 */
public class PedidosController {

    /**
     * Agrega un pedido para un cliente específico.
     *
     * @param idCliente el ID del cliente
     * @return el ID del pedido agregado, o -1 si ocurre un error
     */
    public static int agregarPedido(int idCliente) {
        int idPedido = -1;

        java.util.Date fechaActual = new java.util.Date();
        java.sql.Timestamp fechaPedido = new java.sql.Timestamp(fechaActual.getTime());

        ConexionBD.openConnection();

        try {
            // Consulta para obtener el idCarrito usando el idCliente
            String carritoSql = "SELECT id FROM carritodecompra WHERE id_cliente = ?";
            PreparedStatement carritoStatement = ConexionBD.prepareStatement(carritoSql);
            carritoStatement.setInt(1, idCliente);
            ResultSet carritoResult = carritoStatement.executeQuery();

            int idCarrito = -1;

            if (carritoResult.next()) {
                idCarrito = carritoResult.getInt("id");
            } else {
                Log log = new Log("No se encontró un carrito asociado al cliente.");
                return idPedido;
            }

            // Inserción del pedido con el idCarrito obtenido
            String pedidoSql = "INSERT INTO Pedidos (idCarrito, idCliente, fechaPedido) VALUES (?, ?, ?)";
            PreparedStatement pedidoStatement = ConexionBD.prepareStatement(pedidoSql);

            pedidoStatement.setInt(1, idCarrito);
            pedidoStatement.setInt(2, idCliente);
            pedidoStatement.setTimestamp(3, fechaPedido);

            pedidoStatement.executeUpdate();

            ConexionBD.commit();
            idPedido = obtenerUltimoIdPedido();
            Log log = new Log("Pedido agregado correctamente. ID del pedido: " + idPedido);
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al agregar el pedido: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }

        return idPedido;
    }

    /**
     * Obtiene el último ID de pedido registrado en la base de datos.
     *
     * @return el último ID de pedido, o -1 si ocurre un error
     */
    public static int obtenerUltimoIdPedido() {
        int idPedido = -1;

        ConexionBD.openConnection();

        try {
            String sql = "SELECT MAX(id) FROM Pedidos";
            ResultSet resultSet = ConexionBD.query(sql);
            if (resultSet.next()) {
                idPedido = resultSet.getInt(1);
            }
            ConexionBD.commit();
        } catch (SQLException e) {
            Log log = new Log(Log.Tipo.ERROR, "Error al obtener el último ID de pedido: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }

        return idPedido;
    }
}
