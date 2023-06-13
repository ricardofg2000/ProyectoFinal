package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.ProductosController;
import model.Producto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaJFrame extends JFrame {

    private static JTextArea textArea;

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
    	setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 767, 541);
        JPanel contentPane = new JPanel();
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

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textArea.setRows(10);
        textArea.setColumns(10);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(5, 291, 741, 206);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane);

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

    public static void listarProductos() {
        textArea.setText("Listado de Productos:\n");
        List<Producto> productos = ProductosController.listadoProductos();
        for (Producto producto : productos) {
            textArea.append(producto.toString() + "\n");
        }
    }

    public static void agregarProducto() {
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

        textArea.setText("Producto agregado correctamente:\n" + nuevoProducto.toString());
    }

    public static void eliminarProducto() {
        String idEliminarStr = JOptionPane.showInputDialog("Ingrese el ID del producto a eliminar:");
        int idEliminar = Integer.parseInt(idEliminarStr);

        ProductosController.borrarProducto(idEliminar);

        textArea.setText("Producto eliminado correctamente:\nID: " + idEliminar);
    }

    public static void actualizarProducto() {
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
