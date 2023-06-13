package model;

import java.util.HashMap;
import java.util.Map;

public class CarritoDeCompra {
	private Map<Producto, Integer> productos;

	public CarritoDeCompra() {
		productos = new HashMap<>();
	}

	public void agregarProducto(Producto producto, int cantidad) {
		int cantidadExistente = productos.getOrDefault(producto, 0);
		int cantidadTotal = cantidadExistente + cantidad;
		productos.put(producto, cantidadTotal);

		System.out.println("Producto agregado: " + producto.getNombre() + ", Cantidad: " + cantidadTotal);
	}

	public void removerProducto(Producto producto, int cantidad) {
		int cantidadExistente = productos.getOrDefault(producto, 0);
		if (cantidadExistente > cantidad) {
			productos.put(producto, cantidadExistente - cantidad);
		} else {
			productos.remove(producto);
		}
	}

	public Map<Producto, Integer> getProductos() {
		return productos;
	}

	public int getCantidadProductos() {
		int cantidadTotal = 0;
		for (int cantidad : productos.values()) {
			cantidadTotal += cantidad;
		}
		return cantidadTotal;
	}

	public double getTotal() {
		double total = 0;
		for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
			Producto producto = entry.getKey();
			int cantidad = entry.getValue();
			total += producto.getPrecio() * cantidad;
		}
		return total;
	}
}
