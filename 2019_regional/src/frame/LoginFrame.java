package frame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.SessionManager;
import db.UserManager;
import dto.User;
import util.SwingHelper;

import static util.SwingHelper.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;

public class LoginFrame extends FrameBase {

	private JTextField tfId = SwingHelper.createComponent(new JTextField(), tf -> tf.setBounds(70, 5, 160, 25));
	private JPasswordField pfPw = SwingHelper.createComponent(new JPasswordField(), pf -> pf.setBounds(70, 35, 160, 25));
	
	public LoginFrame() {
		super(340, 190, "로그인");
		
		SessionManager.logout();
		
		add(SwingHelper.createComponent(new JLabel("STARBOX", JLabel.CENTER), lb -> {
			lb.setFont(new Font("Franklin Gothic Heavy", 0, 24));
			lb.setPreferredSize(new Dimension(0, 40));
		}), BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(null);
		
		centerPanel.add(SwingHelper.createComponent(new JLabel("ID : ", JLabel.RIGHT), lb -> lb.setBounds(10, 0, 60, 30)));
		centerPanel.add(SwingHelper.createComponent(new JLabel("PW : ", JLabel.RIGHT), lb -> lb.setBounds(10, 30, 60, 30)));
		
		centerPanel.add(tfId);
		centerPanel.add(pfPw);
		
		centerPanel.add(SwingHelper.createComponent(new JButton("로그인"), btn -> {
			btn.addActionListener(this::clickLogin);
			btn.setMargin(new Insets(0, 0, 0, 0));
			btn.setBounds(240, 0, 60, 70);
		}));
		
		add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		southPanel.add(SwingHelper.createComponent(new JButton("회원가입"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new SignUpFrame().setVisible(true);				
			});
		}));
		southPanel.add(SwingHelper.createComponent(new JButton("종료"), btn -> {
			btn.addActionListener(e -> {
				dispose();		
			});
		}));
		
		add(southPanel, BorderLayout.SOUTH);
	}
	
	private void clickLogin(ActionEvent e) {
		String id = tfId.getText();
		String pw = pfPw.getText();
		
		if (id.length() == 0 || pw.length() == 0) {
			SwingHelper.showErrorMsg("빈칸이 존재합니다.", "메시지");
		} else if (id.equals("admin") && pw.equals("1234")) {
			dispose();
			new AdminMenuFrame().setVisible(true);
		} else {
			User user = UserManager.canLogin(id, pw);
			
			if (user == null) {
				SwingHelper.showErrorMsg("회원정보가 틀립니다.다시입력해주세요.", "메시지");
			} else {
				SessionManager.login(user);
				dispose();
				new StarBoxFrame().setVisible(true);
			}
		}
		
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

}
