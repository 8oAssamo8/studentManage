package service;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tools.EMailTool;
import tools.Encryption;
import tools.FileTool;
import tools.PageInformation;
import tools.WebProperties;
import bean.Score;
import bean.User;
import dao.ClassDao;
import dao.DatabaseDao;
import dao.ScoreDao;
import dao.UserDao;
import net.sf.json.JSONObject;

public class UserService {
	public Integer register(User user) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao UserDao = new UserDao();
			if (UserDao.hasUser(user, databaseDao)) {
				return 0;// 失败，用户已存在
			} else {// 没有同名用户，可以注册
				if (UserDao.register(user, databaseDao) > 0)
					return 1; // 成功
				else
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

	public Integer login(User user) {
		int result = -2; // 数据库操作失败
		try {
			UserDao UserDao = new UserDao();
			return UserDao.login(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 修改密码
	public Integer changePassword(User user, String newPassword) {
		try {
			UserDao userDao = new UserDao();
			return userDao.changePassword(user, newPassword);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;// 数据库操作失败
		} catch (Exception e) {
			e.printStackTrace();
			return -2;// 其他异常
		}

	}

	public Integer findPasswordByEmail(User user, Integer rand) {// 返回值：1成功发送邮件，-1发送邮件失败，-2邮箱未注册过
		try {
			UserDao userDao = new UserDao();
			DatabaseDao databaseDao = new DatabaseDao();
			Integer result = 0;
			if (userDao.hasStringValue("email", user.getEmail(), databaseDao) == 1)// 该email存在
				result = EMailTool.sendReturnPassword(user.getEmail(), rand);// 发送邮件
			else// 该email不存在
				result = -2;
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3; // 出现其他错误
		}
	}

	public Integer updatePassword(User user) {// 返回值：1成功发送邮件，-1发送邮件失败，-2邮箱未注册过
		try {
			UserDao userDao = new UserDao();
			DatabaseDao databaseDao = new DatabaseDao();
			Integer result;
			// 根据密码生成盐和加密密码
			Encryption.encryptPasswd(user);
			if (userDao.updatePassword(user, databaseDao))// 修改密码成功
				result = 1;
			else// 修改密码失败！
				result = -1;
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		}
	}

	/*
	 * public Integer changePassword1(User user, String newPassword) { try {
	 * UserDao userDao = new UserDao(); DatabaseDao databaseDao = new
	 * DatabaseDao(); if (userDao.checkOldPassword(user, databaseDao)) {
	 * user.setPassword(newPassword); // 根据密码生成盐和加密密码
	 * Encryption.encryptPasswd(user); if (userDao.updatePassword(user,
	 * databaseDao)) return 1; else return -1; } else { return 0; } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); return -2; } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); return -3; } }
	 */
	public List<User> check(PageInformation pageInformation, String id) {
		List<User> users = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();

			if (id != null && !id.isEmpty())
				userDao.changeEnable(id, databaseDao);

			users = userDao.getOnePage(pageInformation, databaseDao);

		} catch (SQLException e) {
			users = null;
			e.printStackTrace();
		} catch (Exception e) {
			users = null;
			e.printStackTrace();
		}
		return users;
	}

	// 删除多条记录
	public List<User> deletes(PageInformation pageInformation) {
		List<User> users = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();
			userDao.deletesStudent(pageInformation.getIds(), databaseDao);
			pageInformation.setIds(null);
			users = userDao.getOnePageStudent(pageInformation, databaseDao);
		} catch (SQLException e) {
			users = null;
			e.printStackTrace();
		} catch (Exception e) {
			users = null;
			e.printStackTrace();
		}
		return users;
	}

	public List<User> getOnePageStudent(PageInformation pageInformation) {
		List<User> users = new ArrayList<User>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();
			users = userDao.getOnePageStudent(pageInformation, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public Integer addStudent(User user) {
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();
			if (userDao.hasStudent(user.getUserId(), databaseDao)) {
				return 0; // 已经存在该学生，添加失败
			} else {
				if (userDao.addStudent(user, databaseDao) > 0) {
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

	public List<User> changeStudent(String classId, PageInformation pageInformation) {
		List<User> users = null;
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();
			User user = new User();
			user.setUserId(pageInformation.getIds());
			user.setStudentName(pageInformation.getNewName());
			user.setClassId(classId);
			user.setPassword(pageInformation.getNewTerm());
			userDao.changeStudent(user, databaseDao);
			pageInformation.setIds(null);
			users = UserDao.getOnePageStudent(pageInformation, databaseDao);
		} catch (SQLException e) {
			users = null;
			e.printStackTrace();
		} catch (Exception e) {
			users = null;
			e.printStackTrace();
		}
		return users;
	}

	public Integer updatePrivate(User user, HttpServletRequest request) {
		Integer result;
		try {
			String oldHeadIconUrl = user.getHeadIconUrl();
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Configure a repository (to ensure a secure temp location is used)
			String fullPath = request.getServletContext().getRealPath(WebProperties.config.getString("tempDir"));// 获取相对路径的绝对路径
			File repository = new File(fullPath);
			factory.setRepository(repository);// 设置临时文件存放的文件夹
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解析request，将其中各表单元素和上传文件提取出来

			List<FileItem> items = upload.parseRequest(request);// items存放各表单元素
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {// 遍历表单元素
				FileItem item = iter.next();
				File uploadedFile;
				String randomFileName;
				do {
					randomFileName = FileTool.getRandomFileNameByCurrentTime(item.getName());
					String full = request.getServletContext()
							.getRealPath(WebProperties.config.getString("headIconDirDefault") + "\\" + randomFileName);
					uploadedFile = new File(full);
				} while (uploadedFile.exists());// 确保文件未存在

				item.write(uploadedFile);// 将临时文件转存为新文件保存
				result = 1;// 表示上传文件成功
				item.delete();// 删除临时文件
				result = 2;// 表示上传文件成功，且临时文件删除
				user.setHeadIconUrl("\\" + WebProperties.config.getString("projectName")
						+ WebProperties.config.getString("headIconDirDefault") + "\\" + randomFileName);
				// }
			}
			DatabaseDao databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();

			// 开始事务处理
			databaseDao.setAutoCommit(false);
			userDao.updateHeadIcon(user, databaseDao);// 更新用户表的头像
			databaseDao.commit();
			databaseDao.setAutoCommit(true);
			result = 3;// 表示上传文件成功，临时文件删除，且路径保存到数据库成功

			if (oldHeadIconUrl.contains(FileTool.getFileName(WebProperties.config.getString("headIconFileDefault"))))
				result = 5;//// 表示上传文件成功，临时文件删除，且路径保存到数据库成功，老的图片使用系统默认图片，不需要删除
			else// 老的图片没有使用系统默认图片，需要删除
			if (FileTool.deleteFile(new File(
					FileTool.root.replace("\\" + WebProperties.config.getString("projectName"), "") + oldHeadIconUrl)))
				result = 5;//// 表示上传文件成功，临时文件删除，且路径保存到数据库成功，老的图片被删除
			else
				result = 4;//// 表示上传文件成功，临时文件删除，且路径保存到数据库成功，老的图片无法被删除
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		}
		return result;
	}

	/* 获取学生信息（JqGrid显示部分） */
	public JSONObject getStudentInfoJqGrid(Integer rows, Integer page, String sidx, String sord) {
		DatabaseDao databaseDao;
		JSONObject result = null;
		try {
			databaseDao = new DatabaseDao();
			UserDao userDao = new UserDao();
			result = userDao.getStudentInfoJqGrid(rows, page, sidx, sord, databaseDao);
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
			UserDao userDao = new UserDao();
			return userDao.deletes(ids, databaseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
