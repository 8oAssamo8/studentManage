package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.Class;
import bean.Course;
import bean.Score;
import bean.SubjectScore;
import bean.User;
import net.sf.json.JSONObject;
import service.ClassService;
import service.CourseService;
import service.ScoreService;
import tools.Message;
import tools.PageInformation;
import tools.Tool;

public class ScoreServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type1");
		ScoreService scoreService = new ScoreService();
		Message message = new Message();
		if ("getOneScore".equals(type)) {
			User user = (User) request.getSession().getAttribute("user");
			String studentId = user.getUserId();
			CourseService courseService = new CourseService();
			List<Course> courses = courseService.getCoursesByStudentId(studentId);

			SubjectScore scoreResult = new SubjectScore();

			String courseId = request.getParameter("courseId");
			if (courseId != null) {
				scoreResult = scoreService.getOneScore(studentId, courseId);
			}

			request.setAttribute("scoreResult", scoreResult);
			request.setAttribute("courses", courses);
			request.setAttribute("courseId", courseId);
			getServletContext().getRequestDispatcher("/user/student/selectOneScore.jsp").forward(request, response);
		} else if ("getAllScore".equals(type)) {
			User user = (User) request.getSession().getAttribute("user");
			String studentId = user.getUserId();
			List<String> terms = scoreService.getAllTermsListByStudentId(studentId);

			List<SubjectScore> scoreResult = new ArrayList<SubjectScore>();

			String term = request.getParameter("term");
			if (term != null) {
				scoreResult = scoreService.getAllScoreByTerms(studentId, term);
			}

			request.setAttribute("scoreResult", scoreResult);
			request.setAttribute("terms", terms);
			request.setAttribute("term", term);
			getServletContext().getRequestDispatcher("/user/student/selectAllScore.jsp").forward(request, response);
		} else if ("addScore".equals(type)) {
			Score score = new Score();
			score.setCourseId(request.getParameter("courseId"));
			score.setStudentId(request.getParameter("studentId"));
			score.setScore(request.getParameter("score"));
			int result = scoreService.addScore(score);
			message.setResult(result);
			if (result == 1) {
				message.setMessage("添加成绩成功！");
				message.setRedirectUrl("/studentManage/user/manage/addScore.jsp");
			} else if (result == 0) {
				message.setMessage("编号为“ " + score.getCourseId() + " ”的课程不存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addScore.jsp");
			} else if (result == -2) {
				message.setMessage("编号为“ " + score.getStudentId() + " ”的学生不存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addScore.jsp");
			} else if (result == -3) {
				message.setMessage("编号为“ " + score.getCourseId() + " ”的课程,编号为“ " + score.getStudentId()
						+ "”的学生的成绩已存在，请检查之后再重新添加！");
				message.setRedirectUrl("/studentManage/user/manage/addScore.jsp");
			} else {
				message.setMessage("添加成绩失败！");
				message.setRedirectUrl("/studentManage/user/manage/addScore.jsp");
			}
			// request.setAttribute("message", message);
			// getServletContext().getRequestDispatcher("/message.jsp").forward(request,
			// response);
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		} else if ("changeScore".equals(type)) {
			PageInformation pageInformation = new PageInformation();
			Tool.getPageInformation("score", request, pageInformation);
			List<Score> scores = scoreService.changeScore(pageInformation);
			request.setAttribute("pageInformation", pageInformation);
			request.setAttribute("scores", scores);
			getServletContext().getRequestDispatcher("/user/manage/changeScore.jsp").forward(request, response);
		} else if (type.equals("showScoreForJqGrid")) { // 显示成绩信息--jqgrid
			// System.out.println(request.getQueryString());
			Integer rows = Integer.parseInt(request.getParameter("rows")); // 每页的记录数量
			Integer page = Integer.parseInt(request.getParameter("page")); // 当前页数
			String sidx = request.getParameter("sidx"); // 排序列名
			String sord = request.getParameter("sord"); // 排序方式
			// System.out.println(rows + "---" + page + "---" + sidx + "---" +
			// sord);
			JSONObject jsonObj = new JSONObject();
			jsonObj = scoreService.getScoreInfoJqGrid(rows, page, sidx, sord);
			response.getWriter().write(jsonObj.toString());
		} else if (type.equals("deleteScore")) { // 删除成绩信息--jqgrid
			// System.out.println(request.getQueryString());
			String ids = request.getParameter("ids"); // 需要删除的成绩Id号
			// System.out.println(ids);
			Integer result = scoreService.deletes(ids);
			if (result > 0) {
				message.setMessage("所选成绩信息删除成功！");
			} else {
				message.setMessage("所选成绩信息删除失败！请检查之后再进行操作……");
			}
			message.setResult(result);
			Gson gson = new Gson();
			String jsonString = gson.toJson(message);
			Tool.returnJsonString(response, jsonString);
		}
	}
}
