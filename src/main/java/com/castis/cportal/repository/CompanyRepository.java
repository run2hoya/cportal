package com.castis.cportal.repository;

import com.castis.cportal.common.enumeration.CompanyProductType;
import com.castis.cportal.dto.company.CompanyTitleDto;
import com.castis.cportal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<CompanyTitleDto> findAllProjectedBy();

    List<CompanyTitleDto> findByCompanyProductType(CompanyProductType companyProductType);

    @Query(value = "SELECT companyType as type, COUNT(c.id) as cnt FROM tbl_company AS c " +
            "WHERE companyType IS NOT NULL GROUP BY companyType ", nativeQuery = true)
    List<Object[]> countTotalCompanyByType();
}