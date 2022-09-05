package com.castis.cportal.dto.company;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CompanyTitleDto implements Serializable {
    private final Integer id;
    private final String companyName;
    private final String companyNameEn;
    private final String companyCeo;
    private final String companyDes;
    private final String companybg;
    private final ProductType productType;
    private final String companyType;
    private final String companyEmail;
    private final String phone;
}
