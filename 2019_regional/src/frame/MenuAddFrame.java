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
		super(340, 245, "메뉴추가");
		
		
		menuPanel.btnPicture.setText("사진등록");
		
		add(menuPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		southPanel.add(SwingHelper.createComponent(new JButton("등록"), btn -> {
			btn.addActionListener(this::clickSubmit);
		}));
		
		southPanel.add(SwingHelper.createComponent(new JButton("취소"), btn -> {
			
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
			SwingHelper.showErrorMsg("빈칸이 존재합니다.", "메시지");
			return;
		}
		
		int price = 0;
		
		try {
			price = Integer.parseInt(menuPanel.tfPrice.getText());
		} catch (Exception ex) {
			SwingHelper.showErrorMsg("가격은 숫자로 입력해주세요.", "메시지");
			return;
		}
		
		if (MenuManager.isDuplicated(0, menu) == true) {
			SwingHelper.showErrorMsg("이미 존재하는 메뉴명입니다.", "메시지");
			return;
		}
		
		MenuManager.insertMenu(group, menu, price);
		
		try {
			Files.copy(Paths.get(menuPanel.imagePath), Paths.get("./DataFiles/이미지/" + menu + ".jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SwingHelper.showInfoMsg("메뉴가 등록되었습니다.", "메시지");
	}
	
	public static void main(String[] args) {
		new MenuAddFrame().setVisible(true);
	}

}
