/** 
 * 2015 UssSessionUserInfo.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.role.Role;
import melfood.framework.user.User;
import melfood.framework.user.UserService;

/**
 * @author Steven
 *
 */
public class SessionUserInfo {

	private static final Logger logger = LoggerFactory.getLogger(SessionUserInfo.class);

	private UserService userService;

	private User user;
	private String loginIp = "";
	private String loginDate;
	private Role sessionRole;

	private static Map<SessionUserInfo, HttpSession> loginUsers = new HashMap<SessionUserInfo, HttpSession>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get current logined users as Map
	 * 
	 * @return
	 */
	public static Map<SessionUserInfo, HttpSession> getLoginUsers() {
		return loginUsers;
	}

	/**
	 * Get current Login user. If there are no login user it will be retuan as a NULL.
	 * 
	 * @param userId
	 * @return
	 */
	public static SessionUserInfo getCurrentLoginUser(String userId) {

		SessionUserInfo loginUser = null;

		try {

			Iterator iterator = loginUsers.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				// UssSessionUserInfo sessUser = (UssSessionUserInfo) mapEntry.getValue();
				SessionUserInfo sessUser = (SessionUserInfo) mapEntry.getKey();

				if (StringUtils.equalsIgnoreCase(sessUser.getUser().getUserId(), userId)) {
					loginUser = sessUser;
				}

				// logger.info("The key is: " + mapEntry.getKey() + ",value is :" + mapEntry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loginUser;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Role getSessionRole() {
		return sessionRole;
	}

	public void setSessionRole(Role sessionRole) {
		this.sessionRole = sessionRole;
	}

}
