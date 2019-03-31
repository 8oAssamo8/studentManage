package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Class;
import bean.User;
import dao.ClassDao;
import dao.DatabaseDao;
import dao.UserDao;
import net.sf.json.JSONObject;
import tools.PageInformation;

public class ClassService {
	public Integer addClass(Class class1) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			if (classDao.hasSameIdAndSameNameClass(class1, databaseDao)) {
				return 0; // 数据库中存在信息完全相同的班级，添加失败
			} else if (classDao.hasSameIdClass(class1, databaseDao)) {
				return 2; // 数据库中存在编号相同的班级，添加失败
			} else if (classDao.hasSameNameClass(class1, databaseDao)) {
				return 3; // 数据库中存在名称相同的班级，添加失败
			} else {
				if (classDao.addClass(class1, databaseDao) > 0) {
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

	// 获取所有班级信息，返回Map映射类型
	public Map<String, String> getAllClassesMap() {
		Map<String, String> classes = new HashMap<String, String>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			classes = classDao.getAllClassesMap(databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	// 获取所有班级信息，返回list类型
	public List<Class> getAllClassesList() {
		List<Class> classes = new ArrayList<Class>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			classes = classDao.getAllClassesList(databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public List<Class> changeClass(PageInformation pageInformation) {
		List<Class> classes = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			classDao.changeClasses(pageInformation.getIds(), pageInformation.getNewName(), databaseDao);
			pageInformation.setIds(null);
			classes = ClassDao.getOnePageClass(pageInformation, databaseDao);
		} catch (SQLException e) {
			classes = null;
			e.printStackTrace();
		} catch (Exception e) {
			classes = null;
			e.printStackTrace();
		}
		return classes;
	}

	// 判断是否存在同名班级
	public Integer hasSameNameClass(String className) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			if (classDao.hasSameNameClass1(className, databaseDao)) {
				return 0; // 数据库中存在名称完全相同的班级，不能进行后续的修改操作
			}
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;// 数据库操作失败
		} catch (Exception e) {
			e.printStackTrace();
			return -2;// 其他异常
		}
	}

	/* 获取班级信息（JqGrid显示部分） */
	public JSONObject getClassInfoJqGrid(Integer rows, Integer page, String sidx, String sord) {
		DatabaseDao databaseDao;
		JSONObject result = null;
		try {
			databaseDao = new DatabaseDao();
			ClassDao classDao = new ClassDao();
			result = classDao.getClassInfoJqGrid(rows, page, sidx, sord, databaseDao);
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
			ClassDao classDao = new ClassDao();
			return classDao.deletes(ids, databaseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
