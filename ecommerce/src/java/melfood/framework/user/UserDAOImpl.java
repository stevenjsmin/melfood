/** 
 * 2015 UserDAOImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import melfood.framework.core.BaseDAO;
import melfood.framework.role.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven J.S Min
 * 
 */

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Override
	public List<User> getUsers(User user) throws Exception {
		List<User> list = sqlSession.selectList("mySqlUserDao.getUsers", user);
		return list;
	}

	@Override
	public Integer getTotalCntForUsers(User user) {
		return sqlSession.selectOne("mySqlUserDao.getTotalCntForGetUsers", user);
	}

	@Override
	public Integer deleteUser(User user) throws Exception {
		if (StringUtils.isBlank(user.getUserId())) throw new Exception("Cannot be empty userId for delete");
		return sqlSession.delete("mySqlUserDao.deleteUser", user);
	}

	@Override
	public String insertUser(User user) throws Exception {
		int updateCnt = sqlSession.insert("mySqlUserDao.insertUser", user);
		if (updateCnt > 0) {
			return user.getUserId();
		} else {
			return null;
		}

	}

	@Override
	public List<Role> getUserRoles(String userId) throws Exception {
		return sqlSession.selectList("mySqlUserDao.getUserRoles", userId);
	}

	@Override
	public List<Role> getUserRolesNotMaped(String userId) throws Exception {
		return sqlSession.selectList("mySqlUserDao.getUserRolesNotMaped", userId);
	}

	@Override
	public Integer applyMapping(String userId, List<Role> roles) throws Exception {
		if (roles.size() <= 0) return 0;

		List<UserRole> list = new ArrayList<UserRole>();
		UserRole userRole = null;

		for (Role role : roles) {
			userRole = new UserRole(userId, role.getRoleId());
			list.add(userRole);
		}

		return sqlSession.insert("mySqlUserDao.applyMapping", list);
	}

	@Override
	public Integer removeMapping(String userId, List<Role> roles) throws Exception {
		if (roles.size() <= 0) return 0;

		List<UserRole> list = new ArrayList<UserRole>();
		UserRole userRole = null;

		for (Role role : roles) {
			userRole = new UserRole(userId, role.getRoleId());
			list.add(userRole);
		}

		return sqlSession.delete("mySqlUserDao.removeMapping", list);
	}

	@Override
	public String passwordChange(String userId, String password) throws Exception {
		User user = new User(userId, password);
		return this.updateUserForNotNull(user);
	}

	@Override
	public String updateUser(User user) throws Exception {
		int updateCnt = sqlSession.update("mySqlUserDao.updateUser", user);
		if (updateCnt > 0) {
			return user.getUserId();
		} else {
			return null;
		}
	}

	@Override
	public String updateUserForNotNull(User user) throws Exception {
		int updateCnt = sqlSession.update("mySqlUserDao.updateUserForNotNull", user);
		if (updateCnt > 0) {
			return user.getUserId();
		} else {
			return null;
		}
	}

	@Override
	public String initialUserAccount(User user, boolean includePassword) throws Exception {
		int updateCnt = sqlSession.update("mySqlUserDao.initialUserAccount", user);
		if (updateCnt > 0) {
			return user.getUserId();
		} else {
			return null;
		}
	}

	@Override
	public String getAllRoles(String userId) throws Exception {
		return sqlSession.selectOne("mySqlUserDao.getAllRoles", userId);
	}

	@Override
	public String updateUserProfilePhoto(User user) throws Exception {
		sqlSession.update("mySqlUserDao.updateUserProfilePhoto", user);
		return user.getUserId();
	}

	@Override
	public List<User> getUsersByRoleIds(String[] userRoles) throws Exception {
		List<Role> roles = new ArrayList<Role>();
		for (String role : userRoles) {
			roles.add(new Role(role));
		}
		return sqlSession.selectList("mySqlUserDao.getUsersByRoleIds", roles);
	}

}
