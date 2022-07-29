package com.castis.cportal.dto.company;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CompanyDto implements Serializable {
    private Integer id;
    private Integer registerId;
    private String companyName;
    private String companyNameEn;
    private String companyCeo;
    private String companyEmail;
    private String companyDes;
    private String companyView;
    private ProductType productType;
    private String content;
    private LocalDateTime registDate;
    private LocalDateTime updateDate;
}
