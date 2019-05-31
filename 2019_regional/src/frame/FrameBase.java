package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class FrameBase extends JFrame {

	public FrameBase(int width, int height, String title) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
	}
	
}
