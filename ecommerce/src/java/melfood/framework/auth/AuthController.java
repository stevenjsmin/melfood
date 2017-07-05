/**
 * 2015 AuthController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.auth;

import melfood.framework.Ctx;
import melfood.framework.MelfoodConstants;
import melfood.framework.role.Role;
import melfood.framework.system.BaseController;
import melfood.framework.user.User;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Authorization controller
 *
 * @author Steven J.S Min
 */
@Controller
@RequestMapping("/common/auth")
public class AuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    GroupPurchaseService groupPurchaseService;

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/welcome")
    public ModelAndView welcome(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/welcome");
        String viewName = "tiles/welcome";

        Map<String, Object> retVal = null;
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

                retVal = this.getGroupPurchaseList(request);
                mav.addObject("groupPurchaselist", (List<GroupPurchase>) retVal.get("list"));
                mav.addObject("groupPurchaseAlllist", (List<GroupPurchase>) retVal.get("allList"));
                mav.addObject("groupPurchaselistTotalCount", (Integer) retVal.get("totalCount"));
            }
        } else {
            retVal = this.getGroupPurchaseList(request);
            mav.addObject("groupPurchaselist", (List<GroupPurchase>) retVal.get("list"));
            mav.addObject("groupPurchaseAlllist", (List<GroupPurchase>) retVal.get("allList"));
            mav.addObject("groupPurchaselistTotalCount", (Integer) retVal.get("totalCount"));
        }

        if (retVal != null) {
            mav.addObject("previousPage", (Integer) retVal.get("previousPage"));
            mav.addObject("nextPage", (Integer) retVal.get("nextPage"));
            mav.addObject("currPage", (Integer) retVal.get("currPage"));
        }

        mav.setViewName(viewName);

        return mav;
    }


    private Map<String, Object> getGroupPurchaseList(HttpServletRequest request) throws Exception {

        GroupPurchase groupPurchase = new GroupPurchase();
        Map<String, Object> retVal = new HashMap<String, Object>();

        String param1 = request.getParameter("page");
        String param2 = request.getParameter("pageSize");

        int page = 0;
        int pageSize = 0;

        int previousPage = 0;
        int nextPage = 0;

        if (StringUtils.isBlank(param1)) {
            page = 0;
        } else {
            page = Integer.parseInt(param1);
        }
        if (StringUtils.isBlank(param2)) {
            pageSize = Integer.parseInt(Ctx.getVar("FRONT.GRP.PURCHASE.PASESIZE"));
        } else {
            pageSize = Integer.parseInt(param2);
        }

        // For Pagination
        groupPurchase.setPagenationPage(page);
        groupPurchase.setPagenationPageSize(pageSize);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        groupPurchase.setSearchDateFrom(df.format(cal.getTime()));

        Integer totalCount = 0;
        List<GroupPurchase> list = null;
        List<GroupPurchase> allList = null;
        list = groupPurchaseService.getGroupPurchaseForMallFront(groupPurchase);
        totalCount = groupPurchaseService.getTotalCntGroupPurchaseForMallFront(groupPurchase);

        groupPurchase.setPagenationPage(0);
        groupPurchase.setPagenationPageSize(99999);
        allList = groupPurchaseService.getGroupPurchaseForMallFront(groupPurchase);


        retVal.put("pageSize", pageSize);

        previousPage = page - pageSize;
        if (previousPage <= 0) previousPage = 0; // 0: No need to go back

        nextPage = page + pageSize;
        if (nextPage >= totalCount) nextPage = 99999; //99999 : No need to go next


        retVal.put("previousPage", previousPage);
        retVal.put("nextPage", nextPage);
        retVal.put("currPage", page);


        retVal.put("list", list);
        retVal.put("allList", allList);
        retVal.put("totalCount", totalCount);

        return retVal;
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
                        message = "현재 사용자께서는 등록절차가 아직 마무리 되지 않았습니다. : STATUS[" + user.getApplyStatus() + "]. 자세한 문의 사항은 "
                                + "<br/><br/><ul>"
                                + "<li><b>" + Ctx.xmlConfig.getString("contact-info/default-customer-service/name") + "</b> [" + Ctx.xmlConfig.getString("contact-info/default-customer-service/email") + "]</li>"
                                + "<li>" + Ctx.xmlConfig.getString("contact-info/default-customer-service/phone") + "</li>"
                                + "</ul>"
                                + " 에게 문의해주세요.";
                    }

                } else {
                    // 사용자 ID에 해당하는 사용자는 존재하지만 비밀번호가 잘 못된경우.
                    boolean isLocked = userService.loginFailureIncrease(user);
                    if (isLocked) {
                        message = "로그인 실패 : 현재 고객님의 계정은 잠김 상태입니다. 자세한 사항은 "
                                + Ctx.xmlConfig.getString("contact-info/customer-service/name")
                                + " 에게 문의해주세요";
                    } else {
                        message = "로그인 실패 : " + (user.getPasswordFailureCnt() + 1) + "/" + Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt") + ". 로그인이 실패 횟수가 " + Ctx.xmlConfig.getInt("system-config/login-fail-allow-cnt") + "이상인 경우 계정이 잠길 수 있습니다.";
                    }

                    status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
                }

            } else if (StringUtils.equalsIgnoreCase(user.getUseYn(), "N")) {
                // 사용자 정보도 존재하지만 사용상태가 N 인경우
                // Password change period check.
                status = MelfoodConstants.LOGIN_AUTH_STATUS_FAILURE;
                message = "로그인 실패 : "
                        + "현재 고객님의 계정은 잠겨있는 상태입니다. 자세한 문의는"
                        + "<br/><br/><ul>"
                        + "<li><b>" + Ctx.xmlConfig.getString("contact-info/default-customer-service/name") + "</b> [" + Ctx.xmlConfig.getString("contact-info/default-customer-service/email") + "]</li>"
                        + "<li>" + Ctx.xmlConfig.getString("contact-info/default-customer-service/phone") + "</li>"
                        + "</ul>"
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
