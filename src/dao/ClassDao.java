package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Class;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import bean.User;
import tools.PageInformation;
import tools.Tool;

public class ClassDao {
	public boolean hasSameIdAndSameNameClass(Class class1, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from class where classId='" + class1.getClassId() + "' and className='"
				+ class1.getClassName() + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public boolean hasSameIdClass(Class class1, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from class where classId='" + class1.getClassId() + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public boolean hasSameNameClass(Class class1, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from class where className='" + class1.getClassName() + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public boolean hasSameNameClass1(String className, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from class where className='" + className + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public Integer addClass(Class class1, DatabaseDao databaseDao) throws SQLException {
		String sql = "insert into class(classId,className) values('" + class1.getClassId() + "','"
				+ class1.getClassName() + "')";
		return databaseDao.update(sql);
	}

	// 获取所有班级信息，返回Map映射类型
	public Map<String, String> getAllClassesMap(DatabaseDao databaseDao) throws SQLException {
		Map<String, String> classes = new HashMap<String, String>();
		String sql = "select * from class";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			classes.put(databaseDao.getString("classId"), databaseDao.getString("className"));
		}
		return classes;
	}

	// 获取所有班级信息，返回list类型
	public List<Class> getAllClassesList(DatabaseDao databaseDao) throws SQLException {
		List<Class> classes = new ArrayList<Class>();
		String sql = "select * from class";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			Class class1 = new Class();
			class1.setClassId(databaseDao.getString("classId"));
			class1.setClassName(databaseDao.getString("className"));
			classes.add(class1);
		}
		return classes;
	}

	public static List<Class> getOnePageClass(PageInformation pageInformation, DatabaseDao databaseDao)
			throws SQLException {
		List<Class> classes = new ArrayList<Class>();
		String sqlCount = Tool.getSql(pageInformation, "count");
		Integer allRecordCount = databaseDao.getCount(sqlCount);// 符合条件的总记录数
		Tool.setPageInformation(allRecordCount, pageInformation);// 更新pageInformation的总页数等

		String sqlSelect = Tool.getSql(pageInformation, "select");
		databaseDao.query(sqlSelect);
		while (databaseDao.next()) {
			Class class1 = new Class();
			class1.setClassId(databaseDao.getString("classId"));
			class1.setClassName(databaseDao.getString("className"));
			classes.add(class1);
		}
		return classes;
	}

	// 修改单个班级名称
	public Integer changeClasses(String ids, String newName, DatabaseDao databaseDao) throws SQLException {// 修改失败返回-1
		if (ids != null && newName != null) {
			String sql = "UPDATE class SET className ='" + newName + "' WHERE classId = " + ids;
			return databaseDao.update(sql);
		} else
			return -1;
	}

	/* 获取班级信息（JqGrid显示部分） */
	public JSONObject getClassInfoJqGrid(Integer rows, Integer page, String sidx, String sord,
			DatabaseDao databaseDao) {
		String selectInfoSQL = "SELECT classId,className FROM class ORDER BY " + sidx + " " + sord + " LIMIT "
				+ (page - 1) * rows + "," + page * rows;
		String selectPageSQL = "SELECT COUNT(*) as count FROM class";
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
				row.put("id", databaseDao.getString("classId"));
				JSONArray rowCell = new JSONArray();
				rowCell.add(databaseDao.getString("classId"));
				rowCell.add(databaseDao.getString("className"));
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

	// 删除多个班级信息
	public Integer deletes(String ids, DatabaseDao databaseDao) throws SQLException {// 删除失败返回-1
		if (ids != null && ids.length() > 0) {
			String sql = "delete from class where classId in (" + ids + ")";
			return databaseDao.update(sql);
		} else
			return -1;
	}
}
