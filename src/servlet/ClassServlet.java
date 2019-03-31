package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.DatabaseDao;
import net.sf.json.JSONObject;
import bean.Class;
import bean.User;
import dao.ClassDao;
import service.ClassService;
import tools.Message;
import tools.PageInformation;
import tools.Tool;

public class ClassServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type1");
		ClassService classService = new ClassService();
		Message message = new Message();
		if (type.equals("addClass")) {
			Class class1 = new Class();
			class1.setClassId(request.getParameter("classId"));
			class1.setClassName(request.getParameter("className"));
			int result = classService.addClass(class1);
			message.setResult(result);
			if (result == 1) {
				message.setMessage("添加班级成功！");
				message.setRedirectUrl("/studentManage/user/manage/addClass.jsp");
			} else if (result == 0) {
				message.setMessage(
						"编号为“ " + class1.getClassId() + " ”以及名称为“ " + class1.getClassName() + " ”的班级已存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addClass.jsp");
			} else if (result == 2) {
				message.setMessage("编号为“ " + class1.getClassId() + " ”的班级已存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addClass.jsp");
			} else if (result == 3) {
				message.setMessage("名称为“ " + class1.getClassName() + " ”的班级已存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addClass.jsp");
			} else {
				message.setMessage("添加班级失败！");
				message.setRedirectUrl("/studentManage/user/manage/addClass.jsp");
			}
			// request.setAttribute("message", message);
			// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
			// response);
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		} else if (type.equals("changeClass")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("class", request, pageInformation);

			// 判断同名班级
			String newName = request.getParameter("newName");
			int result = classService.hasSameNameClass(newName);
			message.setResult(result);
			if (result == 1) {
				;// message.setMessage("修改班级成功！");
			} else if (result == 0) {
				message.setMessage("名称为“ " + newName + " ”的班级已存在，请检查之后再重新输入！");
				message.setRedirectUrl("/studentManage/servlet/ClassServlet?type1=changeClass&page=1&pageSize=15");
				request.setAttribute("message", message);
				getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			} else {
				message.setMessage("修改班级失败！");
				message.setRedirectUrl("/studentManage/servlet/ClassServlet?type1=changeClass&page=1&pageSize=15");
				request.setAttribute("message", message);
				getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			List<Class> classes = classService.changeClass(pageInformation);
			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("classes", classes);
			getServletContext().getRequestDispatcher("/user/manage/changeClass.jsp").forward(request, response);
		}

		else if (type.equals("showClassForJqGrid")) { // 显示班级信息--jqgrid
			// System.out.println(request.getQueryString());

			Integer rows = Integer.parseInt(request.getParameter("rows")); // 每页的记录数量
			Integer page = Integer.parseInt(request.getParameter("page")); // 当前页数
			String sidx = request.getParameter("sidx"); // 排序列名
			String sord = request.getParameter("sord"); // 排序方式
			// System.out.println(rows + "---" + page + "---" + sidx + "---" +
			// sord);

			JSONObject jsonObj = new JSONObject();
			jsonObj = classService.getClassInfoJqGrid(rows, page, sidx, sord);
			response.getWriter().write(jsonObj.toString());
		} else if (type.equals("deleteClass")) { // 删除班级信息--jqgrid
			// System.out.println(request.getQueryString());

			String ids = request.getParameter("ids"); // 需要删除的班级Id号
			// System.out.println(ids);

			Integer result = classService.deletes(ids);
			if (result > 0) {
				message.setMessage("所选班级信息删除成功！");
			} else {
				message.setMessage("所选班级信息删除失败！请检查之后再进行操作……");
			}

			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		}

	}
}
