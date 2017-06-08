/** 
 * 2016 MapUserRole.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import melfood.framework.role.Role;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class UserRole extends Role {

	private String userId;

	public UserRole() {
	}

	public UserRole(String userId, String roleId) {
		this.userId = userId;
		super.setRoleId(roleId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
