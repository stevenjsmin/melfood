/** 
 * 2015 UssContextListener.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import melfood.framework.core.SystemInitializer;

/**
 * @author Steven
 *
 */
public class MelfoodContextListener extends ContextLoaderListener {

	private static final Logger logger = LoggerFactory.getLogger(MelfoodContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);

		try {
			SystemInitializer initializer = SystemInitializer.getInstance();
			initializer.init(event);

		} catch (Exception e) {
			logger.info("시스템을 초기화 하는 도중에 문제가 발생하여 시스템 가동을 중지합니다. : " + e.getMessage());
			System.exit(1);
		}
	}
}
