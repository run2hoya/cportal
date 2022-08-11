package com.castis.cportal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="tbl_authority_user")
public class AuthorityUser implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="userId")
    private Integer userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="authorityId")
	private Authority authority;

}
