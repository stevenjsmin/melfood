/** 
 * 2016 CheckBeforeBuyMgtController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.checkbeforebuy.CheckBeforeBuy;
import melfood.shopping.checkbeforebuy.CheckBeforeBuyService;
import melfood.shopping.contract.ContractInfoService;

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/admin/checkbeforebuy")
public class CheckBeforeBuyMgtController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CheckBeforeBuyMgtController.class);

	@Autowired
	private CheckBeforeBuyService checkBeforeBuyService;

	@Autowired
	private ContractInfoService contractInfoService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/checkbeforebuy/main");
		Properties htmlProperty = new Properties();

		List<Option> isDefaultOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_IS_DEFAULT");
		List<Option> confirmStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_CONFIRM_STATUS");
		htmlProperty = new Properties("isDefault");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsDefault", codeService.generateCmbx(isDefaultOptions, htmlProperty));

		htmlProperty = new Properties("confirmStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxConfirmStatus", codeService.generateCmbx(confirmStatusOptions, htmlProperty));

		// 판매자목록을 가저온다.
		User seller = new User();
		seller.setUseYn("Y");
		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		return mav;
	}

	@RequestMapping(value = "/getCheckBeforeBuyList", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCheckBeforeBuyList(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		CheckBeforeBuy checkBeforeBuy = new CheckBeforeBuy();

		// For Pagination
		checkBeforeBuy.setPagenationPage(getPage(request));
		checkBeforeBuy.setPagenationPageSize(getPageSize(request));

		String id = request.getParameter("id");
		String sellerId = request.getParameter("sellerId");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		String isDefault = request.getParameter("isDefault");
		String confirmStatus = request.getParameter("confirmStatus");

		if (StringUtils.isNotBlank(id)) checkBeforeBuy.setId(Integer.parseInt(id));
		if (StringUtils.isNotBlank(sellerId)) checkBeforeBuy.setSellerId(sellerId);
		if (StringUtils.isNotBlank(subject)) checkBeforeBuy.setSubject(subject);
		if (StringUtils.isNotBlank(contents)) checkBeforeBuy.setContents(contents);
		if (StringUtils.isNotBlank(isDefault)) checkBeforeBuy.setIsDefault(isDefault);
		if (StringUtils.isNotBlank(confirmStatus)) checkBeforeBuy.setConfirmStatus(confirmStatus);

		List<CheckBeforeBuy> checkBeforeBuyList = checkBeforeBuyService.getCheckBeforeBuyList(checkBeforeBuy);

		Integer totalCount = 0;
		totalCount = checkBeforeBuyService.getTotalCntForCheckBeforeBuyList(checkBeforeBuy);
		model.put("totalCount", totalCount);
		model.put("list", checkBeforeBuyList);

		return model;
	}

	@RequestMapping("/getCheckBeforeBuy")
	public ModelAndView noticeDiscuss(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/checkbeforebuy/getCheckBeforeBuy");

		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

		CheckBeforeBuy checkBeforeBuy = checkBeforeBuyService.getCheckBeforeBuyInfo(Integer.parseInt(id));
		mav.addObject("checkBeforeBuy", checkBeforeBuy);

		return mav;
	}

	@RequestMapping("/regist")
	public ModelAndView noticeDiscussRegistForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/checkbeforebuy/regist");
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		mav.addObject("creator", sessionUser.getUser().getUserName());

		Properties htmlProperty = new Properties();

		List<Option> isDefaultOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_IS_DEFAULT", "N");
		List<Option> confirmStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_CONFIRM_STATUS", "ON_DRAFT");

		htmlProperty = new Properties("isDefault");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsDefault", codeService.generateCmbx(isDefaultOptions, htmlProperty));

		htmlProperty = new Properties("confirmStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxConfirmStatus", codeService.generateCmbx(confirmStatusOptions, htmlProperty));

		// 판매자목록을 가저온다.
		User seller = new User();
		seller.setUseYn("Y");
		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		return mav;
	}

	@RequestMapping("/modify")
	public ModelAndView noticeDiscussModifyForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/checkbeforebuy/modify");

		String id = request.getParameter("id");
		CheckBeforeBuy checkBeforeBuy = checkBeforeBuyService.getCheckBeforeBuyInfo(Integer.parseInt(id));

		Properties htmlProperty = new Properties();

		List<Option> isDefaultOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_IS_DEFAULT", checkBeforeBuy.getIsDefault());
		List<Option> confirmStatusOptions = codeService.getValueCmbxOptions("PROD_MGT", "CHK_BEFORE_BUY_CONFIRM_STATUS", checkBeforeBuy.getConfirmStatus());

		htmlProperty = new Properties("isDefault");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsDefault", codeService.generateCmbx(isDefaultOptions, htmlProperty));

		htmlProperty = new Properties("confirmStatus");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxConfirmStatus", codeService.generateCmbx(confirmStatusOptions, htmlProperty));

		// 판매자목록을 가저온다.
		User seller = new User();
		seller.setUseYn("Y");
		List<Option> contractorOptions = contractInfoService.getSellers(seller, checkBeforeBuy.getSellerId());
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		mav.addObject("checkBeforeBuy", checkBeforeBuy);

		return mav;
	}

	@RequestMapping(value = "/saveModify", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveModify(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String actionMode = request.getParameter("actionMode");
		if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

		String id = request.getParameter("id");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		String sellerId = request.getParameter("sellerId");
		String isDefault = request.getParameter("isDefault");
		String confirmStatus = request.getParameter("confirmStatus");
		String creator = sessionUser.getUser().getUserId();

		CheckBeforeBuy checkBeforeBuy = null;

		try {

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_MODIFY)) {
				if (StringUtils.isBlank(id) || StringUtils.isBlank(subject) || StringUtils.isBlank(contents)) {
					throw new Exception("[ID | SUBJECT | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				checkBeforeBuy = new CheckBeforeBuy(id);
			} else {
				if (StringUtils.isBlank(subject) || StringUtils.isBlank(contents)) {
					throw new Exception("[SUBJECT | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				checkBeforeBuy = new CheckBeforeBuy();
				checkBeforeBuy.setCreator(creator);
			}
			if (StringUtils.isNotBlank(sellerId)) checkBeforeBuy.setSellerId(sellerId);
			if (StringUtils.isNotBlank(subject)) checkBeforeBuy.setSubject(subject);
			if (StringUtils.isNotBlank(contents)) checkBeforeBuy.setContents(contents);
			if (StringUtils.isNotBlank(isDefault)) {
				checkBeforeBuy.setIsDefault(isDefault);
			} else {
				checkBeforeBuy.setIsDefault("N");
			}
			if (StringUtils.isNotBlank(confirmStatus)) {
				checkBeforeBuy.setConfirmStatus(confirmStatus);
			} else {
				checkBeforeBuy.setConfirmStatus("ON_DRAFT");
			}

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				updateCnt = checkBeforeBuyService.registCheckBeforeBuy(checkBeforeBuy);
			} else {
				updateCnt = checkBeforeBuyService.modifyCheckBeforeBuy(checkBeforeBuy);
			}

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/deleteCheckBeforeBuy", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteCheckBeforeBuy(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String id = request.getParameter("id");

		try {
			if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

			int updateCnt = 0;
			updateCnt = checkBeforeBuyService.deleteCheckBeforeBuy(Integer.parseInt(id));

			model.put("resultCode", "0");
			model.put("message", updateCnt + " record deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

}
