/**
 * 2016 JoinMemberController.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import melfood.framework.Ctx;
import melfood.framework.email.EmailServices;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.guest.JoinMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원가입에 필요한 콘트롤러들을 정의한다.
 *
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/guest/joinmember")
public class JoinMemberController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(JoinMemberController.class);

    @Autowired
    private JoinMemberService joinMemberService;

    @RequestMapping("/join")
    public ModelAndView join(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/guest/joinmember/join");

        Properties htmlProperty = new Properties();
        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        htmlProperty.setCssStyle("background-color: #FFFFFF;");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        String userDefaultName = joinMemberService.getDefaultUserName();
        mav.addObject("userDefaultName", userDefaultName);

        return mav;
    }

    @RequestMapping(value = "/existUser", produces = "application/json")
    @ResponseBody
    public Map<String, Object> existUser(HttpServletRequest request) throws Exception {

        HashMap<String, String> param = new HashMap<String, String>();
        Map<String, Object> model = new HashMap<String, Object>();

        boolean existUer = false;

        String userId = request.getParameter("userId");
        param.put("userId", userId);
        try {
            existUer = userService.existUser(userId);

            model.put("existUer", existUer);
            model.put("resultCode", "0");
            model.put("message", "Checked successfully");

        } catch (Exception e) {
            e.printStackTrace();

            model.put("existUer", null);
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/joinMember", produces = "application/json")
    @ResponseBody
    public Map<String, Object> joinMember(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        boolean existUer = false;

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String userName = request.getParameter("userName");
        String userNameReal = request.getParameter("userNameReal");
        String email = request.getParameter("email");
        String addressState = request.getParameter("addressState");
        String addressPostcode = request.getParameter("addressPostcode");
        String addressSuburb = request.getParameter("addressSuburb");
        String addressStreet = request.getParameter("addressStreet");


        try {
            if (!StringUtils.equals(password, password2)) throw new Exception("입력하신 비밀번호와 재입력하신 비밀번호가 일치하지않습니다.");

            existUer = userService.existUser(userId);
            if (existUer) throw new Exception("등록하려는 아이디는 이미 등록된 사용자의  ID 입니다.");

            User user = new User(userId, password);

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

            if (!StringUtils.isBlank(email)) user.setEmail(email);
            if (!StringUtils.isBlank(addressState)) user.setAddressState(addressState);
            if (!StringUtils.isBlank(addressPostcode)) user.setAddressPostcode(addressPostcode);
            if (!StringUtils.isBlank(addressSuburb)) user.setAddressSuburb(addressSuburb);
            if (!StringUtils.isBlank(addressStreet)) user.setAddressStreet(addressStreet);
            user.setSellerIsMandatoryChooseDeliveryPickupDate("Y");
            String userAddress = addressStreet + " " + addressSuburb + " " + addressPostcode + " " + addressState;

            // TODO : 모바일 인증
            user.setMobileAuthFinished("Y");

            user.setUseYn("Y");
            user.setApplyStatus("COMPLETE");
            user.setMobile(userId); // 아이디가 모바일번호이므로 그냥 설정해준다.
            userService.addUser(user);

            if (!StringUtils.isBlank(email) && StringUtils.equalsIgnoreCase(Ctx.getVar("EMAIL.AFTR.REGIST.MEMBER"), "Y")) {
                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("dearWhom=" + userName + "^");
                emailContents.append("userId=" + userId + "^");
                emailContents.append("userName=" + userName + "^");
                emailContents.append("email=" + email + "^");
                emailContents.append("userAddress=" + userAddress + "^");

                EmailServices emailSvc = new EmailServices();
                emailSvc.sendEmailUsingHtmlTemplate(user.getEmail(), "[멜푸드] 멜푸드몰에 회원이 되셨습니다. 감사합니다.", emailContents.toString(), "1");
            }

            model.put("user", userService.getUserInfo(userId));
            model.put("resultCode", "0");
            model.put("message", "정상적으로 사용자 등록이 완료되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();

            model.put("user", null);
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/openMemberJoinAgreement")
    public ModelAndView openMemberJoinAgreement(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/guest/joinmember/openMemberJoinAgreement");
        return mav;
    }

    @RequestMapping("/findPassword")
    public ModelAndView findPassword(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/guest/joinmember/findPassword");
        return mav;
    }

    @RequestMapping(value = "/sendEmailForForgotUserPassword", produces = "application/json")
    @ResponseBody
    public Map<String, Object> sendEmailForForgotUserPassword(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        try {

            String userId = request.getParameter("userId");
            User user = userService.getUserInfo(userId);

            if (user != null) {

                if (!StringUtils.isBlank(user.getEmail())) {
                    StringBuffer emailContents = new StringBuffer("");
                    emailContents.append("dearWhom=" + user.getUserName() + "^");
                    emailContents.append("userId=" + user.getUserId() + "^");
                    emailContents.append("userName=" + user.getUserName() + "^");
                    emailContents.append("password=" + user.getPassword() + "^");

                    EmailServices email = new EmailServices();
                    email.sendEmailUsingHtmlTemplate(user.getEmail(), "[멜푸드] 요청하신 비밀번호입니다.", emailContents.toString(), "2");

                    // userService.initialUserAccount(user.getUserId());
                    model.put("message", "요청하신 사용자 비빌번호를 등록하신 이메일(" + user.getEmail() + ")로 발송하였습니다.");
                } else {
                    // Email 이 존재하지 않는 경우.
                    String name = Ctx.xmlConfig.getString("contact-info/default-customer-service/name");
                    String email = Ctx.xmlConfig.getString("contact-info/default-customer-service/email");
                    String phone = Ctx.xmlConfig.getString("contact-info/default-customer-service/phone");
                    String sysAdmin = "<br/><br/>담당자 : " + name + "<br/>Email : " + email + "<br/>Tel : " + phone;

                    model.put("message", "요청하신 비빌번호를 Email로 발송하려고 하였으나 등록된 Email이 존재하지 않아 발송하지 못하였습니다.<br/> 시스템 관리자에게 문의 하여주시기 바랍니다.<br/>" + sysAdmin);
                }
            } else {
                model.put("message", "요청하신 사용자에대한 정보가 존재 하지 않습니다. 입력하신 사용자 ID(모바일번호)를 다시한번 확인해 주세요.");
            }
            model.put("resultCode", "0");

        } catch (Exception e) {
            e.printStackTrace();
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }
}
