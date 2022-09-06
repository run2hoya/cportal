package com.castis.cportal.common.setting;

import com.castis.commonLib.util.tableLog.Block;
import com.castis.commonLib.util.tableLog.Board;
import com.castis.commonLib.util.tableLog.Table;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;


@Component("properties")
@Data
public class Properties {

	private static Log log = LogFactory.getLog(Properties.class);	
	
	@Value("${lang.locale:en}")
	private String locale;


	@Value("${cportalFile.dir:/cportalFile/}")
	private String cportalFile;

	@Value("${mailTemp.Dir:/mailTemp/}")
	private String mailTempDir;

	@Value("${platinum1.id:0}")
	private Integer platinum1;

	@Value("${platinum2.id:0}")
	private Integer platinum2;

	@Value("${premier3.id:0}")
	private Integer platinum3;

	public Properties() {
		super();
	}
	
	public String getCportalUrl() {
		String[] words = cportalFile.split(":");
		if(words.length == 1) {
			return words[0];			
		} else
			return words[1];
	}

	@PostConstruct
	public void setAndPrintProperties() throws Exception {
		

		FileUtils.forceMkdir(new File(this.cportalFile));
		
		List<String> headersList = Arrays.asList("KEY", "VALUE");
		List<List<String>> rowsList_properties = Arrays.asList(
				Arrays.asList("cportalFile.dir", this.cportalFile),
				Arrays.asList("mailTemp.Dir", this.mailTempDir)
		);

				
		Board board = new Board(150);
		board.setInitialBlock(new Block(board, 145, 1, "cportal").setDataAlign(Block.DATA_CENTER));
		board.appendTableTo(0, Board.APPEND_BELOW, new Table(board, 147, headersList, rowsList_properties));
		board.build();
		
		String tableString = board.getPreview();						
		log.info("\n" + tableString);
	}



}
