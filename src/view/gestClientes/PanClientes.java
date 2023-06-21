package view.gestClientes;

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

import controller.ClientesController;
import model.Cliente;

public class PanClientes extends JPanel {

	private JTable tableClientes;

	public PanClientes() {
		setLayout(new BorderLayout());

		tableClientes = new JTable();
		JScrollPane scrollPane = new JScrollPane(tableClientes);
		add(scrollPane, BorderLayout.CENTER);

		List<Cliente> clientes = ClientesController.listadoClientes();

		String[] columnas = { "ID", "Nombre", "Usuario", "Contraseña", "Teléfono", "Dirección" };
		DefaultTableModel tabla = new DefaultTableModel(columnas, 0);

		for (Cliente cliente : clientes) {
			Object[] fila = { cliente.getId(), cliente.getNombre(), cliente.getUsuario(), cliente.getContrasena(),
					cliente.getTelefono(), cliente.getDireccion()};
			tabla.addRow(fila);
		}

		tableClientes.setModel(tabla);
		tableClientes.getColumnModel().getColumn(0).setMaxWidth(0);
		tableClientes.getColumnModel().getColumn(0).setMinWidth(0);
		tableClientes.getColumnModel().getColumn(0).setPreferredWidth(0);

		JButton btnAgregar = new JButton("Agregar Cliente");
		JButton btnEliminar = new JButton("Eliminar Cliente");
		JButton btnActualizar = new JButton("Actualizar Cliente");

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnActualizar);
		add(panelBotones, BorderLayout.SOUTH);

		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente nuevoCliente = new Cliente();
				JDialogFormClientes dialog = new JDialogFormClientes(nuevoCliente);
				Cliente clienteNuevo = dialog.showDialog();
				if (clienteNuevo != null) {
					System.out.println("estaOK");
					ClientesController.crearCliente(clienteNuevo);
					actualizarPan();
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableClientes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int id = (int) tableClientes.getValueAt(filaSeleccionada, 0);
					String nombre = (String) tableClientes.getValueAt(filaSeleccionada, 1);
					String usuario = (String) tableClientes.getValueAt(filaSeleccionada, 2);
					String contrasena = (String) tableClientes.getValueAt(filaSeleccionada, 3);
					String telefono = (String) tableClientes.getValueAt(filaSeleccionada, 4);
					String direccion = (String) tableClientes.getValueAt(filaSeleccionada, 5);

					System.out.println("Cliente que se va a eliminar:");
					System.out.println("ID: " + id);
					System.out.println("Nombre: " + nombre);
					System.out.println("Dirección: " + direccion);
					System.out.println("Teléfono: " + telefono);
					System.out.println("Usuario: " + usuario);
					System.out.println("Contraseña: " + contrasena);

					int confirm = JOptionPane.showConfirmDialog(PanClientes.this,
							"¿Estás seguro de eliminar el cliente seleccionado?", "Confirmar Eliminación",
							JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						ClientesController.borrarCliente(id);
						actualizarPan();
					}
				} else {
					JOptionPane.showMessageDialog(PanClientes.this, "Selecciona un cliente para eliminar");
				}
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableClientes.getSelectedRow();
				if (filaSeleccionada >= 0) {
					int id = (int) tableClientes.getValueAt(filaSeleccionada, 0);
					String nombre = (String) tableClientes.getValueAt(filaSeleccionada, 1);
					String usuario = (String) tableClientes.getValueAt(filaSeleccionada, 2);
					String contrasena = (String) tableClientes.getValueAt(filaSeleccionada, 3);
					String telefono = (String) tableClientes.getValueAt(filaSeleccionada, 4);
					String direccion = (String) tableClientes.getValueAt(filaSeleccionada, 5);

					Cliente clienteInicial = new Cliente(id, usuario, contrasena, nombre, telefono, direccion);

					JDialogFormClientes dialog = new JDialogFormClientes(clienteInicial);
					Cliente clienteActualizado = dialog.showDialog();

					if (clienteActualizado != null) {
						clienteActualizado.setId(id);
						ClientesController.actualizarCliente(clienteActualizado);
						actualizarPan();
					}
				} else {
					JOptionPane.showMessageDialog(PanClientes.this, "Selecciona un cliente para actualizar");
				}
			}
		});
	}

	public void actualizarPan() {
		List<Cliente> clientes = ClientesController.listadoClientes();

		DefaultTableModel modelo = (DefaultTableModel) tableClientes.getModel();
		modelo.setRowCount(0);

		for (Cliente cliente : clientes) {
			Object[] fila = { cliente.getId(), cliente.getNombre(), cliente.getUsuario(), cliente.getContrasena(),
					cliente.getTelefono(), cliente.getDireccion()};
			modelo.addRow(fila);
		}
	}
}
