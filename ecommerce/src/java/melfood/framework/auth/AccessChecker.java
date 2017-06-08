/** 
 * 2015 AccessChecker.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.MelfoodConstants;
import melfood.framework.service.ServiceDto;

/**
 * @author Steven
 *
 */
public class AccessChecker {
	private static final Logger logger = LoggerFactory.getLogger(AccessChecker.class);

	public boolean hasAuth(HttpServletRequest request) {
		boolean hasAuth = false;

		try {

			String requestedUrl = request.getServletPath();
			String serviceUrl = StringUtils.substringBeforeLast(requestedUrl, "/");
			ServiceDto svcDto = null;

			logger.debug("Service URL:" + serviceUrl);
			HttpSession session = request.getSession(false);
			SessionUserInfo sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);

			/*
			 * HashMap<String, ServiceDto> serviceMap = sessionUser.getServiceMap(); // if(serviceMap == null) return false; if (serviceMap == null)
			 * return true; // Temporary
			 * 
			 * if (serviceMap.containsKey(serviceUrl)) { hasAuth = true; svcDto = (ServiceDto) serviceMap.get(serviceUrl);
			 * logger.debug("Requested service information :" + svcDto); } else { // Checking for service that don't need to be authorization if
			 * (UssContext.NO_LIMITED_SERVICE_MAP.containsKey(serviceUrl)) { hasAuth = true; } else { hasAuth = false; } }
			 */

			hasAuth = true; // TODO : This is temporary setting.

		} catch (Exception e) {
			logger.debug("During checking of access rights, problem happen:" + e.getMessage());
			e.printStackTrace();
			hasAuth = false;
		}
		return hasAuth;
	}
}
