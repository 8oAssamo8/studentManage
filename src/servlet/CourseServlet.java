package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.Class;
import bean.Course;
import net.sf.json.JSONObject;
import service.CourseService;
import tools.Message;
import tools.PageInformation;
import tools.Tool;

public class CourseServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type1");
		CourseService courseService = new CourseService();
		Message message = new Message();
		if (type.equals("addCourse")) {
			Course course1 = new Course();
			course1.setCourseId(request.getParameter("courseId"));
			course1.setCourseName(request.getParameter("courseName"));
			course1.setTerms(request.getParameter("terms"));
			int result = courseService.addCourse(course1);
			message.setResult(result);
			if (result == 1) {
				message.setMessage("添加课程成功！");
				message.setRedirectUrl("/studentManage/user/manage/addCourse.jsp");
			} else if (result == 0) {
				message.setMessage("编号为“ " + course1.getCourseId() + " ”的课程已存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addCourse.jsp");
			} else {
				message.setMessage("添加课程失败！");
				message.setRedirectUrl("/studentManage/user/manage/addCourse.jsp");
			}
			// request.setAttribute("message", message);
			// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
			// response);
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		} else if (type.equals("changeCourse")) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("course", request, pageInformation);
			List<Course> courses = courseService.changeCourse(pageInformation);
			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("courses", courses);
			getServletContext().getRequestDispatcher("/user/manage/changeCourse.jsp").forward(request, response);
		} else if (type.equals("showCourseForJqGrid")) { // 显示课程信息--jqgrid
			// System.out.println(request.getQueryString());
			Integer rows = Integer.parseInt(request.getParameter("rows")); // 每页的记录数量
			Integer page = Integer.parseInt(request.getParameter("page")); // 当前页数
			String sidx = request.getParameter("sidx"); // 排序列名
			String sord = request.getParameter("sord"); // 排序方式
			// System.out.println(rows + "---" + page + "---" + sidx + "---" +
			// sord);
			JSONObject jsonObj = new JSONObject();
			jsonObj = courseService.getCourseInfoJqGrid(rows, page, sidx, sord);
			response.getWriter().write(jsonObj.toString());
		} else if (type.equals("deleteCourse")) { // // 删除课程信息--jqgrid
			// System.out.println(request.getQueryString());
			String ids = request.getParameter("ids"); // 需要删除的班级Id号
			// System.out.println(ids);
			Integer result = courseService.deletes(ids);
			if (result > 0) {
				message.setMessage("所选课程信息删除成功！");
			} else {
				message.setMessage("所选课程信息删除失败！请检查之后再进行操作……");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		}
	}
}
