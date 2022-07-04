package com.castis.cportal.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.castis.cportal.common.setting.CportalSetting;

public class RequestInterceptor extends HandlerInterceptorAdapter {
	Logger logger = Logger.getLogger(RequestInterceptor.class);
	protected static final Class<org.springframework.web.bind.annotation.RequestMapping> annotationClass = org.springframework.web.bind.annotation.RequestMapping.class;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		try{
		if(MDC.get(CportalSetting.PRODUCT_VERSION_KEY)==null)
			MDC.put(CportalSetting.PRODUCT_VERSION_KEY, CportalSetting.PRODUCT_VERSION);
		}catch(Exception e){
			
		}
		
		return true;
	}
	
	
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView
	) throws Exception {
		
	}

	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
