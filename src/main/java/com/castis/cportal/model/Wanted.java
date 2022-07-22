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

@Entity
@Table(name = "tbl_wanted")
@NoArgsConstructor
@Getter
@Setter
public class Wanted implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private int id;

	@Column(name="registerId")
    private int registerId;

	@Column(name="title")
	private String title;

	@Column(name="content", columnDefinition = "LONGTEXT")
	private String content;

	@Column(name="wantedType")
	private String wantedType;

	@Column(name="jobType")
	private String jobType;

	@Column(name="jobTypeDetail")
	private String jobTypeDetail;

	@Column(name="bgImg")
	private String bgImg;

	@Column(name="viewCnt")
	private int viewCnt;

	@Column(name="candidateCnt")
	private int candidateCnt;

	@Column(name="likeCnt")
	private int likeCnt;

	@Column(name="productType")
	@Enumerated(EnumType.STRING)
	private ProductType productType;

	@Column(name="startDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime startDate;

	@Column(name="endDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime endDate;

	@Column(name="registDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime registDate;

	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;


}
