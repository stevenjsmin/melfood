package melfood.controller.common;

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자와 관련한 유틸리티 콘트롤 메소드모음
 * <p>
 * Created by Steven on 8/6/17.
 */
@Controller
@RequestMapping("/common/userutil")
public class UserUtilController extends BaseController {

    @RequestMapping("/findUserForm")
    public ModelAndView addCalendarForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/common/userutil/findUserUtilForm");
        Properties htmlProperty = new Properties();

        List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", "Y");
        // List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE");
        htmlProperty = new Properties("useYn");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

        List<Option> applyStatusOptions = codeService.getValueCmbxOptions("USER_MGT", "APPLY_STATUS");
        htmlProperty = new Properties("applyStatus");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxApplyStatus", codeService.generateCmbx(applyStatusOptions, htmlProperty));

        List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
        htmlProperty = new Properties("userType");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxUserType", codeService.generateCmbx(userTypeOptions, htmlProperty));

        List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX");
        htmlProperty = new Properties("sex");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE");
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        String returnObjId = request.getParameter("returnObjId");
        String returnObjLabel = request.getParameter("returnObjLabel");
        String includeIdOnName = request.getParameter("includeIdOnName");
        if (StringUtils.isBlank(returnObjId)) {
            throw new Exception("Please specify object id to be returned");
        }
        if (StringUtils.isBlank(includeIdOnName)) {
            includeIdOnName = "false";
        }
        mav.addObject("returnObjId", returnObjId);
        mav.addObject("returnObjLabel", returnObjLabel);
        mav.addObject("includeIdOnName", includeIdOnName);

        return mav;
    }

    @RequestMapping(value = "/listUsers", produces = "application/json")
    @ResponseBody
    public Map<String, Object> listUsers(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        List<User> list = null;

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String sex = request.getParameter("sex");
        String applyStatus = request.getParameter("applyStatus");
        String addressStreet = request.getParameter("addressStreet");
        String addressSuburb = request.getParameter("addressSuburb");
        String addressState = request.getParameter("addressState");
        String addressPostcode = request.getParameter("addressPostcode");
        String acn = request.getParameter("acn");
        String abn = request.getParameter("abn");
        String userType = request.getParameter("userType");
        String useYn = request.getParameter("useYn");

        User user = new User();

        // For Pagination
        user.setPagenationPage(getPage(request));
        user.setPagenationPageSize(getPageSize(request));

        if (StringUtils.isNotBlank(userName)) user.setUserName(userName);
        if (StringUtils.isNotBlank(email)) user.setEmail(email);
        if (StringUtils.isNotBlank(dob)) user.setDob(dob);
        if (StringUtils.isNotBlank(sex)) user.setSex(sex);
        if (StringUtils.isNotBlank(applyStatus)) user.setApplyStatus(applyStatus);
        if (StringUtils.isNotBlank(addressStreet)) user.setAddressStreet(addressStreet);
        if (StringUtils.isNotBlank(addressSuburb)) user.setAddressSuburb(addressSuburb);
        if (StringUtils.isNotBlank(addressState)) user.setAddressState(addressState);
        if (StringUtils.isNotBlank(addressPostcode)) user.setAddressPostcode(addressPostcode);
        if (StringUtils.isNotBlank(acn)) user.setAcn(acn);
        if (StringUtils.isNotBlank(abn)) user.setAbn(abn);
        if (StringUtils.isNotBlank(userType)) user.setRoleId(userType);
        if (StringUtils.isNotBlank(useYn)) user.setUseYn(useYn);

        try {
            Integer totalCount = 0;

            list = userService.getUsers(user);
            totalCount = userService.getTotalCntForUsers(user);

            model.put("totalCount", totalCount);
            model.put("list", list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
