package test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import bean.Class;
import dao.ClassDao;
import dao.DatabaseDao;

public class MyFirstTest {
	static protected DatabaseDao databaseDao;
	static protected ClassDao classDao;

	@BeforeClass
	static public void beforeClass() throws Exception {
		DatabaseDao.drv = "com.mysql.jdbc.Driver";
		DatabaseDao.url = "jdbc:mysql://localhost:3306/student";
		DatabaseDao.usr = "root";
		DatabaseDao.pwd = "123456";
		databaseDao = new DatabaseDao();
		classDao = new ClassDao();
	}

	@Test
	public void testGetAllClassesMap() throws Exception {
		Map<String, String> result = classDao.getAllClassesMap(databaseDao);
		int count = result.size();
		assertEquals(count, 18);
	}

	@Test
	public void testHasSameIdAndSameNameClass() throws SQLException {
		Class class1 = new Class();
		class1.setClassId("201741");
		class1.setClassName("2017商英1班");
		boolean result = classDao.hasSameIdAndSameNameClass(class1, databaseDao);
		assertEquals(result, true);
	}
}
