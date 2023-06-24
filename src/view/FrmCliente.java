package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Producto;
import view.gestCompras.JDialogCarrito;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * La clase FrmCliente representa la ventana principal del cliente en la aplicación.
 */
public class FrmCliente extends JFrame {

	/** El panel de contenido. */
	private JPanel contentPane;
	
	/** La instancia de la ventana principal del cliente. */
	private static FrmCliente frame;

	/**
	 * Crea una nueva instancia de FrmCliente.
	 *
	 * @param usuario el nombre de usuario del cliente
	 */
	public FrmCliente(String usuario) {
		setTitle("Mark&GO");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 420);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("images/tienda.png").getImage());

		contentPane = new PanBienvenida();
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnPaginaPrincipal = new JMenu("Página Principal");
		menuBar.add(mnPaginaPrincipal);

		JMenuItem mntmListadoProductos = new JMenuItem("Listado de Productos");
		mntmListadoProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanelProductos(usuario);
			}
		});
		JMenuItem mntmVerCarrito = new JMenuItem("Ver Carrito");
		mntmVerCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCarrito(usuario);
			}
		});
		mnPaginaPrincipal.add(mntmListadoProductos);
		mnPaginaPrincipal.add(mntmVerCarrito);
		
		setVisible(true);
	}

	/**
	 * Muestra el panel de productos.
	 *
	 * @param usuario el nombre de usuario del cliente
	 */
	private void mostrarPanelProductos(String usuario) {
		contentPane.removeAll();
		contentPane.add(new view.gestCompras.PanCompras(usuario), BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	/**
	 * Muestra el carrito de compras.
	 *
	 * @param usuario el nombre de usuario del cliente
	 */
	private void mostrarCarrito(String usuario) {
		JDialogCarrito dialogCarrito = new JDialogCarrito(usuario);
        dialogCarrito.setVisible(true);
	}
}
