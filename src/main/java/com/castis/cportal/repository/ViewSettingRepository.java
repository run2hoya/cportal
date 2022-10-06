package com.castis.cportal.repository;

import com.castis.cportal.model.ViewSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewSettingRepository extends JpaRepository<ViewSetting, Long> {

    ViewSetting findOneByViewTableId(Long viewTableId);

}