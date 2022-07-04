package com.castis.cportal.service;


import com.castis.commonLib.dto.TransactionID;
import com.castis.commonLib.util.CiFileUtil;
import com.castis.commonLib.util.idgenerator.IdGenerator;
import com.castis.cportal.common.enumeration.UploadFileType;
import com.castis.cportal.common.setting.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

	private final Properties properties;

	public void delete(TransactionID trId, String fileName) {
		try {
			String deleteFileName = StringUtils.substringAfter(fileName, properties.getCportalFile());
			log.info(trId + "파일을 삭제 합니다. deleteFileName : " + properties.getCportalFile() + deleteFileName);
			CiFileUtil.deleteFile(new File(deleteFileName));
		} catch (Exception e) {
			log.error(trId + "" , e);
		}
	}

	public String restore(MultipartFile multipartFile, TransactionID trId, UploadFileType uploadFileType) {
		String url = null;

		try {
			// 파일 정보
			String originFilename = multipartFile.getOriginalFilename();
			String extName = FilenameUtils.getExtension(originFilename);

			Long size = multipartFile.getSize();

			// 서버에서 저장 할 파일 이름
			String saveFileName = IdGenerator.getInstance().generateId() + "." + extName;
			String dir = null;

			switch (uploadFileType) {
				case COMPANY114:
					dir = "company114";
					break;
				case USER_IMG:
					dir = "userImg";
					break;
				default:
					dir = "default";
			}
			dir = properties.getCportalFile() + dir + "/" + LocalDate.now().format( DateTimeFormatter.ofPattern("yyyyMM")) +"/";
			FileUtils.forceMkdir(new File(dir));

			url = dir + saveFileName;
			log.info(trId + "파일을 저장 합니다. originFilename : " + originFilename + ", size : " + size + ", saveFileName : " + url);

			writeFile(multipartFile, url);

		} catch (Exception e) {
			log.error(trId + "" , e);
			return null;
		}
		return url;
	}

	// 파일을 실제로 write 하는 메서드
	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(saveFileName);
		fos.write(data);
		fos.close();
	}

}
