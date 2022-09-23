package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.DonationState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_view_donation")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewDonation implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="title")
	private String title;

	@Column(name="des")
	private String des;

	@Column(name="ownerId")
	private Integer ownerId;

	@Column(name="ownerName")
	private String ownerName;

	@Column(name="viewMemberName")
	private String viewMemberName;

	@Column(name="viewMemberId")
	private Integer viewMemberId;

	@Column(name="viewTableId")
	private Long viewTableId;

	@Column(name="point")
	private Integer point;

	@Column(name="givePoint")
	private Integer givePoint;

	@Column(name="plus")
	private Float plus;

	@Column(name="multiply")
	private Float multiply;

	@Column(name="publishDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	@CreationTimestamp
	private LocalDateTime publishDate;

	@Column(name="donationState")
	@Enumerated(EnumType.STRING)
	private DonationState donationState;

}
