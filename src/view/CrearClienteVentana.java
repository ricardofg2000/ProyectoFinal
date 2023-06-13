package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearClienteVentana extends JFrame {

    private JPanel contentPane;
    private JTextField campoTextoNombre;
    private JTextField campoTextoUsuario;
    private JPasswordField campoContrasena;
    private JTextField campoTextoTelefono;
    private JTextField campoTextoDireccion;

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

        campoTextoNombre = new JTextField();
        panel.add(campoTextoNombre);
        campoTextoNombre.setColumns(10);

        JLabel lblUsuario = new JLabel("Usuario:");
        panel.add(lblUsuario);

        campoTextoUsuario = new JTextField();
        panel.add(campoTextoUsuario);
        campoTextoUsuario.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña:");
        panel.add(lblContrasena);

        campoContrasena = new JPasswordField();
        panel.add(campoContrasena);

        JLabel lblTelefono = new JLabel("Teléfono:");
        panel.add(lblTelefono);

        campoTextoTelefono = new JTextField();
        panel.add(campoTextoTelefono);
        campoTextoTelefono.setColumns(10);

        JLabel lblDireccion = new JLabel("Dirección:");
        panel.add(lblDireccion);

        campoTextoDireccion = new JTextField();
        panel.add(campoTextoDireccion);
        campoTextoDireccion.setColumns(10);

        JButton btnCrear = new JButton("Crear");
        panel.add(btnCrear);

        JButton btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoTextoNombre.getText();
                String usuario = campoTextoUsuario.getText();
                String contrasena = new String(campoContrasena.getPassword());
                String telefono = campoTextoTelefono.getText();
                String direccion = campoTextoDireccion.getText();

                if (ClienteController.existeUsuario(usuario)) {
                    JOptionPane.showMessageDialog(contentPane, "El usuario ya existe. Por favor, elija otro nombre de usuario.");
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
