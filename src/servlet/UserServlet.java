package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import service.ClassService;
import service.CourseService;
import service.UserService;
import tools.Message;
import tools.PageInformation;
import tools.SearchTool;
import tools.Tool;
import bean.Class;
import bean.Score;
import bean.User;
import dao.ClassDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type1");
		UserService userService = new UserService();
		Message message = new Message();
		if ("login".equals(type)) {
			Integer result = -9;
			User user = new User();
			user.setUserId(request.getParameter("userId"));
			user.setPassword(request.getParameter("password"));
			// 验证码
			String checkCode = request.getParameter("checkCode");
			HttpSession session = request.getSession();
			String severCheckCode = (String) session.getAttribute("checkCode");// 获取session中的验证码
			if (severCheckCode == null) {// 服务器端验证图片验证码不存在
				result = -3;
			} else if (!severCheckCode.equals(checkCode)) {// 服务器端验证图片验证码验证失败
				result = -4;
			} else {
				result = userService.login(user);
			}
			message.setResult(result);
			if (result == -3 || result == -4) {
				message.setMessage("验证码输入错误！");
			} else if (result == 1) {
				user.setPassword(null);// 防止密码泄露
				request.getSession().setAttribute("user", user);
				String originalUrl = (String) request.getSession().getAttribute("originalUrl");
				if (originalUrl == null)
					message.setRedirectUrl("/studentManage/user/manageUIMain/manageMain.jsp");
				else
					message.setRedirectUrl(originalUrl);
			} else if (result == 0) {
				message.setMessage("用户存在，但已被停用，请联系管理员！");
			} else if (result == -1) {
				message.setMessage("用户不存在，或者密码错误，请重新登录！");
			} else if (result == -2) {
				message.setMessage("出现其它错误，请重新登录！");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		} else if ("register".equals(type)) {
			int result = 0;
			// 验证码
			String checkCode = request.getParameter("checkCode");
			HttpSession session = request.getSession();
			String severCheckCode = (String) session.getAttribute("checkCode");// 获取session中的验证码
			if (!severCheckCode.equals(checkCode)) {// 服务器端验证图片验证码验证失败
				result = -4;
			} else {
				User user = new User();
				user.setUserId(request.getParameter("userId"));
				user.setPassword(request.getParameter("password"));
				user.setEmail(request.getParameter("email"));
				if (user.getUserId().length() == 6) {
					user.setEnable("stop");
				} else {
					user.setEnable("use");
				}
				result = userService.register(user);
			}
			message.setResult(result);
			if (result == 1) {
				message.setMessage("注册成功！");
				message.setRedirectUrl("/studentManage/index.jsp");
			} else if (result == 0) {
				message.setMessage("同名用户已存在，请改名重新注册！");
				// message.setRedirectUrl("/studentManage/register.jsp");
			} else if (result == -4) {
				message.setMessage("验证码输入错误！");
				// message.setRedirectUrl("/studentManage/register.jsp");
			} else {
				message.setMessage("注册失败！");
				// message.setRedirectUrl("/studentManage/register.jsp");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		} else if (type.equals("exit")) {
			request.getSession().removeAttribute("user");
			response.sendRedirect("/studentManage/index.jsp");
		} else if ("findPassword".equals(type)) {
			//////////////////////////////////////////////////////////////////////////////////////
			User user = new User();
			user.setEmail(request.getParameter("email"));
			Integer rand = Tool.getRandomInRangeInteger(10, 100000);// 随机数作为验证修改密码用
			Integer result = userService.findPasswordByEmail(user, rand);
			if (result == 1) {// 发送邮件成功
				HttpSession session = request.getSession();
				session.setAttribute("email", user.getEmail());
				session.setAttribute("rand", rand);
				session.setAttribute("time", new Date());
				message.setMessage("找回密码邮件已发送至" + user.getEmail() + ",请查收邮件并点击链接通过验证！");
				message.setRedirectUrl("/studentManage/index.jsp");
			} else if (result == 0 || result == -1) {
				message.setMessage("");
				message.setRedirectUrl("/studentManage/findPassword.jsp");
			} else if (result == -2) {
				message.setMessage("该邮箱不存在，请先注册后再进行操作！");
				message.setRedirectUrl("/studentManage/register.jsp");
			} else if (result == -3) {
				message.setMessage("出现其他错误，请重新找回密码！");
				message.setRedirectUrl("/studentManage/findPassword.jsp");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
			//////////////////////////////////////////////////////////////////////////////////////
		} else if ("newPassword".equals(type)) {
			User user = new User();
			user.setPassword(request.getParameter("password"));
			String rand = (String) request.getParameter("rand");
			HttpSession session = request.getSession();
			Integer trueRand = (Integer) session.getAttribute("rand");
			user.setEmail((String) session.getAttribute("email"));
			Date old = (Date) session.getAttribute("time");
			Integer result = 0;

			if (!rand.equals(trueRand.toString())) {// rand值不对，无权限修改密码
				message.setMessage("修改密码失败，请重新找回密码！");
				message.setRedirectUrl("/studentManage/findPassword.jsp");
			} else if (old == null || Tool.getSecondFromNow(old) > 300) {
				message.setMessage("修改密码超时，请重新找回密码！");
				message.setRedirectUrl("/studentManage/findPassword.jsp");
			} else {
				result = userService.updatePassword(user);
				if (result == 0) {
					message.setMessage("修改密码失败，请重新找回密码！");
					message.setRedirectUrl("/studentManage/findPassword.jsp");
				} else if (result == 1) {
					message.setMessage("修改密码成功，正在跳转登陆界面！");
					message.setRedirectUrl("/studentManage/index.jsp");
				} else if (result == -1) {
					message.setMessage("数据库操作失败，请重新找回密码！");
					message.setRedirectUrl("/studentManage/findPassword.jsp");
				} else {
					message.setMessage("出现其他错误，请重新找回密码！");
					message.setRedirectUrl("/studentManage/findPassword.jsp");
				}
			}
			session.removeAttribute("email");// 删除session数据
			session.removeAttribute("rand");
			session.removeAttribute("time");

			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
			///////////////////////////////////////////////////////////////////////////////////////////////
		} else if (type.equals("changePassword")) {
			String newPassword = request.getParameter("newPassword");
			User user = (User) request.getSession().getAttribute("user");
			user.setPassword(request.getParameter("oldPassword"));
			Integer result = userService.changePassword(user, newPassword);
			message.setResult(result);
			if (result == 1) {
				message.setMessage("修改密码成功！");
			} else if (result == 0) {
				message.setMessage("旧密码错误,修改密码失败！");
			}
			message.setRedirectTime(1000);
			request.setAttribute("message", message);
			getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		}

		else if (type.equals("check")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("user", request, pageInformation);
			String id = pageInformation.getIds();
			pageInformation.setIds(null);
			List<User> users = userService.check(pageInformation, id);
			if (users == null) {
				message.setMessage("切换可用性失败，请联系管理员！");
				message.setRedirectUrl("/news/servlet/UserServlet?type1=check&page=1&pageSize=15");
			} else {
				request.setAttribute("pageInformation", pageInformation);
				request.setAttribute("users", users);
				getServletContext().getRequestDispatcher("/user/manage/userCheck.jsp").forward(request, response);
			}
		} else if (type.equals("delete")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("student", request, pageInformation);
			List<User> users = userService.deletes(pageInformation);

			ClassService classService = new ClassService();
			Map<String, String> classes = classService.getAllClassesMap();

			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("users", users);
			request.setAttribute("classes", classes);
			getServletContext().getRequestDispatcher("/user/manage/studentDelete.jsp").forward(request, response);
		} else if (type.equals("showStudent")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("student", request, pageInformation);
			List<User> users = userService.deletes(pageInformation);

			ClassService classService = new ClassService();
			Map<String, String> classes = classService.getAllClassesMap();

			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("users", users);
			request.setAttribute("classes", classes);
			getServletContext().getRequestDispatcher("/user/manage/showStudent.jsp").forward(request, response);
		} else if (type.equals("search")) {
			ClassService classService = new ClassService();
			List<Class> classes = classService.getAllClassesList();
			request.setAttribute("classes", classes);
			getServletContext().getRequestDispatcher("/user/manage/studentSearch.jsp").forward(request, response);
		} else if (type.equals("searchResult")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("student", request, pageInformation);
			pageInformation.setSearchSql(SearchTool.studentUser(request));
			List<User> users = userService.getOnePageStudent(pageInformation);
			// 获取所有班级信息
			ClassService classService = new ClassService();
			Map<String, String> classes = classService.getAllClassesMap();

			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("users", users);
			request.setAttribute("classes", classes);
			getServletContext().getRequestDispatcher("/user/manage/showStudent.jsp").forward(request, response);
		} else if ("addStudent".equals(type)) {
			ClassService classService = new ClassService();
			List<Class> classes = classService.getAllClassesList();
			String classId = request.getParameter("classId");
			request.setAttribute("classes", classes);
			request.setAttribute("classId", classId);

			User user = new User();
			user.setUserId(request.getParameter("studentId"));
			user.setStudentName(request.getParameter("studentName"));
			user.setClassId(request.getParameter("classId"));
			user.setPassword(request.getParameter("password"));
			int result = 0;
			if (user.getUserId() != "" && user.getUserId() != null) {
				result = userService.addStudent(user);
			}
			message.setResult(result);
			if (user.getUserId() != "" && user.getUserId() != null) {
				if (result == 1) {
					message.setMessage("添加学生信息成功！");
					message.setRedirectUrl("/studentManage/servlet/UserServlet?type1=addStudent");
				} else if (result == 0) {
					message.setMessage("学号为“ " + user.getUserId() + " ”的学生已存在，请检查之后再重新添加！");
					message.setRedirectUrl("/studentManage/servlet/UserServlet?type1=addStudent");
				} else {
					message.setMessage("添加学生信息失败！");
					message.setRedirectUrl("/studentManage/servlet/UserServlet?type1=addStudent");
				}
				// request.setAttribute("message", message);
				// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
				// response);
				message.setResult(result);
				Gson gson = new Gson();
				String jsonString = gson.toJson(message);
				Tool.returnJsonString(response, jsonString);
			} else {
				getServletContext().getRequestDispatcher("/user/manage/addStudent.jsp").forward(request, response);
			}
		} else if ("changeStudent".equals(type)) {

			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("student", request, pageInformation);

			String classId = request.getParameter("classId");
			List<User> users = userService.changeStudent(classId, pageInformation);

			ClassService classService = new ClassService();
			Map<String, String> classes = classService.getAllClassesMap();
			List<Class> classes1 = classService.getAllClassesList();

			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("users", users);
			request.setAttribute("classes", classes);
			request.setAttribute("classes1", classes1);
			request.setAttribute("classId", classId);
			getServletContext().getRequestDispatcher("/user/manage/changeStudent.jsp").forward(request, response);
		} else if (type.equals("changePrivate")) {// 修改管理员头像
			User user = (User) request.getSession().getAttribute("user");
			Integer result = userService.updatePrivate(user, request);
			message.setResult(result);
			if (result == 5) {
				message.setMessage("修改头像成功！");
				message.setRedirectUrl("/studentManage/user/manage/changeHeadIcon.jsp");
			} else if (result == 0) {
				message.setMessage("修改头像失败，请联系管理员！");
				message.setRedirectUrl("/studentManage/user/manage/changeHeadIcon.jsp");
			}
			request.setAttribute("message", message);
			getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		} else if (type.equals("showStudentForJqGrid")) { // 显示学生信息（JqGrid显示部分）
			// System.out.println(request.getQueryString());
			Integer rows = Integer.parseInt(request.getParameter("rows")); // 每页的记录数量
			Integer page = Integer.parseInt(request.getParameter("page")); // 当前页数
			String sidx = request.getParameter("sidx"); // 排序列名
			String sord = request.getParameter("sord"); // 排序方式
			// System.out.println(rows + "---" + page + "---" + sidx + "---" +
			// sord);
			JSONObject jsonObj = new JSONObject();
			jsonObj = userService.getStudentInfoJqGrid(rows, page, sidx, sord);
			response.getWriter().write(jsonObj.toString());
		} else if (type.equals("deleteStudent")) { // 删除学生信息--jqgrid
			// System.out.println(request.getQueryString());
			String ids = request.getParameter("ids"); // 需要删除的学生Id号
			// System.out.println(ids);
			Integer result = userService.deletes(ids);
			if (result > 0) {
				message.setMessage("所选学生信息删除成功！");
			} else {
				message.setMessage("所选学生信息删除失败！请检查之后再进行操作……");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		}

	}
}
