package servlet_register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegUtil {
				// table
				public static final String TABLE_USERINFO = "table_userInfo";
			 
				//����mysql���ݿ�
				public static Connection getConnect() {
					String url = "jdbc:mysql://localhost:3306/lifestyle_database?serverTimezone=UTC"; // ���ݿ��Url
					Connection connecter = null;
					try {
						Class.forName("com.mysql.cj.jdbc.Driver"); // java���䣬�̶�д��
						connecter = (Connection) DriverManager.getConnection(url, "root", "liuyuqi666");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();      //�������д�ӡ�쳣��Ϣ
					} catch (SQLException e) {
						System.out.println("SQLException: " + e.getMessage());
						System.out.println("SQLState: " + e.getSQLState());
						System.out.println("VendorError: " + e.getErrorCode());
					}
					return connecter;
				}
				
				//�����Ϣ
				public static void log(String s) {
					System.out.println(s);
				}
}
