package com.castis.cportal.controller.common;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.castis.commonLib.dto.ResultDetail;
import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.idgenerator.IdGenerator;
import com.castis.cportal.common.setting.Properties;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class AbstrctController {
		
	@Autowired
	Properties properties;
	
	public TransactionID startLog(HttpServletRequest req, String eventType) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI"+ req.getRequestURI());
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, Principal user) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		if(user != null)
			logString.append("["+ user.getName() + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI"+ req.getRequestURI());
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, String body, Principal user) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		if(user != null)
			logString.append("["+ user.getName() + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI:"+ req.getRequestURI());
		logString.append(", RequestBody:"+ body);
		
		log.info(logString.toString());
		return trId; 
	}
	
	public TransactionID startLog(HttpServletRequest req, String eventType, String body) throws Exception {		
		
		TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());		
		StringBuilder logString = new StringBuilder(trId + ">>> " + "[" + eventType + "] ");
		logString.append("clientAddr["+ req.getRemoteAddr()+":" + Integer.toString(req.getRemotePort())+"], ");
		logString.append("RequestURI:"+ req.getRequestURI());
		logString.append(", RequestBody:"+ body);
		
		log.info(logString.toString());
		return trId; 
	}
	
	public void endLog(long startTime, String eventType, TransactionID trId, ResultDetail error) {
					
		StringBuilder logString = new StringBuilder(trId + "<<< " + "[" + eventType + "]");
		if(error != null)
			logString.append(String.format(" ErrorName[%s], Description[%s]", error.getName(), error.getDescription()));
		
		logString.append(String.format(" processingTimeSec[%s]", String.valueOf((System.currentTimeMillis() - startTime)/1000.0)));
		log.info(logString.toString());
	}
	
}	
		
		
		
		
