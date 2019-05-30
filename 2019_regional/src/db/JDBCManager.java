package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager {
	
	static Connection con;
	
	static {
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/coffee?serverTimezone=UTC", "user", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
