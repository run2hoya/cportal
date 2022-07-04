package com.castis.cportal.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.MDC;

import com.castis.cportal.common.setting.CportalSetting;

public class MDCConfigListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//MDC Properties Setting...
		MDC.put(CportalSetting.PRODUCT_VERSION_KEY,CportalSetting.PRODUCT_VERSION);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
