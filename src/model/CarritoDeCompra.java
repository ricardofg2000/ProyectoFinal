package model;

import java.util.HashMap;
import java.util.Map;

public class CarritoDeCompra {
    private Map<Producto, Integer> productos;

    public CarritoDeCompra() {
        productos = new HashMap<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
            productos.put(producto, cantidad);
    }

    public void actualizarCantidadProducto(Producto producto, int cantidad) {
        if (productos.containsKey(producto)) {
            productos.put(producto, cantidad);
        }
    }
    
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public boolean contieneProducto(Producto producto) {
        return productos.containsKey(producto.getId());
    }

    public int getCantidadProducto(Producto producto) {
        return productos.getOrDefault(producto.getId(), 0);
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }
}
