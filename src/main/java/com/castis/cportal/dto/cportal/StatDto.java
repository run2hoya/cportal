package com.castis.cportal.dto.cportal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StatDto implements Serializable {
    private Long companyCnt;
    private Long userCnt;
    private Long jobCnt;
    private Long workCnt;
    private Long albaCnt;
    private Long sellCompanyCnt;

}
