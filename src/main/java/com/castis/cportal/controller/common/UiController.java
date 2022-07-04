package com.castis.cportal.controller.common;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.dto.TransactionID;



@Controller
public class UiController extends AbstrctController{
	
	private String jspName = "empty";
	private static Log log = LogFactory.getLog(UiController.class);
	

	@RequestMapping(value = { "/","/index", "/main"})
	public String goIndex(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request, Principal user, @RequestParam(value="type", required=false) String type){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		
		try {
			trId = startLog(request, Constants.request.GET, user);
			if(type == null || type.isEmpty())
				type = "channel";
			model.addAttribute("type", type);
			jspName = "/company114";
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		}
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	@RequestMapping(value = "/convert", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goConvertMain(HttpServletRequest req, Model model, Principal user) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		String jspName = "convert";
		
		try {
			trId = startLog(req, Constants.request.GET, user);			
		} catch (Exception e) {
			log.error("", e);
			req.setAttribute("errorCode", "500");			
			jspName = "/common/errorPage"; 
		}		
	
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}

	@RequestMapping(value = "/bypass", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goByPassMain(HttpServletRequest req, Model model, Principal user) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		String jspName = "byPass";
		
		try {
			trId = startLog(req, Constants.request.GET, user);			
		} catch (Exception e) {
			log.error("", e);
			req.setAttribute("errorCode", "500");			
			jspName = "/common/errorPage"; 
		}		
	
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	@RequestMapping(value = { "/logout"})
	public String goLogOut(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		
		try {
			trId = startLog(request, Constants.request.GET, user);
			httpSession.invalidate();
			jspName = "/login/login";	
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		}
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	@RequestMapping(value = { "/login"})
	public String goLogIn(ModelMap model, HttpSession httpSession, HttpServletRequest request, @RequestParam(required=false) Boolean error, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		
		try {
			trId = startLog(request, Constants.request.GET, user);
			
			if(error != null && error == Boolean.FALSE) {
				log.info(trId + " ########  로그인 실패   ########");
				model.addAttribute("description", "가입하지 않은 Id이거나, 잘못된 비밀번호입니다.");
			}
			
			jspName = "/login/login";	
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		}
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	@RequestMapping(value = { "/register"})
	public String goUserRegister(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		
		try {
			trId = startLog(request, Constants.request.GET, user);
			jspName = "/login/register";	
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		}
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	
	@RequestMapping(value = { "/setting"})
	public String goSetting(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		
		try {
			trId = startLog(request, Constants.request.GET, user);
			jspName = "/setting";	
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		}
		endLog(startTime, Constants.request.GET, trId, null);
		return jspName;
	}
	
	
	
	
	@RequestMapping("/not_chrome")
	public String goNotChrome(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){		
		try {
			jspName = "/common/not_chrome";
		} catch (Exception e) {
			log.error("", e);
		}
		return jspName;
	}
	
	@RequestMapping(value = { "/errorPage/500"})
	public String go500ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "500");
		jspName = "/common/errorPage";
		return jspName;
	}
	
	@RequestMapping(value = { "/errorPage/505"})
	public String go505ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "505");
		jspName = "/common/errorPage";
		return jspName;
	}
	
	@RequestMapping(value = { "/errorPage/404"})
	public String go404ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "404");
		jspName = "/common/errorPage";
		return jspName;
	}
	
	@RequestMapping(value = { "/errorPage/403"})
	public String go403ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "403");
		jspName = "/common/errorPage403";
		return jspName;
	}
	@RequestMapping(value = { "/errorPage/402"})
	public String go402ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "402");
		jspName = "/common/errorPage";
		return jspName;
	}
	@RequestMapping(value = { "/errorPage/401"})
	public String go401ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "401");
		jspName = "/common/errorPage";
		return jspName;
	}
	@RequestMapping(value = { "/errorPage/400"})
	public String go400ErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "400");
		jspName = "/common/errorPage";
		return jspName;
	}

}	
		
		
		
		
