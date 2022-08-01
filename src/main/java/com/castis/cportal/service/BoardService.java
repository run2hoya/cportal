package com.castis.cportal.service;

import com.castis.commonLib.util.ObjectMapperUtils;
import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.dto.board.BoardDto;
import com.castis.cportal.dto.board.BoardWithContentDto;
import com.castis.cportal.model.Board;
import com.castis.cportal.model.User;
import com.castis.cportal.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    public void save(Board board) { boardRepository.save(board); }

    public Board getBoard(long boardId) { return boardRepository.findOne(boardId);}

    public BoardWithContentDto getBoardWithContentDto(long boardId) {
        Board board = boardRepository.findOne(boardId);
        BoardWithContentDto boardWithContentDto;
        if (board != null) {
            board.setViewCnt(board.getViewCnt() + 1);
            save(board);
            boardWithContentDto = ObjectMapperUtils.map(board, BoardWithContentDto.class);
        }
        else return null;

        User user = userService.getUser(boardWithContentDto.getRegisterId());
        boardWithContentDto.setNickName(user.getNickName());
        boardWithContentDto.setUserImg(user.getUserImg());

        return boardWithContentDto;
    }

    public List<BoardDto> getBoardDtoList(String start, String end, BoardType boardType) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(start, formatter), LocalDateTime.MIN.toLocalTime());
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(end, formatter), LocalDateTime.MAX.toLocalTime());

        return boardRepository.getBoardDtoList(startDateTime, endDateTime, boardType);
    }

    @Transactional
    public void updateBoard(Board board) {
        Board existBoard = boardRepository.findOne(board.getId());
        existBoard.setContent(board.getContent());
        existBoard.setTitle(board.getTitle());
        boardRepository.save(existBoard);
    }

    @Transactional
    public void incrementViewCnt(long boardId) {
        Board existBoard = boardRepository.findOne(boardId);
        existBoard.setViewCnt(existBoard.getViewCnt() + 1);
        boardRepository.save(existBoard);
    }

}
