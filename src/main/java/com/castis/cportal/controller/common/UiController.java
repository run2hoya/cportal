package com.castis.cportal.controller.common;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.dto.TransactionID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;



@Controller
public class UiController extends AbstrctController{
	
	private String jspName = "empty";
	private static Log log = LogFactory.getLog(UiController.class);
	

	@RequestMapping(value = { "/","/index"})
	public String goIndex(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;

		try {
			trId = startLog(request, Constants.request.GET, user);
			jspName = "/main";
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage"; 
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}
		return jspName;
	}
	
	@RequestMapping(value = "/convert", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goConvertMain(HttpServletRequest req, Model model, Principal user) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		
		try {
			trId = startLog(req, Constants.request.GET, user);			
		} catch (Exception e) {
			log.error("", e);
			req.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}

		return jspName;
	}

	@RequestMapping(value = "/hunter", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String hunterMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/hunter";
	}

	@RequestMapping(value = "/resume", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String resumeMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/resume";
	}

	@RequestMapping(value = "/sellCompany", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String sellCompanyMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/sellCompany";
	}

	@RequestMapping(value = "/alba", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String albaMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/alba";
	}

	@RequestMapping(value = "/business", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String businessMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/business";
	}

	@RequestMapping(value = "/job", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String jobMain(HttpServletRequest req, Model model, Principal user) {
		return "/jobcast/job";
	}
	@RequestMapping(value = "/company114", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String company114Main(HttpServletRequest req, Model model, Principal user) {
		return "/company114";
	}

	@RequestMapping(value = "/cportal", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goCportalMain(HttpServletRequest req, Model model, Principal user) {
		return "/cportal";
	}

	@RequestMapping(value = "/setting", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goUserSettingMain(HttpServletRequest req, Model model, Principal user) {
		return "/login/setting";
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

	@RequestMapping(value = { "/errorPage"})
	public String goErrorPage(ModelMap model, HttpSession httpSession, String search, HttpServletRequest request){
		request.setAttribute("errorCode", "500");
		jspName = "/common/errorPage";
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
		
		
		
		
