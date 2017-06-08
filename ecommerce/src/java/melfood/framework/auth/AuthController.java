/** 
 * 2015 AuthController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.MelfoodConstants;
import melfood.framework.Ctx;
import melfood.framework.role.Role;
import melfood.framework.system.BaseController;
import melfood.framework.user.User;

/**
 * Authorization controller
 * 
 * @author Steven J.S Min
 * 
 */
@Controller
@RequestMapping("/common/auth")
public class AuthController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/welcome");
		String viewName = "tiles/welcome";

		if (authService.alreadyLogin(request)) {
			HttpSession session = request.getSession(false);
			SessionUserInfo sessionUser = (SessionUserInfo) session.getAttribute(MelfoodConstants.LOGIN_USER_SESSION_ATTR);

			Role role = sessionUser.getSessionRole();

			if (StringUtils.equalsIgnoreCase(role.getRoleId(), MelfoodConstants.USER_TYPE_ADMIN)) {
				viewName = "tiles/admin/welcome";
			} else if (StringUtils.equalsIgnoreCase(role.getRoleId(), MelfoodConstants.USER_TYPE_SELLER)) {
				viewName = "tiles/seller/welcome";
			} else {
				viewName = "tiles/customer/welcome";
			}
		}

		mav.setViewName(viewName);

		return mav;
	}

	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ModelAndView mav = new ModelAndView("tiles/welcome");
		// return mav;

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		if (sessionUser != null) {
			authService.logout(request);
		}
		response.sendRedirect("/common/auth/welcome.yum");

	}

	@RequestMapping("/restrictArea")
	public ModelAndView restrictArea(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/auth/accessRestrict");
		return mav;
	}

	@RequestMapping("/licenseRestrict")
	public ModelAndView licenseRestrict(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/common/auth/licenseRestrict");
		return mav;
	}

	@RequestMapping("/loginForm")
	public ModelAndView loginForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/auth/login");
		return mav;
	}

	@RequestMapping("/sessionExpired")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("tiles/common/auth/sessionExpired");
		return mav;
	}

	@RequestMapping(value = "/login", produces = "application/json")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String sessionRole = "";

		if (StringUtils.isNotBlank(userId)) {
			userId = StringUtils.replace(StringUtils.trim(userId.toLowerCase()), " ", "");
		}
		if (StringUtils.contains(userId, "/")) {
			sessionRole = StringUtils.upperCase(StringUtils.substringBefore(userId, "/"));
			userId = StringUtils.substringAfter(userId, "/");
		} else {
			sessionRole = MelfoodConstants.USER_TYPE_CUSTOMER;
		}

		String message = "";
		User user = userService.getUserInfo(userId);
		boolean valid = userService.validateUser(userId, password);
		String status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;

		if (user != null) {
			boolean isPasswordChangePeriod = user.isPasswordChangePeriod();

			if (StringUtils.equalsIgnoreCase(user.getUseYn(), "Y")) {
				// 사용자 정보도 존재하고, 사용상태가 Y 인경우

				if (valid) {

					if (StringUtils.equalsIgnoreCase(user.getApplyStatus(), "COMPLETE")) {
						status = MelfoodConstants.LOGIN_AUTH_STATUS_SUCCESS;
						authService.setSuccessLogin(userId);
						authService.setSessionUserInfo(userId, request);

						if (StringUtils.isNotBlank(sessionRole)) {
							boolean hasRole = authService.hasRole(request, sessionRole);
							if (hasRole) {
								Role role = roleService.getRoleInfo(sessionRole);
								authService.getSessionUserInfo(request).setSessionRole(role);
							} else {
								Role role = roleService.getRoleInfo(MelfoodConstants.USER_TYPE_CUSTOMER);
								authService.getSessionUserInfo(request).setSessionRole(role);
							}
						}

						// 비밀번호 변경 기간이 되었는지 체크
						if (isPasswordChangePeriod) status = MelfoodConstants.LOGIN_AUTH_STATUS_CHANGE_PWD;

					} else {
						status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
						message = "현재 사용자께서는 등록절차가 아직 마무리 되지 않았습니다. : STATUS[" + user.getApplyStatus() + "]. 자세한 문의 사항은 " + Ctx.xmlConfig.getString("contact-info/customer-service/name") + " 에게 문의해주세요.";
					}

				} else {
					// 사용자 ID에 해당하는 사용자는 존재하지만 비밀번호가 잘 못된경우.
					boolean isLocked = userService.loginFailureIncrease(user);
					if (isLocked) {
						message = "로그인 실패 : 현재 고객님의 계정은 잠김 상태입니다. 자세한 사항은 " + Ctx.xmlConfig.getString("contact-info/customer-service/name") + " 에게 문의해주세요";
					} else {
						message = "로그인 실패 : " + (user.getPasswordFailureCnt() + 1) + "/" + Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt") + ". 로그인이 실패 횟수가 " + Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt") + "이상인 경우 계정이 잠길 수 있습니다.";
					}

					status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
				}

			} else if (StringUtils.equalsIgnoreCase(user.getUseYn(), "N")) {
				// 사용자 정보도 존재하지만 사용상태가 N 인경우
				// Password change period check.
				status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
				message = "로그인 실패 : " + user.getPasswordFailureCnt() + "/" + Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt") + ". 현재 고객님의 계정은 잠겨있는 상태입니다. " + "자세한 문의는" + Ctx.xmlConfig.getString("contact-info/customer-service/name")
						+ " 으로 문의하여 주시기 바랍니다.";

			} else {
				status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
				message = "로그인 실패  : 입력하신 ID(Email 또는 모바일번호)를 확인해주세요";
			}

		} else {
			logger.debug("There is no user information with '" + userId + "'");
			message = "입력하신 ID(Email 또는 모바일번호)에 해당하는 사용자 정보가 존재하지 않습니다.";
			status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
		}

		model.put("status", status);
		model.put("message", message);

		return model;
	}

}
