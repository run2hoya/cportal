package com.castis.cportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserSettingDto implements Serializable {
    private Integer id;
    private Boolean adNotiEmail;
    private Boolean adNotiApp;
    private Boolean jobcastNotiEmail;
    private Boolean jobcastNotiApp;

}
