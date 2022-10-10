package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.BookingState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_view", indexes = { @Index(name = "IDX_VIEW_DATE", columnList = "viewDate, viewTableId") })
@NoArgsConstructor
@Getter
@Setter
@ToString
@OptimisticLocking(type = OptimisticLockType.VERSION)
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

	@Column(name="bookingInfo")
	private String bookingInfo;

	@Column(name="title")
	private String title;

	@Column(name="registerMember")
	private String registerMember;

	@Column(name="bookingState")
	@Enumerated(EnumType.STRING)
	private BookingState bookingState;

	@Column(name="isOnline")
	private Boolean isOnline;

	@Version
	private Integer version;

	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;

	public View(Long viewTableId, LocalDate viewDate, String timezone) {
		this.viewTableId = viewTableId;
		this.viewDate = viewDate;
		this.timezone = timezone;
	}
}
