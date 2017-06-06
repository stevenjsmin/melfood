/** 
 * 2015 RoleDAOImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;

import melfood.framework.core.BaseDAO;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Repository
public class RoleDAOImpl extends BaseDAO implements RoleDAO {

	@Override
	public Integer addMyRoles(List<Role> roles) throws Exception {
		return sqlSession.insert("mySqlRoleDao.addMyRoles", roles);
	}

	@Override
	public Integer removeMyRoles(String userId) throws Exception {
		return sqlSession.delete("mySqlRoleDao.removeMyRoles", userId);
	}

	@Override
	public Role getRoleInfo(HashMap<String, String> map) throws Exception {
		return sqlSession.selectOne("mySqlRoleDao.getRoleInfo", map);
	}

	@Override
	public List<Role> getRoleList(HashMap<String, String> map) throws Exception {
		return sqlSession.selectList("mySqlRoleDao.roleList", map);
	}

	@Override
	public List<Role> getMyRoleList(String userId) throws Exception {
		return sqlSession.selectList("mySqlRoleDao.myRoleList", userId);
	}

	@Override
	public Integer deleteRoleInfo(String roleId) throws Exception {
		return sqlSession.delete("mySqlRoleDao.deleteRoleInfo", roleId);
	}

	@Override
	public Integer insertRoleInfo(HashMap<String, String> map) throws Exception {
		String newId = ((Integer) this.getNewRoleId()).toString();
		map.put("roleId", newId);
		return sqlSession.insert("mySqlRoleDao.insertRoleInfo", map);
	}

	@Override
	public Integer modifyRoleInfo(HashMap<String, String> map) throws Exception {
		return sqlSession.insert("mySqlRoleDao.modifyRoleInfo", map);
	}

	@Override
	public Integer getNewRoleId() throws Exception {
		return sqlSession.selectOne("mySqlRoleDao.getNewRoleId");
	}

	@Override
	public Integer applyMappingWithService(ArrayList<Object> services) throws Exception {
		if (services.size() <= 0) return 0;

		List<HashMap> list = new ArrayList<HashMap>();
		HashMap<String, String> param = null;

		for (Object obj : services) {
			param = new HashMap<String, String>();
			Map<String, String> mp = BeanUtils.describe(obj);
			param.put("svcId", (mp.get("svcId")));
			param.put("roleId", (mp.get("roleId")));
			list.add(param);
		}

		return sqlSession.insert("mySqlRoleDao.applyMappingWithService", list);
	}

	@Override
	public Integer removeRoleMappingWithService(String roleId, ArrayList<Object> services) throws Exception {
		if (services.size() <= 0) return 0;

		List<String> list = new ArrayList<String>();
		HashMap<String, String> svcIds = null;
		HashMap<String, Object> param = new HashMap<String, Object>();

		for (Object obj : services) {
			Map<String, String> mp = BeanUtils.describe(obj);
			list.add(mp.get("svcId"));
		}
		param.put("roleId", roleId);
		param.put("list", list);

		return sqlSession.delete("mySqlRoleDao.removeRoleMappingWithService", param);
	}

	@Override
	public boolean existRole(String name) throws Exception {
		boolean exist = true;
		HashMap<String, String> map = new HashMap();
		map.put("name", name);

		List<Role> list = this.getRoleList(map);
		if (list != null && list.size() > 0) {
			exist = true;
		} else {
			exist = false;
		}

		return exist;
	}

}
