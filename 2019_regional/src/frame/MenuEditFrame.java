package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import component.MenuPanel;
import db.MenuManager;
import dto.Menu;
import util.SwingHelper;

public class MenuEditFrame extends FrameBase {
	ArrayList<Menu> menuList;
	JComboBox<String> cbGroup = new JComboBox<String>(new String[] {"��ü", "����", "Ǫ��", "��ǰ"});
	JTextField tfSearch = SwingHelper.createComponent(new JTextField(), tf -> tf.setPreferredSize(new Dimension(210, 25)));
	DefaultTableModel dfm = new DefaultTableModel(new String[] {"�з�", "�޴���", "����"}, 0);
	JTable table = new JTable(dfm) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	MenuPanel menuPanel = new MenuPanel();
	
	public MenuEditFrame() {
		super(700, 280, "�޴� ����");
		
		JPanel nouthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		nouthPanel.add(new JLabel("�˻�"));
		nouthPanel.add(cbGroup);
		nouthPanel.add(tfSearch);
		nouthPanel.add(SwingHelper.createComponent(new JButton("ã��"), btn -> btn.addActionListener(this::clickSearch)));
		
		add(nouthPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(table);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		
		table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getTableHeader().getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getTableHeader().getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				chagneTableItem();
			}
		});
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel center_eastPanel = new JPanel(new BorderLayout());
		JPanel center_east_southPanel = new JPanel(new FlowLayout());
		
		menuPanel.btnPicture.setText("��������");
		
		center_eastPanel.setPreferredSize(new Dimension(330, 0));
		center_eastPanel.add(menuPanel, BorderLayout.CENTER);
		center_eastPanel.add(center_east_southPanel, BorderLayout.SOUTH);
		
		center_east_southPanel.add(SwingHelper.createComponent(new JButton("����"), btn -> btn.addActionListener(this::clickDelete)));
		center_east_southPanel.add(SwingHelper.createComponent(new JButton("����"), btn -> btn.addActionListener(this::clickUpdate)));
		center_east_southPanel.add(SwingHelper.createComponent(new JButton("���"), btn -> btn.addActionListener(e -> {
			dispose();
			new AdminMenuFrame().setVisible(true);
		})));
		
		center_eastPanel.add(center_east_southPanel, BorderLayout.SOUTH);
		centerPanel.add(center_eastPanel, BorderLayout.EAST);
		
		add(centerPanel, BorderLayout.CENTER);
		
		clickSearch(null);
	}
	
	private void clear() {
		menuPanel.cbGroup.setSelectedIndex(0);
		menuPanel.tfName.setText("");
		menuPanel.tfPrice.setText("");
		menuPanel.setImage(null);
	}
	
	private void chagneTableItem() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			return;
		}
		
		Menu menu = menuList.get(row);
		
		menuPanel.cbGroup.setSelectedItem(menu.group);
		menuPanel.tfName.setText(menu.name);
		menuPanel.tfPrice.setText(String.valueOf(menu.price));
		menuPanel.setImage("./DataFiles/�̹���/" + menu.name + ".jpg");
	}
	
	private void clickUpdate(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			SwingHelper.showErrorMsg("������ �޴��� �������ּ���.", "�޽���");
			return;
		}
		
		Menu menu = menuList.get(row);
		String group = (String)menuPanel.cbGroup.getSelectedItem();
		String name = menuPanel.tfName.getText();
		int price = 0;
		
		if (name.length() == 0 || menuPanel.imagePath == null) {
			SwingHelper.showErrorMsg("��ĭ�� �����մϴ�.", "�޽���");
			return;
		}
		
		try {
			price = Integer.parseInt(menuPanel.tfPrice.getText()); 
		} catch (Exception ex) {
			SwingHelper.showErrorMsg("������ �ٽ� �Է����ּ���.", "�޽���");
			return;
		}
		
		if (MenuManager.isDuplicated(menu.no, name)) {
			SwingHelper.showErrorMsg("�̹� �����ϴ� �޴����Դϴ�.", "�޽���");
			return;
		}
		
		MenuManager.update(menu.no, group, name, price);
		
		Path src = Paths.get("./DataFiles/�̹���/" + menu.name + ".jpg");
		Path dst = Paths.get("./DataFiles/�̹���/" + name + ".jpg");
		
		try {
			Files.move(src, dst);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SwingHelper.showInfoMsg("�����Ǿ����ϴ�.", "�޽���");
		clickSearch(null);
		clear();
	}
	
	private void clickDelete(ActionEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			SwingHelper.showErrorMsg("������ �޴��� �������ּ���.", "�޽���");
			return;
		}
		
		try {
			MenuManager.delete(menuList.get(row).no);
			Files.delete(Paths.get("./DataFiles/�̹���/" + menuList.get(row).name + ".jpg"));
			SwingHelper.showInfoMsg("�����Ǿ����ϴ�.", "�޽���");
			clickSearch(null);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void clickSearch(ActionEvent e) {
		String group = null;
		
		dfm.setRowCount(0);
		
		if (cbGroup.getSelectedIndex() == 0) {
			// ��ü �˻�
			group = "%";
		} else {
			group = (String)cbGroup.getSelectedItem();
		}
		
		menuList = MenuManager.getMenuSearch(group, "%" + tfSearch.getText() + "%");
		
		dfm.setRowCount(menuList.size());
		
		for (int i = 0; i < menuList.size(); i++) {
			dfm.setValueAt(menuList.get(i).group, i, 0);
			dfm.setValueAt(menuList.get(i).name, i, 1);
			dfm.setValueAt(menuList.get(i).price, i, 2);
		}
		
	}
	
	public static void main(String[] args) {
		new MenuEditFrame().setVisible(true);
	}

}
