package com.castis.cportal.service;

import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.cportal.model.User;
import com.castis.cportal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserService {

	@Autowired UserRepository userRepository;

	public List<User> getUserList(){
		return userRepository.findAll();
	}

	public ResponseEntity<ResultDetail> saveUser(User user) {

		try {

			userRepository.saveAndFlush(user);						
		
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.OK, ResultCode.OK_NAME, null), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			log.info("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.DUPLICATE_ID, ResultCode.DUPLICATE_ID_NAME
					, "동일한 ID가 존재합니다"), HttpStatus.CONFLICT);
	    } catch (Exception e) {
	    	log.info("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.DB_GENERAL_ERROR, ResultCode.DB_GENERAL_ERROR_NAME
					, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}				
	}
	
	@Transactional
	public void updateUser(int id, User user) {
		User savedUser = userRepository.getOne(id);
		savedUser.setEmail(user.getEmail());
		savedUser.setName(user.getName());
		savedUser.setPhone(user.getPhone());
		if(user.getUserImg() != null && !user.getUserImg().isEmpty())
			savedUser.setUserImg(user.getUserImg());
		savedUser.setIntroduction(user.getIntroduction());
		userRepository.saveAndFlush(savedUser);
	}
	

	
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	public User getUserByUserID(String userId) {
		return userRepository.findTopByUserID(userId);
	}	
	
	
}
