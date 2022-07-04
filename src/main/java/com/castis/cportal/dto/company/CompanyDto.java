package com.castis.cportal.dto.company;

import com.castis.cportal.common.enumeration.CompanyProductType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CompanyDto implements Serializable {
    private final Integer id;
    private final String companyName;
    private final String companyNameEn;
    private final String companyCeo;
    private final String companyEmail;
    private final String companyDes;
    private final String companyView;
    private final CompanyProductType companyProductType;
    private final String content;
    private final LocalDateTime registDate;
    private final LocalDateTime updateDate;
}
