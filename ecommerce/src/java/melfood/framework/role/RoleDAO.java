/** 
 * 2015 Role.java
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

/**
 * Framework Role data access interface.
 * 
 * @author Steven J.S Min
 * 
 */
public interface RoleDAO {

	public Integer addMyRoles(List<Role> roles) throws Exception;
	public Integer removeMyRoles(String userId) throws Exception;
	
	
	/**
	 * Get a role information
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Role getRoleInfo(HashMap<String, String> map) throws Exception;

	/**
	 * Get role list corresponding with role name or description<br>
	 * This method will be search roles by like search. Therefore you have to set<br>
	 * 'name' and 'description' in map of HashMap<String key, String value>
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRoleList(HashMap<String, String> map) throws Exception;

	/**
	 * Get role list corresponding with userId<br>
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Role> getMyRoleList(String userId) throws Exception;

	/**
	 * Delete a role corresponding roleId
	 * 
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Integer deleteRoleInfo(String roleId) throws Exception;

	/**
	 * Insert a role information<br>
	 * All role information have to set in HashMap<String, String>. All keys of HashMap should be matched with properties of Role type
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer insertRoleInfo(HashMap<String, String> map) throws Exception;

	/**
	 * Modify a role information<br>
	 * All role information have to set in HashMap<String, String>. All keys of HashMap should be matched with properties of Role type
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer modifyRoleInfo(HashMap<String, String> map) throws Exception;

	/**
	 * Getting next role ID for new role information that will be inserted
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer getNewRoleId() throws Exception;

	/**
	 * Mapping with services
	 * 
	 * @param services
	 * @return
	 * @throws Exception
	 */
	public Integer applyMappingWithService(ArrayList<Object> services) throws Exception;

	/**
	 * Remove mapping with services
	 * 
	 * @param roleId
	 * @param services
	 * @return
	 * @throws Exception
	 */
	public Integer removeRoleMappingWithService(String roleId, ArrayList<Object> services) throws Exception;

	/**
	 * Check existing role by role name
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean existRole(String name) throws Exception;

}