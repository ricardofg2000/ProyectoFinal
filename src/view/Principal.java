package view;

import java.util.List;
import java.util.Scanner;

import model.Producto;
import controller.ProductosController;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("********** Menú de Productos **********");
            System.out.println("1. Listado de Productos");
            System.out.println("2. Agregar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Actualizar Producto");
            System.out.println("0. Salir");
            System.out.println("***************************************");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    VistaProductos.listadoProductos();
                    break;
                case 2:
                    VistaProductos.agregarProducto();
                    break;
                case 3:
                    VistaProductos.eliminarProducto();
                    break;
                case 4:
                    VistaProductos.actualizarProducto();
                    break;
                case 0:
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println();
        } while (opcion != 0);

        scanner.close();
    }
}

