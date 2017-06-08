/**
 * 2016 SellerShopController.java
 * Created by steven.min
 *
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import melfood.framework.system.BaseController;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.payment.PaymentMethodService;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductImageService;
import melfood.shopping.product.ProductOptionService;
import melfood.shopping.product.ProductService;

/**
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/shop")
public class SellerShopController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SellerShopController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductOptionService productOptionService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private DeliveryCalendarService deliveryCalendarService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@RequestMapping("/no")
	public ModelAndView join(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/shop/no");
		String sellerId = request.getParameter("seller");

		// TODO : 판매자 정보를 가저온다.
		User seller = userService.getUserInfo(sellerId);
		mav.addObject("seller", seller);

		if (seller == null || StringUtils.isBlank(sellerId)) {
			mav = new ModelAndView("tiles/shop/notfoundshop");
			return mav;
		}

		// TODO : 판매자의 배송일정을 가저온다.
		// TODO : 판매자의 공지사항 내용을 가저온다.
		// TODO : 판매자의 메인페이지의 상단에 뿌려질 내용을 가저온다.
		// TODO : 판매자의 메인페이지의 하단에 뿌려질 내용을 가저온다.

		// 판매자의 상품을 모두 가저온다.
		Product sellerProduct = new Product();
		sellerProduct.setSeller(sellerId);
		sellerProduct.setPagenationPageSize(99999);
		List<Product> sellerProducts = productService.getProducts(sellerProduct);
		mav.addObject("sellerProducts", sellerProducts);

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
		if (StringUtils.isNotBlank(sellerId)) deliveryCalendar.setSellerId(sellerId);

		Integer totalCount = 0;
		List<DeliveryCalendar> list = deliveryCalendarService.getDeliveryCalendars(deliveryCalendar);
		totalCount = deliveryCalendarService.getTotalCntForDeliveryCalendars(deliveryCalendar);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}

}
