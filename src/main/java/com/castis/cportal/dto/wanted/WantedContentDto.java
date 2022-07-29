package com.castis.cportal.dto.wanted;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WantedContentDto implements Serializable {

    private long id;
    private String content;
}
