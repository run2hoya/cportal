package com.castis.cportal.service;

import com.castis.commonLib.util.ObjectMapperUtils;
import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.dto.EditContentDto;
import com.castis.cportal.dto.company.CompanyByType;
import com.castis.cportal.dto.company.CompanyGroupByType;
import com.castis.cportal.dto.company.CompanyInfoDto;
import com.castis.cportal.dto.company.CompanyTitleDto;
import com.castis.cportal.model.Company;
import com.castis.cportal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyGroupByType getCompanyTitleDto() {

        List<CompanyTitleDto> premier = companyRepository.findByProductType(ProductType.PREMIER);
        List<CompanyTitleDto> normal = companyRepository.findByProductType(ProductType.NORMAL);
        List<CompanyTitleDto> etc = companyRepository.findByProductType(ProductType.ETC);

        return new CompanyGroupByType(premier, normal, etc);
    }

    public Company getCompany(int companyId) {
        return companyRepository.findOne(companyId);
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public void saveCompany(CompanyInfoDto companyInfo) {
        Company company = ObjectMapperUtils.map(companyInfo, Company.class);
        companyRepository.save(company);
    }

    public CompanyByType countTotalCompanyByType() {
        List<Object[]> res = companyRepository.countTotalCompanyByType();

        CompanyByType companyByType = new CompanyByType(res.size());

        int i = 0;
        for (Object[] row : res) {
            companyByType.getLabels()[i] = (String) row[0];
            BigInteger bigInteger = (BigInteger)row[1];
            companyByType.getSeries()[i] = bigInteger.intValue();
            i++;
        }

        return companyByType;
    }

    @Transactional
    public void updateCompanyContent(int companyId, EditContentDto editContentDto) {
        Company company = companyRepository.findOne(companyId);
        company.setContent(editContentDto.getContent());
        companyRepository.save(company);
    }




}
