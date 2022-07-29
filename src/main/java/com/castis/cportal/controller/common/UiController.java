package com.castis.cportal.controller.common;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.dto.TransactionID;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;



@Controller
public class UiController extends AbstrctController{
	
	private String jspName = "empty";
	private static Log log = LogFactory.getLog(UiController.class);
	

	@RequestMapping(value = { "/","/index", "/main"})
	public String goIndex(ModelMap model, HttpSession httpSession, HttpServletRequest request,
						  @RequestParam(name = "page", required = false) String page, Principal user){
				
		long startTime = System.currentTimeMillis();
		TransactionID trId = null;

		try {
			trId = startLog(request, Constants.request.GET, user);
			if(StringUtils.isEmpty(page)) {
				page = "cportal";
			}
			model.addAttribute("page", page);
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

	@RequestMapping(value = "/wanted/popup/edit/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goWantedEditMain(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Model model, Principal user) {

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		String jspName = "popup";

		try {
			trId = startLog(req, Constants.request.GET, user);
			model.addAttribute("wantedId", wantedId);
			model.addAttribute("target", "/cportalJS/jobcast/popup/wantedPopupEditMain");
		} catch (Exception e) {
			log.error("", e);
			req.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}

		return jspName;
	}

	@RequestMapping(value = "/wanted/popup/view/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goWantedViewMain(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Model model, Principal user) {

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		String jspName = "popup";

		try {
			trId = startLog(req, Constants.request.GET, user);
			model.addAttribute("wantedId", wantedId);
			model.addAttribute("target", "/cportalJS/jobcast/popup/wantedPopupViewMain");
		} catch (Exception e) {
			log.error("", e);
			req.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}

		return jspName;
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
		
		
		
		
