package view.gestCompras;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Producto;
import view.FrmCliente;
import view.PanBienvenida;
import model.CarritoDeCompra;
import controller.CarritoDeCompraController;
import controller.ClientesController;
import controller.JsonController;
import controller.PedidosController;
import java.util.ArrayList;
import java.util.HashMap;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 * La clase JDialogCarrito representa un cuadro de diálogo que muestra el carrito de compras de un usuario.
 */
public class JDialogCarrito extends JDialog {

    private JTable tablaCarrito;
    private JLabel lblTotal;
    private JTextField textField;

    /**
     * Crea un nuevo JDialogCarrito.
     *
     * @param usuario el nombre de usuario del cliente
     */
    public JDialogCarrito(String usuario) {
        setResizable(false);
        setTitle("Carrito de Compras");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 338);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("images/tienda.png").getImage());

        JsonController gest = new JsonController();
        final String FILE_NAME = "recibo.json";

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);

        tablaCarrito = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        scrollPane.setBounds(10, 5, 514, 258);
        contentPane.add(scrollPane);

        String[] columnas = {"Nombre", "Precio", "Cantidad", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        List<Producto> productos = CarritoDeCompraController.obtenerProductosCarrito(usuario);
        int totalProductos = 0;
        double total = 0.0;

        for (Producto producto : productos) {
            String cantidadString = CarritoDeCompraController.obtenerCantidadProducto(usuario, producto.getId());
            int cantidad = Integer.parseInt(cantidadString);
            double subTotal = producto.getPrecio() * cantidad;
            totalProductos += cantidad;
            total += subTotal;
            Object[] fila = {producto.getNombre(), producto.getPrecio(), cantidad, subTotal};
            modelo.addRow(fila);
        }

        tablaCarrito.setModel(modelo);

        JPanel panelBoton = new JPanel();
        panelBoton.setBounds(10, 263, 514, 33);
        panelBoton.setLayout(null);

        JLabel lblCantidadTotal = new JLabel("Cantidad de artículos: " + totalProductos);
        lblCantidadTotal.setBounds(0, 7, 168, 19);
        panelBoton.add(lblCantidadTotal);
        lblCantidadTotal.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblTotal_1 = new JLabel("Total: $" + new DecimalFormat("#.00").format(total));
        lblTotal_1.setBounds(205, 7, 120, 19);
        lblTotal_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 12));
        panelBoton.add(lblTotal_1);
        contentPane.add(panelBoton);

        JButton btnTramitarPedido = new JButton("Tramitar Pedido");
        btnTramitarPedido.setBounds(384, 5, 120, 23);
        panelBoton.add(btnTramitarPedido);
        btnTramitarPedido.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnTramitarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                tramitarPedido(usuario, gest);
            }
        });
        setContentPane(contentPane);
    }

    /**
     * Realiza el trámite del pedido.
     *
     * @param usuario el nombre de usuario del cliente
     * @param gest el controlador JSON
     */
    private void tramitarPedido(String usuario, JsonController gest) {
        int idCliente = ClientesController.obtenerIdCliente(usuario);
        PedidosController.agregarPedido(idCliente);
        guardarRecibo(usuario, gest);
    }

    /**
     * Guarda el recibo del pedido en un archivo JSON y posterioriormente borra el carrito de ese usuario.
     *
     * @param usuario el nombre de usuario del cliente
     * @param gest el controlador JSON
     */
    private void guardarRecibo(String usuario, JsonController gest) {
        int saveOption = JOptionPane.showConfirmDialog(null, "¿Desea guardar el recibo?", "Guardar Recibo",
                JOptionPane.YES_NO_OPTION);
        if (saveOption == JOptionPane.YES_OPTION) {
            List<Producto> productos = CarritoDeCompraController.obtenerProductosCarrito(usuario);
            List<Map<String, Object>> productosConCantidad = new ArrayList<>();
            for (Producto producto : productos) {
                String cantidadString = CarritoDeCompraController.obtenerCantidadProducto(usuario, producto.getId());
                int cantidad = Integer.parseInt(cantidadString);
                Map<String, Object> productoConCantidad = new HashMap<>();
                productoConCantidad.put("producto", producto);
                productoConCantidad.put("cantidad", cantidad);
                productosConCantidad.add(productoConCantidad);
            }

            JFileChooser jfc = new JFileChooser();

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                System.out.println("You selected the directory: " + jfc.getSelectedFile().toString() + ".json");

                gest.writeJsonInFile(jfc.getSelectedFile().toString() + ".json", productosConCantidad);
                JOptionPane.showMessageDialog(null, "Recibo guardado correctamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            CarritoDeCompraController.borrarCarrito(usuario);
        } else {
            CarritoDeCompraController.borrarCarrito(usuario);
        }
    }
}
