package frame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.SwingHelper;

public class AdminMenuFrame extends FrameBase {

	public AdminMenuFrame() {
		super(270, 180, "������ �޴�");
		
		JPanel panel = new JPanel(new GridLayout(3, 1));
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		panel.add(SwingHelper.createComponent(new JButton("�޴� ���"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new MenuAddFrame().setVisible(true);
			});
		}));
		
		panel.add(SwingHelper.createComponent(new JButton("�޴� ����"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new MenuEditFrame().setVisible(true);
			});
		}));
		
		panel.add(SwingHelper.createComponent(new JButton("�α׾ƿ�"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new LoginFrame().setVisible(true);				
			});
		}));
		
		add(panel);
	}
	
	public static void main(String[] args) {
		new AdminMenuFrame().setVisible(true);
	}

}
