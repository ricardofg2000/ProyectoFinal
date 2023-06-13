package model;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<Producto, Integer> inventario;

    public Inventario() {
        inventario = new HashMap<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        inventario.put(producto, cantidad);
    }

    public void eliminarProducto(Producto producto) {
        inventario.remove(producto);
    }

    public void actualizarCantidad(Producto producto, int cantidad) {
        inventario.put(producto, cantidad);
    }

	public Map<Producto, Integer> getInventario() {
		return inventario;
	}

	public void setInventario(Map<Producto, Integer> inventario) {
		this.inventario = inventario;
	}

    
}
