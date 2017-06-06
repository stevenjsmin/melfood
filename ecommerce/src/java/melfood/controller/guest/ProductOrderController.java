/** 
 * 2016 ProductOrderController.java
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

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.payment.PaymentMethodService;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductImage;
import melfood.shopping.product.ProductImageService;
import melfood.shopping.product.ProductOptionService;
import melfood.shopping.product.ProductService;

/**
 * @author steven.min
 *
 */
@Controller
@RequestMapping("/shop/order")
public class ProductOrderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProductOrderController.class);

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

	@RequestMapping("/product")
	public ModelAndView orderProduct(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

		ModelAndView mav = new ModelAndView("tiles/shop/order/product");
		String prodId = request.getParameter("prodId");

		// 주문할 상품정보를 얻어온다.
		Product product = productService.getProduct(new Product(prodId));
		if (product == null) {
			mav = new ModelAndView("tiles/shop/order/notfoundproduct");
			return mav;
		}
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
		if (sessionUser != null) {
			mav.addObject("seller", seller);
		} else {
			mav.addObject("seller", null);
		}
		mav.addObject("sellerBusinessName", seller.getSellerBusinessName());
		mav.addObject("sellerId", seller.getUserId());
		mav.addObject("sellerName", seller.getUserName());

		// 판매자의 상품을 모두 가저온다.
		Product sellerProduct = new Product();
		sellerProduct.setSeller(sellerId);
		sellerProduct.setPagenationPageSize(99999);
		List<Product> sellerProducts = productService.getProducts(sellerProduct);
		mav.addObject("sellerProducts", sellerProducts);

		// 판매자의 상품배송 일정 검색을 위한 콤보박스 구성
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
		String addressPostcode = request.getParameter("addressPostcode");
		String addressSuburb = request.getParameter("addressSuburb");

		// 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		deliveryCalendar.setSearchDateFrom(df.format(cal.getTime()));

		if (StringUtils.isNotBlank(isPickup)) deliveryCalendar.setIsPickup(isPickup);
		if (StringUtils.isNotBlank(addressPostcode)) deliveryCalendar.setAddressPostcode(addressPostcode);
		if (StringUtils.isNotBlank(addressSuburb)) deliveryCalendar.setAddressSuburb(addressSuburb);
		if (StringUtils.isNotBlank(sellerId)) deliveryCalendar.setSellerId(sellerId);

		Integer totalCount = 0;
		List<DeliveryCalendar> list = null;
		list = deliveryCalendarService.getDeliveryCalendarsForGuestOrder(deliveryCalendar);
		totalCount = deliveryCalendarService.getTotalCntForDeliveryCalendarsForGuestOrder(deliveryCalendar);

		model.put("totalCount", totalCount);
		model.put("list", list);

		return model;
	}
}
