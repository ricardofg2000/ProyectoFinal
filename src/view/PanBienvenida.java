package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class PanBienvenida extends JPanel {

    public PanBienvenida() {
        setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("¡Bienvenido!");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon imageIcon = new ImageIcon("images/imagen.jpg");
        Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel lblImage = new JLabel(scaledImageIcon);
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        centerPanel.add(lblWelcome);
        centerPanel.add(lblImage);

        add(centerPanel, BorderLayout.CENTER);
    }
}
