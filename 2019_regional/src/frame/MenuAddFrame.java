package frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JPanel;

import component.MenuPanel;
import db.MenuManager;
import util.SwingHelper;

public class MenuAddFrame extends FrameBase {
	MenuPanel menuPanel = new MenuPanel();

	public MenuAddFrame() {
		super(340, 245, "�޴��߰�");
		
		
		menuPanel.btnPicture.setText("�������");
		
		add(menuPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		southPanel.add(SwingHelper.createComponent(new JButton("���"), btn -> {
			btn.addActionListener(this::clickSubmit);
		}));
		
		southPanel.add(SwingHelper.createComponent(new JButton("���"), btn -> {
			
			btn.addActionListener(e -> {
				dispose();
				new AdminMenuFrame().setVisible(true);				
			});
		}));
		
		add(southPanel, BorderLayout.SOUTH);
	}
	
	private void clickSubmit(ActionEvent e) {
		String group = (String)menuPanel.cbGroup.getSelectedItem();
		String menu = menuPanel.tfName.getText();
		
		if (menu.length() == 0 || menuPanel.tfPrice.getText().length() == 0 || menuPanel.imagePath == null) {
			SwingHelper.showErrorMsg("��ĭ�� �����մϴ�.", "�޽���");
			return;
		}
		
		int price = 0;
		
		try {
			price = Integer.parseInt(menuPanel.tfPrice.getText());
		} catch (Exception ex) {
			SwingHelper.showErrorMsg("������ ���ڷ� �Է����ּ���.", "�޽���");
			return;
		}
		
		if (MenuManager.isDuplicated(0, menu) == true) {
			SwingHelper.showErrorMsg("�̹� �����ϴ� �޴����Դϴ�.", "�޽���");
			return;
		}
		
		MenuManager.insertMenu(group, menu, price);
		
		try {
			Files.copy(Paths.get(menuPanel.imagePath), Paths.get("./DataFiles/�̹���/" + menu + ".jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SwingHelper.showInfoMsg("�޴��� ��ϵǾ����ϴ�.", "�޽���");
	}
	
	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}

}
