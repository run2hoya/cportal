package com.castis.cportal.repository;

import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.dto.board.BoardDto;
import com.castis.cportal.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value="SELECT new com.castis.cportal.dto.board.BoardDto"
            + "(b.id, b.registerId, b.title, b.viewCnt, b.registerDate, b.updateDate, "
            + "u.userImg, u.nickName) "
            + "FROM Board b "
            + "left outer join User u on b.registerId= u.id "
            + "where b.registerDate >= :startDate and b.registerDate <= :endDate and b.boardType = :boardType "
            + "order by b.id desc" ,
            nativeQuery = false)
    List<BoardDto> getBoardDtoList(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   @Param("boardType") BoardType boardType);

}