/** 
 * 2015 SystemConfigService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.List;

/**
 * 시스템 환경변수 설정을 위한 인터페이스 설정
 * 
 * @author Steven J.S Min
 * 
 */
public interface SystemConfigService {

	public SystemConfig getSystemConfig(SystemConfig sysConfig) throws Exception;

	public List<SystemConfig> getSystemConfigs(SystemConfig sysConfig) throws Exception;

	public Integer getTotalCntForSystemConfigs(SystemConfig sysConfig);

	public boolean existSysConfig(String stage, String key) throws Exception;

	public Integer insertSystemConfig(SystemConfig sysConfig) throws Exception;

	public Integer modifySystemConfig(SystemConfig sysConfig) throws Exception;

	public Integer modifySystemConfigForNotNull(SystemConfig sysConfig) throws Exception;

	public Integer deleteSystemConfig(SystemConfig sysConfig) throws Exception;

	public Boolean getBoolean(String stage, String key) throws Exception;

	public String getString(String stage, String key) throws Exception;

	public Integer getInt(String stage, String key) throws Exception;

	public Boolean updatSystemVariable(SystemConfig sysConfig) throws Exception;
}
