package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Menu;

public class MenuManager {
	
	static PreparedStatement pstMenuQuery;
	static PreparedStatement pstMenuSearch;
	static PreparedStatement pstInsert;
	static PreparedStatement pstUpdate;
	static PreparedStatement pstDuplicated;
	static PreparedStatement pstDelete;
	
	static {
		try {
			pstMenuQuery = JDBCManager.con.prepareStatement("SELECT * FROM menu WHERE m_group = ?");
			pstMenuSearch = JDBCManager.con.prepareStatement("SELECT * FROM menu WHERE m_group LIKE ? AND m_name LIKE ?");
			pstDuplicated = JDBCManager.con.prepareStatement("SELECT * FROM menu WHERE m_name = ? AND m_no != ?");
			pstUpdate = JDBCManager.con.prepareStatement("UPDATE menu SET m_group = ?, m_name = ?, m_price = ? WHERE m_no = ?");
			pstInsert = JDBCManager.con.prepareStatement("INSERT INTO menu VALUES(0, ?, ?, ?)");
			pstDelete = JDBCManager.con.prepareStatement("DELETE FROM menu WHERE m_no = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(int no) {
		
		try {
			pstDelete.setObject(1, no);
			
			pstDelete.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void update(int no, String group, String name, int price) {
		
		try {
			pstUpdate.setObject(1, group);
			pstUpdate.setObject(2, name);
			pstUpdate.setObject(3, price);
			pstUpdate.setObject(4, no);
			
			pstUpdate.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isDuplicated(int exceptNo, String name) {
		
		try {
			pstDuplicated.setObject(1, name);
			pstDuplicated.setObject(2, exceptNo);
			
			try (var rs = pstDuplicated.executeQuery()) {
				return rs.next();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void insertMenu(String group, String name, int price) {
		
		try {
			pstInsert.setObject(1, group);
			pstInsert.setObject(2, name);
			pstInsert.setObject(3, price);
			
			pstInsert.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Menu> getMenuSearch(String group, String keyword) {
		ArrayList<Menu> list = new ArrayList<>();
		
		try {	
			pstMenuSearch.setString(1, group);				
			pstMenuSearch.setString(2, keyword);
			
			try (var rs = pstMenuSearch.executeQuery()) {
				
				while (rs.next()) {
					list.add(new Menu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static ArrayList<Menu> getMenuFromGroup(String group) {
		ArrayList<Menu> list = new ArrayList<>();
		
		try {
			pstMenuQuery.setString(1, group);
			
			try (var rs = pstMenuQuery.executeQuery()) {
				
				while (rs.next()) {
					list.add(new Menu(rs.getInt(1), group, rs.getString(3), rs.getInt(4)));
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
