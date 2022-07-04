package com.castis.cportal.controller.common;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.model.User;
import com.castis.cportal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
public class UserController extends AbstrctController{
		
	
	@Autowired
	public UserService userService;

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody public User getUser(HttpServletRequest req, @PathVariable("userId") String userId) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		User user = null;
		
		try {
			trId = startLog(req, Constants.request.GET);
			user = userService.getUserByUserID(userId);
			log.info(trId + user.toString());
		} catch (Exception e) {
			log.error("", e);
		}		
	
		endLog(startTime, Constants.request.GET, trId, null);
		return user;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	public ResponseEntity<ResultDetail> saveUser(HttpServletRequest req, @RequestBody final User user) {
		
		long startTime = System.currentTimeMillis();
		ResponseEntity<ResultDetail> result = null;
		TransactionID trId = null;		
		
		try {
			trId = startLog(req, Constants.request.POST, user.toString());
			result = userService.saveUser(user);
			log.info(trId + "result:" + result);
						
		} catch (Exception e) {
			log.error(trId + "ERROR", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
					"관리자에게 연락 부탁드립니다."), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	
		endLog(startTime, Constants.request.POST, trId, null);
		return result;
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
	public ResponseEntity<Boolean> updateUser(HttpServletRequest req,
			@PathVariable("id") int id, @RequestBody final User user) {
		
		long startTime = System.currentTimeMillis();		
		TransactionID trId = null;
		ResponseEntity<Boolean> response = null;
		
		try {
			trId = startLog(req, Constants.request.PUT, user.toString());
			userService.updateUser(id, user);
			response = new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			response = new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	
		endLog(startTime, Constants.request.PUT, trId, null);
		return response;
	}

}	
		
		
		
		
