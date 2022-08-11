package com.castis.cportal.controller.common;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.ObjectMapperUtils;
import com.castis.cportal.dto.UserDto;
import com.castis.cportal.dto.UserSettingDto;
import com.castis.cportal.model.User;
import com.castis.cportal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
@Slf4j
public class UserController extends AbstrctController{
		
	
	@Autowired
	public UserService userService;

	@RequestMapping(value = { "/logout"})
	public String goLogOut(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		String jspName = "empty";

		try {
			trId = startLog(request, Constants.request.GET, user);
			httpSession.invalidate();
			jspName = "/login/login";
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}
		return jspName;
	}

	@RequestMapping(value = { "/login"})
	public String goLogIn(ModelMap model, HttpSession httpSession, HttpServletRequest request, @RequestParam(required=false) Boolean error, Principal user){

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		String jspName = "empty";
		try {
			trId = startLog(request, Constants.request.GET, user);

			String uri =  request.getRequestURL().toString().replace(request.getRequestURI(), "");
			log.info("OriginalURI ==>" + uri );
			if(uri.contains("dododo")) {
				model.addAttribute("userId", 22);
				model.addAttribute("page", "board");
				jspName = "/dododo";
			} else {
				if(error != null && error == Boolean.FALSE) {
					log.info(trId + " ########  로그인 실패   ########");
					model.addAttribute("description", "가입하지 않은 Id이거나, 잘못된 비밀번호입니다.");
				}

				jspName = "/login/login";
			}

		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}

		return jspName;
	}

	@RequestMapping(value = { "/register"})
	public String goUserRegister(ModelMap model, HttpSession httpSession, HttpServletRequest request, Principal user){

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		String jspName = "empty";
		try {
			trId = startLog(request, Constants.request.GET, user);
			jspName = "/login/register";
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("errorCode", "500");
			jspName = "/common/errorPage";
		} finally {
			endLog(startTime, Constants.request.GET, trId, null);
		}

		return jspName;
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody public UserDto getUser(HttpServletRequest req, @PathVariable("userId") String userId) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		UserDto userDto = null;
		
		try {
			trId = startLog(req, Constants.request.GET);
			User user = userService.getUserByUserID(userId);
			userDto = ObjectMapperUtils.map(user, UserDto.class);
			log.info(trId + userDto.toString());
		} catch (Exception e) {
			log.error("", e);
		}		
	
		endLog(startTime, Constants.request.GET, trId, null);
		return userDto;
	}

	@RequestMapping(value = "/user/setting/{userId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody public UserSettingDto getUserSetting(HttpServletRequest req, @PathVariable("userId") String userId) {

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		UserSettingDto userSettingDto = null;

		try {
			trId = startLog(req, Constants.request.GET);
			userSettingDto = userService.getUserSettingByUserID(userId);
			log.info(trId + userSettingDto.toString());
		} catch (Exception e) {
			log.error("", e);
		}

		endLog(startTime, Constants.request.GET, trId, null);
		return userSettingDto;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public ResponseEntity<?> saveUser(HttpServletRequest req, @RequestBody final UserDto userDto) {
		
		long startTime = System.currentTimeMillis();
		ResponseEntity<ResultDetail> result = null;
		TransactionID trId = null;		
		
		try {
			trId = startLog(req, Constants.request.POST, userDto.toString());
			result = userService.saveUser(trId, userDto);
			log.info(trId + "result:" + result);
						
		} catch (Exception e) {
			log.error(trId + "ERROR", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
					"관리자에게 연락 부탁드립니다."), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	
		endLog(startTime, Constants.request.POST, trId, null);
		return result;
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
	public ResponseEntity<Boolean> updateUser(HttpServletRequest req,
			@PathVariable("userId") String userId, @RequestBody final UserDto userDto) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		ResponseEntity<Boolean> response = null;
		
		try {
			trId = startLog(req, Constants.request.PUT, userDto.toString());
			userService.updateUser(userId, userDto);
			response = new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			response = new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			endLog(startTime, Constants.request.PUT, trId, null);
		}

		return response;
	}

	@RequestMapping(value = "/user/setting/{userId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
	public ResponseEntity<Boolean> updateUserSetting(HttpServletRequest req,
											  @PathVariable("userId") String userId, @RequestBody final UserSettingDto userSettingDto) {

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		ResponseEntity<Boolean> response = null;

		try {
			trId = startLog(req, Constants.request.PUT, userSettingDto.toString());
			userService.updateUserSetting(userId, userSettingDto);
			response = new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			response = new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			endLog(startTime, Constants.request.PUT, trId, null);
		}

		return response;
	}

	@RequestMapping(value = "/user/password/{userId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
	public ResponseEntity<ResultDetail> updatePassword(HttpServletRequest req,
											  @PathVariable("userId") String userId, @RequestBody final UserDto userDto) {

		long startTime = System.currentTimeMillis();
		TransactionID trId = null;
		ResponseEntity<ResultDetail> response = null;

		try {
			trId = startLog(req, Constants.request.PUT, userDto.toString());
			response = userService.updatePassword(userId, userDto);
		} catch (Exception e) {
			log.error("", e);
			response = new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME
					, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			endLog(startTime, Constants.request.PUT, trId, null);
		}

		return response;
	}

}	
		
		
		
		
