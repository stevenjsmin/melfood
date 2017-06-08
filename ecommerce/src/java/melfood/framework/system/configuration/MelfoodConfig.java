/** 
 * 2015 UssConfig.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */
package melfood.framework.system.configuration;

import java.io.File;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import melfood.framework.Ctx;

/**
 * 쿠팡시스템에서는 시스템 환경설정을 위해서 두군데로 부터 환경 설정을 읽어들이는데, 하나는 데이터베이스 테이블과
 * 또하나는 XML파일로부터 읽어들인다. 이 클래스에서는 XML에 설정된 환경을 읽기위한 API를 구성한다. 
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class MelfoodConfig extends XMLConfiguration {

	/**
	 * @param file
	 * @throws ConfigurationException
	 */
	public MelfoodConfig(File file) throws ConfigurationException {
		super(file);
	}

	/**
	 * @param cc
	 */
	public MelfoodConfig(CombinedConfiguration cc) {
		super(cc);
	}

	/**
	 * Getting configuration value according to running server type
	 * 
	 * @param key
	 * @return
	 */
	public String getVar(String key) {
		String returnValue = null;
		returnValue = Ctx.xmlConfig.getString(key + "[@type='" + StringUtils.lowerCase(Ctx.env) + "']");
		return returnValue;
	}
	
	

}
