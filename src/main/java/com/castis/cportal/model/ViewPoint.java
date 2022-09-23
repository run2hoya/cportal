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
@Table(name="tbl_view_point")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewPoint implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="viewMemberName")
	private String viewMemberName;

	@Column(name="viewTableId")
	private Long viewTableId;

	@Column(name="currentPoint")
	private Long currentPoint = 0L;

	@Column(name="accumulatePoint")
	private Long accumulatePoint = 0L;

	@Column(name="usePoint")
	private Long usePoint = 0L;


	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;


}
