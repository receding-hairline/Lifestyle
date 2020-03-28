package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// table
	public static final String TABLE_USERINFO = "user_info";
 
	// connect to MySql database
	public static Connection getConnect() {
		String url = "jdbc:mysql://localhost:3306/lifestyle?serverTimezone=GMT%2B8"; // ���ݿ��Url
		Connection connecter = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // java���䣬�̶�д��
			connecter = (Connection) DriverManager.getConnection(url, "root", "123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return connecter;
	}
}