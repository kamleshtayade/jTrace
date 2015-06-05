/**
 * 
 */
package com.spring.app.web.rest.util;

/**
 * @author ktayade
 *
 * Utility class for code generation
 */
public class CodeUtil {
	
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
		
		String code = prefix.substring(0,3)+"-"+uniqueNumber+"-"+suffix;
		
		return code;
	}

}
