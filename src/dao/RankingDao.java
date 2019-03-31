package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Ranking;

public class RankingDao {
	public List<Ranking> getRankingList(String classId, String terms, DatabaseDao databaseDao) throws SQLException {
		List<Ranking> rankings = new ArrayList<Ranking>();
		String sql = "select student.studentId as studentId,studentName,ifnull(sum(score),0) as sumScore from student,class,score,course where class.classId='"
				+ classId
				+ "'and student.classId=class.classId and student.studentId=score.studentId and course.courseId=score.courseId and terms='"
				+ terms + "' group by student.studentId order by ifnull(sum(score),0) desc";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			Ranking ranking = new Ranking();
			ranking.setStudentId(databaseDao.getString("studentId"));
			ranking.setStudentName(databaseDao.getString("studentName"));
			ranking.setSumScore(databaseDao.getInt("sumScore"));
			rankings.add(ranking);
		}
		return rankings;
	}
}
