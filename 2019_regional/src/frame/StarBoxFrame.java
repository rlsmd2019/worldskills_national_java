package frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import db.MenuManager;
import db.OrderlistManager;
import db.SessionManager;
import db.ShoppingManager;
import dto.Menu;
import dto.Orderlist;
import dto.User;
import util.SwingHelper;

import static db.SessionManager.currentUser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StarBoxFrame extends FrameBase {
	private static final int EAST_PANEL_WIDTH = 310;
	private static final int EAST_PANEL_HEIGHT = 210;
	private static final int MENU_PANEL_WIDTH = 180;
	private static final int MENU_PANEL_HEIGHT = 180;

	JLabel lbCaption = SwingHelper.createComponent(new JLabel(), lb -> {
		lb.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		lb.setFont(new Font("���� ���", Font.BOLD, 14));
	});
	
	JPanel foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel eastPanel = new JPanel(new BorderLayout());
	JLabel lbImg = new JLabel();;
	JTextField tfMenu = new JTextField();
	JTextField tfPrice = new JTextField();
	JTextField tfAmount = new JTextField();
	JComboBox<Integer> cbQuantity = new JComboBox<>();
	JComboBox<String> cbSize = new JComboBox<>();
	
	Menu curMenu;
	
	private class MenuPanel extends JPanel {
		
		public MenuPanel(Menu menu) {
			setPreferredSize(new Dimension(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT));
			setLayout(new BorderLayout());
			
			int imgHeight = MENU_PANEL_WIDTH - 25;
			String imgPath = "./DataFiles/�̹���/" + menu.name + ".jpg";
			var imgIcon = SwingHelper.getImageIcon(imgPath, MENU_PANEL_WIDTH - 5, imgHeight);
			
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			add(SwingHelper.createComponent(new JLabel(imgIcon), lb -> {
				lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				lb.setPreferredSize(new Dimension(0, imgHeight));
				
			}), BorderLayout.CENTER);
			
			add(SwingHelper.createComponent(new JLabel(menu.name, JLabel.CENTER), lb -> lb.setPreferredSize(new Dimension(0, 25)))
					, BorderLayout.SOUTH);
			
			addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					StarBoxFrame.this.setSize(700 + EAST_PANEL_WIDTH, 600);
					StarBoxFrame.this.setLocationRelativeTo(null);
					eastPanel.setVisible(true);
					curMenu = menu;
					lbImg.setIcon(SwingHelper.getImageIcon(imgPath, lbImg.getWidth(), lbImg.getHeight()));
					tfMenu.setText(menu.name);
					tfPrice.setText(String.valueOf(menu.price));
					
					cbSize.removeAllItems();
					
					if (menu.group.equals("��ǰ") == false) {
						cbSize.addItem("M");
						cbSize.addItem("L");
					} else {
						// null ����
						cbSize.addItem("");
					}
					
					calculateAmount();
				}
			});
		}
	}
	
	public StarBoxFrame() {
		super(700, 600, "STARBOX");

		
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel north_centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		north_centerPanel.add(SwingHelper.createComponent(new JButton("���ų���"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new OrderlistFrame().setVisible(true);
			});	
		}));
		
		north_centerPanel.add(SwingHelper.createComponent(new JButton("��ٱ���"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new ShoppingFrame().setVisible(true);
			});	
		}));
		
		north_centerPanel.add(SwingHelper.createComponent(new JButton("�α��ǰ Top5"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new Top5Frame().setVisible(true);
			});	
		}));
		
		north_centerPanel.add(SwingHelper.createComponent(new JButton("Logout"), btn -> {
			btn.addActionListener(e -> {
				dispose();
				new LoginFrame().setVisible(true);
			});	
		}));
		
		northPanel.add(north_centerPanel, BorderLayout.CENTER);
		northPanel.add(lbCaption, BorderLayout.NORTH);
		
		refreshCaption();
		add(northPanel, BorderLayout.NORTH);
		
		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		
		for (String text : new String[] { "����", "Ǫ��", "��ǰ" }) {
			westPanel.add(SwingHelper.createComponent(new JButton(text), btn -> {
				btn.setPreferredSize(new Dimension(60, 40));
				btn.addActionListener(e -> changeGroup(text));
			}));
		}
		
		
		westPanel.setPreferredSize(new Dimension(60, 0));
		
		add(westPanel, BorderLayout.WEST);
		
		changeGroup("����");
		
		JPanel centerPanel = new JPanel(null);
		
		centerPanel.add(SwingHelper.createComponent(new JScrollPane(foodPanel), sp -> sp.setBounds(0, 0, 580, 475)));
		
		add(centerPanel, BorderLayout.CENTER);
		
		lbImg.setBounds(0, 0, 100, 100);
		lbImg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		cbQuantity.addActionListener((e) -> calculateAmount());
		cbSize.addActionListener((e) -> calculateAmount());
		
		JPanel east_centerPanel = new JPanel(null);
		
		east_centerPanel.add(lbImg);
		
		String[] labels = new String[] {"�ֹ��޴� : ", "���� : ", "���� : ", "������ : ", "�ѱݾ� : "};
		JComponent[] fields = new JComponent[] {
			tfMenu, tfPrice, cbQuantity, cbSize, tfAmount
		};
		
		for (int i = 0; i < labels.length; i++) {
			int y = i * 30;
			
			east_centerPanel.add(SwingHelper.createComponent(new JLabel(labels[i], JLabel.RIGHT), lb -> lb.setBounds(100, y, 78, 30)));
			fields[i].setBounds(180, y, 130, 25);
			east_centerPanel.add(fields[i]);
		}
		
		
		for (int i = 1; i <= 10; i++) {
			cbQuantity.addItem(i);
		}
		
		tfMenu.setEditable(false);
		tfPrice.setEditable(false);
		tfAmount.setEditable(false);
		
		JPanel east_southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		east_southPanel.add(SwingHelper.createComponent(new JButton("��ٱ��Ͽ� ���"), btn -> btn.addActionListener(this::clickCart)));
		east_southPanel.add(SwingHelper.createComponent(new JButton("�����ϱ�"), btn -> btn.addActionListener(this::clickBuy)));
		
		eastPanel.add(east_centerPanel, BorderLayout.CENTER);
		eastPanel.add(east_southPanel, BorderLayout.SOUTH);

		eastPanel.setVisible(false);
		eastPanel.setBounds(600, 100, EAST_PANEL_WIDTH, EAST_PANEL_HEIGHT);
		
		centerPanel.add(eastPanel);
	}
	
	private void calculateAmount() {
		if (curMenu == null || cbSize.getSelectedItem() == null) {
			tfAmount.setText("0");
		} else {
			int quantity = (Integer)cbQuantity.getSelectedItem();
			int price = curMenu.price;
			
			if (cbSize.getSelectedItem().equals("L")) {
				price += 1000;
			}
			
			int amount = (int)((quantity * price) * (1f - SessionManager.currentUser.getDiscount()));
			
			tfAmount.setText(String.valueOf(amount));			
		}
	}
	
	private void clickCart(ActionEvent e) {
		
		ShoppingManager.insertShopping(curMenu.no, 
				curMenu.price, 
				(Integer)cbQuantity.getSelectedItem(),
				(String)cbSize.getSelectedItem(),
				Integer.parseInt(tfAmount.getText()));
		
		SwingHelper.showInfoMsg("��ٱ��Ͽ� ��ҽ��ϴ�.", "�޽���");
	}
	
	private void clickBuy(ActionEvent e) {
		var orderlist = new ArrayList<Orderlist>();
		
		orderlist.add(new Orderlist(0, null, 
				SessionManager.currentUser.no, 
				curMenu.no, 
				curMenu.group, 
				(String)cbSize.getSelectedItem(), 
				Integer.parseInt(tfPrice.getText()), 
				(Integer)cbQuantity.getSelectedItem(), 
				Integer.parseInt(tfAmount.getText())));
		
		OrderlistManager.buyProducts(orderlist);
		refreshCaption();
	}
	
	private void changeGroup(String group) {
		var menuList = MenuManager.getMenuFromGroup(group);
		int rows = menuList.size() / 3;
		
		if (menuList.size() % 3 != 0) {
			rows++;
		}
		
		setSize(700, 600);
		setLocationRelativeTo(null);
		eastPanel.setVisible(false);
		foodPanel.removeAll();
		
		foodPanel.setPreferredSize(new Dimension(500, (MENU_PANEL_HEIGHT + 5) * rows));
		
		for (Menu menu : menuList) {
			foodPanel.add(new MenuPanel(menu));
		}
		
		foodPanel.revalidate();
	}
	
	private void refreshCaption() {
		lbCaption.setText(String.format("ȸ���� : %s / ȸ����� : %s / �� ���� ����Ʈ : %d", currentUser.name, currentUser.grade, currentUser.point));
	}
	
	public static void main(String[] args) {
		SessionManager.login(new User(1, "coffee1", "1234", "�̱��", "2019-01-01", 0, "Gold"));
		
		new StarBoxFrame().setVisible(true);
	}

}
