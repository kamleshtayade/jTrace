/**
 * 
 */
package com.spring.app.jms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ktayade
 *
 */
public class DBConnPostGre {
	
	private final Logger log = LoggerFactory.getLogger(DBConnPostGre.class);
	
	public static Connection getConnection() {

		PropertiesValue properties = new PropertiesValue();
		Properties prop = new Properties();
		Connection connection = null;

		prop = properties.gerPropertiesValue();
		try {

			Class.forName(prop.getProperty("postgresqldriver"));

			connection = DriverManager.getConnection(
					prop.getProperty("postgreurl"), prop.getProperty("dbuser"),
					prop.getProperty("dbpassword"));

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
			return null;
		}

		if (connection != null) {
			return connection;

		} else {
			return null;
		}
	}

}
