package view.gestClientes;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Cliente;
import start.Log;

public class JDialogFormClientes extends JDialog {

    private JTextField textFieldNombre;
    private JTextField textFieldUsuario;
    private JTextField textFieldContrasena;
    private JTextField textFieldTelefono;
    private JTextField textFieldDireccion;

    private Cliente cliente;
    private boolean camposCompletos; // Variable para verificar si los campos obligatorios están completos

    public JDialogFormClientes(Cliente cliente) {
        setResizable(false);
        this.cliente = cliente;
        setTitle("Formulario de Cliente");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 460, 249);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 440, 188);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre*:");
        lblNombre.setBounds(10, 10, 100, 20);
        panel.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(120, 10, 280, 20);
        panel.add(textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblUsuario = new JLabel("Usuario*:");
        lblUsuario.setBounds(10, 40, 100, 20);
        panel.add(lblUsuario);

        textFieldUsuario = new JTextField();
        textFieldUsuario.setBounds(120, 40, 280, 20);
        panel.add(textFieldUsuario);
        textFieldUsuario.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña*:");
        lblContrasena.setBounds(10, 70, 100, 20);
        panel.add(lblContrasena);

        textFieldContrasena = new JTextField();
        textFieldContrasena.setBounds(120, 70, 280, 20);
        panel.add(textFieldContrasena);
        textFieldContrasena.setColumns(10);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(10, 100, 100, 20);
        panel.add(lblTelefono);

        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(120, 100, 280, 20);
        panel.add(textFieldTelefono);
        textFieldTelefono.setColumns(10);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(10, 130, 100, 20);
        panel.add(lblDireccion);

        textFieldDireccion = new JTextField();
        textFieldDireccion.setBounds(120, 130, 280, 20);
        panel.add(textFieldDireccion);
        textFieldDireccion.setColumns(10);

        JButton okButton = new JButton("OK");
        okButton.setBounds(206, 161, 89, 23);
        panel.add(okButton);
        okButton.addActionListener(e -> {
            if (validarCampos()) { 
                actualizarCliente();
                Log log = new Log("Cliente actualizado exitosamente.");
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(305, 161, 89, 23);
        panel.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        
        
        textFieldNombre.setText(cliente.getNombre());
        textFieldUsuario.setText(cliente.getUsuario());
        textFieldContrasena.setText(cliente.getContrasena());
        textFieldTelefono.setText(cliente.getTelefono());
        textFieldDireccion.setText(cliente.getDireccion());
    }

    private boolean validarCampos() {
        String nombre = textFieldNombre.getText().trim();
        String usuario = textFieldUsuario.getText().trim();
        String contrasena = textFieldContrasena.getText().trim();

        if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.",
                    "Campos obligatorios vacíos", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void actualizarCliente() {
        cliente.setNombre(textFieldNombre.getText());
        cliente.setUsuario(textFieldUsuario.getText());
        cliente.setContrasena(textFieldContrasena.getText());
        cliente.setTelefono(textFieldTelefono.getText());
        cliente.setDireccion(textFieldDireccion.getText());
    }

    public Cliente showDialog() {
        setVisible(true);
        return cliente;
    }
}
