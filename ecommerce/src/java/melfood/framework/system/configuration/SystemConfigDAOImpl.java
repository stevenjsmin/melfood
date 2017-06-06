/** 
 * 2016 SystemConfigDAOImpl.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.util.List;

import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
@Repository
public class SystemConfigDAOImpl extends BaseDAO implements SystemConfigDAO {

	@Override
	public List<SystemConfig> getSystemConfigs(SystemConfig sysConfig) throws Exception {
		return sqlSession.selectList("mySqlSystemConfigDao.getSystemConfigs", sysConfig);
	}

	@Override
	public Integer getTotalCntForSystemConfigs(SystemConfig sysConfig) {
		return sqlSession.selectOne("mySqlSystemConfigDao.getTotalCntForSystemConfigs", sysConfig);
	}

	@Override
	public Integer insertSystemConfig(SystemConfig sysConfig) throws Exception {
		return sqlSession.insert("mySqlSystemConfigDao.insertSystemConfig", sysConfig);
	}

	@Override
	public Integer modifySystemConfig(SystemConfig sysConfig) throws Exception {
		return sqlSession.update("mySqlSystemConfigDao.modifySystemConfig", sysConfig);
	}

	@Override
	public Integer modifySystemConfigForNotNull(SystemConfig sysConfig) throws Exception {
		return sqlSession.update("mySqlSystemConfigDao.modifySystemConfigForNotNull", sysConfig);
	}

	@Override
	public Integer deleteSystemConfig(SystemConfig sysConfig) throws Exception {
		return sqlSession.delete("mySqlSystemConfigDao.deleteSystemConfig", sysConfig);
	}

	@Override
	public Boolean existSysConfig(String stage, String key) throws Exception {
		SystemConfig sysConfig = new SystemConfig(stage, key);
		return sqlSession.selectOne("mySqlSystemConfigDao.existSysConfig", sysConfig);
	}

}
