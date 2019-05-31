package frame.lecture;

import frame.FrameBase;

public class AdminMenuFrame extends FrameBase {

	public AdminMenuFrame() {
		super(300, 300, "관리자 메뉴");
	}

	public static void main(String[] args) {
		new AdminMenuFrame().setVisible(true);
	}

}
