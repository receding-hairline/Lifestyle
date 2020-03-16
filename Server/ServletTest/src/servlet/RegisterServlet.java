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
	 * 由于我们还没有说到POST请求，所以还是用GET来处理，汗颜啊，有点慢
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account"); // 从 request 中获取名为 account 的参数的值
		String password = request.getParameter("password"); // 从 request 中获取名为 password 的参数的值
		String sex = request.getParameter("sex"); // 从 request 中获取名为 sex 的参数的值
		String email = request.getParameter("email"); // 从 request 中获取名为 email 的参数的值
		System.out.println("account:" + account + "\npassord:" + password); // 打印出来看一看
 
		String resCode = "";
		String resMsg = "";
		String userId = "";
		
		
		/* 这里我们做一个最简单的注册逻辑，当然，你的实际业务可以相当复杂 */
		try {
			Connection connect = DBUtil.getConnect();
			Statement statement = connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现
			ResultSet result;
			
			//构造一个查询语句
			String sqlQuery = "select * from " + DBUtil.TABLE_USERINFO + " where account = '" + account + "'";
			
			// 查询类操作返回一个ResultSet集合，没有查到结果时ResultSet的长度为0
			result = statement.executeQuery(sqlQuery); // 先查询同样的账号是否存在
			if(result.next()){ // 已存在
				resCode = "201";
				resMsg = "该账号已注册，请使用此账号直接登录或使用其他账号注册";
				userId = "";
			} else { // 不存在
				String sqlInsertPass = "insert into " + DBUtil.TABLE_USERINFO + "(account, password, sex, email) values('"+account+"','"+password+"','"+sex+"','"+email+"')";
				// 更新类操作返回一个int类型的值，代表该操作影响到的行数
				int row1 = statement.executeUpdate(sqlInsertPass); // 插入帐号密码
				if(row1 == 1){
					String sqlQueryId = "select id from " + DBUtil.TABLE_USERINFO + " where account='" + account + "'";
					ResultSet result2 = statement.executeQuery(sqlQueryId); // 查询新增记录的userId
					if(result2.next()){
						userId = result2.getInt("id") + "";
					}
					resCode = "100";
					resMsg = "注册成功";
				} else {
					resCode = "202";
					resMsg = "用户信息表插入错误";
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
		
		response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式
		PrintWriter pw = response.getWriter(); // 获取 response 的输出流
		pw.println(map.toString()); // 通过输出流把业务逻辑的结果输出
		pw.flush();
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}


