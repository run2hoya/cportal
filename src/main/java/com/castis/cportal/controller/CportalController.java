package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.dto.chart.ChartDataDto;
import com.castis.cportal.dto.cportal.StatDto;
import com.castis.cportal.service.CportalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CportalController extends AbstrctController {

    private final CportalService cportalService;
    private final Properties properties;


    @RequestMapping(value = "/cportal/stat", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getCompanyTitle(HttpServletRequest req) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET);

            StatDto res = cportalService.getStatDto();
            log.info(trId + "result = " + res.toString());

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

    @RequestMapping(value = "/cportal/jobcast/chart", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getJobcastGroupChart(HttpServletRequest req) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET);

            List<ChartDataDto> res = cportalService.getJobcastGroupChart();
            log.info(trId + "result = " + res.toString());

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


}
