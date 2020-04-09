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
@WebServlet(
		description = "ѧϰservlet����", 
		urlPatterns = { 
				"/FirstServlet", 
				"/Home/FirstServlet"
		}, 
		initParams = { 
				@WebInitParam(name = "userName", value = "abc", description = "�û�����")
		})
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		String account = request.getParameter("account"); // �� request �л�ȡ��Ϊ account �Ĳ�����ֵ
		String password = request.getParameter("password"); // �� request �л�ȡ��Ϊ password �Ĳ�����ֵ
		System.out.println("account:" + account + "\npassword:" + password); // ��ӡ������һ��
 
		String result = "";
		if("��x".equals(account) 
				&& "��x".equals(password)){ // �������
			result = "Login Success!" + "�ɹ��ˣ�"; // ��ӦҲ�ӵ�����
		}else {
			result = "Sorry! Account or password error." + "�е����⣡"; // ��ӦҲ�ӵ�����
		}
		/* ��������ֻ��ģ����һ����򵥵�ҵ���߼�����Ȼ�����ʵ��ҵ������൱���� */
		
		response.setContentType("text/html;charset=utf-8"); // ������Ӧ���ĵı����ʽ
		PrintWriter pw = response.getWriter(); // ��ȡ response �������
		pw.println(result); // ͨ���������ҵ���߼��Ľ�����
		pw.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
