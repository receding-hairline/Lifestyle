package servlet_register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(description = "ע��ʹ�õ�servlet", urlPatterns = { "/RegisterServlet" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			RegUtil.log("���󷽷���GET");
			doGet(request, response);
		} else if ("POST".equals(method)) {
			RegUtil.log("���󷽷���POST");
			doPost(request, response);
		} else {
			RegUtil.log("���󷽷��ֱ�ʧ�ܣ�");
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//����������Ӧ���ĵı����ʽ
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String code = "";
		//String message = "";
		
		//��ȡע�������е��û���Ϣ
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		/*boolean sexbool=false;
		if(sex.equals("male"))
		{
			sexbool=true;
		}*/
		RegUtil.log(account + ";" + password);
 
		Connection connect = DatabaseUtil.getConnect();
		try {
			Statement statement = connect.createStatement();
			String sql = "select userAccount from " + DatabaseUtil.TABLE_USERINFO + " where userAccount='" + account + "'";
			RegUtil.log(sql);
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) { // �ܲ鵽���˺ţ�˵���Ѿ�ע�����
				code = "100";
				//message = "���˺��Ѵ���";
			} else {
				String sqlInsert = "insert into " + DatabaseUtil.TABLE_USERINFO + "(userAccount, userPassword,Email,Phone,Sex) values('"
						+ account + "', '" + password + "', '" + email + "', '" + phone +  "', '" + sex +"')";
				RegUtil.log(sqlInsert);
				if (statement.executeUpdate(sqlInsert) > 0) { // �������ע���߼����������˻���Ϣ�����ݿ�
					code = "200";
					//message = "ע��ɹ�";
				} else {
					code = "300";
					//message = "ע��ʧ��";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
		response.getWriter().append(code);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void destroy() {
		RegUtil.log("RegisterServlet destory.");
		super.destroy();
	}

}
