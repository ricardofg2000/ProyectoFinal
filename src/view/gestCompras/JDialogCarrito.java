package view.gestCompras;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import controller.PedidosController;

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

public class JDialogCarrito extends JDialog {

    private JTable tablaCarrito;
    private JLabel lblTotal;
    private JTextField textField;

    public JDialogCarrito(int idCarrito) {
        setTitle("Carrito de Compras");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 531, 373);

        JPanel contentPane = new JPanel(new BorderLayout());

        tablaCarrito = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        String[] columnas = {"Nombre", "Precio", "Cantidad", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        List<Producto> productos = CarritoDeCompraController.obtenerProductosCarrito(idCarrito);
        int totalProductos = 0;
        double total = 0.0;

        for (Producto producto : productos) {
            String cantidadString = CarritoDeCompraController.obtenerCantidadProducto(idCarrito, producto.getId());
            int cantidad = Integer.parseInt(cantidadString);
            double subTotal = producto.getPrecio() * cantidad;
            totalProductos += cantidad;
            total += subTotal;
            Object[] fila = {producto.getNombre(), producto.getPrecio(), cantidad, subTotal};
            modelo.addRow(fila);
        }

        tablaCarrito.setModel(modelo);
        

        JLabel lblCantidadTotal = new JLabel("Cantidad total: " + totalProductos);
        lblCantidadTotal.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(lblCantidadTotal, BorderLayout.NORTH);

        JButton btnTramitarPedido = new JButton("Tramitar Pedido");
        btnTramitarPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                JLabel lblUsuario = new JLabel("Usuario:");
                JTextField txtUsuario = new JTextField();
                JLabel lblContrasena = new JLabel("Contraseña:");
                JTextField txtContrasena = new JTextField();

                Object[] message = {
                    lblUsuario, txtUsuario,
                    lblContrasena, txtContrasena
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Confirmar Credenciales", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    String usuario = txtUsuario.getText();
                    String contrasena = new String(txtContrasena.getText());

                    boolean valid = ClientesController.validarCredenciales(usuario, contrasena);

                    if (valid) {
                        System.out.println("Todo correcto");
                        int idCliente = ClientesController.obtenerIdCliente(usuario);
                        PedidosController.agregarPedido(idCliente, idCarrito);
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblTotal_1 = new JLabel("Total: $" + new DecimalFormat("#.00").format(total));
        lblTotal_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 16));
        panelBoton.add(lblTotal_1);
        panelBoton.add(btnTramitarPedido);
        contentPane.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }
}
