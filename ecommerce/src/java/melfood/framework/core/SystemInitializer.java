/** 
 * 2015 SystemInitializer.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import melfood.framework.system.configuration.MelfoodConfigurator;

/**
 * This class process system initializing work
 * 
 * @author Steven Min
 *
 */
public class SystemInitializer {
	private static final Logger logger = LoggerFactory.getLogger(SystemInitializer.class);

	private static SystemInitializer singleton;

	private SystemInitializer() {
	}

	public static SystemInitializer getInstance() {
		if (singleton == null) {
			singleton = new SystemInitializer();
		}
		return singleton;
	}

	public void init(ServletContextEvent event) throws Exception {

		System.out.println();
		System.out.println();

		logger.info("Melfood eCommerce Engin Initialize............................");

		ServletContext ctx = event.getServletContext();
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);

		try {
			// System configuration load and save to UssContext static property(UssContext.SYS_CONFIG)
			MelfoodConfigurator.getInstance();

			// TODO ~~~~~~~~~~~~~~~~~~~~~
			// Callable DAO
			// SysProptyDao sysProptyDao = (SysProptyDao) springContext.getBean("sysProptyDao");
			// Map<String, Object> sysProperty = sysProptyDao.getSysPropertyInfo2();
			// UssContext.SYSTEM_ADMINISTRATOR = (User) sysProperty.get("user");
			// UssContext.SYSTEM_PROPERTY = (SysProptyDto) sysProperty.get("propertyDto");

			// TODO : All service load that don't need to be check authorization
			// ServiceDao serviceDao = (ServiceDao) springContext.getBean("serviceDao");
			// CosmosContext.NO_LIMITED_SERVICE_MAP = serviceDao.getServiceListForNotAuthReq();\

			logger.info("Melfood eCommerce Engin Initializing finished successfully......");

		} catch (Exception e) {
			logger.info("Melfood eCommerce Engin Initializing failured ......");
			throw e;
		}

		System.out.println();
		System.out.println();

	}
}
