package view.gestCompras;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CarritoDeCompraController;
import controller.ProductosController;
import model.Producto;
import model.CarritoDeCompra;

public class PanCompras extends JPanel {

    private JTable tableProductos;
    private CarritoDeCompra carrito;

    public PanCompras(String usuario) {
        setLayout(new BorderLayout());

        tableProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableProductos);
        add(scrollPane, BorderLayout.CENTER);

        List<Producto> productos = ProductosController.listadoProductos();
        int idCarrito = CarritoDeCompraController.generarIdCarrito();
        
        crearTablaProductos(productos);

        carrito = new CarritoDeCompra();

        JButton btnAgregarCarrito = new JButton("Añadir al Carrito");
        JButton btnVerCarrito = new JButton("Ver Carrito");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarCarrito);
        panelBotones.add(btnVerCarrito);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregarCarrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCarrito(usuario);
            }
        });


        btnVerCarrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialogCarrito dialogCarrito = new JDialogCarrito(usuario);
                dialogCarrito.setVisible(true);
            }
        });
    }

	private void crearTablaProductos(List<Producto> productos) {
		String[] columnas = { "ID", "Nombre", "Precio", "Descripción", "Código de Barras" };
        DefaultTableModel tabla = new DefaultTableModel(columnas, 0);

        for (Producto producto : productos) {
            Object[] fila = { producto.getId(),
                    producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), producto.getCodigoBarras() };
            tabla.addRow(fila);
        }

        tableProductos.setModel(tabla);
        tableProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tableProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tableProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
	}

	private void agregarCarrito(String usuario) {
		int filaSeleccionada = tableProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) tableProductos.getValueAt(filaSeleccionada, 0);
            String nombre = (String) tableProductos.getValueAt(filaSeleccionada, 1);
            double precio = (double) tableProductos.getValueAt(filaSeleccionada, 2);
            String descripcion = (String) tableProductos.getValueAt(filaSeleccionada, 3);
            String codigoBarras = (String) tableProductos.getValueAt(filaSeleccionada, 4);

            Producto producto = new Producto(id, nombre, precio, descripcion, codigoBarras);

            String cantidadString = JOptionPane.showInputDialog("Ingrese la cantidad:", CarritoDeCompraController.obtenerCantidadProducto(usuario, producto.getId()));
            if (cantidadString != null) {
                try {
                    int cantidad = Integer.parseInt(cantidadString);

                    CarritoDeCompraController.agregarProductoAlCarrito(usuario, producto.getId(), cantidad);

                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito correctamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
}
