/** 
 * 2015 MypageController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.Ctx;
import melfood.framework.system.BaseController;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/common/mypage")
public class MypageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);

	@RequestMapping("/PasswordChange")
	public ModelAndView logout(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/mypage/passwordchange");
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		int passwordMinLength = Ctx.xmlConfig.getInt("system-config/password-minimum-length");
		int passwordChgPeriodDays = Ctx.xmlConfig.getInt("system-config/password-change-period-days");		
		mav.addObject("passwordChgPeriodDays", passwordChgPeriodDays);
		mav.addObject("passwordMinLength", passwordMinLength);
		mav.addObject("sessionUser", sessionUser.getUser());

		return mav;
	}

}
