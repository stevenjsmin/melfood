/** 
 * 2015 DefaultPropertyConfigurer.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */


package melfood.framework.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Steven
 *
 */
public class DefaultPropertyConfigurer extends PropertyPlaceholderConfigurer {
	private Map<String, String> startingProperties = new HashMap<String, String>();

	public void setStartingProperties(Map<String, String> startingProperties) {
		this.startingProperties = startingProperties;
	}

	public DefaultPropertyConfigurer() {
		try {
			loadDefaultProperties();
		} catch (IOException e) {
			logger.warn("failed to load default properties", e);
		}
	}

	private void loadDefaultProperties() throws IOException {
		final Properties defaultProperties = new Properties();
		for (Map.Entry<String, String> entry : startingProperties.entrySet()) {
			defaultProperties.put(entry.getKey(), entry.getValue());
		}
		this.setPropertiesArray(new Properties[] { defaultProperties });
	}
}
