/**
 * 2015 UserController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.customer;

import melfood.framework.Ctx;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.email.EmailServices;
import melfood.framework.notice.NoticeDiscuss;
import melfood.framework.notice.NoticeDiscussService;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 마이페이지 <br>
 */
@Controller
@RequestMapping("/customer/mypage")
public class MyPageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);

    @Autowired
    private NoticeDiscussService noticeDiscussService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/main");
        return mav;
    }

    @RequestMapping("/passwordChangeForm")
    public ModelAndView passwordChangeForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/passwordChangeForm");
        return mav;
    }

    @RequestMapping(value = "/passwordChange", produces = "application/json")
    @ResponseBody
    public Map<String, Object> passwordChange(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        String userId = sessionUser.getUser().getUserId();
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String passwordCurrent = request.getParameter("passwordCurrent");

        try {

            User checkUser = userService.getUserInfo(userId);

            if (!StringUtils.equals(passwordCurrent, checkUser.getPassword())) throw new Exception("입력하신 현재비밀번호가 일치하지않습니다.");
            if (!StringUtils.equals(password1, password2)) throw new Exception("입력하신 비밀번호와 재입력하신 비밀번호가 일치하지않습니다.");

            User user = new User(userId);
            user.setPassword(password1);

            String updateUserId = userService.passwordChange(userId, password1);

            // 사용자 정보가 변경되었다는 이메일 발송
            String userEmail = checkUser.getEmail();
            if (!StringUtils.isBlank(userEmail) && StringUtils.equalsIgnoreCase(Ctx.getVar("EMAIL.AFTR.CHANGE.PWD"), "Y")) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());

                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("dearWhom=" + checkUser.getUserName() + "^");
                emailContents.append("changedDatetime=" + df.format(cal.getTime()) + "^");
                emailContents.append("whatChanged=개인정보^");

                if (!StringUtils.isBlank(checkUser.getEmail())) {
                    EmailServices emailSvc = new EmailServices();
                    emailSvc.sendEmailUsingHtmlTemplate(checkUser.getEmail(), "[멜푸드] 고객님의 비밀번호가 변경되었습니다.", emailContents.toString(), "3");
                }
            }

            model.put("resultCode", "0");
            model.put("message", "정상적으로 사용자 비밀번호를 변경하였습니다.");

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/modifyMyDetailInfo")
    public ModelAndView modifyCodeForm(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/modifyMyDetailInfo");

        String userId = sessionUser.getUser().getUserId();

        User user = userService.getUserInfo(userId);

        Properties htmlProperty = new Properties();

        List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX", user.getSex());
        htmlProperty = new Properties("sex");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressState());
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        List<Option> useSocialMessengerOptions = codeService.getValueCmbxOptions("COMM", "SOCIAL_MESSENGER", user.getUseSocialMessenger());
        htmlProperty = new Properties("useSocialMessenger");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxUseSocialMessenger", codeService.generateCmbx(useSocialMessengerOptions, htmlProperty));

        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping("/myDetailInfo")
    public ModelAndView myDetailInfo(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/myDetailInfo");

        User user = new User();

        String userId = sessionUser.getUser().getUserId();
        user = userService.getUserInfo(userId);

        String birthDt = user.getDob();
        if (!StringUtils.isEmpty(birthDt) && StringUtils.length(birthDt) == 8) {
            birthDt = StringUtils.substring(birthDt, 0, 2) + "/" + StringUtils.substring(birthDt, 2, 4) + "/" + StringUtils.substring(birthDt, 4);
        }
        user.setDob(birthDt);

        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(value = "/saveUser", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveUser(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();
        String updateUserId = "";

        String userId = sessionUser.getUser().getUserId();
        String userName = request.getParameter("userName");
        String userNameReal = request.getParameter("userNameReal");
        String sex = request.getParameter("sex");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        // String mobile = request.getParameter("mobile");
        String telephone = request.getParameter("telephone");
        String useSocialMessenger = request.getParameter("useSocialMessenger");
        String useSocialMessengerId = request.getParameter("useSocialMessengerId");
        String addressState = request.getParameter("addressState");
        String addressPostcode = request.getParameter("addressPostcode");
        String addressSuburb = request.getParameter("addressSuburb");
        String addressStreet = request.getParameter("addressStreet");

        try {

            if (StringUtils.isBlank(userId)) throw new Exception("[사용자 ID]  이항목(들)은 빈 값이 될 수 없습니다.");
            User user = new User(userId);

            if(StringUtils.isBlank(userName)){
                user.setUserName(userId.substring(userId.length() - 3));
                // user.setUserName(joinMemberService.getDefaultUserName());
            } else {
                user.setUserName(userName);
            }

            if(StringUtils.isBlank(userNameReal)){
                user.setUserNameReal(userId.substring(userId.length() - 3));
                // user.setUserNameReal(joinMemberService.getDefaultUserName());
            } else {
                user.setUserNameReal(userNameReal);
            }

            user.setMobileAuthFinished("Y");

            if (!StringUtils.isBlank(sex)) user.setSex(sex);
            if (!StringUtils.isBlank(dob)) user.setDob(dob);
            // if (!StringUtils.isBlank(mobile)) user.setMobile(mobile);
            if (!StringUtils.isBlank(telephone)) user.setTelephone(telephone);
            if (!StringUtils.isBlank(email)) user.setEmail(email);
            if (!StringUtils.isBlank(useSocialMessenger)) user.setUseSocialMessenger(useSocialMessenger);
            if (!StringUtils.isBlank(useSocialMessengerId)) user.setUseSocialMessengerId(useSocialMessengerId);
            if (!StringUtils.isBlank(addressPostcode)) user.setAddressPostcode(addressPostcode);
            if (!StringUtils.isBlank(addressState)) user.setAddressState(addressState);
            if (!StringUtils.isBlank(addressSuburb)) user.setAddressSuburb(addressSuburb);
            if (!StringUtils.isBlank(addressStreet)) user.setAddressStreet(addressStreet);

            updateUserId = userService.modifyUserForNotNull(user);

            if (!StringUtils.isBlank(user.getEmail()) && StringUtils.equalsIgnoreCase(Ctx.getVar("EMAIL.AFTR.CHANGE.MYINFO"), "Y")) {

                // 사용자 정보가 변경되었다는 이메일 발송
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());

                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("dearWhom=" + user.getUserName() + "^");
                emailContents.append("changedDatetime=" + df.format(cal.getTime()) + "^");
                emailContents.append("whatChanged=개인정보^");

                if (!StringUtils.isBlank(user.getEmail())) {
                    EmailServices emailSvc = new EmailServices();
                    emailSvc.sendEmailUsingHtmlTemplate(user.getEmail(), "[멜푸드] 고객님의 개인정보가 변경되었습니다.", emailContents.toString(), "3");
                }
            }

            // 현재 세션의 이름 설정
            sessionUser.getUser().setUserName(user.getUserName());

            model.put("resultCode", "0");
            model.put("message", updateUserId + " 의 정보가 저장/변경 되었습니다.");
            model.put("userId", updateUserId);

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
            model.put("userId", updateUserId);
        }

        return model;
    }

    @RequestMapping("/myOrderList")
    public ModelAndView myOrderList(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/myOrderList");
        String userId = sessionUser.getUser().getUserId();
        return mav;
    }

    @RequestMapping("/myQnAs")
    public ModelAndView myQnAs(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/myQnAs");
        String userId = sessionUser.getUser().getUserId();

        NoticeDiscuss noticeDiscuss = new NoticeDiscuss();
        noticeDiscuss.setWriteFrom(userId);
        noticeDiscuss.setWriteTo(userId);

        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        if (StringUtils.isBlank(searchDateFrom)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -1);
            searchDateFrom = df.format(cal.getTime());
        }

        if (StringUtils.isNotBlank(searchDateFrom)) {
            noticeDiscuss.setSearchDateFrom(searchDateFrom);
            mav.addObject("searchDateFrom", searchDateFrom);
        } else {
            mav.addObject("setSearchDateFrom", "");
        }
        if (StringUtils.isNotBlank(searchDateTo)) {
            noticeDiscuss.setSearchDateTo(searchDateTo);
            mav.addObject("searchDateTo", searchDateTo);
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            searchDateTo = df.format(cal.getTime());
            mav.addObject("searchDateTo", searchDateTo);
        }

        // For Pagination
        noticeDiscuss.setPagenationPage(0);
        noticeDiscuss.setPagenationPageSize(99999);

        List<NoticeDiscuss> list = noticeDiscussService.getAllMyDiscussListForCustomer(noticeDiscuss);

        mav.addObject("noticeDiscussList", list);
        mav.addObject("sessionUserId", userId);

        return mav;
    }


}
