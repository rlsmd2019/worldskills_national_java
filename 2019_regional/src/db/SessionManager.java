package db;

import dto.User;

public class SessionManager {
	// 편의상 public으로 사용
	public static User currentUser;

	public static void login(User user) {
		currentUser = user;
	}
	
	public static void logout() {
		currentUser = null;
	}
	
}
