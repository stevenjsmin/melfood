/** 
 * 2015 AccessCheckFilter.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.MelfoodConstants;
import melfood.framework.Ctx;

/**
 * @author Steven
 *
 */
public class AccessCheckFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AccessCheckFilter.class);
	private ArrayList<String> ignorUrlList;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// String urls = UssContext.APPLICATION_INFO.getAcessChkExceptUrl();
		String urls[] = Ctx.xmlConfig.getStringArray("system-config/acess-check-except-url");

		// urls = StringUtils.trim(urls);
		// StringTokenizer token = new StringTokenizer(urls, ",");
		ignorUrlList = new ArrayList<String>();
		for (int i = 0; i < urls.length; i++) {
			ignorUrlList.add(StringUtils.trim(urls[i]));
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		AccessChecker accChecker = new AccessChecker();
		SessionUserInfo sessionUser = null;

		HttpSession session = request.getSession(false);
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
		}

		String url = request.getServletPath();
		// logger.debug("\n\nAccessCheckFilter: Requested URL : " + url + "\n\n");
		if (ignorUrlList.contains(url) || StringUtils.startsWith(url, "/guest/") || StringUtils.startsWith(url, "/order/") || StringUtils.startsWith(url, "/shop/")) {

			// If URL don't need to check, execute next process
			// Because /common/auth/RestrictArea.uss require user information,
			// if you redirect directly, it can make some error.
			if (StringUtils.equals(url, "/common/auth/restrictArea.yum")) {
				if (sessionUser != null) {
					chain.doFilter(req, res);
				} else {
					response.sendRedirect("/common/auth/welcome.yum");
				}

			} else {
				chain.doFilter(req, res);
			}

		} else {

			if (session == null) {
				response.sendRedirect("/common/auth/welcome.yum");
			} else {

				if (sessionUser != null) {
					if (accChecker.hasAuth(request) || StringUtils.endsWithIgnoreCase(sessionUser.getUser().getUserId(), "admin")) {
						chain.doFilter(req, res);
					} else {
						// In case of don't have proper rights.
						response.sendRedirect("/common/auth/restrictArea.yum");
					}
				} else if (sessionUser == null && StringUtils.equals(url, "/common/auth/sessionExpired.yum")) {
					chain.doFilter(req, res);
				} else {
					 response.sendRedirect("/common/auth/sessionExpired.yum");

				}
			}
		}

	}

	@Override
	public void destroy() {
	}

}
