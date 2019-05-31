package frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

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
import db.ShoppingManager;
import dto.Orderlist;
import dto.Shopping;
import dto.User;
import util.SwingHelper;

public class ShoppingFrame extends FrameBase {
	ArrayList<Shopping> shoppingList = ShoppingManager.getShoppingList();
	DefaultTableModel dfm = new DefaultTableModel(new String[] {
			"메뉴명", "가격", "수량", "사이즈", "금액"
	}, shoppingList.size());

	JTable table = new JTable(dfm) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	public ShoppingFrame() {
		super(700, 350, "장바구니");
		
		add(SwingHelper.createComponent(new JLabel(SessionManager.currentUser.name + "회원님 장바구니", JLabel.CENTER),
				lb -> {
					lb.setFont(new Font("굴림", Font.BOLD, 24));
					lb.setPreferredSize(new Dimension(0, 45));
				}),
				BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(200);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		Insets insets = new Insets(0, 40, 0, 40);
		
		southPanel.add(SwingHelper.createComponent(new JButton("구매"), btn -> {
			btn.setMargin(insets);
			btn.addActionListener(this::clickBuy);
		}));
		
		southPanel.add(SwingHelper.createComponent(new JButton("삭제"), btn -> {
			btn.setMargin(insets);			
			btn.addActionListener(this::clickDelete);
		}));
		southPanel.add(SwingHelper.createComponent(new JButton("닫기"), btn -> {
			btn.setMargin(insets);
			
			btn.addActionListener(e -> {
				dispose();
				new StarBoxFrame().setVisible(true);
			});
		}));
		
		add(southPanel, BorderLayout.SOUTH);
		refreshShopping();
	}
	
	private void refreshShopping() {
		shoppingList = ShoppingManager.getShoppingList();
		
		dfm.setRowCount(shoppingList.size());
		
		for (int i = 0; i < shoppingList.size(); i++) {
			dfm.setValueAt(shoppingList.get(i).menuName, i, 0);
			dfm.setValueAt(shoppingList.get(i).price, i, 1);
			dfm.setValueAt(shoppingList.get(i).count, i, 2);
			dfm.setValueAt(shoppingList.get(i).size, i, 3);
			dfm.setValueAt(shoppingList.get(i).amount, i, 4);
		}
	}
	
	private void clickDelete(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			SwingHelper.showErrorMsg("삭제할 메뉴를 선택해주세요.", "메시지");
		} else {
			ShoppingManager.delete(shoppingList.get(row).no);
			
			refreshShopping();
		}
		
	}
	
	private void clickBuy(ActionEvent e) {
		int rowCount = dfm.getRowCount();
		
		if (rowCount == 0) {
			SwingHelper.showInfoMsg("구매할 제품이 없습니다.", "메시지");
			return;
		}
		
		ArrayList<Orderlist> orderlist = new ArrayList<Orderlist>();
		
		for (Shopping shopping : shoppingList) {
			orderlist.add(new Orderlist(0, null, 
					SessionManager.currentUser.no,
					shopping.menuNo, 
					shopping.group, 
					shopping.size, 
					shopping.price, 
					shopping.count, 
					shopping.amount));
		}
		
		OrderlistManager.buyProducts(orderlist);
		ShoppingManager.deleteAll();
		dispose();
		new StarBoxFrame().setVisible(true);
	}

	public static void main(String[] args) {
		SessionManager.login(new User(1, "coffee1", "1234", "이기민", "2019-01-01", 0, "Gold"));
		
		new ShoppingFrame().setVisible(true);
	}

}
