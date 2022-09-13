package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.UserDto;
import com.castis.cportal.dto.view.ViewData;
import com.castis.cportal.dto.view.ViewGroupByType;
import com.castis.cportal.model.ViewTable;
import com.castis.cportal.service.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ViewController extends AbstrctController {


    private final ViewService viewService;


    @RequestMapping(value = "/view/title", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getCompanyTitle(HttpServletRequest req, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            ViewGroupByType res = viewService.getViewTitleDto();
            log.info(trId + "result: platinum size = " + res.getPlatinum().size() +
                    ", Premier size = " + res.getPremier().size() +
                    ", Normal size = " + res.getNormal().size() +
                    ", ETC size = " + res.getEtc().size());

            result = new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }


    @RequestMapping(value = "/view/booking/{viewTableId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getBookingInfo(HttpServletRequest req, @PathVariable("viewTableId") Long viewTableId
            , Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            log.info(trId + "user = " + user) ;

            int monthsToadd = 2;
            boolean isOwner = false;
            if(user != null) {
                UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
                ViewTable viewTable = viewService.getViewTable(viewTableId);
                log.info(trId + "viewTable = " + viewTable) ;

                if(viewTable != null) {
                    UserDto userDto = (UserDto)userDetails.getDetails();
                    if(viewTable.getOwnerId() == Integer.parseInt(userDto.getId())) {
                        monthsToadd = 3;
                        isOwner = true;
                    }
                }
            }

            LocalDate start = LocalDate.now().minusDays(2);
            LocalDate end = LocalDate.now().plusMonths(monthsToadd);
            end = end.withDayOfMonth(end.lengthOfMonth());

            log.info(trId + "viewTableId = " + viewTableId +  ", start = " + start +  ", end = " + end);

            ViewData viewData = viewService.getViewList(trId, viewTableId, start, end);
            viewData.setOwner(isOwner);
            result = new ResponseEntity<>(viewData, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/view/booking/month/{month}/{viewTableId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getBookingInfo(HttpServletRequest req, @PathVariable("month") String month, @PathVariable("viewTableId") Long viewTableId
            , Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            log.info(trId + "viewTableId = " + viewTableId +  ", month = " + month);
            viewService.createViewMonth(month, viewTableId);

            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }




}
