package view.gestProductos;

import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.JsonController;
import controller.ProductosController;
import model.Producto;
import start.Log;

public class PanProductos extends JPanel {

	private JTable tableProductos;

	public PanProductos() {
		setLayout(new BorderLayout());

		tableProductos = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane scrollPane = new JScrollPane(tableProductos);
		add(scrollPane, BorderLayout.CENTER);

		List<Producto> productos = ProductosController.listadoProductos();

		String[] columnas = { "ID", "Nombre", "Precio", "Descripción", "Código de Barras" };
		DefaultTableModel tabla = new DefaultTableModel(columnas, 0);

		for (Producto producto : productos) {
			Object[] fila = { producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(),
					producto.getCodigoBarras() };
			tabla.addRow(fila);
		}

		tableProductos.setModel(tabla);
		tableProductos.getColumnModel().getColumn(0).setMaxWidth(0);
		tableProductos.getColumnModel().getColumn(0).setMinWidth(0);
		tableProductos.getColumnModel().getColumn(0).setPreferredWidth(0);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		tableProductos.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tableProductos.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		JButton btnAgregar = new JButton("Agregar Producto");
		JButton btnEliminar = new JButton("Eliminar Producto");
		JButton btnActualizar = new JButton("Actualizar Producto");
		JButton btnImportar = new JButton("Importar Productos");

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
		panelBotones.add(btnImportar);
		add(panelBotones, BorderLayout.SOUTH);

		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarProducto();
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarProducto();
			}
		});

		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importarProductos();
			}
		});

	}

	private void agregarProducto() {
		Producto nuevoProducto = new Producto();

		JDialogFormProductos dialog = new JDialogFormProductos(nuevoProducto);
		Producto productoNuevo = dialog.showDialog();

		if (productoNuevo.getNombre() != null) {
			ProductosController.agregarProducto(productoNuevo);
			actualizarPan();
		}
	}

	public void actualizarPan() {
		List<Producto> productos = ProductosController.listadoProductos();

		DefaultTableModel modelo = (DefaultTableModel) tableProductos.getModel();
		modelo.setRowCount(0);

		for (Producto producto : productos) {
			Object[] fila = { producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(),
					producto.getCodigoBarras() };
			modelo.addRow(fila);
		}
	}

	private void eliminarProducto() {
		int filaSeleccionada = tableProductos.getSelectedRow();
		if (filaSeleccionada >= 0) {
			int id = (int) tableProductos.getValueAt(filaSeleccionada, 0);
			String nombre = (String) tableProductos.getValueAt(filaSeleccionada, 1);
			double precio = (double) tableProductos.getValueAt(filaSeleccionada, 2);
			String descripcion = (String) tableProductos.getValueAt(filaSeleccionada, 3);
			String codigoBarras = (String) tableProductos.getValueAt(filaSeleccionada, 4);

			Log log = new Log("\nProducto que se va a eliminar:" + "\nID: " + id + "\nNombre: " + nombre + "\nPrecio: "
					+ precio + "\nDescripción: " + descripcion + "\nCódigo de Barras: " + codigoBarras);

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

	private void actualizarProducto() {
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

	private void importarProductos() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccionar archivo JSON");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON", "json"));

		int seleccion = fileChooser.showOpenDialog(PanProductos.this);
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivo = fileChooser.getSelectedFile();
			String rutaArchivo = archivo.getAbsolutePath();

			JsonController gest = new JsonController();

			List<Producto> productos = gest.leerListProductos(rutaArchivo);
			if (productos != null) {
				for (Producto producto : productos) {
					importarProducto(producto);
				}
				actualizarPan();
			}
		} else {
			Log log = new Log("No se encontraron productos en el archivo.");
		}
	}

	private void importarProducto(Producto producto) {
		boolean existeProducto = ProductosController.existeProducto(producto.getNombre());

		if (existeProducto) {
			ProductosController.actualizarProducto(producto);
			Log log = new Log("Producto actualizado: " + producto.getNombre());
		} else {
			ProductosController.agregarProducto(producto);
			Log log = new Log("Producto insertado: " + producto.getNombre());
		}
	}
}
