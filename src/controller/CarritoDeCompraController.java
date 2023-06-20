package controller;

import bdm.ConexionBD;
import model.CarritoDeCompra;
import model.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CarritoDeCompraController {
	public static void agregarProductoAlCarrito(int idCarrito, int idProducto, int cantidad) {
        ConexionBD.openConnection();

        try {
            String selectSql = "SELECT * FROM carritodecompra WHERE id = ? AND producto_id = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(selectSql);
            selectStatement.setInt(1, idCarrito);
            selectStatement.setInt(2, idProducto);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int nuevaCantidad = cantidad;

                String updateSql = "UPDATE carritodecompra SET cantidad = ? WHERE id = ? AND producto_id = ?";
                PreparedStatement updateStatement = ConexionBD.prepareStatement(updateSql);
                updateStatement.setInt(1, nuevaCantidad);
                updateStatement.setInt(2, idCarrito);
                updateStatement.setInt(3, idProducto);

                updateStatement.executeUpdate();

                System.out.println("Cantidad actualizada en el carrito correctamente.");
            } else {
                String insertSql = "INSERT INTO carritodecompra (id, producto_id, cantidad) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = ConexionBD.prepareStatement(insertSql);
                insertStatement.setInt(1, idCarrito);
                insertStatement.setInt(2, idProducto);
                insertStatement.setInt(3, cantidad);

                insertStatement.executeUpdate();

                System.out.println("Producto agregado al carrito correctamente.");
            }

            ConexionBD.commit();
        } catch (SQLException e) {
            System.out.println("Error al agregar el producto al carrito: " + e.getMessage());
            ConexionBD.rollback();
        } finally {
            ConexionBD.closeConnection();
        }
    }
	
	public static int generarIdCarrito() {
        ConexionBD.openConnection();

        int idCarrito = 0;

        try {
            String selectSql = "SELECT MAX(id) AS max_id FROM carritodecompra";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(selectSql);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                idCarrito = resultSet.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al generar el ID del carrito: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return idCarrito;
    }
	
	public static String obtenerCantidadProducto(int idCarrito, int idProducto) {
        ConexionBD.openConnection();

        String cantidad = "";

        try {
            String selectSql = "SELECT cantidad FROM carritodecompra WHERE id = ? AND producto_id = ?";
            PreparedStatement selectStatement = ConexionBD.prepareStatement(selectSql);
            selectStatement.setInt(1, idCarrito);
            selectStatement.setInt(2, idProducto);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                cantidad = resultSet.getString("cantidad");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la cantidad del producto en el carrito: " + e.getMessage());
        } finally {
            ConexionBD.closeConnection();
        }

        return cantidad;
    }
	
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
	        System.out.println("Error al obtener el producto: " + e.getMessage());
	    } finally {
	        ConexionBD.closeConnection();
	    }

	    return producto;
	}

	
	public static List<Producto> obtenerProductosCarrito(int idCarrito) {
	    ConexionBD.openConnection();
	    List<Producto> productos = new ArrayList<>();

	    try {
	        String sql = "SELECT producto_id FROM carritodecompra WHERE id = ?";
	        PreparedStatement selectStatement = ConexionBD.prepareStatement(sql);
	        selectStatement.setInt(1, idCarrito);

	        ResultSet resultSet = selectStatement.executeQuery();

	        while (resultSet.next()) {
	            int productoId = resultSet.getInt("producto_id");
	            Producto producto = obtenerProducto(productoId); // 
	            productos.add(producto);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al obtener los productos del carrito: " + e.getMessage());
	    } finally {
	        ConexionBD.closeConnection();
	    }

	    return productos;
	}
	
}
