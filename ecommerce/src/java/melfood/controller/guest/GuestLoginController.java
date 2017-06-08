/** 
 * 2016 GuestLoginController.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.system.BaseController;

/**
 * 회원가입에 필요한 콘트롤러들을 정의한다.
 * 
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/guest/login")
public class GuestLoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(JoinMemberController.class);

	@RequestMapping("/openWin")
	public ModelAndView join(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/guest/login/openWin");
		return mav;
	}

}
