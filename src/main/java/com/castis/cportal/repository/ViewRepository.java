package com.castis.cportal.repository;

import com.castis.cportal.model.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ViewRepository extends JpaRepository<View, Long> {


    List<View> findByViewTableIdAndViewDateBetween(long viewTableId, LocalDate start, LocalDate end);
}