package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Course;
import bean.Score;
import bean.SubjectScore;
import dao.ClassDao;
import dao.CourseDao;
import dao.DatabaseDao;
import dao.ScoreDao;
import net.sf.json.JSONObject;
import tools.PageInformation;

public class ScoreService {

	public Integer addScore(Score score) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			if (!scoreDao.hasScore(score, databaseDao)) {
				return 0; // 不存在该课程，添加失败
			} else if (!scoreDao.hasStudent(score, databaseDao)) {
				return -2; // 不存在该学生
			} else if (scoreDao.hasStudentScore(score, databaseDao)) {
				return -3;
			} else {
				if (scoreDao.addScore(score, databaseDao) > 0) {
					return 1; // 添加成功
				} else
					return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;// 数据库操作失败
		} catch (Exception e) {
			e.printStackTrace();
			return -2;// 其他异常
		}
	}

	public List<Score> changeScore(PageInformation pageInformation) {
		List<Score> scores = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			Score score = new Score();
			score.setCourseId(pageInformation.getIds());
			score.setStudentId(pageInformation.getNewName());
			score.setScore(pageInformation.getNewTerm());
			scoreDao.changeScore(score, databaseDao);
			pageInformation.setIds(null);
			scores = ScoreDao.getOnePageScore(pageInformation, databaseDao);
		} catch (SQLException e) {
			scores = null;
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			scores = null;
			e.printStackTrace();
		}
		return scores;
	}

	public SubjectScore getOneScore(String studentId, String courseId) {
		SubjectScore subjectScore = new SubjectScore();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			subjectScore = scoreDao.getOneScore(studentId, courseId, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectScore;
	}

	public List<SubjectScore> getAllScoreByTerms(String studentId, String term) {
		List<SubjectScore> resultList = new ArrayList<SubjectScore>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			resultList = scoreDao.getAllScoreByTerms(studentId, term, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	// 根据学号获取该学生选修课程所在的学期，以便根据学期数查询所有科目成绩
	public List<String> getAllTermsListByStudentId(String studentId) {
		List<String> terms = new ArrayList<String>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			terms = scoreDao.getAllTermsListByStudentId(studentId, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return terms;
	}

	/* 获取成绩信息（JqGrid显示部分） */
	public JSONObject getScoreInfoJqGrid(Integer rows, Integer page, String sidx, String sord) {
		DatabaseDao databaseDao;
		JSONObject result = null;
		try {
			databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			result = scoreDao.getScoreInfoJqGrid(rows, page, sidx, sord, databaseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 删除多条记录
	public Integer deletes(String ids) {
		DatabaseDao databaseDao;
		try {
			databaseDao = new DatabaseDao();
			ScoreDao scoreDao = new ScoreDao();
			return scoreDao.deletes(ids, databaseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
