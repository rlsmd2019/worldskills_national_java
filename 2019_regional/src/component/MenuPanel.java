package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.SwingHelper;

public class MenuPanel extends JPanel {
	public final JComboBox<String> cbGroup = new JComboBox<String>(new String[] {"음료", "푸드", "상품"});
	public final JTextField tfName = new JTextField();
	public final JTextField tfPrice = new JTextField();
	public final JButton btnPicture = new JButton();
	
	// TODO 편의상,,, 안티패턴
	public String imagePath;
	JLabel lbPicture = new JLabel();
	
	public MenuPanel() {
		setLayout(null);
		
		String[] labels = {"분류", "메뉴명", "가격"};
		JComponent[] fields = {cbGroup, tfName, tfPrice};
		
		for (int i = 0; i < labels.length; i++) {
			int y = 50 * i;
			
			add(SwingHelper.createComponent(new JLabel(labels[i]), lb -> lb.setBounds(5, 15 + y, 40, 25)));
			fields[i].setBounds(60, 15 + y, 130, 25);
			add(fields[i]);
		}
		
		
		
		lbPicture.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lbPicture.setBounds(195, 5, 128, 128);
		btnPicture.setBounds(195, 135, 128, 30);
		
		cbGroup.setSize(60, 25);
		
		btnPicture.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			
			chooser.setFileFilter(new FileNameExtensionFilter("JPG image", "jpg"));
			
			chooser.showOpenDialog(null);
			
			if (chooser.getSelectedFile() != null) {
				imagePath = chooser.getSelectedFile().getAbsolutePath();
				setImage(imagePath);
			}
		});
		
		add(lbPicture);
		add(btnPicture);
	}
	
	public void setImage(String path) {
		imagePath = path;
		lbPicture.setIcon(SwingHelper.getImageIcon(path, lbPicture.getWidth(), lbPicture.getHeight()));
	}
	
	
}
