package com.castis.cportal.scheduler;

import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.idgenerator.IdGenerator;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.UserService;
import com.castis.cportal.service.createMail.JobcastMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class WantedMailScheduler {


	private final Properties properties;
	private final MailService mailService;
	private final JobcastMailService jobcastMailService;
	private final UserService userService;

	@Scheduled(cron="${wantedMailScheduler.cron:0 0 7 * * 1,4}")
	protected void executeInternal() throws JobExecutionException {
		long start = System.currentTimeMillis();

		try {
			InternetAddress[] bccAddr = userService.getJobCastMailList();
			log.info(Arrays.toString(bccAddr));

			InternetAddress[] toAddr = new InternetAddress[1];
			toAddr[0] = new InternetAddress("cportal-cast@naver.com");

			TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());
			mailService.sendMailWithImage(trId, jobcastMailService.generateHtml(), "[jobcast] 당신을 위한 정보 ",
					toAddr, bccAddr,"cportal-cast@naver.com", new File("/cportalFile/img/wanted.jpg"));


		} catch (Exception e) {
			log.error("",e);			
		} finally {log.info("WantedMailScheduler - end(Processing time: " + (System.currentTimeMillis() - start) /1000.0 + " s)");}

	}
	
}
