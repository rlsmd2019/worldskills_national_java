package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Shopping;

public class ShoppingManager {

	static PreparedStatement pstInsert;
	static PreparedStatement pstQuery;
	static PreparedStatement pstDelete;
	static PreparedStatement pstDeleteAll;
	
	static {
		
		try {
			pstInsert = JDBCManager.con.prepareStatement("INSERT INTO shopping VALUES(0, ?, ?, ?, ?, ?)");
			pstQuery = JDBCManager.con.prepareStatement("SELECT s.*, m.m_name, m.m_group FROM shopping AS s INNER JOIN menu AS m ON s.m_no = m.m_no");
			pstDelete = JDBCManager.con.prepareStatement("DELETE FROM shopping WHERE s_no = ?");
			pstDeleteAll = JDBCManager.con.prepareStatement("DELETE FROM shopping");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteAll() {
		try {
			pstDeleteAll.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(int no) {
		try {
			pstDelete.setInt(1, no);
			pstDelete.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Shopping> getShoppingList() {
		ArrayList<Shopping> list = new ArrayList<Shopping>();
		
		try(var rs = pstQuery.executeQuery()) {
			
			while (rs.next()) {
				list.add(new Shopping(rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getInt(6),
						rs.getString(7),
						rs.getString(8)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static boolean insertShopping(int menuNo, int price, int count, String size, int amount) {
		
		try {
			pstInsert.setObject(1, menuNo);
			pstInsert.setObject(2, price);
			pstInsert.setObject(3, count);
			pstInsert.setObject(4, size);
			pstInsert.setObject(5, amount);
			
			pstInsert.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
	}
	
}
