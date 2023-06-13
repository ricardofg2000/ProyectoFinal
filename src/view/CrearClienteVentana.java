package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearClienteVentana extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldNombre;
    private JTextField textFieldUsuario;
    private JPasswordField passwordField;
    private JTextField textFieldTelefono;
    private JTextField textFieldDireccion;

    public CrearClienteVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Crear Nuevo Cliente");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(6, 2, 0, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        panel.add(lblNombre);

        textFieldNombre = new JTextField();
        panel.add(textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblUsuario = new JLabel("Usuario:");
        panel.add(lblUsuario);

        textFieldUsuario = new JTextField();
        panel.add(textFieldUsuario);
        textFieldUsuario.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña:");
        panel.add(lblContrasena);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel lblTelefono = new JLabel("Teléfono:");
        panel.add(lblTelefono);

        textFieldTelefono = new JTextField();
        panel.add(textFieldTelefono);
        textFieldTelefono.setColumns(10);

        JLabel lblDireccion = new JLabel("Dirección:");
        panel.add(lblDireccion);

        textFieldDireccion = new JTextField();
        panel.add(textFieldDireccion);
        textFieldDireccion.setColumns(10);

        JButton btnCrear = new JButton("Crear");
        panel.add(btnCrear);

        JButton btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldNombre.getText();
                String usuario = textFieldUsuario.getText();
                String contrasena = new String(passwordField.getPassword());
                String telefono = textFieldTelefono.getText();
                String direccion = textFieldDireccion.getText();

                if (ClienteController.existeUsuario(usuario)) {
                    JOptionPane.showMessageDialog(contentPane, "El usuario ya existe. Por favor, elija otro nombre de usuario.");
                    textFieldUsuario.requestFocus();
                    textFieldUsuario.selectAll();
                } else {
                    ClienteController.crearCliente(nombre, usuario, contrasena, telefono, direccion);
                    JOptionPane.showMessageDialog(contentPane, "Cliente creado exitosamente.");
                    dispose();
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
