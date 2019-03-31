package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Class;
import bean.Course;
import dao.ClassDao;
import dao.CourseDao;
import dao.DatabaseDao;
import net.sf.json.JSONObject;
import tools.PageInformation;

public class CourseService {
	public Integer addCourse(Course course) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			CourseDao courseDao = new CourseDao();
			if (courseDao.hasCourse(course, databaseDao)) {
				return 0; // 存在编号相同的课程，添加失败
			} else {
				if (courseDao.addCourse(course, databaseDao) > 0) {
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

	public List<Course> changeCourse(PageInformation pageInformation) {
		List<Course> courses = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			CourseDao courseDao = new CourseDao();
			Course course = new Course();
			course.setCourseId(pageInformation.getIds());
			course.setCourseName(pageInformation.getNewName());
			course.setTerms(pageInformation.getNewTerm());
			courseDao.changeCourse(course, databaseDao);
			pageInformation.setIds(null);
			courses = CourseDao.getOnePageCourse(pageInformation, databaseDao);
		} catch (SQLException e) {
			courses = null;
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			courses = null;
			e.printStackTrace();
		}
		return courses;
	}

	// 获取所有学期信息，返回list类型
	public List<String> getAllTermsList() {
		List<String> terms = new ArrayList<String>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			CourseDao courseDao = new CourseDao();
			terms = courseDao.getAllTermsList(databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return terms;
	}

	public List<Course> getCoursesByStudentId(String studentId) {
		List<Course> courses = new ArrayList<Course>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			CourseDao courseDao = new CourseDao();
			courses = courseDao.getCoursesByStudentId(studentId, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courses;
	}

	/* 获取课程信息（JqGrid显示部分） */
	public JSONObject getCourseInfoJqGrid(Integer rows, Integer page, String sidx, String sord) {
		DatabaseDao databaseDao;
		JSONObject result = null;
		try {
			databaseDao = new DatabaseDao();
			CourseDao courseDao = new CourseDao();
			result = courseDao.getCourseInfoJqGrid(rows, page, sidx, sord, databaseDao);
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
			CourseDao courseDao = new CourseDao();
			return courseDao.deletes(ids, databaseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
