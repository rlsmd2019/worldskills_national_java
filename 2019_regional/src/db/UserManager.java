package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.User;

public class UserManager {
	
	static PreparedStatement pstLogin;
	static PreparedStatement pstInsert;
	static PreparedStatement pstDuplicated;
	static PreparedStatement pstGetTotalAmount;
	static PreparedStatement pstUpdatePointAndGrade;
	
	static {
		
		try {
			pstLogin = JDBCManager.con.prepareStatement("SELECT * FROM user WHERE u_id = ? AND u_pw = ?");
			pstGetTotalAmount = JDBCManager.con.prepareStatement("SELECT SUM(o_amount) FROM orderlist WHERE u_no = ?");
			pstInsert = JDBCManager.con.prepareStatement("INSERT INTO user VALUES(0, ?, ?, ?, ?, 0, '¿œπ›')");
			pstDuplicated = JDBCManager.con.prepareStatement("SELECT u_no FROM user WHERE u_id = ?");
			pstUpdatePointAndGrade = JDBCManager.con.prepareStatement("UPDATE user SET u_point = ?, u_grade = ? WHERE u_no = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getTotalAmount(int userNo) {
		try {
			pstGetTotalAmount.setObject(1, userNo);
			
			try (var rs = pstGetTotalAmount.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void updatePointAndGrade(int point, String grade, int userNo) {
		try {
			SessionManager.currentUser.point = point;
			SessionManager.currentUser.grade = grade;
			
			pstUpdatePointAndGrade.setObject(1, point);
			pstUpdatePointAndGrade.setObject(2, grade);
			pstUpdatePointAndGrade.setObject(3, userNo);
			
			pstUpdatePointAndGrade.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isDuplicatedId(String id) {

		try {
			pstDuplicated.setObject(1, id);
			
			try (var rs = pstDuplicated.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			return true;
		}
	}
	
	public static boolean insertUser(String id, String pwd, String name, String birthDate) {
		
		try {
			pstInsert.setObject(1, id);
			pstInsert.setObject(2, pwd);
			pstInsert.setObject(3, name);
			pstInsert.setObject(4, birthDate);
			
			pstInsert.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
	}
	
	public static User canLogin(String id, String pwd) {
		
		try {
			pstLogin.setString(1, id);
			pstLogin.setString(2, pwd);
			
			try (var rs = pstLogin.executeQuery()) {
				if (rs.next() == false) {
					return null;
				}
				
				return new User(rs.getInt(1), id, pwd, rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
}
