package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */

@WebServlet(description = "学习Servlet创建的", urlPatterns = { "/Home/FirstServlet" }, initParams = {
		@WebInitParam(name = "userName", value = "abc", description = "用户姓名") })

public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String account = request.getParameter("account"); // 从 request 中获取名为 account 的参数的值
		String password = request.getParameter("password"); // 从 request 中获取名为 password 的参数的值
		System.out.println("account:" + account + "\npassword:" + password); // 打印出来看一看
 
		String result = "";
		if("abc".equals(account) 
				&& "123".equals(password)){ // 模拟登陆账号、密码验证
			result = "Login Success!";
		}else {
			result = "Sorry! Account or password error.";
		}
		/* 这里我们只是模拟了一个最简单的业务逻辑，当然，你的实际业务可以相当复杂 */
		
		PrintWriter pw = response.getWriter(); // 获取 response 的输出流
		pw.println(result); // 通过输出流把业务逻辑的结果输出
		pw.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("\n初始化参数userName = " + getInitParameter("userName"));
	}

}
