package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.enumeration.BoardType;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.model.Authority;
import com.castis.cportal.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthorityController extends AbstrctController {


    private final AuthorityService authorityService;

    @RequestMapping(value = "/authority/user/{userId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getHasAuthority(HttpServletRequest req, @PathVariable("userId") Integer userId,
                                             @RequestParam BoardType type, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getHasAuthority) userId :" + userId + ", type :" + type);
            Authority authority = authorityService.getAuthorityByUserID(type, userId);

            log.info(trId + "result: getAuthorityInfo = " + authority);

            if(authority != null)
                result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            else
                result = new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }
        return result;
    }


    @RequestMapping(value = "/authority/owner/{ownerId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getAuthorityInfo(HttpServletRequest req, @PathVariable("ownerId") Integer ownerId,
                                          @RequestParam BoardType type, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);
            log.info(trId + "(getAuthorityInfo) ownerId :" + ownerId + ", type :" + type);
            Authority authority = authorityService.getAuthority(type, ownerId);

            log.info(trId + "result: getAuthorityInfo = " + authority.getAuthoritylList());
            result = new ResponseEntity<>(authority.getAuthoritylList(), HttpStatus.OK);
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
