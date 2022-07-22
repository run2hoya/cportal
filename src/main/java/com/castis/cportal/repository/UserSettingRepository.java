package com.castis.cportal.repository;

import com.castis.cportal.model.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingRepository extends JpaRepository<UserSetting, Integer> {
}