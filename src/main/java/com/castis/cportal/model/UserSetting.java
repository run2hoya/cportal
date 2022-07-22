package com.castis.cportal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user_setting")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSetting {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="adNotiEmail")
    private Boolean adNotiEmail = true;

    @Column(name="adNotiApp")
    private Boolean adNotiApp = true;

    @Column(name="jobcastNotiEmail")
    private Boolean jobcastNotiEmail = true;

    @Column(name="jobcastNotiApp")
    private Boolean jobcastNotiApp = true;

//    @Column(name="magazineNotiEmail")
//    private Boolean magazineNotiEmail = true;
}