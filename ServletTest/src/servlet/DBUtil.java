package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// table
		public static final String TABLE_PASSWORD = "table_user_password";
		public static final String TABLE_USERINFO = "table_user_info";
	 
		// connect to MySql database
		public static Connection getConnect() {
			String url = "jdbc:mysql://localhost:3306/first_mysql_test?serverTimezone=UTC"; // ���ݿ��Url
			Connection connecter = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // java���䣬�̶�д��
				connecter = (Connection) DriverManager.getConnection(url, "root", "liuyuqi");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();      //�������д�ӡ�쳣��Ϣ
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
			}
			return connecter;
		}
	}
	