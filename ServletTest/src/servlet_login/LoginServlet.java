package servlet_login;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "登录用的servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置请求、响应报文的编码格式
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//响应返回的信息
		String message = "";
		
		//获取登录请求中的账户信息
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		LogUtil.log(account + ";" + password);
 
		Connection connect = DatabaseUtil.getConnect();
		try {
			Statement statement = connect.createStatement();
			String sql = "select userAccount from " + DatabaseUtil.TABLE_USERINFO + " where userAccount='" + account
					+ "' and userPassword='" + password + "'";
			LogUtil.log(sql);
			ResultSet result = statement.executeQuery(sql);   //查询该账号是否存在
			if (result.next()) { // 能查到该账号，说明已经注册过了
				//响应返回successful
				message = "successful";
			} else {
				//响应返回error,表示登录失败
				message = "error";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
		response.getWriter().append(message);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
