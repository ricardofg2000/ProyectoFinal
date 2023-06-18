package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteVentana extends JFrame {

    private JPanel contentPane;
    private JTextField campoTextoUsuario;
    private JPasswordField campoTextoContrasena;
    private JButton btnIniciarSesion;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClienteVentana frame = new ClienteVentana();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ClienteVentana() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(443, 325);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(20, 11, 394, 241);
        contentPane.add(panel);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(53, 69, 96, 30);
        panel.add(lblUsuario);

        campoTextoUsuario = new JTextField();
        campoTextoUsuario.setBounds(139, 69, 187, 30);
        campoTextoUsuario.setColumns(20);
        panel.add(campoTextoUsuario);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(53, 110, 96, 30);
        panel.add(lblContrasena);

        campoTextoContrasena = new JPasswordField();
        campoTextoContrasena.setBounds(139, 110, 187, 30);
        campoTextoContrasena.setColumns(20);
        panel.add(campoTextoContrasena);

        JPanel panelBoton = new JPanel();
        panelBoton.setBounds(-19, 166, 428, 64);
        panelBoton.setLayout(null);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBounds(154, 7, 125, 23);
        panelBoton.add(btnIniciarSesion);
        panel.add(panelBoton);

        JButton btnNuevoUsuario = new JButton("¿Nuevo? Crea una cuenta");
        btnNuevoUsuario.setBounds(115, 41, 198, 23);
        panelBoton.add(btnNuevoUsuario);

        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitulo.setBounds(-19, 11, 428, 47);
        panel.add(lblTitulo);

        btnIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = campoTextoUsuario.getText();
                String contrasena = new String(campoTextoContrasena.getPassword());

                if (ClienteController.validarCredenciales(usuario, contrasena)) {
                    int idCliente = ClienteController.obtenerIdCliente(usuario);
                    mostrarVentanaProductos(idCliente);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Credenciales inválidas. Intente nuevamente.");
                }
            }
        });

        btnNuevoUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CrearClienteVentana crearClienteVentana = new CrearClienteVentana();
                crearClienteVentana.setVisible(true);
            }
        });
    }

    private void mostrarVentanaProductos(int idCliente) {
        dispose();

        ProductosVentanaMenu productosVentana = new ProductosVentanaMenu(idCliente);
        productosVentana.setVisible(true);
    }
}
