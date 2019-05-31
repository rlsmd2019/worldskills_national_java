package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.UserManager;
import util.SwingHelper;

public class SignUpFrame extends FrameBase {

	private JTextField tfName = SwingHelper.createComponent(new JTextField(), tf -> tf.setPreferredSize(new Dimension(210, 25)));
	private JTextField tfId = SwingHelper.createComponent(new JTextField(), tf -> tf.setPreferredSize(new Dimension(210, 25)));
	private JPasswordField pfPw = SwingHelper.createComponent(new JPasswordField(), pf -> pf.setPreferredSize(new Dimension(210, 25)));
	
	private JComboBox<Integer> cbYear = new JComboBox<>();
	private JComboBox<Integer> cbMonth = new JComboBox<>();
	private JComboBox<Integer> cbDay = new JComboBox<>();
	
	public SignUpFrame() {
		super(320, 230, "ȸ������");
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		add(SwingHelper.createComponent(new JLabel("�̸�", JLabel.RIGHT), lb -> lb.setPreferredSize(new Dimension(60, 25))));
		add(tfName);
		
		add(SwingHelper.createComponent(new JLabel("���̵�", JLabel.RIGHT), lb -> lb.setPreferredSize(new Dimension(60, 25))));
		add(tfId);
		
		add(SwingHelper.createComponent(new JLabel("��й�ȣ", JLabel.RIGHT), lb -> lb.setPreferredSize(new Dimension(60, 25))));
		add(pfPw);
		
		add(new JLabel("�������"));
		add(cbYear);
		add(new JLabel("��"));
		add(cbMonth);
		add(new JLabel("��"));
		add(cbDay);
		add(new JLabel("��"));
		
		Calendar cal = Calendar.getInstance();
		
		cbYear.addActionListener(this::changeDate);
		cbMonth.addActionListener(this::changeDate);

		cbYear.addItem(null);
		for (int y = 1900; y <= cal.get(Calendar.YEAR); y++) {
			cbYear.addItem(y);
		}
		
		cbMonth.addItem(null);
		for (int m = 1; m <= 12; m++) {
			cbMonth.addItem(m);
		}
		
		add(SwingHelper.createComponent(new JButton("���� �Ϸ�"), btn -> btn.addActionListener(this::clickSubmit)));
		add(SwingHelper.createComponent(new JButton("���"), btn -> btn.addActionListener(e -> {
			dispose();
			new LoginFrame().setVisible(true);			
		})));
	}
	
	private void changeDate(ActionEvent e) {
		cbDay.removeAllItems();
		
		if (cbYear.getSelectedItem() != null && cbMonth.getSelectedItem() != null) {
			Calendar cal = Calendar.getInstance();
			
			cal.set((Integer)cbYear.getSelectedItem(), (Integer)cbMonth.getSelectedItem() - 1, 1);
			
			for (int d = 1; d <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) ; d++) {
				cbDay.addItem(d);
			}
		}
	}
	
	private void clickSubmit(ActionEvent e) {
		boolean condition = tfName.getText().length() == 0 ||
				tfId.getText().length() == 0 ||
				pfPw.getText().length() == 0 ||
				cbDay.getSelectedItem() == null;
		
		if (condition == true) {
			SwingHelper.showErrorMsg("������ �׸��� �ֽ��ϴ�.", "�޽���");
		} else if (UserManager.isDuplicatedId(tfId.getText()) == true) {
			SwingHelper.showErrorMsg("���̵� �ߺ��Ǿ����ϴ�.", "�޽���");
		} else {
			String birthDate = String.format("%d-%02d-%02d", (Integer)cbYear.getSelectedItem(), 
					(Integer)cbMonth.getSelectedItem(), 
					(Integer)cbDay.getSelectedItem());
			
			if (UserManager.insertUser(tfId.getText(), 
					pfPw.getText(), 
					tfName.getText(), 
					birthDate) == true) {
				SwingHelper.showInfoMsg("���ԿϷ� �Ǿ����ϴ�.", "�޽���");
				dispose();
				new LoginFrame().setVisible(true);				
			} else {
				SwingHelper.showErrorMsg("DB ���̺� �´� ���̷� �Է��ؾ� �մϴ�.", "�޽���");
			}
			
		}
	}
	
	public static void main(String[] args) {
		new SignUpFrame().setVisible(true);
	}
	
}
