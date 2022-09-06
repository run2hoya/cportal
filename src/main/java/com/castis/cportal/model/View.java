package com.castis.cportal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="tbl_view", indexes = { @Index(name = "IDX_REGISTER_ID", columnList = "registerId") })
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

	@Column(name="ownerId")
	private Integer ownerId;

	@Column(name="viewDate")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate viewDate;

	@Column(name="booking")
	private String booking;

}
