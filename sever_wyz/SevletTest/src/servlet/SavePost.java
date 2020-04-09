package servlet;

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

//保存帖子用得到

@WebServlet(description = "保存帖子使用的", urlPatterns = { "/SavePost" })
public class SavePost extends HttpServlet{

	private static final long serialVersionUID = 1L;
 
	/**
	 * Default constructor.
	 * @return 
	 */
	public void RegisterServlet() {
		//LogUtil.log("RegisterServlet construct...");
		System.out.println("RegisterServlet construct...");
	}
 
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			LogUtil.log("请求方法：GET");
			doGet(request, response);
		} else if ("POST".equals(method)) {
			LogUtil.log("请求方法：POST");
			doPost(request, response);
		} else {
			LogUtil.log("请求方法分辨失败！");
		}
	}
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = "";
		String message = "";
 
		String userId = request.getParameter("userId");
		String content = request.getParameter("content");
		String time = request.getParameter("time");
		LogUtil.log(userId + ";" + content + ";" + time);
 
		Connection connect = DatabaseUtil.getConnection();
		try {
			Statement statement = connect.createStatement();

			String sqlInsert = "insert into " + DatabaseUtil.TABLE_POSTS + "(userId, postContent, postTime) values('"
						+ userId + "', '" + content + "', '" + time + "')";
			LogUtil.log(sqlInsert);
			if (statement.executeUpdate(sqlInsert) > 0) {
				code = " ";
				message = "Sent Successfully";
			} else {
				code = " ";
				message = "Sent Failure, Try again.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
		//response.getWriter().append("code:").append(code).append(";message:").append(message);
		response.getWriter().append(message);
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
	}
 
	@Override
	public void destroy() {
		LogUtil.log("RegisterServlet destory.");
		super.destroy();
	}
}
