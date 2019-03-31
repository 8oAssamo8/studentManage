package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import tools.Message;

public class AuthorityFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		User user = (User) req.getSession().getAttribute("user");

		String originalUrl = req.getRequestURI();// 获取用户请求的原始网址

		if (originalUrl.startsWith("/studentManage/user/manageUIMain/")) { // 主界面权限限制
			if (user == null) {// 无权限
				Message message = new Message();
				message.setMessage("权限不够，无法访问！<br>请先登陆后再进行操作……");
				message.setRedirectTime(3);
				message.setRedirectUrl("/studentManage/index.jsp");
				request.setAttribute("message", message);
				request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else
				chain.doFilter(request, response);// 有权限，可以继续访问
		} else if (originalUrl.startsWith("/studentManage/user/manageUI/")) { // 功能界面权限限制
			if (user == null) {// 无权限
				Message message = new Message();
				message.setMessage("权限不够，无法访问！<br>请先登陆后再进行操作……");
				message.setRedirectTime(3);
				message.setRedirectUrl("/studentManage/index.jsp");
				request.setAttribute("message", message);
				request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else
				chain.doFilter(request, response);// 有权限，可以继续访问
		} else if ("/studentManage/user/changePassword.jsp".equals(originalUrl)) { // 修改密码界面权限限制
			if (user == null) {// 无权限
				Message message = new Message();
				message.setMessage("权限不够，无法访问！<br>请先登陆后再进行操作……");
				message.setRedirectTime(3);
				message.setRedirectUrl("/studentManage/index.jsp");
				request.setAttribute("message", message);
				request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else
				chain.doFilter(request, response);// 有权限，可以继续访问
		} else if (originalUrl.startsWith("/studentManage/user/manage/")) { // 管理员功能界面权限限制
			if (user == null || user.getUserId().length() == 12) {// 无权限
				Message message = new Message();
				message.setMessage("权限不够，无法访问！");
				message.setRedirectTime(1000);
				request.setAttribute("message", message);
				request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else
				chain.doFilter(request, response);// 有权限，可以继续访问
		} else if (originalUrl.startsWith("/studentManage/user/student/")) { // 学生功能界面权限限制
			if (user == null || user.getUserId().length() == 6) {// 无权限
				Message message = new Message();
				message.setMessage("权限不够，无法访问！");
				message.setRedirectTime(1000);
				request.setAttribute("message", message);
				request.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else
				chain.doFilter(request, response);// 有权限，可以继续访问
		} else {
			chain.doFilter(request, response);// 有权限，可以继续访问
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
