package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Producto;
import view.gestCompras.JDialogCarrito;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class FrmCliente extends JFrame {

	private JPanel contentPane;
	private static FrmCliente frame;

	public FrmCliente(String usuario) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 436);

		contentPane = new PanBienvenida();
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnPaginaPrincipal = new JMenu("Pagina Principal");
		menuBar.add(mnPaginaPrincipal);

		JMenuItem mntmListadoProductos = new JMenuItem("Listado de Productos");
		mntmListadoProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanelProductos(usuario);
			}
		});
		mnPaginaPrincipal.add(mntmListadoProductos);

		setVisible(true);
	}

	private void mostrarPanelProductos(String usuario) {
		contentPane.removeAll();
		contentPane.add(new view.gestCompras.PanCompras(usuario), BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
