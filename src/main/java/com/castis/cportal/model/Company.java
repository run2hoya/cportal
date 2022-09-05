package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="companyName")
    private String companyName;

    @Column(name="companyNameEn")
    private String companyNameEn;

    @Column(name="companyCeo")
    private String companyCeo;

    @Column(name="companyEmail")
    private String companyEmail;

    @Column(name="companyDes")
    private String companyDes;

    @Column(name="companyView")
    private String companyView;

    @Column(name="companyPlace")
    private String companyPlace;

    @Column(name="companyType")
    private String companyType;

    @Column(name="phone")
    private String phone;

    @Column(name="companybg")
    private String companybg;

    @Column(name="registerId")
    private Integer registerId;

    @Column(name="productType")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name="content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name="registDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime registDate;

    @Column(name="updateDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyNameEn='" + companyNameEn + '\'' +
                ", companyCeo='" + companyCeo + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyDes='" + companyDes + '\'' +
                ", companyView='" + companyView + '\'' +
                ", companyPlace='" + companyPlace + '\'' +
                ", companyType='" + companyType + '\'' +
                ", companybg='" + companybg + '\'' +
                ", companyProductType=" + productType +
                ", registDate=" + registDate +
                ", updateDate=" + updateDate +
                '}';
    }
}