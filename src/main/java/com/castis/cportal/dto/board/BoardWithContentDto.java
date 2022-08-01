package com.castis.cportal.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardWithContentDto extends BoardDto{

    String content;

    public BoardWithContentDto(Long id, Integer registerId, String title, Integer viewCnt,
                               LocalDateTime registerDate, LocalDateTime updateDate, String userImg, String nickName, String content) {
        super(id, registerId, title, viewCnt, registerDate, updateDate, userImg, nickName);
        this.content = content;
    }


}
