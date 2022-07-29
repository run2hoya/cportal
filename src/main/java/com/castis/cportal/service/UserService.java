package com.castis.cportal.service;

import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.exception.CiException;
import com.castis.commonLib.util.ObjectMapperUtils;
import com.castis.cportal.dto.UserDto;
import com.castis.cportal.dto.UserSettingDto;
import com.castis.cportal.model.User;
import com.castis.cportal.model.UserSetting;
import com.castis.cportal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserService {

	@Autowired UserRepository userRepository;

	public User getUser(int id) {return userRepository.findOne(id);}
	public List<User> getUserList(){
		return userRepository.findAll();
	}

	public ResponseEntity<ResultDetail> saveUser(UserDto userDto) {

		try {
			User user = ObjectMapperUtils.map(userDto, User.class);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setUserSetting(new UserSetting());
			userRepository.saveAndFlush(user);


		
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.OK, ResultCode.OK_NAME, null), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			log.info("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.DUPLICATE_ID, ResultCode.DUPLICATE_ID_NAME
					, "동일한 ID가 존재합니다"), HttpStatus.OK);
	    } catch (Exception e) {
	    	log.info("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.DB_GENERAL_ERROR, ResultCode.DB_GENERAL_ERROR_NAME
					, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}				
	}
	
	@Transactional
	public void updateUser(String userId, UserDto userDto) {
		User savedUser = userRepository.findTopByUserId(userId);
		savedUser.setEmail(userDto.getEmail());
		savedUser.setNickName(userDto.getNickName());
		savedUser.setPhone(userDto.getPhone());
		if(StringUtils.isNotEmpty(userDto.getUserImg()))
			savedUser.setUserImg(userDto.getUserImg());
		savedUser.setIntroduction(userDto.getIntroduction());
		userRepository.saveAndFlush(savedUser);
	}

	@Transactional
	public void updateUserSetting(String userId, UserSettingDto userSettingDto) {
		User savedUser = userRepository.findTopByUserId(userId);
		UserSetting userSetting = savedUser.getUserSetting();
		userSetting.setAdNotiEmail(userSettingDto.getAdNotiEmail());
		userSetting.setAdNotiApp(userSettingDto.getAdNotiApp());
		userSetting.setJobcastNotiEmail(userSettingDto.getJobcastNotiEmail());
		userSetting.setJobcastNotiApp(userSettingDto.getJobcastNotiApp());
		userRepository.saveAndFlush(savedUser);
	}

	@Transactional
	public ResponseEntity<ResultDetail> updatePassword(String userId, UserDto userDto) {

		try {

			User savedUser = userRepository.findTopByUserId(userId);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (!passwordEncoder.matches(userDto.getPassword(), savedUser.getPassword()))
				throw new CiException("현재 비밀번호랑 일치 하지 않습니다.");

			savedUser.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
			userRepository.saveAndFlush(savedUser);

			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.OK, ResultCode.OK_NAME
					, "success."), HttpStatus.OK);
		} catch (CiException e) {
			log.error("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.CONFLICT_ID, ResultCode.CONFLICT_NAME
					, "현재 비밀번호랑 일치 하지 않습니다."), HttpStatus.OK);
		} catch (Exception e) {
			log.error("", e);
			return new ResponseEntity<ResultDetail>(new ResultDetail(ResultCode.DB_GENERAL_ERROR, ResultCode.DB_GENERAL_ERROR_NAME
					, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	public User getUserByUserID(String userId) {
		return userRepository.findTopByUserId(userId);
	}

	@Transactional
	public UserSettingDto getUserSettingByUserID(String userId) {
		UserSetting userSetting = userRepository.findTopByUserId(userId).getUserSetting();
		return ObjectMapperUtils.map(userSetting, UserSettingDto.class);
	}
	
	
}
