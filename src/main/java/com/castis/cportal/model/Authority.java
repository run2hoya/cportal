package com.castis.cportal.model;

import com.castis.cportal.common.enumeration.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_authority", indexes = { @Index(name = "IDX_TYPE", columnList = "boardType, ownerId") })
@NoArgsConstructor
@Getter
@Setter
public class Authority implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="ownerId")
    private Integer ownerId;

	@Column(name="boardType")
	@Enumerated(EnumType.STRING)
	private BoardType boardType;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true, mappedBy="authority")
	private List<AuthorityUser> authoritylList = new ArrayList<AuthorityUser>();


}
