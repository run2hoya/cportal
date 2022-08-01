package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.board.BoardDto;
import com.castis.cportal.dto.board.BoardWithContentDto;
import com.castis.cportal.model.Board;
import com.castis.cportal.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController extends AbstrctController {


    private final BoardService boardService;

    @RequestMapping(value = "/board", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> registerBoard(HttpServletRequest req, @RequestBody final Board board, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, board.toString(), user);
            boardService.save(board);
            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {endLog(startTime, Constants.request.POST, trId, null);}
        return result;
    }

    @RequestMapping(value = "/board/list", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getBoardInfoList(HttpServletRequest req, @RequestParam BoardType boardType
            , @RequestParam String startDate, @RequestParam String endDate, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getBoardInfoList) boardType :" + boardType + " startDate :" + startDate + " endDate :" + endDate);
            List<BoardDto> boardDtoList = boardService.getBoardDtoList(startDate, endDate, boardType);

            log.info(trId + "result: boardDtoList size = " + boardDtoList.size());
            result = new ResponseEntity<>(boardDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }


    @RequestMapping(value = "/board/{boardId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getBoardInfo(HttpServletRequest req, @PathVariable("boardId") Long boardId, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getBoardInfo) boardId :" + boardId);
            BoardWithContentDto boardWithContentDto = boardService.getBoardWithContentDto(boardId);

            log.info(trId + "result: boardWithContentDto = " + boardWithContentDto);
            result = new ResponseEntity<>(boardWithContentDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }
        return result;
    }

    @RequestMapping(value = "/board/{boardId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity<?> updateBoard(HttpServletRequest req, @PathVariable("boardId") Long boardId
            , @RequestBody final Board board, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.PUT, user);
            log.info(trId + "(updateBoard) boardId :" + boardId);

            boardService.updateBoard(board);
            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.PUT, trId, null);
        }
        return result;
    }





}
