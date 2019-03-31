package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tools.Encryption;
import tools.PageInformation;
import tools.Tool;
import tools.WebProperties;
import bean.Score;
import bean.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserDao {
	// 根据字段名，查是否有字段值为value的记录
	public int hasStringValue(String fieldName, String value, DatabaseDao databaseDao) throws SQLException {// 返回值：1表示有相同值、-1表示没有相同值
		databaseDao.query("select * from user where " + fieldName + "='" + value + "'");
		while (databaseDao.next()) {
			return 1;
		}
		return -1;
	}

	public boolean hasUser(User user, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from user where userId='" + user.getUserId() + "'";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public Integer register(User user, DatabaseDao databaseDao) throws SQLException {
		user.setHeadIconUrl("\\" + WebProperties.config.getString("projectName")
				+ WebProperties.config.getString("headIconFileDefault"));// 默认头像
		// 根据密码生成盐和加密密码
		Encryption.encryptPasswd(user);
		String sql = "insert into user(userId,password,enable,headIconUrl,addedSaltPassword,salt,email) values('"
				+ user.getUserId() + "','" + user.getPassword() + "','" + user.getEnable() + "','"
				+ user.getHeadIconUrl().replace("\\", "/") + "','" + user.getAddedSaltPassword() + "','"
				+ user.getSalt() + "','" + user.getEmail() + "')";
		return databaseDao.update(sql);
	}

	////////////////////////////////////////////////////////////////////////////////////////
	public Integer login(User user) throws SQLException, Exception {
		DatabaseDao databaseDao = new DatabaseDao();
		String sql = "";
		if (user.getUserId().length() == 12) {// student
			sql = "select * from student where studentId='" + user.getUserId() + "'";
			databaseDao.query(sql);
			while (databaseDao.next()) {// 有此用户
				user.setSalt(databaseDao.getString("salt"));
				String addedSaltPassword = databaseDao.getString("addedSaltPassword");
				boolean checked = Encryption.checkPassword(user, addedSaltPassword);
				if (checked) {// 密码正确
					user.setUserId(databaseDao.getString("studentId"));
					user.setStudentName(databaseDao.getString("studentName"));
					user.setClassId(databaseDao.getString("classId"));
					user.setAddedSaltPassword(addedSaltPassword);
					user.setSalt(databaseDao.getString("salt"));
					return 1;// 可以登录
				}
			}
		} else {// user
			sql = "select * from user where userId='" + user.getUserId() + "'";
			databaseDao.query(sql);
			while (databaseDao.next()) {// 有此用户
				user.setSalt(databaseDao.getString("salt"));
				String addedSaltPassword = databaseDao.getString("addedSaltPassword");
				boolean checked = Encryption.checkPassword(user, addedSaltPassword);
				if (checked) {// 密码正确
					String enable = databaseDao.getString("enable");
					if (("use").equals(enable)) {
						user.setUserId(databaseDao.getString("userId"));
						user.setHeadIconUrl(databaseDao.getString("headIconUrl"));
						user.setAddedSaltPassword(addedSaltPassword);
						user.setSalt(databaseDao.getString("salt"));
						user.setEmail(databaseDao.getString("email"));
						return 1;// 可以登录
					}
					return 0;// 用户存在，但被停用
				}
			}
		}
		return -1;// 该用户不存在或密码错误
	}

	/////////////////////////////////////////////////////////////////////////////////
	/*
	 * public Integer login(User user) throws SQLException, Exception {
	 * DatabaseDao databaseDao = new DatabaseDao(); String sql = ""; if
	 * (user.getUserId().length() == 12) { sql =
	 * "select * from student where studentId='" + user.getUserId() +
	 * "' and password='" + user.getPassword() + "'"; databaseDao.query(sql);
	 * while (databaseDao.next()) {
	 * user.setUserId(databaseDao.getString("studentId"));
	 * user.setStudentName(databaseDao.getString("studentName"));
	 * user.setClassId(databaseDao.getString("classId")); return 1;// 可以登录 } }
	 * else { sql = "select * from user where userId='" + user.getUserId() +
	 * "' and password='" + user.getPassword() + "'"; databaseDao.query(sql);
	 * while (databaseDao.next()) { String enable =
	 * databaseDao.getString("enable"); if (("use").equals(enable)) {
	 * user.setUserId(databaseDao.getString("userId"));
	 * user.setHeadIconUrl(databaseDao.getString("headIconUrl")); return 1;//
	 * 可以登录 } return 0;// 用户存在，但被停用 } } return -1;// 该用户不存在或密码错误 }
	 */
	public Integer changePassword(User user, String newPassword) throws Exception {
		DatabaseDao databaseDao = new DatabaseDao();
		String sql = "";
		Integer result;
		if (user.getUserId().length() == 12) {// student
			sql = "select * from student where studentId='" + user.getUserId() + "'";
			databaseDao.query(sql);
			while (databaseDao.next()) {// 有此用户
				user.setSalt(databaseDao.getString("salt"));
				String addedSaltPassword = databaseDao.getString("addedSaltPassword");
				boolean checked = Encryption.checkPassword(user, addedSaltPassword);
				if (checked) {// 旧密码正确
					user.setPassword(newPassword);
					Encryption.encryptPasswd(user);
					sql = "update student set password='" + newPassword + "',addedSaltPassword='"
							+ user.getAddedSaltPassword() + "',salt='" + user.getSalt() + "'" + " where studentId='"
							+ user.getUserId() + "'";
					result = databaseDao.update(sql);
					return result;
				}
			}
		} else {// user
			sql = "select * from user where userId='" + user.getUserId() + "'";
			databaseDao.query(sql);
			while (databaseDao.next()) {// 有此用户
				user.setSalt(databaseDao.getString("salt"));
				String addedSaltPassword = databaseDao.getString("addedSaltPassword");
				boolean checked = Encryption.checkPassword(user, addedSaltPassword);
				if (checked) {// 旧密码正确
					user.setPassword(newPassword);
					Encryption.encryptPasswd(user);
					sql = "update user set password='" + newPassword + "',addedSaltPassword='"
							+ user.getAddedSaltPassword() + "',salt='" + user.getSalt() + "'" + " where userId='"
							+ user.getUserId() + "'";
					result = databaseDao.update(sql);
					return result;
				}
			}
		}
		return 0; // 旧密码错误
	}
	/*
	 * public Integer changePassword(User user, String newPassword) throws
	 * Exception { DatabaseDao databaseDao = new DatabaseDao(); String sql = "";
	 * Integer result; if (user.getUserId().length() == 12) { sql =
	 * "select password from student where studentId='" + user.getUserId() +
	 * "'"; databaseDao.query(sql); while (databaseDao.next()) { if
	 * (user.getPassword().equals(databaseDao.getString("password"))) { // 旧密码正确
	 * sql = "update student set password='" + newPassword +
	 * "' where studentId='" + user.getUserId() + "'"; result =
	 * databaseDao.update(sql); return result; } } } else { sql =
	 * "select password from user where userId='" + user.getUserId() + "'";
	 * databaseDao.query(sql); while (databaseDao.next()) { if
	 * (user.getPassword().equals(databaseDao.getString("password"))) { // 旧密码正确
	 * sql = "update user set password='" + newPassword + "' where userId='" +
	 * user.getUserId() + "'"; result = databaseDao.update(sql); return result;
	 * } } } return 0; // 旧密码错误 }
	 */

	// 切换用户的可用性
	public Integer changeEnable(String id, DatabaseDao databaseDao) throws SQLException {// 查询失败返回-1
		String sql = "select * from user where userId in (" + id + ")";
		databaseDao.query(sql);
		while (databaseDao.next()) {
			String enable = databaseDao.getString("enable");
			if ("use".equals(enable))
				enable = "stop";
			else
				enable = "use";
			sql = "update user set enable='" + enable + "' where userId in (" + id + ")";
			databaseDao.update(sql);
			return 1;
		}
		return 0;
	}

	public List<User> getOnePage(PageInformation pageInformation, DatabaseDao databaseDao) throws SQLException {
		List<User> users = new ArrayList<User>();
		String sqlCount = Tool.getSql(pageInformation, "count");
		Integer allRecordCount = databaseDao.getCount(sqlCount);// 符合条件的总记录数
		Tool.setPageInformation(allRecordCount, pageInformation);// 更新pageInformation的总页数等

		String sqlSelect = Tool.getSql(pageInformation, "select");
		databaseDao.query(sqlSelect);
		while (databaseDao.next()) {
			User user = new User();
			user.setEnable(databaseDao.getString("enable"));
			user.setUserId(databaseDao.getString("userId"));
			users.add(user);
		}
		return users;
	}

	// 删除多个用户
	public Integer deletesStudent(String ids, DatabaseDao databaseDao) throws SQLException {// 删除失败返回-1
		if (ids != null && ids.length() > 0) {
			String sql = "delete from student where studentId in (" + ids + ")";
			return databaseDao.update(sql);
		} else
			return -1;
	}

	public static List<User> getOnePageStudent(PageInformation pageInformation, DatabaseDao databaseDao)
			throws SQLException {
		List<User> users = new ArrayList<User>();
		String sqlCount = Tool.getSql(pageInformation, "count");
		Integer allRecordCount = databaseDao.getCount(sqlCount);// 符合条件的总记录数
		Tool.setPageInformation(allRecordCount, pageInformation);// 更新pageInformation的总页数等

		String sqlSelect = Tool.getSql(pageInformation, "select");
		databaseDao.query(sqlSelect);
		while (databaseDao.next()) {
			User user = new User();
			user.setUserId(databaseDao.getString("studentId"));
			user.setStudentName(databaseDao.getString("studentName"));
			user.setClassId(databaseDao.getString("classId"));
			user.setPassword(databaseDao.getString("password"));
			users.add(user);
		}
		return users;
	}

	public boolean hasStudent(String studentId, DatabaseDao databaseDao) throws SQLException {
		String sql = "select * from student where studentId='" + studentId + "'";
		databaseDao.query(sql);
		if (databaseDao.next()) {
			return true;
		}
		return false;
	}

	public Integer addStudent(User user, DatabaseDao databaseDao) throws SQLException {
		if (user.getPassword() == null || user.getPassword() == "") {
			user.setPassword("123456");
		}
		String sql = "insert into student(studentId,studentName,classId,password) values('" + user.getUserId() + "','"
				+ user.getStudentName() + "','" + user.getClassId() + "','" + user.getPassword() + "')";
		return databaseDao.update(sql);
	}

	public Integer changeStudent(User user, DatabaseDao databaseDao) throws SQLException {
		if (user.getUserId() != null && user.getUserId() != "") {
			String sql = "";
			Integer result1 = 0, result2 = 0, result3 = 0;
			try {
				if (user.getStudentName() != null && user.getStudentName() != "") {
					sql = "UPDATE student SET studentName ='" + user.getStudentName() + "' WHERE studentId = '"
							+ user.getUserId() + "'";
					result1 = databaseDao.update(sql);
				}
				if (user.getClassId() != null && user.getClassId() != "") {
					sql = "UPDATE student SET classId ='" + user.getClassId() + "' WHERE studentId = '"
							+ user.getUserId() + "'";
					result2 = databaseDao.update(sql);
				}
				if (user.getPassword() != null && user.getPassword() != "") {
					sql = "UPDATE student SET password ='" + user.getPassword() + "' WHERE studentId = '"
							+ user.getUserId() + "'";
					result3 = databaseDao.update(sql);
				}
				if (result1 > 0 || result2 > 0 || result3 > 0)
					return 1;
				else
					return 0;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				return -1;
			}
		} else
			return -1;
	}

	public Integer updateHeadIcon(User user, DatabaseDao databaseDao) throws SQLException {//
		String sql = "update user set headIconUrl='" + user.getHeadIconUrl() + "' where userId ='" + user.getUserId()
				+ "'";
		return databaseDao.update(sql.replace("\\", "/"));
	}

	public boolean updatePassword(User user, DatabaseDao databaseDao) throws SQLException {
		String sql = "update user set password='" + user.getPassword() + "'," + "salt='" + user.getSalt()
				+ "',addedSaltPassword='" + user.getAddedSaltPassword() + "'" + " where email='" + user.getEmail()
				+ "'";
		// String sql = "update user set password='" + user.getPassword() + "'
		// where email='" + user.getEmail() + "'";
		boolean returnValue = false;
		if (databaseDao.update(sql) > 0)
			returnValue = true;// 成功更新密码和盐
		return returnValue;
	}

	/* 获取学生信息（JqGrid显示部分） */
	public JSONObject getStudentInfoJqGrid(Integer rows, Integer page, String sidx, String sord,
			DatabaseDao databaseDao) {
		String selectInfoSQL = "SELECT studentId,studentName,className FROM student,class WHERE student.classId=class.classId ORDER BY "
				+ sidx + " " + sord + " LIMIT " + (page - 1) * rows + "," + page * rows;
		String selectPageSQL = "SELECT COUNT(*) as count FROM student,class WHERE student.classId=class.classId";
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
				row.put("id", databaseDao.getString("studentId"));
				JSONArray rowCell = new JSONArray();
				rowCell.add(databaseDao.getString("studentId"));
				rowCell.add(databaseDao.getString("studentName"));
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

	// 删除多个学生信息
	public Integer deletes(String ids, DatabaseDao databaseDao) throws SQLException {// 删除失败返回-1
		if (ids != null && ids.length() > 0) {
			String sql = "delete from student where studentId in (" + ids + ")";
			return databaseDao.update(sql);
		} else
			return -1;
	}
}
