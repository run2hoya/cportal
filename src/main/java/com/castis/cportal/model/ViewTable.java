package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tbl_view_table")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewTable implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="link")
    private String link;

	@Column(name="title")
	private String title;

	@Column(name="ownerId")
	private Integer ownerId;

	@Column(name="productType")
	@Enumerated(EnumType.STRING)
	private ProductType productType;

}
