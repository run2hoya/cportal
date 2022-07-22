package com.castis.cportal.dto;

import com.castis.cportal.common.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private String id = null;
    private String userId = null;

    private String password = null;
    private String newPassword = null;
    private String authority = null;
    private Boolean enabled = null;
    private String nickName = null;
    private String email = null;
    private String userImg = "/cportalFile/img/d_user.png";
    private String introduction = null;
    private String phone = null;
    private UserType userType = null;

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", authority='" + authority + '\'' +
                ", enabled=" + enabled +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", userImg='" + userImg + '\'' +
                ", phone='" + phone + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
