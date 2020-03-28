package servlet_login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
			// table
			public static final String TABLE_USERINFO = "table_userInfo";
		 
			// connect to MySql database
			public static Connection getConnect() {
				String url = "jdbc:mysql://localhost:3306/lifestyle_database?serverTimezone=UTC"; // 数据库的Url
				Connection connecter = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver"); // java反射，固定写法
					connecter = (Connection) DriverManager.getConnection(url, "root", "liuyuqi");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();      //在命令行打印异常信息
				} catch (SQLException e) {
					System.out.println("SQLException: " + e.getMessage());
					System.out.println("SQLState: " + e.getSQLState());
					System.out.println("VendorError: " + e.getErrorCode());
				}
				return connecter;
			}
}
