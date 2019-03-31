package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Course;
import bean.Score;
import bean.SubjectScore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.PageInformation;
import tools.Tool;

public class ScoreDao {

	public boolean hasScore(Score score, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from course where courseId='" + score.getCourseId() + "'";
		databaseDao.query(sql);
		if (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public boolean hasStudent(Score score, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from student where studentId='" + score.getStudentId() + "'";
		databaseDao.query(sql);
		if (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public boolean hasStudentScore(Score score, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from score where studentId='" + score.getStudentId() + "'" + " and courseId='"
				+ score.getCourseId() + "'";
		databaseDao.query(sql);
		if (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public Integer addScore(Score score, DatabaseDao databaseDao) throws SQLException {
		String sql = "insert into score(studentId,courseId,score) values('" + score.getStudentId() + "','"
				+ score.getCourseId() + "','" + score.getScore() + "')";
		return databaseDao.update(sql);
	}

	public Integer changeScore(Score score, DatabaseDao databaseDao) throws SQLException {
		if (score.getCourseId() != null && score.getCourseId() != "" && score.getStudentId() != null
				&& score.getStudentId() != "") {
			String sql = "";
			sql = "UPDATE score SET score =" + score.getScore() + " WHERE courseId = '" + score.getCourseId()
					+ "' and studentId='" + score.getStudentId() + "'";
			try {
				return databaseDao.update(sql);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				return -1;
			}
		} else
			return -1;
	}

	public static List<Score> getOnePageScore(PageInformation pageInformation, DatabaseDao databaseDao)
			throws SQLException {
		List<Score> scores = new ArrayList<Score>();
		String sqlCount = Tool.getSql(pageInformation, "count");
		Integer allRecordCount = databaseDao.getCount(sqlCount);// 符合条件的总记录数
		Tool.setPageInformation(allRecordCount, pageInformation);// 更新pageInformation的总页数等

		String sqlSelect = Tool.getSqlScore(pageInformation, "select");
		databaseDao.query(sqlSelect);
		while (databaseDao.next()) {
			Score score = new Score();
			score.setCourseId(databaseDao.getString("courseId"));
			score.setStudentId(databaseDao.getString("studentId"));
			score.setScore(databaseDao.getString("score"));
			scores.add(score);
		}
		return scores;
	}

	public SubjectScore getOneScore(String studentId, String courseId, DatabaseDao databaseDao) throws SQLException {
		SubjectScore subjectScore = new SubjectScore();
		String sql = "select courseName,score from course,score where studentId='" + studentId
				+ "' and score.courseId='" + courseId + "' and score.courseId=course.courseId";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			subjectScore.setCourseName(databaseDao.getString("courseName"));
			subjectScore.setScore(databaseDao.getInt("score"));
		}
		return subjectScore;
	}

	public List<SubjectScore> getAllScoreByTerms(String studentId, String term, DatabaseDao databaseDao)
			throws SQLException {
		List<SubjectScore> resultList = new ArrayList<SubjectScore>();
		String sql = "select courseName,score from course,score where studentId='" + studentId + "' and terms='" + term
				+ "' and score.courseId=course.courseId";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			SubjectScore subjectScore = new SubjectScore();
			subjectScore.setCourseName(databaseDao.getString("courseName"));
			subjectScore.setScore(databaseDao.getInt("score"));
			resultList.add(subjectScore);
		}
		return resultList;
	}

	// 根据学号获取该学生选修课程所在的学期，以便根据学期数查询所有科目成绩
	public List<String> getAllTermsListByStudentId(String studentId, DatabaseDao databaseDao) throws SQLException {
		List<String> terms = new ArrayList<String>();
		String sql = "select distinct terms from course,score where studentId='" + studentId
				+ "' and course.courseId=score.courseId";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			String courseTerm = databaseDao.getString("terms");
			terms.add(courseTerm);
		}
		return terms;
	}

	/* 获取成绩信息（JqGrid显示部分） */
	public JSONObject getScoreInfoJqGrid(Integer rows, Integer page, String sidx, String sord,
			DatabaseDao databaseDao) {
		String selectInfoSQL = "SELECT courseId,studentId,score FROM score ORDER BY " + sidx + " " + sord + " LIMIT "
				+ (page - 1) * rows + "," + page * rows;
		String selectPageSQL = "SELECT COUNT(*) as count FROM score";
		JSONObject result = new JSONObject();
		result.put("page", page); // 当前页数
		try {
			databaseDao.query(selectPageSQL);
			while (databaseDao.next()) {
				String pageCount = databaseDao.getString("count");
				result.put("total", Integer.parseInt(pageCount) / rows + 1); // 总页数
				result.put("records", pageCount); // 总记录数
			}
			JSONArray jsonArrayRows = new JSONArray();
			databaseDao.query(selectInfoSQL);
			while (databaseDao.next()) {
				JSONObject row = new JSONObject();
				row.put("id", databaseDao.getString("courseId") + "-" + databaseDao.getString("studentId")); // 利用“-”对课程号以及学号进行分割
				JSONArray rowCell = new JSONArray();
				rowCell.add(databaseDao.getString("courseId"));
				rowCell.add(databaseDao.getString("studentId"));
				rowCell.add(databaseDao.getString("score"));
				row.put("cell", rowCell);
				jsonArrayRows.add(row);
			}
			result.put("rows", jsonArrayRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 删除多个成绩信息
	public Integer deletes(String ids, DatabaseDao databaseDao) throws SQLException {// 删除失败返回-1
		String[] idsStrings = ids.split("-"); // 利用“-”对课程号以及学号进行分割
		if (ids != null && ids.length() > 0) {
			String sql = "delete from score where courseId='" + idsStrings[0] + "' and studentId='" + idsStrings[1]
					+ "'";
			return databaseDao.update(sql);
		} else
			return -1;
	}

}
