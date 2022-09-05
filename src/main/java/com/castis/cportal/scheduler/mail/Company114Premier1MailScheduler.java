package com.castis.cportal.scheduler.mail;

import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.idgenerator.IdGenerator;
import com.castis.cportal.common.setting.Properties;
import com.castis.cportal.model.Company;
import com.castis.cportal.service.CompanyService;
import com.castis.cportal.service.MailService;
import com.castis.cportal.service.UserService;
import com.castis.cportal.service.createMail.Company114PremierMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Arrays;

@Slf4j
//@Service
@RequiredArgsConstructor
public class Company114Premier1MailScheduler {


	private final Properties properties;
	private final MailService mailService;
	private final Company114PremierMailService company114PremierMailService;
	private final UserService userService;
	private final CompanyService companyService;

	//@Scheduled(cron="${wantedMailScheduler.cron:0 0 7 * * 1,4}")
	protected void executeInternal() throws JobExecutionException {
		long start = System.currentTimeMillis();

		try {
			InternetAddress[] bccAddr1 = userService.getAdMailList();
			log.info(Arrays.toString(bccAddr1));

			InternetAddress[] bccAddr = new InternetAddress[1];
			bccAddr[0] = new InternetAddress("run2hoya@castis.com");

			InternetAddress[] toAddr = new InternetAddress[1];
			toAddr[0] = new InternetAddress("cportal-cast@naver.com");

			TransactionID trId = new TransactionID(TransactionID.TRANSACTION_ID_TYPE, IdGenerator.getInstance().generateId());

			Company company = companyService.getCompany(properties.getPremier1());
			mailService.sendMailWithImage(trId, company114PremierMailService.generateHtml(company), "[사업부114] " + company.getCompanyName(),
					toAddr, bccAddr,"cportal-cast@naver.com", new File(company.getCompanybg()));


		} catch (Exception e) {
			log.error("",e);			
		} finally {log.info("WantedMailScheduler - end(Processing time: " + (System.currentTimeMillis() - start) /1000.0 + " s)");}

	}
	
}
