package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// table
	public static final String TABLE_PASSWORD = "table_user_password";
	public static final String TABLE_USERINFO = "table_user_info";
	//�ҵĸı�
	//private static Connection connecter;
	
	
	// connect to MySql database
	public static Connection getConnect() {
		//String url = "jdbc:mysql://localhost:3306/first_mysql_test"; // ���ݿ��Url �������Ҽӵ�
		String url = "jdbc:mysql://localhost:3306/first_mysql_test?serverTimezone=GMT%2B8"; 
		Connection connecter = null;
		//Connection connecter;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // java���䣬�̶�д��  ԭ�����������Ҽӵ�
			//Class.forName("com.mysql.cj.jdbc.Driver"); 
			connecter = (Connection) DriverManager.getConnection(url, "root", "wyzwyz123456");
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