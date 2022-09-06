package com.castis.cportal.scheduler.mail;

import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.idgenerator.IdGenerator;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.model.Company;
import com.castis.cportal.service.CompanyService;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.UserService;
import com.castis.cportal.service.createMail.Company114PlatinumMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class Company114Platinum2MailScheduler {


	private final Properties properties;
	private final MailService mailService;
	private final Company114PlatinumMailService company114PlatinumMailService;
	private final UserService userService;
	private final CompanyService companyService;

	@Scheduled(cron="${company114Platinum2MailScheduler.cron:0 0 8 * * 1}")
	protected void executeInternal() throws JobExecutionException {
		long start = System.currentTimeMillis();

		try {
			InternetAddress[] bccAddr = userService.getAdMailList();

			InternetAddress[] toAddr = new InternetAddress[1];
			toAddr[0] = new InternetAddress("cportal-cast@naver.com");

			TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());

			if(properties.getPlatinum2() != 0) {
				Company company = companyService.getCompany(properties.getPlatinum2());
				mailService.sendMailWithImage(trId, company114PlatinumMailService.generateHtml(company), "[사업부114] " + company.getCompanyName(),
						toAddr, bccAddr,"cportal-cast@naver.com", new File(company.getCompanybg()));
			}


		} catch (Exception e) {
			log.error("",e);			
		} finally {log.info("Company114Platinum2MailScheduler - end(Processing time: " + (System.currentTimeMillis() - start) /1000.0 + " s)");}

	}
	
}
