/** 
 * 2015 UserController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.user;

import melfood.framework.Ctx;
import melfood.framework.MelfoodConstants;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.email.EmailServices;
import melfood.framework.role.Role;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 관리 Controll <br>
 * <br>
 * 사용자의 Type은 다음과 같은 형태가 존재한다.<br>
 * <ul>
 * <li>ADMIN</li>
 * <li>SELLER</li>
 * <li>CUSTOMER</li>
 * </ul>
 * 
 */
@Controller
@RequestMapping("/framework/usermanager")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/usermanager/main");
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

		List<Option> mobileAuthFinishedOptions = codeService.getValueCmbxOptions("USER_MGT", "MOBILE_AUTH");
		htmlProperty = new Properties("mobileAuthFinished");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxMobileAuthFinished", codeService.generateCmbx(mobileAuthFinishedOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/listUsers", produces = "application/json")
	@ResponseBody
	public Map<String, Object> listUsers(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		List<User> list = null;

		String userName = request.getParameter("userName");
		String userNameReal = request.getParameter("userNameReal");
		String email = request.getParameter("email");
		String mobileAuthFinished = request.getParameter("mobileAuthFinished");
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
		if (StringUtils.isNotBlank(userNameReal)) user.setUserNameReal(userNameReal);
		if (StringUtils.isNotBlank(email)) user.setEmail(email);
		if (StringUtils.isNotBlank(mobileAuthFinished)) user.setMobileAuthFinished(mobileAuthFinished);
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

	@RequestMapping("/registUserForm")
	public ModelAndView registCodeForm() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/usermanager/regist");

		Properties htmlProperty = new Properties();

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", "Y");
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> applyStatusOptions = codeService.getValueCmbxOptions("USER_MGT", "APPLY_STATUS", "COMPLETE");
		htmlProperty = new Properties("applyStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxApplyStatus", codeService.generateCmbx(applyStatusOptions, htmlProperty));

		List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
		htmlProperty = new Properties("userRoles");
		// htmlProperty.setCssClass("form-control");
		htmlProperty.setMultipleSelect(true);
		mav.addObject("cbxUserType", codeService.generateCmbx(userTypeOptions, htmlProperty, false));

		List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX");
		htmlProperty = new Properties("sex");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		// List<Option> addressBusinessStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
		List<Option> addressBusinessStateOptions = addressStateOptions;
		htmlProperty = new Properties("addressBusinessState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressBusinessState", codeService.generateCmbx(addressBusinessStateOptions, htmlProperty));

		List<Option> addressSellerDeliveryStateOptions = addressStateOptions;
		htmlProperty = new Properties("sellerDeliveryAddressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerDeliveryAddressState", codeService.generateCmbx(addressSellerDeliveryStateOptions, htmlProperty));

		List<Option> addressSellerPickupStateOptions = addressStateOptions;
		htmlProperty = new Properties("sellerPickupAddressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerPickupAddressState", codeService.generateCmbx(addressSellerPickupStateOptions, htmlProperty));

		List<Option> sellerHaveMinimumPaymentOptions = codeService.getValueCmbxOptions("USER_MGT", "HAVE_MINIMUM_PAYMENT");
		htmlProperty = new Properties("sellerHaveMinimumPayment");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerHaveMinimumPayment", codeService.generateCmbx(sellerHaveMinimumPaymentOptions, htmlProperty));

		List<Option> useSocialMessengerOptions = codeService.getValueCmbxOptions("COMM", "SOCIAL_MESSENGER");
		htmlProperty = new Properties("useSocialMessenger");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseSocialMessenger", codeService.generateCmbx(useSocialMessengerOptions, htmlProperty));

		List<Option> sellerIsMandatoryChooseDeliveryPickupDateOptions = codeService.getValueCmbxOptions("USER_MGT", "MANDATORY_DELIVERY_PICKUP_DATE");
		htmlProperty = new Properties("sellerIsMandatoryChooseDeliveryPickupDate");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerIsMandatoryChooseDeliveryPickupDate", codeService.generateCmbx(sellerIsMandatoryChooseDeliveryPickupDateOptions, htmlProperty));

		List<Option> mobileAuthFinishedOptions = codeService.getValueCmbxOptions("USER_MGT", "MOBILE_AUTH", "N");
		htmlProperty = new Properties("mobileAuthFinished");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxMobileAuthFinished", codeService.generateCmbx(mobileAuthFinishedOptions, htmlProperty));

		return mav;
	}

	@RequestMapping("/modifyUserForm")
	public ModelAndView modifyCodeForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/usermanager/modify");

		String userId = request.getParameter("userId");

		User user = userService.getUserInfo(userId);

		Properties htmlProperty = new Properties();

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", user.getUseYn());
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> applyStatusOptions = codeService.getValueCmbxOptions("USER_MGT", "APPLY_STATUS", user.getApplyStatus());
		htmlProperty = new Properties("applyStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxApplyStatus", codeService.generateCmbx(applyStatusOptions, htmlProperty));

		List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
		htmlProperty = new Properties("userRoles");
		// htmlProperty.setCssClass("form-control");
		htmlProperty.setMultipleSelect(true);
		mav.addObject("cbxUserRoles", codeService.generateCmbx(userTypeOptions, htmlProperty, false));

		String allRoles = userService.getAllRoles(userId);
		List<String> arrRoles = new ArrayList<String>();
		if (StringUtils.isNotBlank(allRoles)) {
			String temp[] = StringUtils.split(allRoles, ",");
			for (String var : temp) {
				arrRoles.add("'" + StringUtils.trim(var) + "'");
			}
		}
		mav.addObject("allRoles", arrRoles);

		List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX", user.getSex());
		htmlProperty = new Properties("sex");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressState());
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		List<Option> addressBusinessStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressBusinessState());
		htmlProperty = new Properties("addressBusinessState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressBusinessState", codeService.generateCmbx(addressBusinessStateOptions, htmlProperty));

		List<Option> addressSellerDeliveryStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getSellerDeliveryAddressState());
		htmlProperty = new Properties("sellerDeliveryAddressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerDeliveryAddressState", codeService.generateCmbx(addressSellerDeliveryStateOptions, htmlProperty));

		List<Option> addressSellerPickupStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getSellerPickupAddressState());
		htmlProperty = new Properties("sellerPickupAddressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerPickupAddressState", codeService.generateCmbx(addressSellerPickupStateOptions, htmlProperty));

		List<Option> sellerHaveMinimumPaymentOptions = codeService.getValueCmbxOptions("USER_MGT", "HAVE_MINIMUM_PAYMENT", user.getSellerHaveMinimumPayment());
		htmlProperty = new Properties("sellerHaveMinimumPayment");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerHaveMinimumPayment", codeService.generateCmbx(sellerHaveMinimumPaymentOptions, htmlProperty));

		List<Option> useSocialMessengerOptions = codeService.getValueCmbxOptions("COMM", "SOCIAL_MESSENGER", user.getUseSocialMessenger());
		htmlProperty = new Properties("useSocialMessenger");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseSocialMessenger", codeService.generateCmbx(useSocialMessengerOptions, htmlProperty));

		List<Option> sellerIsMandatoryChooseDeliveryPickupDateOptions = codeService.getValueCmbxOptions("USER_MGT", "MANDATORY_DELIVERY_PICKUP_DATE", user.getSellerIsMandatoryChooseDeliveryPickupDate());
		htmlProperty = new Properties("sellerIsMandatoryChooseDeliveryPickupDate");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSellerIsMandatoryChooseDeliveryPickupDate", codeService.generateCmbx(sellerIsMandatoryChooseDeliveryPickupDateOptions, htmlProperty));

		List<Option> mobileAuthFinishedOptions = codeService.getValueCmbxOptions("USER_MGT", "MOBILE_AUTH", user.getMobileAuthFinished());
		htmlProperty = new Properties("mobileAuthFinished");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxMobileAuthFinished", codeService.generateCmbx(mobileAuthFinishedOptions, htmlProperty));

		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping("/detailUserForm")
	public ModelAndView detailUserForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/usermanager/detailInfo");

		String userId = request.getParameter("userId");

		User user = userService.getUserInfo(userId);

		Properties htmlProperty = new Properties();

		List<Option> useYnOptions = codeService.getValueCmbxOptions("COMM", "SYSTEM_USE", user.getUseYn());
		htmlProperty = new Properties("useYn");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseYn", codeService.generateCmbx(useYnOptions, htmlProperty));

		List<Option> applyStatusOptions = codeService.getValueCmbxOptions("USER_MGT", "APPLY_STATUS", user.getApplyStatus());
		htmlProperty = new Properties("applyStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxApplyStatus", codeService.generateCmbx(applyStatusOptions, htmlProperty));

		List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
		htmlProperty = new Properties("userType");
		// htmlProperty.setCssClass("form-control");
		htmlProperty.setMultipleSelect(true);
		mav.addObject("cbxUserType", codeService.generateCmbx(userTypeOptions, htmlProperty, false));

		String allRoles = userService.getAllRoles(userId);
		List<String> arrRoles = new ArrayList<String>();
		if (StringUtils.isNotBlank(allRoles)) {
			String temp[] = StringUtils.split(allRoles, ",");
			for (String var : temp) {
				arrRoles.add("'" + StringUtils.trim(var) + "'");
			}
		}
		mav.addObject("allRoles", arrRoles);

		List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX", user.getSex());
		htmlProperty = new Properties("sex");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressState());
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		List<Option> addressBusinessStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressBusinessState());
		htmlProperty = new Properties("addressBusinessState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressBusinessState", codeService.generateCmbx(addressBusinessStateOptions, htmlProperty));

		List<Option> useSocialMessengerOptions = codeService.getValueCmbxOptions("COMM", "SOCIAL_MESSENGER", user.getUseSocialMessenger());
		htmlProperty = new Properties("useSocialMessenger");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUseSocialMessenger", codeService.generateCmbx(useSocialMessengerOptions, htmlProperty));

		List<Option> mobileAuthFinishedOptions = codeService.getValueCmbxOptions("USER_MGT", "MOBILE_AUTH", user.getMobileAuthFinished());
		htmlProperty = new Properties("mobileAuthFinished");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxMobileAuthFinished", codeService.generateCmbx(mobileAuthFinishedOptions, htmlProperty));

		mav.addObject("user", user);

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

		existUer = userService.existUser(userId);

		model.put("message", Boolean.toString(existUer));

		return model;
	}

	@RequestMapping("/getUser")
	public ModelAndView getUser(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("framework/user/userDetailInfo");

		User user = new User();

		String userId = request.getParameter("userId");
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
		Map<String, Object> model = new HashMap<String, Object>();
		String updateUserId = "";

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userNameReal = request.getParameter("userNameReal");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String dob = request.getParameter("dob");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String mobileAuthFinished = request.getParameter("mobileAuthFinished");
		String telephone = request.getParameter("telephone");
		String useSocialMessenger = request.getParameter("useSocialMessenger");
		String useSocialMessengerId = request.getParameter("useSocialMessengerId");
		String addressState = request.getParameter("addressState");
		String addressPostcode = request.getParameter("addressPostcode");
		String addressSuburb = request.getParameter("addressSuburb");
		String addressStreet = request.getParameter("addressStreet");
		String abn = request.getParameter("abn");
		String acn = request.getParameter("acn");
		String useYn = request.getParameter("useYn");
		String passwordFailureCnt = request.getParameter("passwordFailureCnt");
		String applyStatus = request.getParameter("applyStatus");
		String userTypes = request.getParameter("userTypes");

		// For seller
		String sellerDeliveryAddressStreet = request.getParameter("sellerDeliveryAddressStreet");
		String sellerDeliveryAddressSuburb = request.getParameter("sellerDeliveryAddressSuburb");
		String sellerDeliveryAddressState = request.getParameter("sellerDeliveryAddressState");
		String sellerDeliveryAddressPostcode = request.getParameter("sellerDeliveryAddressPostcode");
		String sellerPickupAddressStreet = request.getParameter("sellerPickupAddressStreet");
		String sellerPickupAddressSuburb = request.getParameter("sellerPickupAddressSuburb");
		String sellerPickupAddressState = request.getParameter("sellerPickupAddressState");
		String sellerPickupAddressPostcode = request.getParameter("sellerPickupAddressPostcode");
		String sellerIntroduction = request.getParameter("sellerIntroduction");
		String sellerBusinessName = request.getParameter("sellerBusinessName");
		String sellerHaveMinimumPayment = request.getParameter("sellerHaveMinimumPayment");
		String sellerMinimumPaymentForPickup = request.getParameter("sellerMinimumPaymentForPickup");
		String sellerMinimumPaymentForDeliver = request.getParameter("sellerMinimumPaymentForDeliver");
		String sellerIsMandatoryChooseDeliveryPickupDate = request.getParameter("sellerIsMandatoryChooseDeliveryPickupDate");
		// String sellerProfilePhotoId = request.getParameter("sellerProfilePhotoId");
		if (StringUtils.equalsIgnoreCase(sellerHaveMinimumPayment, "N") || StringUtils.isBlank(sellerHaveMinimumPayment)) {
			sellerHaveMinimumPayment = "N";
			sellerMinimumPaymentForPickup = "0";
			sellerMinimumPaymentForDeliver = "0";
		}

		try {

			if (StringUtils.isBlank(userId)) throw new Exception("[사용자 ID]  이항목(들)은 빈 값이 될 수 없습니다.");
			User user = null;

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				if (StringUtils.isBlank(password)) password = userService.getRamdom4DigitPassword();
				user = new User(userId, password);
			} else {
				// user = userService.getUserInfo(userId);
				user = new User(userId);
			}

			if (!StringUtils.isBlank(userName)) user.setUserName(userName);
			if (!StringUtils.isBlank(userNameReal)) {
				user.setUserNameReal(userNameReal);
			} else {
				user.setUserNameReal(userName);
			}
			if (!StringUtils.isBlank(sex)) user.setSex(sex);
			if (!StringUtils.isBlank(dob)) user.setDob(dob);
			if (!StringUtils.isBlank(mobile)) user.setMobile(mobile);
			if (!StringUtils.isBlank(mobileAuthFinished)) user.setMobileAuthFinished(mobileAuthFinished);
			if (!StringUtils.isBlank(telephone)) user.setTelephone(telephone);
			if (!StringUtils.isBlank(email)) user.setEmail(email);
			if (!StringUtils.isBlank(useSocialMessenger)) user.setUseSocialMessenger(useSocialMessenger);
			if (!StringUtils.isBlank(useSocialMessengerId)) user.setUseSocialMessengerId(useSocialMessengerId);
			if (!StringUtils.isBlank(addressPostcode)) user.setAddressPostcode(addressPostcode);
			if (!StringUtils.isBlank(addressState)) user.setAddressState(addressState);
			if (!StringUtils.isBlank(addressSuburb)) user.setAddressSuburb(addressSuburb);
			if (!StringUtils.isBlank(addressStreet)) user.setAddressStreet(addressStreet);
			if (!StringUtils.isBlank(abn)) user.setAbn(abn);
			if (!StringUtils.isBlank(acn)) user.setAcn(acn);
			if (!StringUtils.isBlank(applyStatus)) user.setApplyStatus(applyStatus);
			if (!StringUtils.isBlank(useYn)) user.setUseYn(useYn);
			if (!StringUtils.isBlank(passwordFailureCnt)) user.setPasswordFailureCnt(Integer.parseInt(passwordFailureCnt));

			if (!StringUtils.isBlank(sellerDeliveryAddressStreet)) user.setSellerDeliveryAddressStreet(sellerDeliveryAddressStreet);
			if (!StringUtils.isBlank(sellerDeliveryAddressSuburb)) user.setSellerDeliveryAddressSuburb(sellerDeliveryAddressSuburb);
			if (!StringUtils.isBlank(sellerDeliveryAddressState)) user.setSellerDeliveryAddressState(sellerDeliveryAddressState);
			if (!StringUtils.isBlank(sellerDeliveryAddressPostcode)) user.setSellerDeliveryAddressPostcode(sellerDeliveryAddressPostcode);
			if (!StringUtils.isBlank(sellerPickupAddressStreet)) user.setSellerPickupAddressStreet(sellerPickupAddressStreet);
			if (!StringUtils.isBlank(sellerPickupAddressSuburb)) user.setSellerPickupAddressSuburb(sellerPickupAddressSuburb);
			if (!StringUtils.isBlank(sellerPickupAddressState)) user.setSellerPickupAddressState(sellerPickupAddressState);
			if (!StringUtils.isBlank(sellerPickupAddressPostcode)) user.setSellerPickupAddressPostcode(sellerPickupAddressPostcode);
			if (!StringUtils.isBlank(sellerIntroduction)) user.setSellerIntroduction(sellerIntroduction);

			if (!StringUtils.isBlank(sellerBusinessName)) user.setSellerBusinessName(sellerBusinessName);
			if (!StringUtils.isBlank(sellerHaveMinimumPayment)) user.setSellerHaveMinimumPayment(sellerHaveMinimumPayment);
			if (!StringUtils.isBlank(sellerMinimumPaymentForPickup)) user.setSellerMinimumPaymentForPickup(Float.parseFloat(sellerMinimumPaymentForPickup));
			if (!StringUtils.isBlank(sellerMinimumPaymentForDeliver)) user.setSellerMinimumPaymentForDeliver(Float.parseFloat(sellerMinimumPaymentForDeliver));
			// if (!StringUtils.isBlank(sellerProfilePhotoId)) user.setSellerProfilePhotoId(Integer.parseInt(sellerProfilePhotoId));
			if (!StringUtils.isBlank(sellerIsMandatoryChooseDeliveryPickupDate)) user.setSellerIsMandatoryChooseDeliveryPickupDate(sellerIsMandatoryChooseDeliveryPickupDate);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				if (userService.existUser(userId)) throw new Exception("이미 존재하고 있는 사용자 입니다.");
				updateUserId = userService.addUser(user);

			} else {
				if (!userService.existUser(userId)) throw new Exception("수정하려는 사용자의 ID가 존재 하지 않습니다.");
				String pwd = userService.getUserInfo(userId).getPassword();
				user.setPassword(pwd);
				updateUserId = userService.modifyUser(user);
			}

			try{
				// 사용자의 집 주소를 갱신한다.
				User newUser = userService.getUserInfo(updateUserId);
				userService.updateHomeAddressGmapCoordinate(newUser);

			} catch(Exception e) {
				e.printStackTrace();
			}

			try {
				roleService.removeMyRoles(userId);
				String[] userTypeArray = StringUtils.split(userTypes, ",");
				List<Role> roleList = new ArrayList<Role>();
				Role role = null;
				for (String type : userTypeArray) {
					role = new Role(type);
					role.setUserId(userId);
					roleList.add(role);
				}
				if (roleList.size() > 0) roleService.addMyRoles(roleList);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("사용자의 Role 권한을 설정하는 도중에 문제가 발생하였습니다. " + e.getMessage());
			}

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

	@RequestMapping(value = "/modifyUserYn", produces = "application/json")
	@ResponseBody
	public Map<String, Object> modifyUserYn(HttpServletRequest request) throws Exception {
		HashMap<String, String> param = new HashMap<String, String>();
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String useYn = request.getParameter("useYn");
		param.put("userId", userId);
		param.put("useYn", useYn);

		String updatedId = userService.changeUserStatus(userId, useYn);

		model.put("message", userId);
		model.put("updateCnt", updateCnt);

		return model;
	}

	@RequestMapping(value = "/enableAndDisable", produces = "application/json")
	@ResponseBody
	public Map<String, Object> enableAndDisable(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String userId = request.getParameter("userId");

		try {
			userService.enableAndDisable(userId);

			model.put("resultCode", "0");
			model.put("message", "사용자 Lock/Unlock 하였습니다.");

		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", "사용자 계정을 사용자 Lock/Unlock 하는 도중에 문제가 발생 하였습니다. :" + e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/changePassword", produces = "application/json")
	@ResponseBody
	public Map<String, Object> changePassword(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		try {
			if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
				throw new Exception("UserID or Password can not be null for password update");
			} else {
				// updateCnt = userService.changeUserStatus(userId, useYn);
			}

		} catch (Exception e) {
			model.put("message", "failure");
		}
		model.put("message", "success");

		return model;
	}

	@RequestMapping(value = "/sendEmailUserInfo", produces = "application/json")
	@ResponseBody
	public Map<String, Object> sendEmailUserInfo(HttpServletRequest request) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		Map<String, Object> model = new HashMap<String, Object>();

		String userId = request.getParameter("userId");
		User user = userService.getUserInfo(userId);

		if (user != null) {

			if (!StringUtils.isBlank(user.getEmail())) {
				String strRole = "";
				List<Role> roleList = roleService.getMyRoleList(userId);
				for (Role role : roleList) {
					strRole = strRole + role.getRoleName() + ",";
				}

				StringBuffer message = new StringBuffer("");
				message.append("dearWhom=" + user.getUserName() + "^");
				message.append("userId=" + user.getUserId() + "^");
				message.append("userPassword=" + user.getPassword() + "^");
				// message.append("role=" + user.getPrimaryRoleName() + "^");
				// message.append("latestLoginDt=" + (StringUtils.isBlank(user.getLatestLoginDt()) ? "" : user.getLatestLoginDt() + "^"));
				// message.append("passwordChangeDt=" + user.getPasswordChangeDt() + "^");
				message.append("loginUrl=" + Ctx.xmlConfig.getVar("login-url/url") + "^");
				message.append("personInCharge=" + Ctx.xmlConfig.getString("contact-info/level1/name") + "|" + Ctx.xmlConfig.getString("contact-info/level1/phone") + "^");

				EmailServices email = new EmailServices();
				email.sendEmailUsingHtmlTemplate(user.getEmail(), "User Account Info", message.toString(), "6");

			}

			// When send account information, setting user information login failure count to '0', use_yn = 'Y' as well
			userService.initialUserAccount(user.getUserId());

		} else {
			model.put("message", "failure");
		}

		model.put("message", "success");

		return model;
	}

	@RequestMapping(value = "/initialUserAccount", produces = "application/json")
	@ResponseBody
	public Map<String, Object> initialUserAccount(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String userId = null;
		String updatedUserId = null;
		try {

			userId = request.getParameter("userId");
			updatedUserId = userService.initialUserAccount(userId);
			// String updatedUserId = userService.initialUserAccount(userId, true);

			model.put("userId", updatedUserId);
			model.put("resultCode", "0");
			model.put("message", "사용자 계정을 초기화 하였습니다.");

		} catch (Exception e) {
			model.put("userId", updatedUserId);
			model.put("resultCode", "-1");
			model.put("message", "사용자 계정을 초기화 하는 도중에 문제가 발생 하였습니다. :" + e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/initialUserPassword", produces = "application/json")
	@ResponseBody
	public Map<String, Object> initialUserPassword(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			String userId = request.getParameter("userId");
			String password = userService.getRamdom4DigitPassword();
			userService.modifyUserForNotNull(new User(userId, password));

			model.put("resultCode", "0");
			model.put("message", "사용자 비밀번호를 초기화 하였습니다.");

		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", "사용자 비밀번호를 초기화 하는 도중에 문제가 발생 하였습니다. :" + e.getMessage());
		}

		return model;
	}

	@RequestMapping("/profileImageUploadForm")
	public ModelAndView profileImageUploadForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/usermanager/profileImageUpload");
		String userId = request.getParameter("userId");

		if (StringUtils.isBlank(userId)) {
			throw new Exception("[USER_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}
		User user = userService.getUserInfo(userId);
		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(value = "/profileImageUpload", produces = "application/json")
	@ResponseBody
	public Map<String, Object> profileImageUpload(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		int sellerProfilePhotoId = 0;

		String subDirectory = Ctx.getVar("USERPROFILEIMAGE.TEMP.UPLOAD.DIR");
		String userId = request.getParameter("userId");

		try {

			User user = userService.getUserInfo(userId);

			// 이미 등록된 파일이 있다면 삭제해준다.
			attachmentFileService.deleteAttachmentFile(new AttachmentFile(user.getProfilePhotoId()));
			userService.updateUserProfilePhoto(new User(userId));

			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 지워준다.
			attachmentFileService.uploadFile(request, subDirectory); // 파일을 임시 업로드 위치에 놓는다.

			sellerProfilePhotoId = userService.transferSellerProfilePhotoToAttachementFileDb(userId, sessionUser.getUser().getUserId());

			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 (다시한번)지워준다.

			user = userService.getUserInfo(userId);
			model.put("user", user);
			model.put("resultCode", "0");
			model.put("message", "File uploaded successfully");

		} catch (Exception e) {
			e.printStackTrace();
			attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);

			model.put("user", null);
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}
		return model;

	}

	@RequestMapping(value = "/deleteProfileImage", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteProfileImage(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		String userId = request.getParameter("userId");

		if (StringUtils.isBlank(userId)) {
			throw new Exception("[USER_ID]  이항목(들)은 빈 값이 될 수 없습니다.");
		}

		User user = userService.getUserInfo(userId);

		try {
			// 물리적인 위치의 파일을 삭제하고, 첨부파일을 관리하는 테이블에서 또한 삭제해 준다.
			attachmentFileService.deleteAttachmentFile(new AttachmentFile(user.getProfilePhotoId()));

			// 사용자테이블의 사진파일 아이디컬럼을 Null 처리해준다.
			userService.updateUserProfilePhoto(new User(userId));

			model.put("resultCode", "0");
			model.put("message", "1개 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/deleteUser", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteUser(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String userId = request.getParameter("userId");

		try {

			// TODO : 현재로써는 아무것도 하지 않음.

			model.put("resultCode", "0");
			model.put("message", "사용자 계정을 삭제 하였습니다.");

		} catch (Exception e) {
			model.put("resultCode", "-1");
			model.put("message", "사용자 계정을 삭제하는 도중에 문제가 발생 하였습니다. :" + e.getMessage());
		}

		return model;
	}

}
