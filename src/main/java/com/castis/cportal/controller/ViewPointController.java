package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.model.ViewPoint;
import com.castis.cportal.service.ViewPointService;
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
public class ViewPointController extends AbstrctController {


    private final ViewPointService viewPointService;

    @RequestMapping(value = "/view/point/{viewTableId}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getDonation(HttpServletRequest req, @PathVariable("viewTableId") Long viewTableId
            , Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET, user);

            log.info(trId + "viewTableId = " + viewTableId);
            List<ViewPoint> viewPointList = viewPointService.findByViewTableId(viewTableId);

            result = new ResponseEntity<>(viewPointList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    "관리자에게 연락 부탁드립니다."));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }

    @RequestMapping(value = "/view/point/", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity<?> updateCompanyContent(HttpServletRequest req,
                                                  @RequestBody final ViewPoint viewPoint, Principal user) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.PUT, user);
            log.info(trId + viewPoint.toString());

            viewPointService.updateViewPoint(trId, viewPoint);
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
