package lecture.generic;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import util.SwingHelper;

public class GenericTest extends JFrame {

	public GenericTest() {
		setSize(500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setLayout(null);
		
		var btn = new JButton("asdasd");
		
		btn.setBounds(0, 0, 100, 100);
		
		add(btn);
		
		add(SwingHelper.createComponent(new JButton("dddd"), e -> {
			e.setBounds(100, 100, 100, 100);
			e.addActionListener(a -> dispose());
		}));
	
		
	}
	
	public static void main(String[] args) {
		new GenericTest().setVisible(true);
		
		

	}

}
