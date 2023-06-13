package model;

import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompra {
    private Cliente cliente;
    private List<Producto> productos;
    private List<Integer> cantidades;

    public CarritoDeCompra(Cliente cliente) {
        this.cliente = cliente;
        productos = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(producto);
        cantidades.add(cantidad);
    }

    public void eliminarProducto(Producto producto) {
        int index = productos.indexOf(producto);
        if (index != -1) {
            productos.remove(index);
            cantidades.remove(index);
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            total += producto.getPrecio() * cantidad;
        }
        return total;
    }

}
