/** 
 * 2015 SessionManagerController.java
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.system.BaseController;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/common/sessionusermanager")
public class SessionUserManagerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SessionUserManagerController.class);

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/sessionusermanager/main");

		Iterator iterator = SessionUserInfo.getLoginUsers().entrySet().iterator();
		List<SessionUserInfo> sessionUserList = new ArrayList<SessionUserInfo>();

		if (iterator == null) return null;

		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			SessionUserInfo sessUser = (SessionUserInfo) mapEntry.getKey();
			sessionUserList.add(sessUser);
		}
		mav.addObject("sessionUserList", sessionUserList);

		return mav;
	}

	@RequestMapping(value = "/KillSessionUser", produces = "application/json")
	@ResponseBody
	public Map<String, Object> killSessionUser(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		String userId = request.getParameter("sessionUserId");

		Map<SessionUserInfo, HttpSession> loginUsers = SessionUserInfo.getLoginUsers();
		Iterator iterator = loginUsers.entrySet().iterator();

		if (iterator == null) return null;

		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			SessionUserInfo sessUser = (SessionUserInfo) mapEntry.getKey();

			if (StringUtils.equalsIgnoreCase(sessUser.getUser().getUserId(), userId)) {
				HttpSession session = loginUsers.get(sessUser);
				if (session != null) {
					session.invalidate();
					model.put("result", "SUCCESS");
					return model;
				}
			}
		}

		model.put("result", "FAILURE");
		return model;
	}
}
