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

public class VistaJFrame extends JFrame {

    private JTable tableProductos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaJFrame frame = new VistaJFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VistaJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 767, 541);
        JPanel contentPane = new JPanel();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelMenu = new JPanel();
        panelMenu.setBounds(5, 5, 741, 286);
        contentPane.add(panelMenu);
        panelMenu.setLayout(null);

        JLabel lblTitulo = new JLabel("Menú de Productos");
        lblTitulo.setBounds(0, 0, 741, 49);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        panelMenu.add(lblTitulo);

        JButton btnListarProductos = new JButton("Listado de Productos");
        btnListarProductos.setBounds(41, 60, 666, 38);
        btnListarProductos.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelMenu.add(btnListarProductos);

        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setBounds(41, 109, 666, 38);
        btnAgregarProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelMenu.add(btnAgregarProducto);

        JButton btnEliminarProducto = new JButton("Eliminar Producto");
        btnEliminarProducto.setBounds(41, 158, 666, 38);
        btnEliminarProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelMenu.add(btnEliminarProducto);

        JButton btnActualizarProducto = new JButton("Actualizar Producto");
        btnActualizarProducto.setBounds(41, 207, 666, 38);
        btnActualizarProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelMenu.add(btnActualizarProducto);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(5, 291, 741, 206);
        contentPane.add(scrollPane);

        tableProductos = new JTable();
        scrollPane.setViewportView(tableProductos);

        btnListarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        btnAgregarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnActualizarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    public void listarProductos() {
        List<Producto> productos = ProductosController.listadoProductos();

        // Definir las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Precio", "Descripción", "Código de Barras"};

        // Crear el modelo de la tabla con las columnas
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);

        // Agregar los productos al modelo de la tabla
        for (Producto producto : productos) {
            Object[] fila = {
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getCodigoBarras()
            };
            tableModel.addRow(fila);
        }

        // Establecer el modelo en la tabla
        tableProductos.setModel(tableModel);
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
