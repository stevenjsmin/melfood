/** 
 * 2015 ApplicationProperties.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;
import melfood.framework.uitl.FileUtils;

/**
 * This class used when appliction startup for initialized system properties
 * 
 * @author steven.min
 *
 */
public class ApplicationProperties {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

	/**
	 * Application properties initializing...
	 * 
	 * @throws Exception
	 */
	public static void appInit(Configuration melfood) throws Exception {
		String client = Ctx.client;

		String env = melfood.getString("SYS.ENV_TYPE");
		String db_username = melfood.getString("DB.USERNAME");
		String db_password = melfood.getString("DB.PASSWORD");
		String db_url = melfood.getString("DB.URL");

		// For read for jar
		// File propFile = new File(new ApplicationProperties().getClass().getClassLoader().getResource("/configuration/jdbc.properties").getFile());
		File propFile = FileUtils.convertInputStreamToFile(ApplicationProperties.class.getResourceAsStream("/configuration/jdbc.properties"));

		Map<String, String> appPropMap = new HashMap<String, String>();

		// SMTP Server Setting
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.SVR.ADDR", melfood.getString("EMAIL.SMTP.SVR.ADDR"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.SVR.PORT", melfood.getString("EMAIL.SMTP.SVR.PORT"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.AUTH", melfood.getString("EMAIL.SMTP.AUTH"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.PROTOCOL", melfood.getString("EMAIL.SMTP.PROTOCOL"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.IAM.USERNAME", melfood.getString("EMAIL.SMTP.IAM.USERNAME"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.USERNAME", melfood.getString("EMAIL.SMTP.USERNAME"));
		appPropMap.put(StringUtils.upperCase(Ctx.env) + "::EMAIL.SMTP.PASSWORD", melfood.getString("EMAIL.SMTP.PASSWORD"));
		
		Properties prop = new Properties();
		InputStream input = new FileInputStream(propFile);

		prop.load(input);

		String dbURL = prop.getProperty("jdbc.mysql." + StringUtils.upperCase(env) + ".url");
		dbURL = StringUtils.replace(dbURL, "${DB_URL}", db_url);
		dbURL = StringUtils.replace(dbURL, "${CLIENT_CODE}", StringUtils.lowerCase(client));

		// String dbUserName = prop.getProperty("jdbc.mysql." + StringUtils.upperCase(env) + ".username");
		// String dbUserPwd = prop.getProperty("jdbc.mysql." + StringUtils.upperCase(env) + ".password");
		String dbUserName = db_username;
		String dbUserPwd = db_password;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUserName, dbUserPwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM system_configuration WHERE use_yn = 'Y'");

			// ResultSetMetaData metadata = rs.getMetaData();
			// String columnName = metadata.getColumnName(0);
			// String columnValue = metadata.getColumnName(1);

			String stage = "";
			String key = "";
			String value = "";
			// int cnt = 1;
			while (rs.next()) {
				stage = StringUtils.upperCase(rs.getString("stage"));
				key = StringUtils.upperCase(stage + "::" + rs.getString("key"));
				value = rs.getString("value");
				appPropMap.put(key, value);
				// logger.info(cnt++ + ":" + key + "=" + value);
			}

			Ctx.setSysProperty(appPropMap);

		} catch (SQLException se) {
			// se.printStackTrace();
			throw se;
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException se) {
				// se.printStackTrace();
				throw se;
			}

			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// e.printStackTrace();
					throw e;
				}
			}
		}
	}

}
