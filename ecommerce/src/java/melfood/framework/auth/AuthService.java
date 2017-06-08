/** 
 * 2015 AuthService.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Steven J.S Min
 * 
 */
public interface AuthService {

	/**
	 * Setting to success
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void setSuccessLogin(String userId) throws Exception;

	/**
	 * Setting login trying count
	 * 
	 * @param userId
	 * @param tryCnt
	 * @return
	 * @throws Exception
	 */
	public Integer setLoginTryCnt(String userId, int tryCnt) throws Exception;

	/**
	 * Setting user information into session
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SessionUserInfo setSessionUserInfo(String userId, HttpServletRequest request) throws Exception;

	/**
	 * Getting user information from session
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SessionUserInfo getSessionUserInfo(HttpServletRequest request) throws Exception;

	/**
	 * Getting user role id information if session have user information
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getLoginUserRoleId(HttpServletRequest request) throws Exception;

	/**
	 * Getting user role name information if session have user information
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getLoginUserRoleName(HttpServletRequest request) throws Exception;

	/**
	 * Checking login information in session
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean alreadyLogin(HttpServletRequest request) throws Exception;

	/**
	 * Remove loging information from session
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void logout(HttpServletRequest request) throws Exception;

	/**
	 * Get current User session list
	 * 
	 * @return
	 * @throws Exception
	 */
	public Iterator<SessionUserInfo> getCurrentLoginSessionUsers() throws Exception;


	public boolean hasRole(HttpServletRequest request, String roleId) throws Exception;

}
