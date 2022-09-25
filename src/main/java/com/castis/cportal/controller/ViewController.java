package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.JsonUtil;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.UserDto;
import com.castis.cportal.dto.view.*;
import com.castis.cportal.model.ViewTable;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.ViewService;
import com.castis.cportal.service.createMail.ViewTableMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ViewController extends AbstrctController {


    private final ViewService viewService;
    private final MailService mailService;
    private final ViewTableMailService viewTableMailService;

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
            String viewTitle = null;
            if(user != null) {
                UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
                ViewTable viewTable = viewService.getViewTable(viewTableId);
                log.info(trId + "viewTable = " + viewTable) ;

                if(viewTable != null) {
                    viewTitle = viewTable.getTitle();
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
            viewData.setViewTitle(viewTitle);
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

    @RequestMapping(value = "/view/booking/item/", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity<?> updateCompanyContent(HttpServletRequest req,
                                                  @RequestBody final ViewItem viewItem, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.PUT, user);
            log.info(trId + viewItem.toString());

            int userId = 0;
            if(user != null) {
                UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
                UserDto userDto = (UserDto)userDetails.getDetails();
                userId = Integer.parseInt(userDto.getId());
            }

            List<ViewResponse> res =  viewService.updateViewItemList(trId, viewItem, userId);
            result = new ResponseEntity<>(res, HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.PUT, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/view/booking/item/", method = RequestMethod.DELETE, produces = "application/json; charset=utf8")
    public ResponseEntity<?> deleteCompanyContent(HttpServletRequest req,
                                                  @RequestBody final ViewItem viewItem, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.DELETE, user);
            log.info(trId + viewItem.toString());

            int userId = 0;
            if(user != null) {
                UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
                UserDto userDto = (UserDto)userDetails.getDetails();
                userId = Integer.parseInt(userDto.getId());
            }

            List<ViewResponse> res =  viewService.deleteViewItemList(trId, viewItem, userId);
            result = new ResponseEntity<>(res, HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.DELETE, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/view/mail", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> viewMail(HttpServletRequest req, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            InternetAddress[] toAddr = new InternetAddress[1];
            toAddr[0] = new InternetAddress("cportal-cast@naver.com");

            InternetAddress[] bccAddr = new InternetAddress[1];
            bccAddr[0] = new InternetAddress("run2hoya@castis.com");

            if(mailService.sendMailWithImage(trId, viewTableMailService.generateHtml(), "[비춰보기] 비춰보기 정보 ",
                    toAddr, bccAddr,"cportal-cast@naver.com", new File("/cportalFile/img/b8.jpg"))) {
                result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                result = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResultDetail(ResultCode.EXTERNAL_SYSTEM_ERROR, ResultCode.EXTERNAL_SYSTEM_ERROR_NAME,
                        "관리자에게 연락 부탁드립니다."));
            }

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.POST, trId, null);
        }
        return result;
    }

    @RequestMapping(value = "/view/register/link/mail", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> registerCompanyInfo(HttpServletRequest req, @RequestBody final ViewLink viewLink, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, viewLink.toString(), user);

            InternetAddress[] toAddr = new InternetAddress[1];
            toAddr[0] = new InternetAddress("run2hoya@castis.com");

            if(mailService.sendMail(trId, JsonUtil.objectToJson(viewLink),
                    "비춰보기 신규 등록 요청", toAddr, null,"cportal-cast@naver.com")) {
                result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                result = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResultDetail(ResultCode.EXTERNAL_SYSTEM_ERROR, ResultCode.EXTERNAL_SYSTEM_ERROR_NAME,
                        "관리자에게 연락 부탁드립니다."));
            }
            log.info(trId + "result:" + result);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.POST, trId, null);
        }

        return result;
    }



}
