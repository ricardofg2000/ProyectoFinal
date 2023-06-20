package controller;

import bdm.ConexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidosController {
    public static int agregarPedido(int idCliente, int idCarrito) {
        int idPedido = -1;

        java.util.Date fechaActual = new java.util.Date();
        java.sql.Timestamp fechaPedido = new java.sql.Timestamp(fechaActual.getTime());

        ConexionBD.openConnection();

        try {
            String sql = "INSERT INTO Pedidos (idCarrito, idCliente, fechaPedido) VALUES (?, ?, ?)";
            PreparedStatement statement = ConexionBD.prepareStatement(sql);

            statement.setInt(1, idCarrito);
            statement.setInt(2, idCliente);
            statement.setTimestamp(3, fechaPedido);

            statement.executeUpdate();

            ConexionBD.commit();
            idPedido = obtenerUltimoIdPedido();
            System.out.println("Pedido agregado correctamente. ID del pedido: " + idPedido);
        } catch (SQLException e) {
            System.out.println("Error al agregar el pedido: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }

        return idPedido;
    }

    public static int obtenerUltimoIdPedido() {
        int idPedido = -1;

        ConexionBD.openConnection();

        try {
            String sql = "SELECT MAX(id) FROM Pedidos";
            ResultSet resultSet = ConexionBD.query(sql);

            if (resultSet.next()) {
                idPedido = resultSet.getInt(1);
                System.out.println("Último ID de pedido obtenido: " + idPedido);
            }

            ConexionBD.commit();
        } catch (SQLException e) {
            System.out.println("Error al obtener el último ID de pedido: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }

        return idPedido;
    }
}
