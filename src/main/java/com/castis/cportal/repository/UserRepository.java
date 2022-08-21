package com.castis.cportal.repository;

import com.castis.cportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findTopByUserId(String userId);

	@Query(value="SELECT DISTINCT u.email "
			+ "FROM tbl_user u "
			+ "left outer join tbl_user_setting us on u.user_setting_id= us.id "
			+ "where u.enabled = true and u.userType != 'EXTERNAL' and "
			+ "us.jobcastNotiEmail = true ",
			nativeQuery = true)
	List<String> getJobCastMailList();
	
}
