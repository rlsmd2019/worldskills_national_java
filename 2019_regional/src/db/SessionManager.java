package db;

import dto.User;

public class SessionManager {
	// ���ǻ� public���� ���
	public static User currentUser;

	public static void login(User user) {
		currentUser = user;
	}
	
	public static void logout() {
		currentUser = null;
	}
	
}
