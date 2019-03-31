package servlet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.mail.SimpleEmail;

import tools.EMailTool;
import tools.FileTool;
import tools.WebProperties;
import dao.DatabaseDao;

public class InitServlet extends HttpServlet {
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		// 初始化数据库参数
		DatabaseDao.drv = this.getServletContext().getInitParameter("drv");
		DatabaseDao.url = this.getServletContext().getInitParameter("url");
		DatabaseDao.usr = this.getServletContext().getInitParameter("usr");
		DatabaseDao.pwd = this.getServletContext().getInitParameter("pwd");
		// 初始化email参数
		EMailTool.simpleEmail = new SimpleEmail();
		EMailTool.emailHost = this.getServletContext().getInitParameter("emailHost");
		EMailTool.emailUserEmail = this.getServletContext().getInitParameter("emailUserEmail");
		EMailTool.emailUserName = this.getServletContext().getInitParameter("emailUserName");
		EMailTool.emailPassword = this.getServletContext().getInitParameter("emailPassword");
		EMailTool.domain = this.getServletContext().getInitParameter("domain");

		ServletContext servletContext = conf.getServletContext();
		FileTool.root = servletContext.getRealPath("\\");

		// 读取属性文件
		String fileDir = servletContext.getRealPath("\\WEB-INF\\web.properties");
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.properties().setFileName(fileDir));
		try {

			WebProperties.config = builder.getConfiguration();
			WebProperties.config.addProperty("projectRoot",
					servletContext.getRealPath(WebProperties.config.getString("projectName")));

		} catch (ConfigurationException cex) {
			cex.printStackTrace();
		}

		String d = this.getServletContext().getRealPath(WebProperties.config.getString("databaseBackupDir"));
		WebProperties.config.setProperty("databaseBackupDir", d);
	}
}
