package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Class;
import bean.Course;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.PageInformation;
import tools.Tool;;

public class CourseDao {
	public boolean hasCourse(Course course, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from course where courseId='" + course.getCourseId() + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public Integer addCourse(Course course, DatabaseDao databaseDao) throws SQLException {
		String sql = "insert into course(courseId,courseName,terms) values('" + course.getCourseId() + "','"
				+ course.getCourseName() + "','" + course.getTerms() + "')";
		return databaseDao.update(sql);
	}

	public Integer changeCourse(Course course, DatabaseDao databaseDao) throws SQLException {
		if (course.getCourseId() != null && course.getCourseId() != "") {
			String sql = "";
			if (course.getCourseName() != null && course.getTerms() == null) {
				sql = "UPDATE course SET courseName ='" + course.getCourseName() + "' WHERE courseId = "
						+ course.getCourseId();
			} else if (course.getCourseName() == null && course.getTerms() != null) {
				sql = "UPDATE course SET  terms = '" + course.getTerms() + "' WHERE courseId = " + course.getCourseId();
			} else if (course.getCourseName() != null && course.getTerms() != null) {
				sql = "UPDATE course SET courseName ='" + course.getCourseName() + "' ,terms = '" + course.getTerms()
						+ "' WHERE courseId = " + course.getCourseId();
			}
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

	public static List<Course> getOnePageCourse(PageInformation pageInformation, DatabaseDao databaseDao)
			throws SQLException {
		List<Course> courses = new ArrayList<Course>();
		String sqlCount = Tool.getSql(pageInformation, "count");
		Integer allRecordCount = databaseDao.getCount(sqlCount);// 符合条件的总记录数
		Tool.setPageInformation(allRecordCount, pageInformation);// 更新pageInformation的总页数等

		String sqlSelect = Tool.getSql(pageInformation, "select");
		databaseDao.query(sqlSelect);
		while (databaseDao.next()) {
			Course course = new Course();
			course.setCourseId(databaseDao.getString("courseId"));
			course.setCourseName(databaseDao.getString("courseName"));
			course.setTerms(databaseDao.getString("terms"));
			courses.add(course);
		}
		return courses;
	}

	// 获取所有学期信息，返回list类型
	public List<String> getAllTermsList(DatabaseDao databaseDao) throws SQLException {
		List<String> terms = new ArrayList<String>();
		String sql = "select distinct terms from course";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			String courseTerm = databaseDao.getString("terms");
			terms.add(courseTerm);
		}
		return terms;
	}

	// 根据学号获取学生选修的科目名称列表，返回list类型
	public List<Course> getCoursesByStudentId(String studentId, DatabaseDao databaseDao) throws SQLException {
		List<Course> courses = new ArrayList<Course>();
		String sql = "select course.courseId as courseId,courseName from course,score where course.courseId=score.courseId and studentId='"
				+ studentId + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			Course course = new Course();
			course.setCourseId(databaseDao.getString("courseId"));
			course.setCourseName(databaseDao.getString("courseName"));
			courses.add(course);
		}
		return courses;
	}

	/* 获取课程信息（JqGrid显示部分） */
	public JSONObject getCourseInfoJqGrid(Integer rows, Integer page, String sidx, String sord,
			DatabaseDao databaseDao) {
		String selectInfoSQL = "SELECT courseId,courseName,terms FROM course ORDER BY " + sidx + " " + sord + " LIMIT "
				+ (page - 1) * rows + "," + page * rows;
		String selectPageSQL = "SELECT COUNT(*) as count FROM course";
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
				row.put("id", databaseDao.getString("courseId"));
				JSONArray rowCell = new JSONArray();
				rowCell.add(databaseDao.getString("courseId"));
				rowCell.add(databaseDao.getString("courseName"));
				rowCell.add(databaseDao.getString("terms"));
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

	// 删除多个课程信息
	public Integer deletes(String ids, DatabaseDao databaseDao) throws SQLException {// 删除失败返回-1
		if (ids != null && ids.length() > 0) {
			String sql = "delete from course where courseId in (" + ids + ")";
			return databaseDao.update(sql);
		} else
			return -1;
	}
}
