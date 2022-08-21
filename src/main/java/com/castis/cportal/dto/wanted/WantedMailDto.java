package com.castis.cportal.dto.wanted;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WantedMailDto implements Serializable {

    private long id;
    private String title;
    private String nickName;
    private String wantedType;

    public WantedMailDto(long id, String title, String nickName, String wantedType) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
        this.wantedType = wantedType;
    }
}
