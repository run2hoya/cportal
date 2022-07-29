package com.castis.cportal.dto.wanted;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WantedWithContentDto extends WantedRegisterDto {

    private String content;
    private String userImg;
    private String nickName;


    @Override
    public String toString() {
        return "WantedWithContentDto{} " + super.toString();
    }

    public WantedWithContentDto(String title, String wantedType, ProductType productType, String bgImg,
                                LocalDate startDate, LocalDate endDate,
                                long id, Integer viewCnt, Integer candidateCnt, Integer likeCnt,
                                String jobType, String userImg, String nickName, LocalDateTime registDate, String email) {
        super(title, wantedType, productType, bgImg, startDate, endDate, id, viewCnt,
                candidateCnt, likeCnt, jobType, registDate, email);
        this.userImg = userImg;
        this.nickName = nickName;
    }
}
