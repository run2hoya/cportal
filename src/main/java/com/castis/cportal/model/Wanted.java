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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_wanted", indexes = { @Index(name = "IDX_REGISTER_ID", columnList = "registerId") })
@NoArgsConstructor
@Getter
@Setter
public class Wanted implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="registerId")
    private Integer registerId;

	@Column(name="title")
	private String title;

	@Column(name="content", columnDefinition = "LONGTEXT")
	private String content;

	@Column(name="wantedType")
	private String wantedType;

	@Column(name="jobType")
	private String jobType ="기타";

	@Column(name="bgImg")
	private String bgImg;

	@Column(name="viewCnt")
	private Integer viewCnt = 0;

	@Column(name="candidateCnt")
	private Integer candidateCnt = 0;

	@Column(name="likeCnt")
	private Integer likeCnt = 0;

	@Column(name="open")
	private Boolean open = Boolean.FALSE;

	@Column(name="productType")
	@Enumerated(EnumType.STRING)
	private ProductType productType;

	@Column(name="email")
	private String email;

	@Column(name="startDate")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate startDate;

	@Column(name="endDate")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate endDate;

	@Column(name="registDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime registDate;

	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;


}
