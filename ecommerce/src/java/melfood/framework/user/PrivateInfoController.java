/** 
 * 2015 PrivateInfoController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;

/**
 * Private Information management
 * 
 * @author Steven J.S Min
 * 
 */
@Controller
@RequestMapping("/framework/privateInfo")
public class PrivateInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PrivateInfoController.class);


	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession(false);
		SessionUserInfo sessionUser = null;
		if (session != null) {
			sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);
			request.setAttribute("userId", sessionUser.getUser().getUserId());
		}

		mav = this.userDetailInfo(request);
		mav.setViewName("framework/privateInfo/main");
		mav.addObject("pupupMode", "NO");
		return mav;
	}

	@RequestMapping("/PrivateDetailInfo")
	public ModelAndView userDetailInfo(HttpServletRequest request) throws Exception {
		String viewName = "tiles/framework/popup/privateDetailInfo";

		ModelAndView mav = new ModelAndView(viewName);

		User user = new User();

		String userId = request.getParameter("userId");
		if (StringUtils.isEmpty(userId)) userId = (String) request.getAttribute("userId");
		user = userService.getUserInfo(userId);

		mav.addObject("user", user);

		return mav;
	}

}
