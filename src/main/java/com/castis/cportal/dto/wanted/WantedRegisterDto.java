package com.castis.cportal.dto.wanted;

import com.castis.cportal.common.enumeration.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WantedRegisterDto implements Serializable {

    private int registerId;
    private String title;
    private String wantedType;
    private ProductType productType;
    private String bgImg = "/cportalFile/img/wanted.jpg";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    private long id;
    private Integer viewCnt = 0;
    private Integer candidateCnt = 0;
    private Integer likeCnt = 0;
    private String jobType = "기타";
    private Boolean open = false;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime registDate;

    public WantedRegisterDto(String title, String wantedType, ProductType productType,
                             String bgImg, LocalDate startDate, LocalDate endDate, long id,
                             Integer viewCnt, Integer candidateCnt, Integer likeCnt, String jobType, LocalDateTime registDate
            , String email) {
        this.title = title;
        this.wantedType = wantedType;
        this.productType = productType;
        this.bgImg = bgImg;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.viewCnt = viewCnt;
        this.candidateCnt = candidateCnt;
        this.likeCnt = likeCnt;
        this.jobType = jobType;
        this.registDate = registDate;
        this.email = email;;
    }
}
