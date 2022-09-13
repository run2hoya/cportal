package com.castis.cportal.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class ShareController extends AbstrctController{
	

	@RequestMapping(value = "/popup/view/wanted/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goWantedViewMain(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Model model, Principal user) {
		model.addAttribute("targetId", wantedId);
		model.addAttribute("target", "/cportalJS/jobcast/popup/wantedPopupViewMain");
		return "popup";
	}

	@RequestMapping(value = "/popup/view/company/{companyId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goCompanyPopUp(HttpServletRequest req, @PathVariable("companyId") Long companyId, Model model, Principal user) {
		model.addAttribute("targetId", companyId);
		model.addAttribute("target", "/cportalJS/company114/popupCompany");
		return "popup";
	}

	@RequestMapping(value = "/popup/wanted/edit/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goWantedEditMain(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Model model, Principal user) {

		model.addAttribute("targetId", wantedId);
		model.addAttribute("target", "/cportalJS/jobcast/popup/wantedPopupEditMain");
		return "popup";
	}

	@RequestMapping(value = "/popup/view/booking/{viewId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	public String goViewMain(HttpServletRequest req, @PathVariable("viewId") Long viewId, Model model, Principal user) {

		model.addAttribute("targetId", viewId);
		model.addAttribute("target", "/cportalJS/view/bookingMain");
		return "booking";
	}




}	
		
		
		
		
