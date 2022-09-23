package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.donation.DonationData;
import com.castis.cportal.model.ViewDonation;
import com.castis.cportal.service.ViewDonationService;
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
public class ViewDonationController extends AbstrctController {


    private final ViewDonationService viewDonationService;

    @RequestMapping(value = "/donation/{ownerId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getDonation(HttpServletRequest req, @PathVariable("ownerId") Integer ownerId
            , @RequestParam String startDate, @RequestParam String endDate, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            log.info(trId + "ownerId = " + ownerId + " startDate :" + startDate + " endDate :" + endDate);
            List<ViewDonation> viewDonationList = viewDonationService.getViewDonation(ownerId, startDate, endDate);

            result = new ResponseEntity<>(viewDonationList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/donation/viewTable/{viewTableId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getDonationByViewTable(HttpServletRequest req, @PathVariable("viewTableId") Long viewTableId
            , @RequestParam String startDate, @RequestParam String endDate, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            log.info(trId + "viewTableId = " + viewTableId + " startDate :" + startDate + " endDate :" + endDate);
            List<ViewDonation> viewDonationList = viewDonationService.getViewDonationByTableId(viewTableId, startDate, endDate);

            result = new ResponseEntity<>(viewDonationList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/donation/new", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> saveDonation(HttpServletRequest req, @RequestBody final DonationData donationData, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.POST, donationData.toString(), user);

            viewDonationService.saveDonationData(donationData);
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



}
