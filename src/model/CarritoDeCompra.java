package model;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase CarritoDeCompra representa un carrito de compra que contiene productos y sus cantidades.
 */
public class CarritoDeCompra {
    
    /** 
     * Los productos y sus cantidades en el carrito.
     */
    private Map<Producto, Integer> productos;

    /**
     * Crea un nuevo carrito de compra.
     */
    public CarritoDeCompra() {
        productos = new HashMap<>();
    }

    /**
     * Agrega un producto al carrito con una determinada cantidad.
     *
     * @param producto el producto a agregar
     * @param cantidad la cantidad del producto a agregar
     */
    public void agregarProducto(Producto producto, int cantidad) {
        productos.put(producto, cantidad);
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param producto el producto a actualizar
     * @param cantidad la nueva cantidad del producto
     */
    public void actualizarCantidadProducto(Producto producto, int cantidad) {
        if (productos.containsKey(producto)) {
            productos.put(producto, cantidad);
        }
    }
    
    /**
     * Elimina un producto del carrito.
     *
     * @param producto el producto a eliminar
     */
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    /**
     * Verifica si el carrito contiene un determinado producto.
     *
     * @param producto el producto a verificar
     * @return true si el producto está en el carrito, false en caso contrario
     */
    public boolean contieneProducto(Producto producto) {
        return productos.containsKey(producto);
    }

    /**
     * Obtiene la cantidad de un producto en el carrito.
     *
     * @param producto el producto del que se desea obtener la cantidad
     * @return la cantidad del producto en el carrito, o 0 si no está presente
     */
    public int getCantidadProducto(Producto producto) {
        return productos.getOrDefault(producto, 0);
    }

    /**
     * Obtiene todos los productos y sus cantidades en el carrito.
     *
     * @return un mapa de productos y sus cantidades
     */
    public Map<Producto, Integer> getProductos() {
        return productos;
    }
}
