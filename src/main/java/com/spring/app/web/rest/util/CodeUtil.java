/**
 * 
 */
package com.spring.app.web.rest.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ktayade
 *
 * Utility class for code generation
 */
public class CodeUtil {
	
	private final Logger log = LoggerFactory.getLogger(CodeUtil.class);
	/**
	 * generateCode
	 * 
	 * used by 
	 * ItemMtr
	 * 
	 * @param prefix - prefix string
	 * @param uniqueNumber - Unique Number
	 * @param suffix - Suffix string
	 * @return
	 */
	public static String generateCode(String prefix, Long uniqueNumber, String suffix ){
		
		String code = prefix+"-"+uniqueNumber+"-"+suffix;
		
		return code;
	}
	
	public static String generateCode(String prefix, Long uniqueNumber,
			int increment) {

		String code = "";

		Hashids hashids = new Hashids(prefix);

		String uid = hashids.encode(1, uniqueNumber); //hasids.encode(1,2,3);

		Calendar calendar = new GregorianCalendar();

		int year = calendar.get(Calendar.YEAR);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		
		for (int val = 1; val < increment + 1; val++) {
			code = code + ","+year + weekOfYear +  uid + String.format("%04d", val);
		}

		/*
		 * long[] numbers = hashids.decode(id);
		 * 
		 * log.debug("encode: "+id);
		 * 
		 * log.debug("decode: "+numbers[1]);
		 */

		return code.substring(1);
	}

}
