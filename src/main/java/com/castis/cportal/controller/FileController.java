package com.castis.cportal.controller;

import com.castis.commonLib.define.Constants;
import com.castis.commonLib.define.ResultCode;
import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.cportal.controller.common.AbstrctController;
import com.castis.cportal.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController extends AbstrctController {

    private final FileService fileService;

    @RequestMapping(value = "/upload/file", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity<?> upload(Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request,
                                    @RequestParam("dir") String dir, @RequestParam(name = "fileName", required = false) String fileName) {

        long startTime = System.currentTimeMillis();
        TransactionID trId = null;
        ResponseEntity<?> response = null;

        try {
            trId = startLog(request, Constants.request.POST);
            log.info(trId + "upload file :" + dir + "/" + fileName);
            String resFileName = fileService.restore(file, trId, dir, fileName);

            response = new ResponseEntity<>(resFileName, HttpStatus.OK);

        } catch (Exception e) {
            log.error("", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME, e.getMessage()));
        } finally {
            endLog(startTime, Constants.request.POST, trId, null);
        }

        return response;
    }


    @RequestMapping(value = "/upload/file", method = RequestMethod.DELETE, produces = "application/json;")
    public ResponseEntity<?> delete(Model model, HttpServletRequest request, @RequestParam(name = "fileName", required = true) String fileName) {

        long startTime = System.currentTimeMillis();
        TransactionID trId = null;
        ResponseEntity<?> response = null;

        try {
            trId = startLog(request, Constants.request.DELETE);
            log.info(trId + "delete file :" + fileName);
            fileService.delete(trId, fileName);

            response = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);

        } catch (Exception e) {
            log.error("", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME, e.getMessage()));
        } finally {
            endLog(startTime, Constants.request.DELETE, trId, null);
        }

        return response;
    }



}	
		
		
		
		
