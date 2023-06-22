package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Color;

public class PanBienvenida extends JPanel {

	public PanBienvenida() {
		setLayout(new BorderLayout());

		ImageIcon imageIcon = new ImageIcon("images/welcome.jpg");
		Image image = imageIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledImageIcon = new ImageIcon(image);
		JLabel lblImage = new JLabel(scaledImageIcon);
		lblImage.setBackground(new Color(255, 255, 255));
		lblImage.setHorizontalAlignment(JLabel.CENTER);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(255, 255, 255));
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		centerPanel.add(lblImage);

		add(centerPanel, BorderLayout.CENTER);
	}
}
