package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmAdministrador extends JFrame {

    private JPanel contentPane;

    public FrmAdministrador() {
    	setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 591, 436);

        contentPane = new PanBienvenida();
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnGestion = new JMenu("Gestión");
        menuBar.add(mnGestion);

        JMenuItem mntmClientes = new JMenuItem("Clientes");
        mntmClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                contentPane.add(new view.gestClientes.PanClientes(), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        mnGestion.add(mntmClientes);

        JMenuItem mntmProductos = new JMenuItem("Productos");
        mntmProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                contentPane.add(new view.gestProductos.PanProductos(), BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        mnGestion.add(mntmProductos);
        
        setVisible(true);
    }
    
    

}
