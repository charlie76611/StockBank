package com.charlie.resource;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.charlie.resource.util.DateUtil;

@SuppressWarnings("serial")
public class AppInitServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {

		System.out.println("======================================================");
		System.out.println("StockBank Project is starting .... ");
		System.out.println("Starttime : " + DateUtil.formatDate(new Date().getTime(), AppConstants.ALTERNATIVE_DATETIME_PATTERN));
		System.out.println("Version : " + AppConstants.version + " ");
		System.out.println("Remark : Made by Charlie just for fun. ");
		System.out.println("======================================================");
	}

	//	
	//	public void initSystemConfig(Document document) {
	//		
	//		AppConstants.version = document.selectSingleNode("//system-config/version").getText();
	//		AppConstants.databaseType = document.selectSingleNode("//system-config/database-type").getText();
	//	}
	//	

	protected String getWebRealPath() throws ServletException {
		String webRealPath = getServletContext().getRealPath("/");
		if (webRealPath != null && webRealPath.endsWith(File.separator))
			webRealPath = webRealPath.substring(0, webRealPath.length() - 1);
		return webRealPath;
	}

	protected boolean isFileExists(String fileName) {
		return (new File(fileName)).exists();
	}
}
