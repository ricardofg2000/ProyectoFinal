package controller;

import bdm.ConexionBD;
import model.CarritoDeCompra;
import model.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetallePedidoController {
	public static void agregarDetallePedido(int idPedido, CarritoDeCompra carrito) {
		ConexionBD.openConnection();

		try {
			for (Producto producto : carrito.getProductos()) {
				String sql = "SELECT * FROM DetallesPedido WHERE pedido_id = ? AND producto_id = ?";
				PreparedStatement selectStatement = ConexionBD.prepareStatement(sql);
				selectStatement.setInt(1, idPedido);
				selectStatement.setInt(2, producto.getId());

				ResultSet resultSet = selectStatement.executeQuery();

				if (resultSet.next()) {
					int cantidadActual = resultSet.getInt("cantidad");
					int nuevaCantidad = cantidadActual + producto.getCantidad();

					// Actualizar la cantidad del producto en el detalle del pedido
					String updateSql = "UPDATE DetallesPedido SET cantidad = ? WHERE pedido_id = ? AND producto_id = ?";
					PreparedStatement updateStatement = ConexionBD.prepareStatement(updateSql);
					updateStatement.setInt(1, nuevaCantidad);
					updateStatement.setInt(2, idPedido);
					updateStatement.setInt(3, producto.getId());

					updateStatement.executeUpdate();
				} else {
					String insertSql = "INSERT INTO DetallesPedido (pedido_id, producto_id, cantidad) VALUES (?, ?, ?)";
					PreparedStatement insertStatement = ConexionBD.prepareStatement(insertSql);
					insertStatement.setInt(1, idPedido);
					insertStatement.setInt(2, producto.getId());
					insertStatement.setInt(3, carrito.getCantidad(producto));

					insertStatement.executeUpdate();
				}
			}
			ConexionBD.commit();
			System.out.println("Detalle de pedido agregado correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al agregar el detalle de pedido: " + e.getMessage());
			ConexionBD.rollback();
		} finally {
			ConexionBD.closeConnection();
		}
	}
}
