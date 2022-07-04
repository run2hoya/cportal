package com.castis.cportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="id")
	private int id;

	@Column(name="userId", unique = true)
	private String userID;
    
	@Column(name="password")
	@JsonIgnore
	private String password;
	
	@Column(name="authority")
    private String authority;
	
	@Column(name="enabled")
    private boolean enabled;
	
	@Column(name="userName")
    private String name;

	@Column(name="email")
    private String email;
	
	@Column(name="userImg")
    private String userImg;
	
	@Column(name="introduction")
    private String introduction;

	@Column(name="phone")
    private String phone;

	@Column(name="registDate")
	@CreationTimestamp
	private LocalDateTime registDate;

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}


	public Collection<GrantedAuthority> getAuthorities() {
		if(authority == null)
			return null;
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(authority));
		return authorities;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userID=" + userID + ", password="
				+ password + ", authority=" + authority + ", enabled="
				+ enabled + ", name=" + name + ", email=" + email
				+ ", userImg=" + userImg + ", introduction=" + introduction
				+ ", phone=" + phone + ", registDate=" + registDate + "]";
	}

	

	
}
