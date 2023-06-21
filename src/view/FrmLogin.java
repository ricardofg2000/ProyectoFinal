package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.ClientesController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmLogin extends JFrame {

	private JPanel contentPane;
	private JTextField campoTextoUsuario;
	private JPasswordField campoTextoContrasena;
	private JButton btnIniciarSesion;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { FrmLogin frame = new FrmLogin();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	public FrmLogin() {
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
				System.out.println(ClientesController.validarCredenciales(usuario, contrasena));
				switch (ClientesController.validarCredenciales(usuario, contrasena)) {
				case 0:
					JOptionPane.showMessageDialog(contentPane, "Credenciales inválidas. Intente nuevamente.");
					break;
				case 1:
					mostrarVentanaAdmin();
					break;
				case 2:
					mostrarVentanaCliente();
					break;
				}
			}
		});

		btnNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearClienteVentana crearClienteVentana = new CrearClienteVentana();
				crearClienteVentana.setVisible(true);
			}
		});
		setVisible(true);
	}

	private void mostrarVentanaCliente() {
		dispose();

		FrmCliente nuevaVentana = new FrmCliente();
		nuevaVentana.setVisible(true);
	}

	private void mostrarVentanaAdmin() {
		dispose();

		FrmAdministrador nuevaVentana = new FrmAdministrador();
		nuevaVentana.setVisible(true);
	}
}