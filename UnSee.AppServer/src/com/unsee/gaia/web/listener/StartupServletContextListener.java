package com.unsee.gaia.web.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.unsee.gaia.biz.constants.GAIAConstants;
import com.unsee.gaia.biz.services.GAIASessionProvider;
import com.unsee.gaia.dal.services.SqlSessionPool;
import com.unsee.gaia.web.util.Setting;
import com.unsee.tea.biz.constants.TeaConstants;
import com.unsee.tea.biz.services.TeaSessionProvider;

public class StartupServletContextListener implements ServletContextListener {
	private final static Logger logger = Logger
			.getLogger(StartupServletContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Servlet Context Destroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		String settingFilePath = event.getServletContext().getRealPath(
				"/WEB-INF/setting.properties");

		Setting setting = new Setting(settingFilePath);
		event.getServletContext().setAttribute("app.setting", setting);

		// load application template variables
		String templateVariables = event.getServletContext().getRealPath(
				"/META-INF/Template/globalVariable.properties");
		File fileTemplate = new File(templateVariables);
		if (fileTemplate.exists()) {
			FileInputStream fis = null;

			try {
				fis = new FileInputStream(templateVariables);
				BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "UTF-8"));  
				Properties globalVariableProps = new Properties();
				globalVariableProps.load(bf);
				event.getServletContext().setAttribute("template.variables",
						globalVariableProps);
			} catch (FileNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			} finally {
				if (fis != null)
					try {
						fis.close();
					} catch (IOException e) {
						logger.error(e);
					}
			}
		}

		// 注册数据源
		SqlSessionPool.getInstance().registerProvider(
				GAIAConstants.PROVIDER_GAIA,
				new GAIASessionProvider(setting.getAllProperties()));
		
		// 注册数据源
		SqlSessionPool.getInstance().registerProvider(
				TeaConstants.PROVIDER_BIZ,
				new TeaSessionProvider(setting.getAllProperties()));
	}
}
