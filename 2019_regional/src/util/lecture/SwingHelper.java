package util.lecture;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class SwingHelper {

	public static JButton creawteButton(String text, ActionListener action) {
		JButton btn = new JButton(text);
		
		btn.addActionListener(action);
		
		return btn;
	}
	
	public static JButton creawteButton(String text, ActionListener action, int width, int height) {
		JButton btn = new JButton(text);
		
		btn.addActionListener(action);
		btn.setPreferredSize(new Dimension(width, height));
		
		return btn;
	}
	
	public static ImageIcon getIcon(String coffeeName, int width, int height) {
		Image img = Toolkit.getDefaultToolkit().getImage("./DataFiles/¿ÃπÃ¡ˆ/" + coffeeName + ".jpg");
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		return new ImageIcon(resizedImg);
		
	}

}
