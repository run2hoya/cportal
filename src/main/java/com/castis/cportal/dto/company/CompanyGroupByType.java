package com.castis.cportal.dto.company;

import lombok.Data;

import java.util.List;

@Data
public class CompanyGroupByType {

    List<CompanyTitleDto> platinum;
    List<CompanyTitleDto> premier;
    List<CompanyTitleDto> normal;
    List<CompanyTitleDto> etc;


    public CompanyGroupByType(List<CompanyTitleDto> platinum, List<CompanyTitleDto> premier,
                              List<CompanyTitleDto> normal, List<CompanyTitleDto> etc) {
        this.platinum = platinum;
        this.premier = premier;
        this.normal = normal;
        this.etc = etc;
    }
}
