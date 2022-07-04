package com.castis.cportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.castis.cportal.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findTopByUserID(String userId);
	
}
