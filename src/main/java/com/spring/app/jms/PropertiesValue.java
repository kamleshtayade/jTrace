/**
 * 
 */
package com.spring.app.jms;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ktayade
 *
 */
public class PropertiesValue {
	
	private final Logger log = LoggerFactory.getLogger(PropertiesValue.class);
	
	Properties prop = new Properties();
	InputStream input = null;
	
	public Properties gerPropertiesValue(){
		try {
			
			log.debug("Inside Property Method");
			input = new FileInputStream(
					"WebContent\\WEB-INF\\jms-config.properties");
			prop.load(input);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.debug("prop" + prop.toString());
		return prop;
	}

}
