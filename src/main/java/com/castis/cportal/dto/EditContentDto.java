package com.castis.cportal.dto;

import lombok.Data;

@Data
public class EditContentDto {
    private Integer id = null;
    private String content = null;

    public EditContentDto() {
    }
}
