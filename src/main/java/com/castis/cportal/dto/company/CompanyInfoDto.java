package com.castis.cportal.dto.company;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CompanyInfoDto implements Serializable {

    private String companyName;
    private String companyNameEn;
    private String companyCeo;
    private String companyEmail;
    private String companyDes;
    private String companyView;
    private ProductType productType;
    private String companyNew;
    private String companyPlace;
    private String companyType;
    private Integer registerId;
    private String phone;



}
