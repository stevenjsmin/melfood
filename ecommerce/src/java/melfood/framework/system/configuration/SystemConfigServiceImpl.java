/** 
 * 2016 SystemConfigServiceImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.Ctx;

/**
 *
 * @author steven.min
 *
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	@Autowired
	private SystemConfigDAO systemConfigDAO;

	@Override
	public SystemConfig getSystemConfig(SystemConfig sysConfig) throws Exception {
		List<SystemConfig> sysConfigs = this.getSystemConfigs(sysConfig);
		if (sysConfigs != null && sysConfigs.size() > 0) {
			return sysConfigs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<SystemConfig> getSystemConfigs(SystemConfig sysConfig) throws Exception {
		return systemConfigDAO.getSystemConfigs(sysConfig);
	}

	@Override
	public Integer getTotalCntForSystemConfigs(SystemConfig sysConfig) {
		return systemConfigDAO.getTotalCntForSystemConfigs(sysConfig);
	}

	@Override
	public boolean existSysConfig(String stage, String key) throws Exception {
		// return (this.getSystemConfig(new SystemConfig(key)) != null) ? true : false;
		return systemConfigDAO.existSysConfig(stage, key);
	}

	@Override
	public Integer insertSystemConfig(SystemConfig sysConfig) throws Exception {
		return systemConfigDAO.insertSystemConfig(sysConfig);
	}

	@Override
	public Integer modifySystemConfig(SystemConfig sysConfig) throws Exception {
		return systemConfigDAO.modifySystemConfig(sysConfig);
	}

	@Override
	public Integer modifySystemConfigForNotNull(SystemConfig sysConfig) throws Exception {
		return systemConfigDAO.modifySystemConfigForNotNull(sysConfig);
	}

	@Override
	public Integer deleteSystemConfig(SystemConfig sysConfig) throws Exception {
		Ctx.removeSysProperty(sysConfig.getKey());
		return systemConfigDAO.deleteSystemConfig(sysConfig);
	}

	@Override
	public Boolean getBoolean(String stage, String key) throws Exception {
		Boolean retValue = false;
		String value = this.getString(stage, key);

		if (StringUtils.equalsIgnoreCase(value, "Y") || StringUtils.equalsIgnoreCase(value, "YES") || StringUtils.equalsIgnoreCase(value, "T") || StringUtils.equalsIgnoreCase(value, "TRUE")
				|| StringUtils.equalsIgnoreCase(value, "1") || StringUtils.equalsIgnoreCase(value, "OK") || StringUtils.equalsIgnoreCase(value, "SUCESS")) {
			retValue = true;
		} else {
			retValue = false;
		}

		return retValue;
	}

	@Override
	public Integer getInt(String stage, String key) throws Exception {
		String value = this.getString(stage, key);
		Integer retValue = 0;

		try {
			retValue = Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			retValue = 0;
		}

		return retValue;
	}

	@Override
	public String getString(String stage, String key) throws Exception {
		SystemConfig sysConfig = this.getSystemConfig(new SystemConfig(stage, key));
		if (sysConfig == null) return null;

		return sysConfig.getValue();
	}

	@Override
	public Boolean updatSystemVariable(SystemConfig sysConfig) throws Exception {
		boolean retVale = false;
		String stage = sysConfig.getStage();
		String key = sysConfig.getKey();
		String value = sysConfig.getValue();
		String useYn = sysConfig.getUseYn();

		try {
			key = StringUtils.upperCase(StringUtils.trim(stage) + "::" + StringUtils.trim(key));
			value = StringUtils.trim(value);

			key = StringUtils.upperCase(key);

			if (StringUtils.equalsIgnoreCase(useYn, "Y")) {
				Ctx.addSysProperty(key, value);
			} else {
				Ctx.removeSysProperty(key);
			}

			retVale = true;
		} catch (Exception e) {
			throw new Exception("시스템 환경변수 정보를 수정하는 도중에 문제가 발생하였습니다. :" + e.getMessage());
		}
		return retVale;
	}

}
