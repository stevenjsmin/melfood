/** 
 * 2015 SystemConfigDAO.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.List;

/**
 * Framwork code data access interface.
 * 
 * @author Steven J.S Min
 * 
 */
public interface SystemConfigDAO {

	public List<SystemConfig> getSystemConfigs(SystemConfig sysConfig) throws Exception;

	public Integer getTotalCntForSystemConfigs(SystemConfig sysConfig);

	public Integer insertSystemConfig(SystemConfig sysConfig) throws Exception;

	public Integer modifySystemConfig(SystemConfig sysConfig) throws Exception;

	public Integer modifySystemConfigForNotNull(SystemConfig sysConfig) throws Exception;

	public Integer deleteSystemConfig(SystemConfig sysConfig) throws Exception;

	public Boolean existSysConfig(String stage, String key) throws Exception;
}
