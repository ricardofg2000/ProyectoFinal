package view.gestProductos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ProductosController;
import model.Producto;

public class PanProductos extends JPanel {

	private JTable tableProductos;

	public PanProductos() {
		setLayout(new BorderLayout());

		tableProductos = new JTable();
		JScrollPane scrollPane = new JScrollPane(tableProductos);
		add(scrollPane, BorderLayout.CENTER);

		List<Producto> productos = ProductosController.listadoProductos();

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

		JButton btnAgregar = new JButton("Agregar Producto");
		JButton btnEliminar = new JButton("Eliminar Producto");
		JButton btnActualizar = new JButton("Actualizar Producto");

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
		add(panelBotones, BorderLayout.SOUTH);


		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Producto nuevoProducto = new Producto();


				JDialogFormProductos dialog = new JDialogFormProductos(nuevoProducto);
				Producto productoNuevo = dialog.showDialog();

				if (productoNuevo.getNombre() != null) {
					ProductosController.agregarProducto(productoNuevo);
					actualizarPan();
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableProductos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int id = (int) tableProductos.getValueAt(filaSeleccionada, 0);
					String nombre = (String) tableProductos.getValueAt(filaSeleccionada, 1);
					double precio = (double) tableProductos.getValueAt(filaSeleccionada, 2);
					String descripcion = (String) tableProductos.getValueAt(filaSeleccionada, 3);
					String codigoBarras = (String) tableProductos.getValueAt(filaSeleccionada, 4);

					System.out.println("Producto que se va a eliminar:");
					System.out.println("ID: " + id);
					System.out.println("Nombre: " + nombre);
					System.out.println("Precio: " + precio);
					System.out.println("Descripción: " + descripcion);
					System.out.println("Código de Barras: " + codigoBarras);

					int confirm = JOptionPane.showConfirmDialog(PanProductos.this,
							"¿Estás seguro de eliminar el producto seleccionado?", "Confirmar Eliminación",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						ProductosController.borrarProducto(id);
						actualizarPan();
					}
				} else {
					JOptionPane.showMessageDialog(PanProductos.this, "Selecciona un producto para eliminar");
				}
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableProductos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int id = (int) tableProductos.getValueAt(filaSeleccionada, 0);
					String nombre = (String) tableProductos.getValueAt(filaSeleccionada, 1);
					double precio = (double) tableProductos.getValueAt(filaSeleccionada, 2);
					String descripcion = (String) tableProductos.getValueAt(filaSeleccionada, 3);
					String codigoBarras = (String) tableProductos.getValueAt(filaSeleccionada, 4);

					Producto productoInicial = new Producto(id, nombre, precio, descripcion, codigoBarras);

					JDialogFormProductos dialog = new JDialogFormProductos(productoInicial);
					Producto productoActualizado = dialog.showDialog();

					if (productoActualizado != null) {
						productoActualizado.setId(id);
						ProductosController.actualizarProducto(productoActualizado);

						actualizarPan();
					}
				} else {
					JOptionPane.showMessageDialog(PanProductos.this, "Selecciona un producto para actualizar");
				}
			}
		});
		
		

	}

	public void actualizarPan() {
		List<Producto> productos = ProductosController.listadoProductos();

		DefaultTableModel modelo = (DefaultTableModel) tableProductos.getModel();
		modelo.setRowCount(0);

		for (Producto producto : productos) {
			Object[] fila = { producto.getId(), producto.getNombre(), producto.getPrecio(),
					producto.getDescripcion(), producto.getCodigoBarras() };
			modelo.addRow(fila);
		}
		
	}
}
