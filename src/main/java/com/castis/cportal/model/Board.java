package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.BoardType;
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
@Table(name="tbl_board", indexes = @Index(name = "idx_boardType_registerDate", columnList = "boardType, registerDate"))
@NoArgsConstructor
@Getter
@Setter
public class Board implements Serializable {

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

	@Column(name="viewCnt")
	private Integer viewCnt = 0;

	@Column(name="likeCnt")
	private Integer likeCnt = 0;

	@Column(name="boardType")
	@Enumerated(EnumType.STRING)
	private BoardType boardType;

	@Column(name="registerDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime registerDate;

	@Column(name="updateDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@UpdateTimestamp
	private LocalDateTime updateDate;


	@Override
	public String toString() {
		return "Board{" +
				"id=" + id +
				", registerId=" + registerId +
				", title='" + title + '\'' +
				", viewCnt=" + viewCnt +
				", likeCnt=" + likeCnt +
				", boardType=" + boardType +
				", registerDate=" + registerDate +
				", updateDate=" + updateDate +
				'}';
	}
}
