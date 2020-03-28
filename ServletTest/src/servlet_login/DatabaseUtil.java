package servlet_login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
			// table
			public static final String TABLE_USERINFO = "table_userInfo";
		 
			// connect to MySql database
			public static Connection getConnect() {
				String url = "jdbc:mysql://localhost:3306/lifestyle_database?serverTimezone=UTC"; // ���ݿ��Url
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
