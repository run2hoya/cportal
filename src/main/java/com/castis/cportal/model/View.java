package com.castis.cportal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="tbl_view", indexes = { @Index(name = "IDX_VIEW_DATE", columnList = "viewDate, viewTableId") })
@NoArgsConstructor
@Getter
@Setter
public class View implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="registerId")
    private Integer registerId;

	@Column(name="viewTableId")
	private Long viewTableId;

	@Column(name="viewDate")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate viewDate;

	@Column(name="timezone")
	private String timezone;

	@Column(name="des")
	private String des;

	@Column(name="bookingInfo")
	private String bookingInfo;

	@Column(name="registerName")
	private String registerName;

	@Column(name="isOnline")
	private Boolean isOnline;


	public View(Long viewTableId, LocalDate viewDate, String timezone) {
		this.viewTableId = viewTableId;
		this.viewDate = viewDate;
		this.timezone = timezone;
	}
}
