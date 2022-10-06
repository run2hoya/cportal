package com.castis.cportal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_view_setting")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewSetting implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="viewMemberName")
	private String memo;

	@Column(name="account")
	private String account;

	@Column(name="viewTableId")
	private Long viewTableId;

	@Column(name="maxMonth")
	private Integer maxMonth = 1;



	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;


}
