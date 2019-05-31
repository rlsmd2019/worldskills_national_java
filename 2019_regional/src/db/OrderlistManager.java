package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dto.Orderlist;
import dto.Top5;
import util.SwingHelper;

public class OrderlistManager {
	static PreparedStatement pstInsert;
	static PreparedStatement pstQueryFromUser;
	static PreparedStatement pstQueryTop5;
	
	static {
		
		try {
			// ��¥�� mysql �Լ��� �ڵ� ���ó�¥
			pstInsert = JDBCManager.con.prepareStatement("INSERT INTO orderlist VALUES(0, CURDATE(), ?, ?, ?, ?, ?, ?, ?)");
			pstQueryFromUser = JDBCManager.con.prepareStatement("SELECT o.*, m.m_name FROM orderlist AS o INNER JOIN menu AS m ON o.m_no = m.m_no WHERE u_no = ?");
			pstQueryTop5 = JDBCManager.con.prepareStatement("SELECT m_name, SUM(o_count) AS totalCount "
					+ "FROM menu AS m "
					+ "INNER JOIN orderlist AS o ON o.m_no = m.m_no "
					+ "WHERE m_group = ? "
					+ "GROUP BY m.m_no "
					+ "ORDER BY totalCount DESC "
					+ "LIMIT 5");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Top5> getTop5List(String group) {
		var top5List = new ArrayList<Top5>();
		
		try {
			pstQueryTop5.setString(1, group);
			
			try (var rs = pstQueryTop5.executeQuery()) {
				while (rs.next()) {
					top5List.add(new Top5(rs.getString(1), rs.getInt(2)));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return top5List;
	}
	
	public static ArrayList<Orderlist> getOrderlist(int userNo) {
		ArrayList<Orderlist> orderlist = new ArrayList<>();
		
		try {
			pstQueryFromUser.setObject(1, userNo);
			
			try (var rs = pstQueryFromUser.executeQuery()) {
				
				while (rs.next()) {
					orderlist.add(new Orderlist(rs.getInt(1), 
							rs.getString(2), 
							rs.getInt(3), 
							rs.getInt(4), 
							rs.getString(5), 
							rs.getString(6), 
							rs.getInt(7), 
							rs.getInt(8), 
							rs.getInt(9),
							rs.getString(10)));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderlist;
	}
	
	public static void insertOrderlist(Orderlist orderlist) {
		
		try {
			pstInsert.setObject(1, orderlist.userNo);
			pstInsert.setObject(2, orderlist.menuNo);
			pstInsert.setObject(3, orderlist.group);
			pstInsert.setObject(4, orderlist.size);
			pstInsert.setObject(5, orderlist.price);
			pstInsert.setObject(6, orderlist.count);
			pstInsert.setObject(7, orderlist.amount);
			
			pstInsert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void buyProducts(ArrayList<Orderlist> orderlist) {
		int sumAmount = orderlist.stream().mapToInt(x -> x.amount).sum();
		int userPoint = SessionManager.currentUser.point;
		int answer = JOptionPane.NO_OPTION;
		int userNo = SessionManager.currentUser.no;
		String curGrade = SessionManager.currentUser.grade;
		
		if (userPoint > sumAmount) {
			// point ó�� ����
			answer = JOptionPane.showConfirmDialog(null, "ȸ������ �� ����Ʈ : " + userPoint + "\n" +
					"����Ʈ�� �����Ͻðڽ��ϱ�?\n" +
					"(�ƴϿ��� Ŭ�� �� ���ݰ����� �˴ϴ�", "��������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		if (answer != JOptionPane.YES_OPTION) {
			// point �ױ� �� grade ó��
			for (Orderlist orderlist2 : orderlist) {
				insertOrderlist(orderlist2);
			}
			
			// 5% ����
			int totalAmount = UserManager.getTotalAmount(userNo) + sumAmount;
			
			String tmpGrade = curGrade;
			
			if (totalAmount >= 800000) {
				tmpGrade = "Gold";
			} else if (totalAmount >= 500000) {
				tmpGrade = "Silver";
			} else if (totalAmount >= 300000) {
				tmpGrade = "Bronze";
			}
			
			SwingHelper.showInfoMsg("���ŵǾ����ϴ�.", "�޽���");
			
			if (curGrade.equals(tmpGrade) == false) {
				SwingHelper.showInfoMsg("�����մϴ�!\nȸ������ ����� " + tmpGrade + "�� �±��ϼ̽��ϴ�.", "�޽���");
			}
			
			UserManager.updatePointAndGrade(userPoint + (int)(sumAmount * 0.05f), tmpGrade, userNo);
		} else {
			// point ����
			// orderlist ��� X
			UserManager.updatePointAndGrade(userPoint - sumAmount, curGrade, userNo);
			SwingHelper.showInfoMsg("����Ʈ�� ���� �Ϸ�Ǿ����ϴ�.\n���� ����Ʈ : " + (userPoint - sumAmount), "�޽���");
		}
		
		
	}
	
}
