package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import db.OrderlistManager;
import db.SessionManager;
import dto.Orderlist;
import dto.User;
import util.SwingHelper;

public class OrderlistFrame extends FrameBase {

	public OrderlistFrame() {
		super(700, 350, "구매내역");
		
		add(SwingHelper.createComponent(new JLabel(SessionManager.currentUser.name + "회원님 구매내역", JLabel.CENTER),
				lb -> {
					lb.setFont(new Font("굴림", Font.BOLD, 24));
					lb.setPreferredSize(new Dimension(0, 45));
				}),
				BorderLayout.NORTH);
		
		ArrayList<Orderlist> orderlist = OrderlistManager.getOrderlist(SessionManager.currentUser.no);
		
		DefaultTableModel dfm = new DefaultTableModel(new String[] {
				"구매일자", "메뉴명", "가격", "사이즈", "수량", "총금액"
		}, orderlist.size());
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		
		for (int i = 0; i < orderlist.size(); i++) {
			dfm.setValueAt(orderlist.get(i).date, i, 0);
			dfm.setValueAt(orderlist.get(i).menuName, i, 1);
			dfm.setValueAt(String.format("%,d", orderlist.get(i).price), i, 2);
			dfm.setValueAt(orderlist.get(i).size, i, 3);
			dfm.setValueAt(orderlist.get(i).count, i, 4);
			dfm.setValueAt(String.format("%,d", orderlist.get(i).amount), i, 5);
		}
		
		JTable table = new JTable(dfm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane scrollPane = new JScrollPane(table);
		
		for (int i = 0; i < dfm.getColumnCount(); i++) {
			table.getTableHeader().getColumnModel().getColumn(i).setCellRenderer(renderer);			
		}
		
		table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(200);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		int amount = orderlist.stream().mapToInt(o -> o.amount).sum();
		
		southPanel.add(new JLabel("총 결제 금액"));
		southPanel.add(SwingHelper.createComponent(new JTextField(String.format("%,d", amount)), tf -> {
			tf.setPreferredSize(new Dimension(200, 25));
			tf.setEditable(false);
			tf.setHorizontalAlignment(JTextField.RIGHT);
		}));
		southPanel.add(SwingHelper.createComponent(new JButton("닫기"), btn -> btn.addActionListener(e -> {
			dispose();
			new StarBoxFrame().setVisible(true);
		})));
		
		add(southPanel, BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		SessionManager.login(new User(1, "coffee1", "1234", "이기민", "2019-01-01", 0, "Gold"));
		
		new OrderlistFrame().setVisible(true);
	}

}
