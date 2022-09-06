package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.JsonUtil;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.EditContentDto;
import com.castis.cportal.dto.company.CompanyByType;
import com.castis.cportal.dto.company.CompanyGroupByType;
import com.castis.cportal.dto.company.CompanyInfoDto;
import com.castis.cportal.model.Company;
import com.castis.cportal.service.CompanyService;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.UserService;
import com.castis.cportal.service.createMail.Company114MailService;
import com.castis.cportal.service.createMail.Company114PlatinumMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.util.Arrays;

@RestController
@Slf4j
@RequiredArgsConstructor
public class Company114Controller extends AbstrctController {

    private final MailService mailService;
    private final CompanyService companyService;
    private final Properties properties;
    private final Company114PlatinumMailService company114PlatinumMailService;
    private final Company114MailService company114MailService;
    private final UserService userService;

    @RequestMapping(value = "/company/mail", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> registerCompanyInfo(HttpServletRequest req, @RequestBody final CompanyInfoDto companyInfoDTO, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, companyInfoDTO.toString(), user);

            InternetAddress[] toAddr = new InternetAddress[1];
            toAddr[0] = new InternetAddress("run2hoya@castis.com");

            if(mailService.sendMail(trId, JsonUtil.objectToJson(companyInfoDTO),
                    "사업부 114 신규 등록 요청", toAddr, null,"cportal-cast@naver.com")) {
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

    @RequestMapping(value = "/company/new", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> saveCompanyInfo(HttpServletRequest req, @RequestBody final CompanyInfoDto companyInfoDTO, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, companyInfoDTO.toString(), user);

            companyService.saveCompany(companyInfoDTO);
            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

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

    @RequestMapping(value = "/company/{companyId}/content", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity<?> updateCompanyContent(HttpServletRequest req, @PathVariable("companyId") Integer companyId,
                                                  @RequestBody final EditContentDto editContentDto, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.PUT, user);
            log.info(trId + editContentDto.toString());

            companyService.updateCompanyContent(companyId, editContentDto);
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

    @RequestMapping(value = "/company/title", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getCompanyTitle(HttpServletRequest req, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            CompanyGroupByType res = companyService.getCompanyTitleDto();
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

    @RequestMapping(value = "/company/chart/type", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getCompanyByType(HttpServletRequest req, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            CompanyByType companyByType = companyService.countTotalCompanyByType();
            log.info(trId + "result:  = " + companyByType.toString());

            result = new ResponseEntity<>(companyByType, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/company/info/{companyId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getCompanyInfo(HttpServletRequest req, @PathVariable("companyId") Integer companyId, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getCompanyInfo) companyId :" + companyId);
            Company res = companyService.getCompany(companyId);

            if(res == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDetail(ResultCode.NOT_FOUND, ResultCode.NOT_FOUND_NAME,
                        "관리자에게 연락 부탁드립니다."));
            }
            log.info(trId + "result: = " + res);

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

    @RequestMapping(value = "/company/mail", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> companyMail(HttpServletRequest req, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            InternetAddress[] bccAddr1 = userService.getAdMailList();
            log.info(Arrays.toString(bccAddr1));

            InternetAddress[] bccAddr = new InternetAddress[1];
            bccAddr[0] = new InternetAddress("run2hoya@castis.com");

            InternetAddress[] toAddr = new InternetAddress[1];
            toAddr[0] = new InternetAddress("cportal-cast@naver.com");

            Company company = companyService.getCompany(properties.getPlatinum1());
            mailService.sendMailWithImage(trId, company114MailService.generateHtml(), "[사업부114] 사업부 소개",
                    toAddr, bccAddr,"cportal-cast@naver.com", new File("/cportalFile/img/p3.jpg"));
            mailService.sendMailWithImage(trId, company114PlatinumMailService.generateHtml(company), "[사업부114] " + company.getCompanyName(),
                    toAddr, bccAddr,"cportal-cast@naver.com", new File(company.getCompanybg()));

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



}
