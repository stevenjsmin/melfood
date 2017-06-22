/** 
 * 2016 DeliveryCalendarMgtController.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.contract.ContractInfoService;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
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
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/admin/deliverycalendarmgt")
public class DeliveryCalendarMgtController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DeliveryCalendarMgtController.class);

	@Autowired
	private DeliveryCalendarService deliveryCalendarService;

	@Autowired
	private ContractInfoService contractInfoService;

	@RequestMapping("/Main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/admin/deliverycalendarmgt/main");

		User seller = new User();
		seller.setUseYn("Y");

		Properties htmlProperty = new Properties();
		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String searchDateFrom = df.format(cal.getTime());
		mav.addObject("defaultSearchDateFrom", searchDateFrom);

		List<Option> isPickupOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_METHOD_ISPICKUP");
		htmlProperty = new Properties("isPickup");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsPickup", codeService.generateCmbx(isPickupOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/getDeliveryCalendars", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getDeliveryCalendars(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		DeliveryCalendar deliveryCalendar = new DeliveryCalendar();

		// For Pagination
		deliveryCalendar.setPagenationPage(getPage(request));
		deliveryCalendar.setPagenationPageSize(getPageSize(request));

		String sellerId = request.getParameter("sellerId");
		String isPickup = request.getParameter("isPickup");
		String searchDateFrom = request.getParameter("searchDateFrom");
		String searchDateTo = request.getParameter("searchDateTo");

		// 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
		if (StringUtils.isBlank(searchDateFrom)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			searchDateFrom = df.format(cal.getTime());
		}
		deliveryCalendar.setSearchDateFrom(searchDateFrom);

		if (StringUtils.isNotBlank(isPickup)) deliveryCalendar.setIsPickup(isPickup);
		if (StringUtils.isNotBlank(searchDateTo)) deliveryCalendar.setSearchDateTo(searchDateTo);
		if (StringUtils.isNotBlank(searchDateTo)) deliveryCalendar.setSearchDateTo(searchDateTo);
		if (StringUtils.isNotBlank(sellerId)) deliveryCalendar.setSellerId(sellerId);

		Integer totalCount = 0;
		List<DeliveryCalendar> list = deliveryCalendarService.getDeliveryCalendars(deliveryCalendar);
		totalCount = deliveryCalendarService.getTotalCntForDeliveryCalendars(deliveryCalendar);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

	@RequestMapping(value = "/enableAndDisableDiliveryCalendar", produces = "application/json")
	@ResponseBody
	public Map<String, Object> enableAndDisableDiliveryCalendar(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String sellerId = request.getParameter("sellerId");
		String yyyyMmDd = request.getParameter("yyyyMmDd");
		String deliverySeq = request.getParameter("deliverySeq");

		try {
			if (StringUtils.isBlank(sellerId) || StringUtils.isBlank(yyyyMmDd) || StringUtils.isBlank(deliverySeq)) throw new Exception("[SELLER_ID | YYYY_MM_DD | DELIVERY_SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");

			DeliveryCalendar deliveryCalendar = deliveryCalendarService.getDeliveryCalendar(new DeliveryCalendar(sellerId, yyyyMmDd, deliverySeq));
			if (StringUtils.equalsIgnoreCase(deliveryCalendar.getUseYn(), "N")) {
				deliveryCalendar.setUseYn("Y");
			} else {
				deliveryCalendar.setUseYn("N");
			}

			updateCnt = deliveryCalendarService.modifyDeliveryCalendarForNotNull(deliveryCalendar);

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "/deleteDeliveryCalendar", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deleteDeliveryCalendar(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String sellerId = request.getParameter("sellerId");
		String yyyyMmDd = request.getParameter("yyyyMmDd");
		String deliverySeq = request.getParameter("deliverySeq");

		try {
			if (StringUtils.isBlank(sellerId) || StringUtils.isBlank(yyyyMmDd) || StringUtils.isBlank(deliverySeq)) throw new Exception("[SELLER_ID | YYYY_MM_DD | DELIVERY_SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
			DeliveryCalendar deliveryCalendar = new DeliveryCalendar(sellerId, yyyyMmDd, deliverySeq);

			updateCnt = deliveryCalendarService.deleteDeliveryCalendar(deliveryCalendar);

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/addCalendarForm")
	public ModelAndView addCalendarForm(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		ModelAndView mav = new ModelAndView("tiles/admin/deliverycalendarmgt/addCalendarForm");
		Properties htmlProperty = new Properties();

		boolean isSessionSeller = StringUtils.equalsIgnoreCase(sessionUser.getSessionRole().getRoleId(), "SELLER");

		User seller = new User();
		seller.setUseYn("Y");
		List<Option> contractorOptions = contractInfoService.getSellers(seller);
		htmlProperty = new Properties("sellerId");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));

		List<Option> addressStateOptions1 = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions1, htmlProperty));

		String defaultPickupState = "VIC";
		String defaultPickupPostcode = "";
		String defaultPickupSurbub = "";
		String defaultPickupStreet = "";
		if (isSessionSeller) {
			defaultPickupPostcode = sessionUser.getUser().getSellerDeliveryAddressPostcode();
			defaultPickupState = sessionUser.getUser().getSellerDeliveryAddressState();
			defaultPickupSurbub = sessionUser.getUser().getSellerDeliveryAddressSuburb();
			defaultPickupStreet = sessionUser.getUser().getSellerDeliveryAddressStreet();
		}
		mav.addObject("defaultPickupPostcode", defaultPickupPostcode);
		mav.addObject("defaultPickupSurbub", defaultPickupSurbub);
		mav.addObject("defaultPickupStreet", defaultPickupStreet);

		List<Option> defaultPickupStreetOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", defaultPickupState);
		htmlProperty = new Properties("sellerPickupAddressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxDefaultPickupState", codeService.generateCmbx(defaultPickupStreetOptions, htmlProperty));

		List<Option> isPickupOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_METHOD_ISPICKUP", "Y");
		htmlProperty = new Properties("isPickup");
		htmlProperty.setOnchange("infoForPickupservice(this)");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsPickup", codeService.generateCmbx(isPickupOptions, htmlProperty));

		return mav;
	}

	@RequestMapping(value = "/addCalendar", produces = "application/json")
	@ResponseBody
	public Map<String, Object> addCalendar(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		int updateCnt = 0;

		String sellerId = request.getParameter("sellerId");
		String yyyyMmDd = request.getParameter("yyyyMmDd");
		// String deliverySeq = request.getParameter("deliverySeq");

		String addressPostcode = request.getParameter("addressPostcode");
		String addressState = request.getParameter("addressState");
		String addressSuburb = request.getParameter("addressSuburb");
		String addressStreet = request.getParameter("addressStreet");
		String addressNote = request.getParameter("addressNote");
		String isPickup = request.getParameter("isPickup");
		String btwnFromHhmm = request.getParameter("btwnFromHhmm");
		String btwnToHhmm = request.getParameter("btwnToHhmm");
		String orderStartDt = request.getParameter("orderStartDt");
		String orderEndDt = request.getParameter("orderEndDt");

		try {
			if (StringUtils.isBlank(sellerId) || StringUtils.isBlank(yyyyMmDd) || StringUtils.isBlank(addressPostcode) || StringUtils.isBlank(addressSuburb) || StringUtils.isBlank(isPickup))
				throw new Exception("[SELLER_ID | YYYY_MM_DD | AM_PM | IS_PICKUP | ADDRESS_POSTCODE | ADDRESS_SUBURB]  이항목(들)은 빈 값이 될 수 없습니다.");

			DeliveryCalendar deliveryCalendar = new DeliveryCalendar(sellerId, yyyyMmDd);

			deliveryCalendar.setIsPickup(isPickup);
			if (StringUtils.isNotBlank(btwnFromHhmm)) deliveryCalendar.setBtwnFromHhmm(btwnFromHhmm);
			if (StringUtils.isNotBlank(btwnToHhmm)) deliveryCalendar.setBtwnToHhmm(btwnToHhmm);
			if (StringUtils.isNotBlank(orderStartDt)) deliveryCalendar.setOrderingStartDt(orderStartDt);
			if (StringUtils.isNotBlank(orderEndDt)) deliveryCalendar.setOrderingEndDt(orderEndDt);

			if (StringUtils.equalsIgnoreCase(isPickup, "Y")) {
				if (StringUtils.isBlank(addressPostcode) || StringUtils.isBlank(addressState) || StringUtils.isBlank(addressSuburb) || StringUtils.isBlank(addressStreet)) {
					throw new Exception("배송방법이 픽업인 경우 정확한 주소를 입력해아합니다.");
				}
				deliveryCalendar.setAddressPostcode(addressPostcode);
				deliveryCalendar.setAddressState(addressState);
				deliveryCalendar.setAddressSuburb(addressSuburb);
				deliveryCalendar.setAddressStreet(addressStreet);
				if (StringUtils.isNotBlank(addressNote)) deliveryCalendar.setAddressNote(addressNote);

			} else if (StringUtils.equalsIgnoreCase(isPickup, "N")) {
				if (StringUtils.isBlank(addressPostcode) || StringUtils.isBlank(addressState) || StringUtils.isBlank(addressSuburb)) {
					throw new Exception("배송방법이 픽업이 아닌 경우 State/Postcode/Suburb 주소를 입력해아합니다.");
				}
				deliveryCalendar.setAddressPostcode(addressPostcode);
				deliveryCalendar.setAddressState(addressState);
				deliveryCalendar.setAddressSuburb(addressSuburb);
				deliveryCalendar.setAddressStreet(null);
			} else {
				deliveryCalendar.setAddressPostcode(null);
				deliveryCalendar.setAddressState(null);
				deliveryCalendar.setAddressSuburb(null);
				deliveryCalendar.setAddressStreet(null);

				if (!StringUtils.isBlank(btwnFromHhmm) && StringUtils.isBlank(btwnToHhmm)) {
					btwnToHhmm = btwnFromHhmm;
				}

			}
			deliveryCalendar.setUseYn("Y");
			updateCnt = deliveryCalendarService.insertDeliveryCalendar(deliveryCalendar);

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

}
