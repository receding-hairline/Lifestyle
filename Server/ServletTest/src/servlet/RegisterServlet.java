package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }
 
	/**
	 * �������ǻ�û��˵��POST�������Ի�����GET���������հ����е���
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account"); // �� request �л�ȡ��Ϊ account �Ĳ�����ֵ
		String password = request.getParameter("password"); // �� request �л�ȡ��Ϊ password �Ĳ�����ֵ
		String sex = request.getParameter("sex"); // �� request �л�ȡ��Ϊ sex �Ĳ�����ֵ
		String email = request.getParameter("email"); // �� request �л�ȡ��Ϊ email �Ĳ�����ֵ
		System.out.println("account:" + account + "\npassord:" + password); // ��ӡ������һ��
 
		String resCode = "";
		String resMsg = "";
		String userId = "";
		
		
		/* ����������һ����򵥵�ע���߼�����Ȼ�����ʵ��ҵ������൱���� */
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = connect.createStatement(); // Statement�������Ϊ���ݿ����ʵ���������ݿ�����в�����ͨ������ʵ��
			ResultSet result;
			
			//����һ����ѯ���
			String sqlQuery = "select * from " + DBUtil.TABLE_USERINFO + " where account = '" + account + "'";
			
			// ��ѯ���������һ��ResultSet���ϣ�û�в鵽���ʱResultSet�ĳ���Ϊ0
			result = statement.executeQuery(sqlQuery); // �Ȳ�ѯͬ�����˺��Ƿ����
			if(result.next()){ // �Ѵ���
				resCode = "201";
				resMsg = "���˺���ע�ᣬ��ʹ�ô��˺�ֱ�ӵ�¼��ʹ�������˺�ע��";
				userId = "";
			} else { // ������
				String sqlInsertPass = "insert into " + DBUtil.TABLE_USERINFO + "(account, password, sex, email) values('"+account+"','"+password+"','"+sex+"','"+email+"')";
				// �������������һ��int���͵�ֵ������ò���Ӱ�쵽������
				int row1 = statement.executeUpdate(sqlInsertPass); // �����ʺ�����
				if(row1 == 1){
					String sqlQueryId = "select id from " + DBUtil.TABLE_USERINFO + " where account='" + account + "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // ��ѯ������¼��userId
					if(result2.next()){
						userId = result2.getInt("id") + "";
					}
					resCode = "100";
					resMsg = "ע��ɹ�";
				} else {
					resCode = "202";
					resMsg = "�û���Ϣ��������";
					userId = "";
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> map = new HashMap<>();
		map.put("resCode", resCode);
		map.put("resMsg", resMsg);
		map.put("userId", userId);
		
		response.setContentType("text/html;charset=utf-8"); // ������Ӧ���ĵı����ʽ
		PrintWriter pw = response.getWriter(); // ��ȡ response �������
		pw.println(map.toString()); // ͨ���������ҵ���߼��Ľ�����
		pw.flush();
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}


