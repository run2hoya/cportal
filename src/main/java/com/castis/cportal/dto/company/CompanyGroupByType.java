package com.castis.cportal.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompanyGroupByType {

    List<CompanyTitleDto> premier;
    List<CompanyTitleDto> normal;
    List<CompanyTitleDto> etc;
}
