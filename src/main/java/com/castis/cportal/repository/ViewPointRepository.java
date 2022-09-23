package com.castis.cportal.repository;

import com.castis.cportal.model.ViewPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewPointRepository extends JpaRepository<ViewPoint, Long> {

    List<ViewPoint> findByViewTableId(Long viewTableId);

}