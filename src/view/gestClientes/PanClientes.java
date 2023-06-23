package view.gestClientes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
	private DefaultTableModel tableModel;

	public PanClientes() {
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(
				new String[] { "ID", "Nombre", "Usuario", "Contraseña", "Teléfono", "Dirección", "Rol" });
		tableClientes = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(tableClientes);
		add(scrollPane, BorderLayout.CENTER);

		actualizarPan();

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
				agregarCliente();
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarCliente();
			}
		});

		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarCliente();
			}
		});
	}

	public void actualizarPan() {
		List<Cliente> clientes = ClientesController.listadoClientes();

		tableModel.setRowCount(0);

		for (Cliente cliente : clientes) {
			Object[] fila = { cliente.getId(), cliente.getNombre(), cliente.getUsuario(), cliente.getContrasena(),
					cliente.getTelefono(), cliente.getDireccion(), cliente.getRol() };
			tableModel.addRow(fila);
		}
	}

	private void agregarCliente() {
		Cliente nuevoCliente = new Cliente();
		JDialogFormClientes dialog = new JDialogFormClientes(nuevoCliente);
		Cliente clienteNuevo = dialog.showDialog();
		if (clienteNuevo.getUsuario() != null) {
			ClientesController.crearCliente(clienteNuevo);
			actualizarPan();
		}
	}

	private void borrarCliente() {
		int filaSeleccionada = tableClientes.getSelectedRow();
		if (filaSeleccionada >= 0) {
			int id = (int) tableModel.getValueAt(filaSeleccionada, 0);
			String nombre = (String) tableModel.getValueAt(filaSeleccionada, 1);
			String usuario = (String) tableModel.getValueAt(filaSeleccionada, 2);
			String contrasena = (String) tableModel.getValueAt(filaSeleccionada, 3);
			String telefono = (String) tableModel.getValueAt(filaSeleccionada, 4);
			String direccion = (String) tableModel.getValueAt(filaSeleccionada, 5);
			String rol = (String) tableModel.getValueAt(filaSeleccionada, 6);

			System.out.println("Cliente que se va a eliminar:");
			System.out.println("ID: " + id);
			System.out.println("Nombre: " + nombre);
			System.out.println("Usuario: " + usuario);
			System.out.println("Contraseña: " + contrasena);
			System.out.println("Teléfono: " + telefono);
			System.out.println("Dirección: " + direccion);
			System.out.println("Rol: " + rol);

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

	private void actualizarCliente() {
		int filaSeleccionada = tableClientes.getSelectedRow();
		if (filaSeleccionada >= 0) {
			int id = (int) tableModel.getValueAt(filaSeleccionada, 0);
			String nombre = (String) tableModel.getValueAt(filaSeleccionada, 1);
			String usuario = (String) tableModel.getValueAt(filaSeleccionada, 2);
			String contrasena = (String) tableModel.getValueAt(filaSeleccionada, 3);
			String telefono = (String) tableModel.getValueAt(filaSeleccionada, 4);
			String direccion = (String) tableModel.getValueAt(filaSeleccionada, 5);
			String rol = (String) tableModel.getValueAt(filaSeleccionada, 6);

			Cliente clienteInicial = new Cliente(id, nombre, usuario, contrasena, telefono, direccion, rol);

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
}
