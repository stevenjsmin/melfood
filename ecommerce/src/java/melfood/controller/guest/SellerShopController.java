/** 
 * 2016 SellerShopController.java
 * Created by steven.min
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import melfood.controller.common.ProductUtilCtrl;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.product.*;
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

//import melfood.framework.notice.NoticeDiscuss;
//import melfood.framework.notice.NoticeDiscussService;

/**
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/shop")
public class SellerShopController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SellerShopController.class);

	@Autowired
	private DeliveryCalendarService deliveryCalendarService;

//	@Autowired
//	private NoticeDiscussService noticeDiscussService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/no")
	public ModelAndView shopNo(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/shop/no");

		String productName = request.getParameter("productName");
		String sellerId = request.getParameter("seller");
		mav.addObject("productName", productName);

//		NoticeDiscuss noticeDiscuss = new NoticeDiscuss();

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		// 판매자 정보를 가저온다.
		User seller = userService.getUserInfo(sellerId);
		mav.addObject("seller", seller);
		if (seller == null || StringUtils.isBlank(sellerId)) {
			mav = new ModelAndView("tiles/shop/notfoundshop");
			return mav;
		}

		// 판매자의 공지사항 내용을 가저온다.
		String noticeCnt = request.getParameter("noticeCnt");
//		if (StringUtils.isBlank(noticeCnt)) noticeCnt = "2";
//		noticeDiscuss = new NoticeDiscuss();
//		noticeDiscuss.setPagenationPage(0);
//		noticeDiscuss.setPagenationPageSize(Integer.parseInt(noticeCnt));
//		noticeDiscuss.setWriteFrom(sellerId);
//		noticeDiscuss.setIsForNotice("Y");
//		List<NoticeDiscuss> noticeDiscussList = noticeDiscussService.getNoticeDiscussList(noticeDiscuss);
//		mav.addObject("noticeDiscussList", noticeDiscussList);
//
//		// 판매자와 로그인 사용자간의 대화 내용을 가저온다.
//		List<NoticeDiscuss> conversationList = null;
//		if (sessionUser != null) {
//			noticeDiscuss = new NoticeDiscuss();
//			String conversationCnt = request.getParameter("conversationCnt");
//			if (StringUtils.isBlank(conversationCnt)) conversationCnt = "5";
//			noticeDiscuss = new NoticeDiscuss();
//			noticeDiscuss.setPagenationPage(0);
//			noticeDiscuss.setPagenationPageSize(Integer.parseInt(conversationCnt));
//			noticeDiscuss.setWriteFrom(sellerId);
//			noticeDiscuss.setWriteTo(sessionUser.getUser().getUserId());
//			conversationList = noticeDiscussService.getConversationList(noticeDiscuss);
//			mav.addObject("sessionUser", sessionUser);
//		} else {
//			mav.addObject("sessionUser", null);
//		}
//		mav.addObject("conversationList", conversationList);

		// 판매자의 상품을 모두 가저온다.
		mav = new ProductUtilCtrl().getSellerProducts(mav, request, sellerId);

		return mav;
	}

	@RequestMapping(value = "/discussSave", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveModify(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		Map<String, Object> model = new HashMap<String, Object>();
		int updateCnt = 0;

		String subject = "대화";
		String contents = request.getParameter("contents");
		String writeFrom = sessionUser.getUser().getUserId();
		String writeTo = request.getParameter("writeTo");
		String isForAllSeller = "N";
		String isForAllCustomer = "N";
		String isForNotice = "N";

//		NoticeDiscuss noticeDiscuss = null;

		try {

			if (StringUtils.isBlank(subject) || StringUtils.isBlank(contents)) {
				throw new Exception("[SUBJECT | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
			}
//			noticeDiscuss = new NoticeDiscuss();
//			noticeDiscuss.setCreator(sessionUser.getUser().getUserId());
//
//			if (StringUtils.isNotBlank(subject)) noticeDiscuss.setSubject(subject);
//			if (StringUtils.isNotBlank(contents)) noticeDiscuss.setContents(contents);
//			if (StringUtils.isNotBlank(writeFrom)) noticeDiscuss.setWriteFrom(writeFrom);
//			if (StringUtils.isNotBlank(writeTo)) noticeDiscuss.setWriteTo(writeTo);
//			if (StringUtils.isNotBlank(isForAllSeller)) noticeDiscuss.setIsForAllSeller(isForAllSeller);
//			if (StringUtils.isNotBlank(isForAllCustomer)) noticeDiscuss.setIsForAllCustomer(isForAllCustomer);
//			if (StringUtils.isNotBlank(isForNotice)) noticeDiscuss.setIsForNotice(isForNotice);
//
//			updateCnt = noticeDiscussService.registNoticeDiscuss(noticeDiscuss);

			model.put("resultCode", "0");
			model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/sellerDeliverySchedule")
	public ModelAndView sellerDeliverySchedule(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView("tiles/shop/sellerDeliverySchedule");
		Properties htmlProperty = new Properties();

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		String isPopup = request.getParameter("isPopup");
		mav.addObject("isPopup", isPopup);

		// 판매자 정보를 얻어온다.
		String sellerId = request.getParameter("sellerId");
		User seller = userService.getUserInfo(sellerId);
		if (seller != null) {
			mav.addObject("seller", seller);
			mav.addObject("sellerId", sellerId);
		} else {
			mav.addObject("seller", null);
			mav.addObject("sellerId", sellerId);
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String searchDateFrom = df.format(cal.getTime());
		mav.addObject("defaultSearchDateFrom", searchDateFrom);

		List<Option> isPickupOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_METHOD_ISPICKUP");
		htmlProperty = new Properties("isPickup");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxIsPickup", codeService.generateCmbx(isPickupOptions, htmlProperty));

		mav.addObject("sessionUser", sessionUser);
		if (sessionUser != null) {
			String sessionUserId = sessionUser.getUser().getUserId();
			User user = userService.getUserInfo(sessionUserId);

			String defaultCustomerPostcode = user.getAddressPostcode();
			String defaultCustomerSuburb = user.getAddressSuburb();

			if (!StringUtils.isBlank(defaultCustomerPostcode)) {
				mav.addObject("defaultCustomerPostcode", defaultCustomerPostcode);
			}
			if (!StringUtils.isBlank(defaultCustomerSuburb)) {
				mav.addObject("defaultCustomerSuburb", defaultCustomerSuburb);
			}

		} else {
			mav.addObject("defaultCustomerPostcode", null);
			mav.addObject("defaultCustomerSuburb", null);
		}

		return mav;
	}

	@RequestMapping("/sellerDeliveryAddressMap")
	public ModelAndView sellerDeliveryAddressMap(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView("tiles/shop/sellerDeliveryAddressMap");

		String yyyyMmDd = request.getParameter("yyyyMmDd");
		String sellerId = request.getParameter("sellerId");
		String deliverySeq = request.getParameter("deliverySeq");

		DeliveryCalendar deliveryCalendar = deliveryCalendarService.getDeliveryCalendar(new DeliveryCalendar(sellerId, yyyyMmDd, deliverySeq));

		StringBuffer fullAddress = new StringBuffer("");
		if (!StringUtils.isBlank(deliveryCalendar.getAddressStreet())) fullAddress.append(deliveryCalendar.getAddressStreet());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressSuburb())) fullAddress.append(", " + deliveryCalendar.getAddressSuburb());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressState())) fullAddress.append(" " + deliveryCalendar.getAddressState());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressPostcode())) fullAddress.append(" " + deliveryCalendar.getAddressPostcode());

		mav.addObject("mapFullAddress", fullAddress.toString());
		mav.addObject("yyyyMmDd", yyyyMmDd);
		mav.addObject("seller", userService.getUserInfo(sellerId));

		if (StringUtils.equalsIgnoreCase(deliveryCalendar.getIsPickup(), "Y")) {
			// 픽업하는 경우. : 선택된 주소정보만 가져온다.
			mav.addObject("mapMessage", getMessageForPickup(deliveryCalendar));
			mav.addObject("deliverySchedules", null);
			mav.addObject("mapIsMultipleMark", "N");

		} else if (StringUtils.equalsIgnoreCase(deliveryCalendar.getIsPickup(), "N")) {
			// 배송하는 경우. : 동일한 날짜의 배송지역 목록을 가져온다.
			List<DeliveryCalendar> deliverySchedule = deliveryCalendarService.getDeliveryCalendars(new DeliveryCalendar(sellerId, yyyyMmDd));
			mav.addObject("mapMessage", null);
			mav.addObject("deliverySchedules", deliverySchedule);
			mav.addObject("mapIsMultipleMark", "Y");
		}

		mav.addObject("deliveryCalendar", deliveryCalendar);

		return mav;
	}

	private String getMessageForPickup(DeliveryCalendar deliveryCalendar) {
		StringBuffer fullAddress = new StringBuffer("");
		if (!StringUtils.isBlank(deliveryCalendar.getAddressStreet())) fullAddress.append(deliveryCalendar.getAddressStreet());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressSuburb())) fullAddress.append(", " + deliveryCalendar.getAddressSuburb());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressState())) fullAddress.append(" " + deliveryCalendar.getAddressState());
		if (!StringUtils.isBlank(deliveryCalendar.getAddressPostcode())) fullAddress.append(" " + deliveryCalendar.getAddressPostcode());

		StringBuffer htmlMessage = new StringBuffer();
		htmlMessage.append("<table>");
		htmlMessage.append("<tr>");
		htmlMessage.append("    <td style='text-align: right;'><font color='#F07800'><b>픽업주소</b></font></td>");
		htmlMessage.append("    <td width='10' style='text-align: center;'>:</td>");
		htmlMessage.append("    <td>" + fullAddress.toString() + "</td>");
		htmlMessage.append("</tr>");

		if (!StringUtils.isBlank(deliveryCalendar.getBtwnFromHhmm()) && !StringUtils.isBlank(deliveryCalendar.getBtwnToHhmm())) {
			htmlMessage.append("<tr>");
			htmlMessage.append("    <td style='text-align: right;'><font color='#808040'>픽업시간 </font></td>");
			htmlMessage.append("    <td width='10' style='text-align: center;'>:</td>");
			htmlMessage.append("    <td>" + deliveryCalendar.getBtwnFromHhmm() + " ~ " + deliveryCalendar.getBtwnToHhmm() + "</td>");
			htmlMessage.append("</tr>");
		}

		if (!StringUtils.isBlank(deliveryCalendar.getAddressNote())) {
			htmlMessage.append("<tr>");
			htmlMessage.append("    <td colspan='3'><br/>" + deliveryCalendar.getAddressNote() + "</td>");
			htmlMessage.append("</tr>");
		}
		htmlMessage.append("</table>");

		return htmlMessage.toString();
	}

	@RequestMapping("/addcart")
	public ModelAndView orderProduct(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		ModelAndView mav = new ModelAndView("tiles/shop/addcart");
		String prodId = request.getParameter("prodId");
		Product product = null;

		// if (sessionUser == null) return new ModelAndView("tiles/common/auth/sessionExpired");

		if (!StringUtils.isBlank(prodId)) product = productService.getProduct(new Product(prodId));

		// 주문할 상품정보를 얻어온다.
		if (product == null) return new ModelAndView("tiles/shop/order/notfoundproduct");
		mav.addObject("product", product);

		// 주문할 상품 이미지 정보를 얻어온다.
		ProductImage productImage = new ProductImage(prodId);
		productImage.setPagenationPage(0);
		productImage.setPagenationPageSize(99999);
		List<ProductImage> productImages = productImageService.getProductImages(productImage);
		mav.addObject("productImages", productImages);

		// 주문할 상품 옵션 정보를 가저온다.
		Properties htmlProperty = new Properties();
		mav.addObject("productOptions", productOptionService.generateCmbxForOptionAndValue(htmlProperty, Integer.parseInt(prodId)));

		// 판매자 정보를 얻어온다.
		String sellerId = product.getSeller();
		mav.addObject("sellerId", sellerId);

		User seller = userService.getUserInfo(sellerId);
		mav.addObject("seller", seller);
		mav.addObject("sellerBusinessName", seller.getSellerBusinessName());
		mav.addObject("sellerId", seller.getUserId());
		mav.addObject("sellerName", seller.getUserName());

		// 판매자의 상품을 모두 가저온다.
		// mav = new ProductUtilCtrl().getSellerProducts(mav, request, sellerId);

		// 판매자의 상품배송 일정 검색을 위한 콤보박스 구성
		// List<Option> isPickupOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_METHOD_ISPICKUP");
		// htmlProperty = new Properties("isPickup");
		// htmlProperty.setCssClass("form-control");
		// mav.addObject("cbxIsPickup", codeService.generateCmbx(isPickupOptions, htmlProperty));
		mav.addObject("sessionUser", sessionUser);

		// String sessionUserId = sessionUser.getUser().getUserId();
		// User user = userService.getUserInfo(sessionUserId);
		//
		// String defaultCustomerPostcode = user.getAddressPostcode();
		// String defaultCustomerSuburb = user.getAddressSuburb();
		//
		// if (!StringUtils.isBlank(defaultCustomerPostcode)) {
		// mav.addObject("defaultCustomerPostcode", defaultCustomerPostcode);
		// }
		// if (!StringUtils.isBlank(defaultCustomerSuburb)) {
		// mav.addObject("defaultCustomerSuburb", defaultCustomerSuburb);
		// }

		return mav;
	}
}
