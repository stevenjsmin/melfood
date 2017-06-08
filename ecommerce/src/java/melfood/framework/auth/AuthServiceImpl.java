/** 
 * 2015 AuthServiceImpl.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import melfood.framework.MelfoodConstants;
import melfood.framework.role.Role;
import melfood.framework.role.RoleService;
import melfood.framework.user.User;
import melfood.framework.user.UserDAO;
import melfood.framework.user.UserService;

/**
 * @author Steven J.S Min
 * 
 */

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Override
	public void setSuccessLogin(String userId) throws Exception {

		// TimeZone tz = TimeZone.getTimeZone("Australia/Melbourne");
		// SimpleDateFormat dFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss a");
		// dFormat.setTimeZone(tz);
		// String curTime = dFormat.format(new Date());
		User user = new User(userId);
		user.setPasswordFailureCnt(0);

		userService.modifyUserForNotNull(user);

	}

	@Override
	public Integer setLoginTryCnt(String userName, int tryCnt) throws Exception {
		// return userService.setLoginTryCnt(userName, tryCnt);
		return null;
	}

	@Override
	public SessionUserInfo setSessionUserInfo(String userId, HttpServletRequest request) throws Exception {

		SessionUserInfo sessionUser = new SessionUserInfo();
		sessionUser.setUserService(userService);

		User user = userService.getUserInfo(userId);
		sessionUser.setUser(user);

		// Setting user roles
		List<Role> roles = roleService.getMyRoleList(userId);
		user.setRoles(roles);

		HttpSession session = request.getSession(true);
		String ipAddress = request.getRemoteAddr();
		sessionUser.setLoginIp(ipAddress);
		session.setAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR, sessionUser);

		logger.debug("Finish to set login user information into session.");

		return sessionUser;
	}

	@Override
	public int getLoginUserRoleId(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		int userRoleId = 0;

		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
			// userRoleId = sessionUser.getUserInfo().getRoleId();
		}

		return userRoleId;
	}

	@Override
	public String getLoginUserRoleName(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		String returnVal = null;
		String userRoleName = null;

		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
			// userRoleName = sessionUser.getUserInfo().getRole();
			returnVal = StringUtils.lowerCase(userRoleName);
		}

		return returnVal;
	}

	@Override
	public boolean alreadyLogin(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		boolean returnVal = false;
		SessionUserInfo sessionUser = null;

		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
		}

		if (sessionUser != null) returnVal = true;

		return returnVal;

	}

	@Override
	public SessionUserInfo getSessionUserInfo(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
		}
		return sessionUser;
	}

	@Override
	public void logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);

			if (sessionUser != null) {
				session.removeAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
			}
		}
	}

	@Override
	public Iterator<SessionUserInfo> getCurrentLoginSessionUsers() throws Exception {

		Iterator iterator = SessionUserInfo.getLoginUsers().entrySet().iterator();
		List<SessionUserInfo> sessionUserList = new ArrayList<SessionUserInfo>();

		if (iterator == null) return null;

		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			SessionUserInfo sessUser = (SessionUserInfo) mapEntry.getValue();
			sessionUserList.add(sessUser);
		}
		return sessionUserList.iterator();
	}

	@Override
	public boolean hasRole(HttpServletRequest request, String roleId) throws Exception {

		boolean returnVal = false;

		List<Role> roles = new ArrayList<Role>();

		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);

		roles = sessionUser.getUser().getRoles();

		for (Role role : roles) {
			if (StringUtils.equalsIgnoreCase(role.getRoleId(), roleId)) {
				returnVal = true;
				break;
			}
		}

		return returnVal;
	}

}
