/** 
 * 2015 RoleServiceImpl.java
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.uitl.html.Option;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public Integer addMyRoles(List<Role> roles) throws Exception {
		return roleDAO.addMyRoles(roles);
	}

	@Override
	public Integer removeMyRoles(String userId) throws Exception {
		return roleDAO.removeMyRoles(userId);
	}

	@Override
	public Role getRoleInfo(String roleId) throws Exception {
		// if (StringUtils.isBlank(roleId)) throw new Exception("ROLE_ID can not be empty. Check the value");
		if (StringUtils.isBlank(roleId)) return null;

		HashMap<String, String> param = new HashMap<String, String>();
		param.put("roleId", roleId);

		return roleDAO.getRoleInfo(param);
	}

	@Override
	public List<Role> getRoleList(HashMap<String, String> param) throws Exception {
		return roleDAO.getRoleList(param);
	}

	@Override
	public List<Role> getMyRoleList(String userId) throws Exception {
		return roleDAO.getMyRoleList(userId);
	}

	@Override
	public Integer deleteRoleInfo(String roleId) throws Exception {
		if (StringUtils.isBlank(roleId)) throw new Exception("ROLE_ID can not be empty. Check the values");

		return roleDAO.deleteRoleInfo(roleId);
	}

	@Override
	public Integer insertRoleInfo(HashMap<String, String> param) throws Exception {
		return roleDAO.insertRoleInfo(param);
	}

	@Override
	public Integer modifyRoleInfo(HashMap<String, String> param) throws Exception {
		String roleId = param.get("roleId");
		if (StringUtils.isBlank(roleId)) throw new Exception("ROLE_ID can not be empty. Check the value");

		return roleDAO.modifyRoleInfo(param);
	}

	@Override
	public Integer applyMappingWithService(ArrayList<Object> services) throws Exception {
		if (services.size() == 0) return 0;

		return roleDAO.applyMappingWithService(services);
	}

	@Override
	public Integer removeRoleMappingWithService(String roleId, ArrayList<Object> services) throws Exception {
		if (StringUtils.isBlank(roleId)) throw new Exception("ROLE_ID can not be empty. Check the value");
		if (services.size() == 0) return 0;

		return roleDAO.removeRoleMappingWithService(roleId, services);
	}

	@Override
	public boolean existRole(String name) throws Exception {
		return roleDAO.existRole(name);
	}

	@Override
	public List<Option> getRoleOptionListForComboBox(String defaultSelect) throws Exception {

		List<Option> options = new ArrayList<Option>();
		options.add(new Option(null, " - select -", false));

		HashMap<String, String> param = null;
		List<Role> list = roleDAO.getRoleList(param);
		for (Role role : list) {
			if (role.getRoleId() == defaultSelect) {
				options.add(new Option(role.getRoleId(), role.getRoleName(), true));
			} else {
				options.add(new Option(role.getRoleId(), role.getRoleName(), false));
			}
		}
		return options;
	}

}
