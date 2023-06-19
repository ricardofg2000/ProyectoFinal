package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.DetallePedidoController;
import controller.PedidosController;
import controller.ProductosController;
import model.CarritoDeCompra;
import model.Producto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class FrmCliente extends JFrame {

    private JPanel contentPane;
    private JTable tableProductos;
    private CarritoDeCompra carritoDeCompra;
    private int idCliente;

    public FrmCliente(int id_cliente) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 568);
        setLocationRelativeTo(null);
        setTitle("Lista de Productos");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblTitulo = new JLabel("Lista de Productos");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        tableProductos = new JTable();
        scrollPane.setViewportView(tableProductos);

        carritoDeCompra = new CarritoDeCompra();
        this.idCliente = id_cliente;

        cargarProductos();
        configurarMenus(id_cliente);
        configurarBotonAgregarCarrito();
    }

    private void cargarProductos() {
        List<Producto> productos = ProductosController.listadoProductos();

        String[] columnas = {"ID", "Nombre", "Precio", "Descripción", "Código de Barras", "Cantidad"};

        DefaultTableModel tabla = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        for (Producto producto : productos) {
            Object[] fila = {producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(),
                    producto.getCodigoBarras(), 0};
            tabla.addRow(fila);
        }

        tableProductos.setModel(tabla);
    }

    private void configurarMenus(int id_cliente) {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnCarrito = new JMenu("Carrito");
        menuBar.add(mnCarrito);

        JMenuItem mntmVerCarrito = new JMenuItem("Ver Carrito");
        mnCarrito.add(mntmVerCarrito);

        JMenu mnPedido = new JMenu("Pedido");
        menuBar.add(mnPedido);

        JMenuItem mntmHacerPedido = new JMenuItem("Hacer Pedido");
        mnPedido.add(mntmHacerPedido);

        mntmVerCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCarrito();
            }
        });

        mntmHacerPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hacerPedido(id_cliente);
            }
        });
    }

    private void configurarBotonAgregarCarrito() {
        JButton btnAgregarCarrito = new JButton("Agregar al Carrito");
        contentPane.add(btnAgregarCarrito, BorderLayout.SOUTH);

        btnAgregarCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tabla = (DefaultTableModel) tableProductos.getModel();
                int rowCount = tabla.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    int cantidad = (int) tabla.getValueAt(i, 5);
                    if (cantidad > 0) {
                        int id = (int) tabla.getValueAt(i, 0);
                        String nombre = (String) tabla.getValueAt(i, 1);
                        double precio = (double) tabla.getValueAt(i, 2);
                        String descripcion = (String) tabla.getValueAt(i, 3);
                        String codigoBarras = (String) tabla.getValueAt(i, 4);

                        Producto producto = new Producto(id, nombre, precio, descripcion, codigoBarras);
                        carritoDeCompra.agregarProducto(producto, cantidad);
                    }
                }

                JOptionPane.showMessageDialog(FrmCliente.this, "Productos agregados al carrito de compra.");
            }
        });
    }

    private void verCarrito() {
        JFrame ventanaCarrito = new JFrame();
        ventanaCarrito.setTitle("Carrito de Compra");
        ventanaCarrito.setSize(400, 300);
        ventanaCarrito.setLocationRelativeTo(this);
        ventanaCarrito.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelCarrito = new JPanel(new BorderLayout());
        ventanaCarrito.setContentPane(panelCarrito);

        JTable tablaCarrito = new JTable();
        DefaultTableModel tabla = new DefaultTableModel(
                new String[]{"Nombre", "Cantidad"}, 0);

        List<Producto> productosEnCarrito = carritoDeCompra.getProductos();
        for (Producto producto : productosEnCarrito) {
            int cantidad = carritoDeCompra.getCantidad(producto);
            Object[] fila = {producto.getNombre(), cantidad};
            tabla.addRow(fila);
        }



        tablaCarrito.setModel(tabla);

        JScrollPane scrollPaneCarrito = new JScrollPane(tablaCarrito);
        panelCarrito.add(scrollPaneCarrito, BorderLayout.CENTER);

        JLabel lblCantidadProductos = new JLabel("Cantidad de Productos: " + carritoDeCompra.getCantidadTotal());
        panelCarrito.add(lblCantidadProductos, BorderLayout.SOUTH);

        ventanaCarrito.setVisible(true);
    }

    private void hacerPedido(int id_cliente) {
        JFrame ventanaPedido = new JFrame();
        ventanaPedido.setTitle("Pedido");
        ventanaPedido.setSize(400, 300);
        ventanaPedido.setLocationRelativeTo(this);
        ventanaPedido.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPedido = new JPanel(new BorderLayout());
        ventanaPedido.setContentPane(panelPedido);

        JTable tablePedido = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Producto", "Cantidad"}, 0);

        List<Producto> productosEnCarrito = carritoDeCompra.getProductos();
        for (Producto producto : productosEnCarrito) {
            int cantidad = carritoDeCompra.getCantidad(producto);
            Object[] fila = {producto.getNombre(), cantidad};
            tableModel.addRow(fila);
        }


        tablePedido.setModel(tableModel);

        JScrollPane scrollPanePedido = new JScrollPane(tablePedido);
        panelPedido.add(scrollPanePedido, BorderLayout.CENTER);

        JLabel lblPrecioTotal = new JLabel("Precio Total: " + carritoDeCompra.getTotal() + "€");
        panelPedido.add(lblPrecioTotal, BorderLayout.SOUTH);

        JButton btnTramitarPedido = new JButton("Tramitar Pedido");
        btnTramitarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(FrmCliente.this,
                        "¿Desea tramitar el pedido?",
                        "Confirmación de pedido", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    int idPedido = PedidosController.agregarPedido(id_cliente);
                    if (idPedido != -1) {
                        List<Producto> productosEnCarrito = carritoDeCompra.getProductos();
                        for (Producto producto : productosEnCarrito) {
                            int cantidad = carritoDeCompra.getCantidad(producto);
                            DetallePedidoController.agregarDetallePedido(idPedido, carritoDeCompra);
                        }
                        JOptionPane.showMessageDialog(FrmCliente.this, "Pedido tramitado con éxito.");
                        ventanaPedido.dispose();
                    } else {
                        JOptionPane.showMessageDialog(FrmCliente.this, "Error al agregar el pedido. Intente nuevamente.");
                    }
                }
            }
        });


        panelPedido.add(btnTramitarPedido, BorderLayout.NORTH);

        ventanaPedido.setVisible(true);
    }
}
