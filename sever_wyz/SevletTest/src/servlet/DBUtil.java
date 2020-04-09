package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// table
	public static final String TABLE_PASSWORD = "table_user_password";
	public static final String TABLE_USERINFO = "table_user_info";
	//我的改变
	//private static Connection connecter;
	
	
	// connect to MySql database
	public static Connection getConnect() {
		//String url = "jdbc:mysql://localhost:3306/first_mysql_test"; // 数据库的Url 下面是我加的
		String url = "jdbc:mysql://localhost:3306/first_mysql_test?serverTimezone=GMT%2B8"; 
		Connection connecter = null;
		//Connection connecter;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // java反射，固定写法  原来，下面是我加的
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