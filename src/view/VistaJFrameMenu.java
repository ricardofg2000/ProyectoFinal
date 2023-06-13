package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ProductosController;
import model.Producto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaJFrameMenu extends JFrame {

    private JTable tableProductos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaJFrameMenu frame = new VistaJFrameMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VistaJFrameMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 767, 541);
        JPanel contentPane = new JPanel();
        setLocationRelativeTo(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnProductos = new JMenu("Productos");
        menuBar.add(mnProductos);

        JMenuItem mntmListarProductos = new JMenuItem("Listado de Productos");
        mnProductos.add(mntmListarProductos);

        JMenuItem mntmAgregarProducto = new JMenuItem("Agregar Producto");
        mnProductos.add(mntmAgregarProducto);

        JMenuItem mntmEliminarProducto = new JMenuItem("Eliminar Producto");
        mnProductos.add(mntmEliminarProducto);

        JMenuItem mntmActualizarProducto = new JMenuItem("Actualizar Producto");
        mnProductos.add(mntmActualizarProducto);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 5, 741, 497);
        contentPane.add(scrollPane);

        tableProductos = new JTable();
        scrollPane.setViewportView(tableProductos);

        mntmListarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        mntmAgregarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        mntmEliminarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        mntmActualizarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    public void listarProductos() {
        List<Producto> productos = ProductosController.listadoProductos();

        String[] columnas = {"ID", "Nombre", "Precio", "Descripción", "Código de Barras"};

        DefaultTableModel tabla = new DefaultTableModel(columnas, 0);

        for (Producto producto : productos) {
            Object[] fila = {
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getCodigoBarras()
            };
            tabla.addRow(fila);
        }

        tableProductos.setModel(tabla);
    }

    public void agregarProducto() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String precioStr = JOptionPane.showInputDialog("Ingrese el precio del producto:");
        double precio = Double.parseDouble(precioStr);
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del producto:");
        String codigoBarras = JOptionPane.showInputDialog("Ingrese el código de barras del producto:");

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setCodigoBarras(codigoBarras);

        ProductosController.agregarProducto(nuevoProducto);

        listarProductos();
    }

    public void eliminarProducto() {
        String idEliminarStr = JOptionPane.showInputDialog("Ingrese el ID del producto a eliminar:");
        int idEliminar = Integer.parseInt(idEliminarStr);

        ProductosController.borrarProducto(idEliminar);

        listarProductos();
    }

    public void actualizarProducto() {
        String idActualizarStr = JOptionPane.showInputDialog("Ingrese el ID del producto a actualizar:");
        int idActualizar = Integer.parseInt(idActualizarStr);

        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:");
        String nuevoPrecioStr = JOptionPane.showInputDialog("Ingrese el nuevo precio del producto:");
        double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
        String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción del producto:");
        String nuevoCodigoBarras = JOptionPane.showInputDialog("Ingrese el nuevo código de barras del producto:");

        Producto productoActualizado = new Producto(idActualizar, nuevoNombre, nuevoPrecio, nuevaDescripcion, nuevoCodigoBarras);
        ProductosController.actualizarProducto(productoActualizado);

        listarProductos();
    }
}
