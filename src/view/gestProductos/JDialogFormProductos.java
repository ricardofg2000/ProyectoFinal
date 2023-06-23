package view.gestProductos;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Producto;
import start.Log;

public class JDialogFormProductos extends JDialog {

    private JTextField textFieldNombre;
    private JTextField textFieldPrecio;
    private JTextField textFieldDescripcion;
    private JTextField textFieldCodigoBarras;

    private Producto producto;

    public JDialogFormProductos(Producto producto) {
        setResizable(false);
        this.producto = producto;
        setTitle("Formulario de Producto");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 467, 241);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 440, 188);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 10, 100, 20);
        panel.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(120, 10, 280, 20);
        panel.add(textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 40, 100, 20);
        panel.add(lblPrecio);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(120, 40, 280, 20);
        panel.add(textFieldPrecio);
        textFieldPrecio.setColumns(10);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 70, 100, 20);
        panel.add(lblDescripcion);

        textFieldDescripcion = new JTextField();
        textFieldDescripcion.setBounds(120, 70, 280, 20);
        panel.add(textFieldDescripcion);
        textFieldDescripcion.setColumns(10);

        JLabel lblCodigoBarras = new JLabel("Código de Barras:");
        lblCodigoBarras.setBounds(10, 100, 100, 20);
        panel.add(lblCodigoBarras);

        textFieldCodigoBarras = new JTextField();
        textFieldCodigoBarras.setBounds(120, 100, 280, 20);
        panel.add(textFieldCodigoBarras);
        textFieldCodigoBarras.setColumns(10);

        JButton okButton = new JButton("OK");
        okButton.setBounds(206, 131, 89, 23);
        panel.add(okButton);
        okButton.addActionListener(e -> {
            if (validarCampos()) {
                actualizarProducto();
                Log log = new Log("Producto actualizado exitosamente.");
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(305, 131, 89, 23);
        panel.add(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        
        
        textFieldNombre.setText(producto.getNombre());
        textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
        textFieldDescripcion.setText(producto.getDescripcion());
        textFieldCodigoBarras.setText(producto.getCodigoBarras());
    }

    private boolean validarCampos() {
        String nombre = textFieldNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Nombre' no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        double precio;
        try {
            precio = Double.parseDouble(textFieldPrecio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo 'Precio' debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (precio == 0) {
            JOptionPane.showMessageDialog(this, "El campo 'Precio' no puede ser 0", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void actualizarProducto() {
        producto.setNombre(textFieldNombre.getText());
        producto.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
        producto.setDescripcion(textFieldDescripcion.getText());
        producto.setCodigoBarras(textFieldCodigoBarras.getText());
    }

    public Producto showDialog() {
        setVisible(true);
        return producto;
    }
}
