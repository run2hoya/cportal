package com.castis.cportal.repository;

import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.model.ViewTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewTableRepository extends JpaRepository<ViewTable, Long> {


    List<ViewTable> findByProductType(ProductType productType);

}