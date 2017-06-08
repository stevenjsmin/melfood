/** 
 * 2015 UssDataSource.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.uitl.FileUtils;

/**
 * Current Spring 4 and Commons DBCP are not support properly for PropertyPlaceholderConfigurer when we use Spring scheduled task.<br>
 * So I customized for BasicDataSource
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class MelfoodDataSource extends BasicDataSource {

	private static final Logger logger = LoggerFactory.getLogger(MelfoodDataSource.class);

	private String url = "";
	private String username = "";
	private String password = "";
	private String autoReconnect = "true";

	// private long maxWait = -1L;

	public MelfoodDataSource() {
		super();

		try {

			Configuration melfood = new PropertiesConfiguration(System.getProperty("COUPANG_CONFIG"));

			// File f = new File(getClass().getClassLoader().getResource("/configuration/jdbc.properties").getFile()); // For read for JAR
			File f = FileUtils.convertInputStreamToFile(MelfoodDataSource.class.getResourceAsStream("/configuration/jdbc.properties"));

			Reader fr = new FileReader(f);

			Properties prop = new Properties();
			prop.load(fr);

			String client = melfood.getString("SYS.CLIENT");
			String env = melfood.getString("SYS.ENV_TYPE");
			String db_username = melfood.getString("DB.USERNAME");
			String db_password = melfood.getString("DB.PASSWORD");
			String db_url = melfood.getString("DB.URL");
			String db_version = melfood.getString("DB.VER");

			if (StringUtils.isBlank(env)) env = "local";
			if (StringUtils.isBlank(client)) client = "melfood";

			if (StringUtils.isNotBlank(prop.getProperty("jdbc.autoReconnect"))) {
				this.autoReconnect = prop.getProperty("jdbc.autoReconnect");
			}

			this.url = prop.getProperty("jdbc.mysql." + StringUtils.upperCase(env) + ".url") + "?autoReconnect=" + this.autoReconnect + "&useUnicode=true&characterEncoding=UTF8";
			this.url = StringUtils.replace(this.url, "${DB_URL}", db_url);
			this.url = StringUtils.replace(this.url, "${CLIENT_CODE}", StringUtils.lowerCase(client));
			this.url = StringUtils.replace(this.url, "${DB_VER}", StringUtils.lowerCase(db_version));

			this.username = db_username;
			this.password = db_password;

			this.initialSize = Integer.parseInt(prop.getProperty("jdbc.initialSize"));
			this.maxIdle = Integer.parseInt(prop.getProperty("jdbc.maxIdle"));
			this.maxActive = Integer.parseInt(prop.getProperty("jdbc.maxActive"));

			super.validationQuery = prop.getProperty("jdbc.validationQuery");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void setUrl(String url) {
		super.setUrl(this.url);
	}

	@Override
	public void setUsername(String username) {
		super.setUsername(this.username);
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(this.password);
	}

	@Override
	public synchronized void setTestOnBorrow(boolean testOnBorrow) {
		super.setTestOnBorrow(true);
	}

}
