package com.castis.cportal.common.setting;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CportalSetting {
	
	static final Log		log = LogFactory.getLog( CportalSetting.class );
	
	public static final String	PRODUCT_VERSION_KEY	= "productVersion";
	public static final	String	PRODUCT_VERSION		=	"1.0.0.QR1";
	
	public CportalSetting() {
		super();		
		String enc = new java.io.OutputStreamWriter(System.out).getEncoding();
        log.info("######### default encoding = " + enc);
	}
	
}
