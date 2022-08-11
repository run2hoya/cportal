package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.enumeration.ProductType;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.wanted.WantedApplicantDto;
import com.castis.cportal.dto.wanted.WantedRegisterDto;
import com.castis.cportal.dto.wanted.WantedWithContentDto;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.WantedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WantedController extends AbstrctController {


    private final WantedService wantedService;
    private final MailService mailService;


    @RequestMapping(value = "/wanted", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> registerWantedInfo(HttpServletRequest req, @RequestBody final WantedRegisterDto wantedRegisterDto, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, wantedRegisterDto.toString(), user);

            wantedService.saveWanted(wantedRegisterDto);
            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.POST, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/wanted/register/{registerId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getWantedInfoList(HttpServletRequest req, @PathVariable("registerId") Integer registerId
            , @RequestParam String startDate, @RequestParam String endDate, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getWantedInfoList) registerId :" + registerId + " startDate :" + startDate + " endDate :" + endDate);
            List<WantedRegisterDto> wantedRegisterDtoList = wantedService.getWantedInfo(registerId, startDate, endDate);

            log.info(trId + "result: getWantedInfo size = " + wantedRegisterDtoList.size());
            result = new ResponseEntity<>(wantedRegisterDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/wanted/viewer/{wantedType}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getWantedViewerList(HttpServletRequest req, @PathVariable("wantedType") String wantedType
            , @RequestParam String date, @RequestParam ProductType productType, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getWantedViewerList) wantedType :" + wantedType + "productType :" + productType );
            List<WantedWithContentDto> wantedList = wantedService.getWanted(wantedType, date, productType);

            log.info(trId + "result: getWantedInfo size = " + wantedList.size());
            result = new ResponseEntity<>(wantedList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/wanted/info/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getWantedInfo(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getWantedInfo) wantedId :" + wantedId);
            WantedWithContentDto wantedWithContentDto = wantedService.getWantedWithContent(wantedId);

            log.info(trId + "result: wantedWithContentDto = " + wantedWithContentDto);
            result = new ResponseEntity<>(wantedWithContentDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }
        return result;
    }

    @RequestMapping(value = "/wanted/{wantedId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity<?> updateWantedInfo(HttpServletRequest req, @PathVariable("wantedId") Long wantedId
            , @RequestBody final WantedWithContentDto wantedWithContentDto, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.PUT, user);
            log.info(trId + wantedWithContentDto.toString());
            wantedService.updateWantedWithContent(wantedId, wantedWithContentDto);
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

    @RequestMapping(value = "/wanted/applicant", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> wantedApplicant(HttpServletRequest req, @RequestBody final WantedApplicantDto wantedApplicantDto, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, user);
            log.info(trId + wantedApplicantDto.toString());

            InternetAddress[] toAddr = new InternetAddress[1];
            toAddr[0] = new InternetAddress(wantedApplicantDto.getCompanyEmail());

            if(mailService.sendMail(trId, mailService.generateHtml(wantedApplicantDto).toString(),
                    "[jobcast] 지원자가 왔습니다 ", toAddr, "no_reply@castis.com")) {
                wantedService.incrementApplicant(wantedApplicantDto.getWantedId());
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

    @RequestMapping(value = "/wanted/popup/edit/{wantedId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public String goWantedEditMain(HttpServletRequest req, @PathVariable("wantedId") Long wantedId, Model model, Principal user) {

        long startTime = System.currentTimeMillis();
        TransactionID trId = null;
        String jspName = "popup";

        try {
            trId = startLog(req, Constants.request.GET, user);
            model.addAttribute("wantedId", wantedId);
            model.addAttribute("target", "/cportalJS/jobcast/popup/wantedPopupEditMain");
        } catch (Exception e) {
            log.error("", e);
            req.setAttribute("errorCode", "500");
            jspName = "/common/errorPage";
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return jspName;
    }




}
