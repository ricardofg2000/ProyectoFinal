package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Producto;
import start.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bdm.ConexionBD;

public class ProductosController {

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
			Log log = new Log(Log.Tipo.ERROR,
					"Error al obtener el producto con id " + id + " en base de datos: " + e.getMessage());
		} finally {
			ConexionBD.closeConnection();
		}
		return producto;
	}

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
