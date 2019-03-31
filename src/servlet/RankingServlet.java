package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import service.ClassService;
import service.CourseService;
import service.RankingService;
import tools.Message;
import bean.Class;
import bean.Ranking;

public class RankingServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type1");
		RankingService rankingService = new RankingService();
		Message message = new Message();
		if (type.equals("ranking")) {
			ClassService classService = new ClassService();
			List<Class> classes = classService.getAllClassesList();
			CourseService courseService = new CourseService();
			List<String> terms = courseService.getAllTermsList();

			String classId = request.getParameter("classId");
			String termsString = request.getParameter("terms");
			List<Ranking> rankingResult = new ArrayList<Ranking>();
			if (classId != null && termsString != null) {
				rankingResult = rankingService.getRankingList(classId, termsString);
				rankingService.computeRankingList(rankingResult);
			}

			request.setAttribute("rankingClassId", classId);
			request.setAttribute("rankingTerms", terms);
			request.setAttribute("classes", classes);
			request.setAttribute("terms", terms);
			request.setAttribute("rankingResult", rankingResult);

			getServletContext().getRequestDispatcher("/user/manage/studentRanking.jsp").forward(request, response);
		}
	}
}
