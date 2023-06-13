package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controller.ProductosController;
import model.Producto;

import java.util.List;
import java.util.Scanner;

public class VistaProductos {
    private static Scanner scanner = new Scanner(System.in);

    public static void listadoProductos() {
        System.out.println("Listado de Productos:");
        List<Producto> productos = ProductosController.listadoProductos();
        productos.forEach(System.out::println);
    }

    public static void agregarProducto() {
        System.out.println("Agregar Producto:");
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese la descripción del producto: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el código de barras del producto: ");
        String codigoBarras = scanner.nextLine();
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setCodigoBarras(codigoBarras);

        ProductosController.agregarProducto(nuevoProducto);
        System.out.println("Producto agregado correctamente.");
    }

    public static void eliminarProducto() {
        System.out.println("Eliminar Producto:");
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int idEliminar = scanner.nextInt();
        ProductosController.borrarProducto(idEliminar);
        System.out.println("Producto eliminado correctamente.");
    }

    public static void actualizarProducto() {
        System.out.println("Actualizar Producto:");
        System.out.print("Ingrese el ID del producto a actualizar: ");
        int idActualizar = scanner.nextInt();
        
        scanner.nextLine();
        
        System.out.print("Ingrese el nuevo nombre del producto (dejar vacío para mantener el valor actual): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio del producto (dejar vacío para mantener el valor actual): ");
        String nuevoPrecioStr = scanner.nextLine();
        double nuevoPrecio = nuevoPrecioStr.isEmpty() ? -1 : Double.parseDouble(nuevoPrecioStr);
        System.out.print("Ingrese la nueva descripción del producto (dejar vacío para mantener el valor actual): ");
        String nuevaDescripcion = scanner.nextLine();
        System.out.print("Ingrese el nuevo código de barras del producto (dejar vacío para mantener el valor actual): ");
        String nuevoCodigoBarras = scanner.nextLine();
        Producto productoActualizado = new Producto(idActualizar, nuevoNombre, nuevoPrecio, nuevaDescripcion, nuevoCodigoBarras);
        ProductosController.actualizarProducto(productoActualizado);
        System.out.println("Producto actualizado correctamente.");
    }

    

}

