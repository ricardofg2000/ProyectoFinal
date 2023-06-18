package model;

import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompra {
    private List<Producto> productos;
    private List<Integer> cantidades;

    public CarritoDeCompra() {
        productos = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        int indice = productos.indexOf(producto);
        if (indice != -1) {
            int cantidadExistente = cantidades.get(indice);
            cantidades.set(indice, cantidadExistente + cantidad);
        } else {
            productos.add(producto);
            cantidades.add(cantidad);
        }

        System.out.println("Producto agregado: " + producto.getNombre() + ", Cantidad: " + getCantidad(producto));
    }

    public void removerProducto(Producto producto, int cantidad) {
        int indice = productos.indexOf(producto);
        if (indice != -1) {
            int cantidadExistente = cantidades.get(indice);
            if (cantidadExistente > cantidad) {
                cantidades.set(indice, cantidadExistente - cantidad);
            } else {
                productos.remove(indice);
                cantidades.remove(indice);
            }
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public int getCantidad(Producto producto) {
        int indice = productos.indexOf(producto);
        if (indice != -1) {
            return cantidades.get(indice);
        }
        return 0;
    }

    public int getCantidadTotal() {
        int cantidadTotal = 0;
        for (int cantidad : cantidades) {
            cantidadTotal += cantidad;
        }
        return cantidadTotal;
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            total += producto.getPrecio() * cantidad;
        }
        return total;
    }
}
