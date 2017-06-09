/** 
 * 2016 NoticeController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.notice;

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

/**
 * Controller for Admin main entry management
 * 
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
@Controller
@RequestMapping("/framework/noticedisscussmanager")
public class NoticeDiscussController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeDiscussController.class);

	@Autowired
	private NoticeDiscussService noticeDiscussService;

	@RequestMapping("/Main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/noticedisscussmanager/main");
		Properties htmlProperty = new Properties();

		List<Option> isForNoticeOptions = codeService.getValueCmbxOptions("COMM", "IS_FOR_NOTICE");
		htmlProperty = new Properties("isForNotice");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForNotice", codeService.generateCmbx(isForNoticeOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/noticeDiscusses", produces = "application/json")
	@ResponseBody
	public Map<String, Object> noticeDiscusses(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		NoticeDiscuss noticeDiscuss = new NoticeDiscuss();

		// For Pagination
		noticeDiscuss.setPagenationPage(getPage(request));
		noticeDiscuss.setPagenationPageSize(getPageSize(request));

		String id = request.getParameter("id");
		String category = request.getParameter("category");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		String writeFrom = request.getParameter("writeFrom");
		String writeTo = request.getParameter("writeTo");
		String isForAllSeller = request.getParameter("isForAllSeller");
		String isForAllCustomer = request.getParameter("isForAllCustomer");
		String isForNotice = request.getParameter("isForNotice");
		String searchDateFrom = request.getParameter("searchDateFrom");
		String searchDateTo = request.getParameter("searchDateTo");

		if (StringUtils.isNotBlank(id)) noticeDiscuss.setId(Integer.parseInt(id));
		if (StringUtils.isNotBlank(category)) noticeDiscuss.setCategory(category);
		if (StringUtils.isNotBlank(subject)) noticeDiscuss.setSubject(subject);
		if (StringUtils.isNotBlank(contents)) noticeDiscuss.setContents(contents);
		if (StringUtils.isNotBlank(writeFrom)) noticeDiscuss.setWriteFrom(writeFrom);
		if (StringUtils.isNotBlank(writeTo)) noticeDiscuss.setWriteTo(writeTo);
		if (StringUtils.isNotBlank(isForAllSeller)) noticeDiscuss.setIsForAllSeller(isForAllSeller);
		if (StringUtils.isNotBlank(isForAllCustomer)) noticeDiscuss.setIsForAllCustomer(isForAllCustomer);
		if (StringUtils.isNotBlank(isForNotice)) noticeDiscuss.setIsForNotice(isForNotice);
		if (StringUtils.isNotBlank(searchDateFrom)) noticeDiscuss.setSearchDateFrom(searchDateFrom);
		if (StringUtils.isNotBlank(searchDateTo)) noticeDiscuss.setSearchDateTo(searchDateTo);

		List<NoticeDiscuss> noticeDiscussList = noticeDiscussService.getNoticeDiscussList(noticeDiscuss);

		Integer totalCount = 0;
		totalCount = noticeDiscussService.getTotalCntForNoticeDiscussList(noticeDiscuss);
		model.put("totalCount", totalCount);
		model.put("list", noticeDiscussList);

		return model;
	}

	@RequestMapping("/noticeDiscuss")
	public ModelAndView noticeDiscuss(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/noticedisscussmanager/detailInfo");

		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

		NoticeDiscuss noticeDiscuss = noticeDiscussService.getNoticeDiscussInfo(Integer.parseInt(id));
		mav.addObject("noticeDiscuss", noticeDiscuss);

		return mav;
	}

	@RequestMapping("/regist")
	public ModelAndView noticeDiscussRegistForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/noticedisscussmanager/regist");
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		mav.addObject("creator", sessionUser.getUser().getUserName());

		Properties htmlProperty = new Properties();

		List<Option> isForNoticeOptions = codeService.getValueCmbxOptions("COMM", "IS_FOR_NOTICE", "Y");
		htmlProperty = new Properties("isForNotice");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForNotice", codeService.generateCmbx(isForNoticeOptions, htmlProperty));

		List<Option> isForAllSellerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", "Y");
		htmlProperty = new Properties("isForAllSeller");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForAllSeller", codeService.generateCmbx(isForAllSellerOptions, htmlProperty));

		List<Option> isForAllCustomerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", "Y");
		htmlProperty = new Properties("isForAllCustomer");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForAllCustomer", codeService.generateCmbx(isForAllCustomerOptions, htmlProperty));

		mav.addObject("writeFrom", sessionUser.getUser().getUserId());
		mav.addObject("writeFromName", sessionUser.getUser().getUserName());
		
		return mav;
	}

	@RequestMapping("/modify")
	public ModelAndView noticeDiscussModifyForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/noticedisscussmanager/modify");

		String id = request.getParameter("id");
		NoticeDiscuss noticeDiscuss = noticeDiscussService.getNoticeDiscussInfo(Integer.parseInt(id));

		Properties htmlProperty = new Properties();

		List<Option> isForNoticeOptions = codeService.getValueCmbxOptions("COMM", "IS_FOR_NOTICE", noticeDiscuss.getIsForNotice());
		htmlProperty = new Properties("isForNotice");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForNotice", codeService.generateCmbx(isForNoticeOptions, htmlProperty));

		List<Option> isForAllSellerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", noticeDiscuss.getIsForAllSeller());
		htmlProperty = new Properties("isForAllSeller");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForAllSeller", codeService.generateCmbx(isForAllSellerOptions, htmlProperty));

		List<Option> isForAllCustomerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", noticeDiscuss.getIsForAllCustomer());
		htmlProperty = new Properties("isForAllCustomer");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsForAllCustomer", codeService.generateCmbx(isForAllCustomerOptions, htmlProperty));

		mav.addObject("noticeDiscuss", noticeDiscuss);

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
		String category = request.getParameter("category");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		String writeFrom = request.getParameter("writeFrom");
		String writeTo = request.getParameter("writeTo");
		String isForAllSeller = request.getParameter("isForAllSeller");
		String isForAllCustomer = request.getParameter("isForAllCustomer");
		String isForNotice = request.getParameter("isForNotice");
		String searchDateFrom = request.getParameter("searchDateFrom");
		String searchDateTo = request.getParameter("searchDateTo");
		String creator = sessionUser.getUser().getUserId();

		if (StringUtils.isBlank(writeFrom)) writeFrom = creator;

		if (!StringUtils.isBlank(writeTo)) {
			isForAllSeller = "N";
			isForAllCustomer = "N";
		}
		if (StringUtils.equalsIgnoreCase(isForAllSeller, "Y") || StringUtils.equalsIgnoreCase(isForAllCustomer, "Y")) {
			writeTo = null;
		}

		NoticeDiscuss noticeDiscuss = null;

		try {

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_MODIFY)) {
				if (StringUtils.isBlank(id) || StringUtils.isBlank(subject) || StringUtils.isBlank(contents)) {
					throw new Exception("[ID | SUBJECT | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				noticeDiscuss = new NoticeDiscuss(id);
			} else {
				if (StringUtils.isBlank(subject) || StringUtils.isBlank(contents)) {
					throw new Exception("[SUBJECT | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
				}
				noticeDiscuss = new NoticeDiscuss();
				noticeDiscuss.setCreator(creator);
			}
			if (StringUtils.isNotBlank(category)) noticeDiscuss.setCategory(category);
			if (StringUtils.isNotBlank(subject)) noticeDiscuss.setSubject(subject);
			if (StringUtils.isNotBlank(contents)) noticeDiscuss.setContents(contents);
			if (StringUtils.isNotBlank(writeFrom)) noticeDiscuss.setWriteFrom(writeFrom);
			if (StringUtils.isNotBlank(writeTo)) noticeDiscuss.setWriteTo(writeTo);
			if (StringUtils.isNotBlank(isForAllSeller)) noticeDiscuss.setIsForAllSeller(isForAllSeller);
			if (StringUtils.isNotBlank(isForAllCustomer)) noticeDiscuss.setIsForAllCustomer(isForAllCustomer);
			if (StringUtils.isNotBlank(isForNotice)) noticeDiscuss.setIsForNotice(isForNotice);
			if (StringUtils.isNotBlank(searchDateFrom)) noticeDiscuss.setSearchDateFrom(searchDateFrom);
			if (StringUtils.isNotBlank(searchDateTo)) noticeDiscuss.setSearchDateTo(searchDateTo);

			if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
				updateCnt = noticeDiscussService.registNoticeDiscuss(noticeDiscuss);
			} else {
				updateCnt = noticeDiscussService.modifyNoticeDiscuss(noticeDiscuss);
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

	@RequestMapping(value = "/deleteNoticeDiscuss", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteNoticeDiscuss(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		String id = request.getParameter("id");

		try {
			if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

			int updateCnt = 0;
			updateCnt = noticeDiscussService.deleteNoticeDiscuss(Integer.parseInt(id));

			model.put("resultCode", "0");
			model.put("message", updateCnt + " record deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/findUserForm")
	public ModelAndView addCalendarForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/framework/noticedisscussmanager/findUserForm");
		Properties htmlProperty = new Properties();

		String objectName = request.getParameter("objectName");
		mav.addObject("objectName", objectName);

		List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
		htmlProperty = new Properties("userType");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxUserType", codeService.generateCmbx(userTypeOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/findUser", produces = "application/json")
	@ResponseBody
	public Map<String, Object> listUsers(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		List<User> list = null;

		String userName = request.getParameter("userName");
		String userType = request.getParameter("userType");

		User user = new User();

		// For Pagination
		user.setPagenationPage(getPage(request));
		user.setPagenationPageSize(getPageSize(request));

		if (StringUtils.isNotBlank(userName)) user.setUserName(userName);
		if (StringUtils.isNotBlank(userType)) user.setRoleId(userType);

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
