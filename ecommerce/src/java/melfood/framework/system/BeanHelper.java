/** 
 * 2016 BeanHelper.java
 * Created by steven.min
 *  
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.user.User;

/**
 * TODO: Class description
 *
 * @author steven.min
 *
 */
public class BeanHelper implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		context = ac;
	}

	public static Object getBean(String beanId) {
		return getApplicationContext().getBean(beanId);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId, Class<T> requiredType) {
		return (T) getApplicationContext().getBean(beanId);
	}

	/**
	 * Get session user information
	 * 
	 * @return
	 * @throws Exception
	 */
	public static SessionUserInfo getSessionUser() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
		} else {
			return null;
		}
		return sessionUser;
	}

	/**
	 * Get session user information
	 * 
	 * @return
	 * @throws Exception
	 */
	public static User getUserFromSession() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
		} else {
			return null;
		}

		if (sessionUser == null) {
			return null;
		} else {
			return sessionUser.getUser();
		}
	}
}
