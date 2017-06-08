/** 
 * 2015 Ctx.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import melfood.framework.service.ServiceDto;
import melfood.framework.system.configuration.MelfoodConfig;

public class Ctx {
	public static String releaseVersion = "1.0.0";
	public static String env = null; // [DEV | TEST | PROD ]

	public static String client = null;

	// 시스템에서 발생하는 데이터들을 저장할 루트 디렉토리
	public static String APP_DATA_DIR = null;

	public static MelfoodConfig xmlConfig = null;
	private static Map<String, String> appProperties = new HashMap<String, String>();

	// List that don't need to be check for authorization
	public static HashMap<String, ServiceDto> NO_LIMITED_SERVICE_MAP = null;

	public static void setSysProperty(Map<String, String> appProp) {
		appProperties = appProp;
	}

	public static String getSysProperty(String key) {
		return appProperties.get(key);
	}

	public static String addSysProperty(String key, String value) {
		return appProperties.put(key, value);
	}

	public static String removeSysProperty(String key) {
		return appProperties.remove(key);
	}

	/**
	 * 해당 Key로 등록된 시스템 환경정보를 얻어온다.
	 * 
	 * @param key
	 * @return
	 */
	public static String getVar(String key) {
		String retValue = "";
		retValue = appProperties.get(StringUtils.upperCase(Ctx.env) + "::" + key);
		return retValue;
	}

	/**
	 * 해당 Key로 등록된 시스템 환경정보를 얻어온다.
	 * 
	 * @param key
	 * @return
	 */
	public static Integer getVarAsInt(String key) {
		int retValue = 0;
		String value = "";
		value = getVar(key);

		try {
			retValue = Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			retValue = 0;
		}
		return retValue;
	}

	/**
	 * 해당 Key로 등록된 시스템 환경정보를 얻어온다.
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean getVarAsBoolean(String key) {
		Boolean retValue = false;
		String value = "";
		value = getVar(key);
		if (StringUtils.equalsIgnoreCase(value, "Y") || StringUtils.equalsIgnoreCase(value, "YES") || StringUtils.equalsIgnoreCase(value, "T") || StringUtils.equalsIgnoreCase(value, "TRUE") || StringUtils.equalsIgnoreCase(value, "1")
				|| StringUtils.equalsIgnoreCase(value, "OK") || StringUtils.equalsIgnoreCase(value, "SUCESS")) {
			retValue = true;
		} else {
			retValue = false;
		}
		return retValue;
	}

	/**
	 * 해당 Key로 등록된 시스템 환경정보를 String type 배열형태 얻어온다. 기본 Delimeter는 콤마(,)로 인식하여 가저온다.
	 * 
	 * @param key
	 * @return
	 */
	public static String[] getVarAsArray(String key) {
		return getVarAsArray(key, ",");
	}

	/**
	 * 해당 Key로 등록된 시스템 환경정보를 String type 배열형태 얻어온다.
	 * 
	 * @param key
	 * @param delimiter
	 * @return
	 */
	public static String[] getVarAsArray(String key, String delimiter) {
		String retValue[] = new String[] {};
		String tmpValue = "";

		if (StringUtils.isBlank(delimiter)) delimiter = ",";

		tmpValue = getVar(key);
		retValue = StringUtils.split(tmpValue, delimiter);
		return retValue;
	}

}
