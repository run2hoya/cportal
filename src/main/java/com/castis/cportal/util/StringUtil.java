package com.castis.cportal.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringUtil {

	 public static String arrayJoin(String glue, List<String> arryList) {
		 if(arryList != null && arryList.isEmpty() == false){
		    StringBuffer sb = new StringBuffer();
		    for(String  str: arryList){
		    	if(str.equals("")==false){
		    		sb.append(str+glue);
		    	}
		    }
		    return sb.toString();
		  } else{
			  return null;
		  }
	 }
	 
	// URL Encoding 된 파라미터를 디코딩하는 하는 함수.
		// 이미 Decoding 된 파라미터는 입력 값을 그대로 반환함.
		public static String decodeUTF8(String param, String method) {
			String decodedParam = null;

			if(param==null || "".equals(param))
				return param;
			
			if(method.equalsIgnoreCase("GET")){
				try {
					if(StringUtil.isUTF8(param, "ISO-8859-1")){
						decodedParam = new String(param.getBytes("8859_1"), "UTF-8");
					} else {
						decodedParam = param;
					}
				} catch (Exception e) {
					decodedParam = null;
				}
			} else
				return param;
			
			return decodedParam;
			
		}
		
		public static boolean isUTF8(String str, String encoding) {
			try {
				if(encoding==null || "".equals(encoding)){
					byte[] bytes = str.getBytes();
					return isUTF8(bytes,0,bytes.length);
				}else{
					byte[] bytes = str.getBytes(encoding);
					return isUTF8(bytes,0,bytes.length);
				}
			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
			}
			
			return false;
		}	
		
		public static boolean isUTF8(byte[] buf, int offset, int length) {

		       boolean yesItIs = false;
		       for (int i=offset; i<offset+length; i++) {
		          if ((buf[i] & 0xC0) == 0xC0) { // 11xxxxxx 패턴 인지 체크 
		             int nBytes;
		             for (nBytes=2; nBytes<8; nBytes++) {
		                int mask = 1 << (7-nBytes);
		                if ((buf[i] & mask) == 0) break;
		             }
		                                  //CJK영역이나 아스키 영역의 경우 110xxxxx 10xxxxxx 패턴으로 올수 없다.
		             if(nBytes==2) return false;
		             
		             // Check that the following bytes begin with 0b10xxxxxx
		             for (int j=1; j<nBytes; j++) {
		                if (i+j >= length || (buf[i+j] & 0xC0) != 0x80) return false;
		             }
	             
	             if(nBytes==3){
	             	// 유니코드 형태로 역치환 해서 0x0800 ~ 0xFFFF 사이의 영역인지 체크한다. 
	                 char c = (char) (((buf[i] & 0x0f) << 12) + ((buf[i+1] & 0x3F) << 6) + (buf[i+2] & 0x3F));
	                 if(!(c >= 0x0800 && c <= 0xFFFF)){
	                     return false;
	                 }	                	
	             }
		                
		             yesItIs = true;
		          }
		       }
		       return yesItIs;
		 }
}
