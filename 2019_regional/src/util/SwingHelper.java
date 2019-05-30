package util;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SwingHelper {
	
	public static void showErrorMsg(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showInfoMsg(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static ImageIcon getImageIcon(String path, int width, int height) {
		var img = Toolkit.getDefaultToolkit().getImage(path);
		var resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		return new ImageIcon(resizedImg);
	}
	
	public static <T> T createComponent(T obj, Consumer<T> consumer) {
		consumer.accept(obj);
		
		return obj;
	}
	
}
