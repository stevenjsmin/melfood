/** 
 * 2015 ShoppingCartController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import melfood.shopping.checkbeforebuy.CheckBeforeBuy;
import melfood.shopping.checkbeforebuy.CheckBeforeBuyService;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.order.OrderProductOption;
import melfood.shopping.order.OrderProductOptionService;
import melfood.shopping.order.ShoppingCart;
import melfood.shopping.order.ShoppingCartService;
import melfood.shopping.payment.PaymentMethodService;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductService;

/**
 * 장바구니 관련 콘트롤러 <br>
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

	@Autowired
	ProductService productService;

	@Autowired
	DeliveryCalendarService deliveryCalendarService;

	@Autowired
	PaymentMethodService paymentMethodService;

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	OrderProductOptionService orderProductOptionService;

	@Autowired
	CheckBeforeBuyService checkBeforeBuyService;

	/**
	 * 이 메소드에서는 선택된(선택된 상품판매자: 선택된 장바구니가 없는 경우 첫번째 장바구니) 장바구니 정보를 보여준다.<br>
	 * 이때 화면 좌측에는 고객이 가지고있는 모든 장바구니 목록(장바구니에 들어있는 상품과 주문갯수 그리고 결재액을 보여주고<br>
	 * 메인 화면에는 선택된 장바구니의 상세정보를 보여준다.<br>
	 * <br>
	 * 메인 화면의 선택된 장바구니의 상세정보에는 담겨진 상세 상품목록과, 상품수령일짜/픽업일짜 그리고 결재방법을 받아드릴수 있는 화면을 구성한다.
	 * 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mycart")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		ModelAndView mav = new ModelAndView("tiles/cart/showMultipleMyCart");
		Properties htmlProperty = new Properties();
		String customerId = sessionUser.getUser().getUserId();

		String selectedSellerId = request.getParameter("selectedSellerId");

		// 현재 고객의 모든 상품판매자 목록을 얻어온다.(판매자 별로 장바구니가 구성되어 있기때문이다.)
		List<User> sellers = shoppingCartService.getProductSellers(customerId);
		if (sellers == null || sellers.size() < 1) {
			mav = new ModelAndView("tiles/cart/notfoundcart");
			return mav;
		}
		if (sellers.size() == 1) {
			// mav = new ModelAndView("tiles/cart/showMyCart");
		}

		User sessUserDeliveryAddr = userService.getUserInfo(customerId);
		String deliveryAddresss = sessUserDeliveryAddr.getAddressStreet() + ", " + sessUserDeliveryAddr.getAddressSuburb() + ", " + sessUserDeliveryAddr.getAddressState() + " " + sessUserDeliveryAddr.getAddressPostcode();
		mav.addObject("deliveryAddresss", deliveryAddresss);

		// 각 상품판매자 별로 장바구니(선택된 여러 상품)를 구성한다음, 그 장바구니를 customerCarts에 담는다.
		List<ShoppingCart> allCarts = new ArrayList<ShoppingCart>();
		List<ShoppingCart> soppingItems = new ArrayList<ShoppingCart>();
		ShoppingCart currentCart = null;

		ShoppingCart aCart = null;
		ShoppingCart tmp = null;
		Product aProduct = null;
		List<Product> prodList = new ArrayList<Product>();
		List<OrderProductOption> orderedOptions = new ArrayList<OrderProductOption>();
		float totalOrderPrice = 0.0f;
		float totalPriceForCart = 0.0f;
		int cartNumber = 0;
		for (User seller : sellers) {
			// 각 판매자별로 장바구니를 구성한다.
			aCart = new ShoppingCart(customerId, seller.getUserId());
			tmp = new ShoppingCart(customerId, seller.getUserId());
			tmp.setPagenationPage(0);
			tmp.setPagenationPageSize(99999);
			soppingItems = shoppingCartService.getShoppingCartProducts(tmp);

			cartNumber++;
			aCart.setCartNumber(cartNumber);

			OrderProductOption orderProductOption = new OrderProductOption();
			int productId = 0;
			int orderItemId = 0;
			int orderAmount = 0;

			prodList = new ArrayList<Product>();
			totalPriceForCart = 0.0f;
			for (int i = 0; i < soppingItems.size(); i++) {
				orderItemId = soppingItems.get(i).getId();
				productId = soppingItems.get(i).getProdId();
				orderAmount = soppingItems.get(i).getAmount();
				orderProductOption.setOrderItemId(orderItemId);

				// 장바구니에 상품정보(상세정보)를 구성한다.
				orderedOptions = orderProductOptionService.getOrderProductOptions(orderProductOption); // 상품에 소속된 옵션항목을 얻는다.
				aProduct = productService.getProduct(new Product(productId)); // 상세 상품정보를 가저와 설정한다.
				aProduct.setProductOrderedOptions(orderedOptions);// 옵션 항목을 상품에 설정한다.
				aProduct.setOrderAmount(orderAmount);
				aProduct.setOrderItemId(orderItemId);
				prodList.add(aProduct);

				totalPriceForCart = totalPriceForCart + (aProduct.getUnitPrice().floatValue() * orderAmount);
				totalOrderPrice = totalOrderPrice + (aProduct.getUnitPrice().floatValue() * orderAmount);

			}
			aCart.setTotalPrice(totalPriceForCart);
			aCart.setProducts(prodList);
			aCart.setSeller(userService.getUserInfo(seller.getUserId()));
			aCart.setCustomer(sessionUser.getUser());

			allCarts.add(aCart);
		}
		mav.addObject("customerCarts", allCarts);
		mav.addObject("totalOrderPrice", totalOrderPrice);

		// 현재 장바구니 설정
		if (StringUtils.isBlank(selectedSellerId)) {
			if (sellers != null) {
				currentCart = allCarts.get(0); // 첫번째 장바구니
				selectedSellerId = currentCart.getSeller().getUserId();
			}
		} else {
			String tempSellerId = null;
			for (ShoppingCart cart : allCarts) {
				tempSellerId = cart.getSeller().getUserId();
				if (StringUtils.equalsIgnoreCase(selectedSellerId, tempSellerId)) {
					currentCart = cart;
				}
			}
			if (currentCart == null) {
				currentCart = allCarts.get(0);
				selectedSellerId = currentCart.getSeller().getUserId();
			}

		}
		mav.addObject("selectedSellerId", selectedSellerId);
		mav.addObject("selectedCart", currentCart);
		mav.addObject("numberOfCart", sellers.size());

		// 판매자의 결재방식지정 콤보박스 구성
		List<Option> paymentMethods = paymentMethodService.getCmbxOptions(selectedSellerId);
		htmlProperty = new Properties("sellerPaymentMethod");
		htmlProperty.setCssClass("form-control");
		htmlProperty.setOnchange("showAdditionalInfoForPaymentMethod(this)");
		mav.addObject("cbxSellerPaymentMethod", paymentMethodService.generateCmbx(paymentMethods, htmlProperty));

		// 판매자 정보를 얻어온다.
		if (sessionUser != null) {
			mav.addObject("seller", currentCart.getSeller());
		}

		// 선택된 판매자(장바구니)의 구매전 확인사항을 가저온다.
		CheckBeforeBuy checkBeforeBuy = checkBeforeBuyService.getDefaultCheckBeforeBuy(selectedSellerId);
		mav.addObject("checkBeforeBuy", checkBeforeBuy);

		return mav;
	}

	/**
	 * 선택한 항목을 장바구니에 주문 수량과 함께 담는다.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProductOnCart", produces = "application/json")
	@ResponseBody
	public Map<String, Object> addProductOnCart(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		Map<String, Object> model = new HashMap<String, Object>();

		String customerId = sessionUser.getUser().getUserId();
		String prodId = request.getParameter("prodId");
		String amount = request.getParameter("amount");
		String productOptions = request.getParameter("productOptions"); // 각 항목은 '항목=선택값'으로 구성되어있으며 각 항목의 구분은'^'로 구분되어있다.

		String prodOptions[] = null;

		try {

			// 상품기본정보와 판매자 정보를 가져온다.
			Product product = productService.getProduct(new Product(prodId));
			String sellerId = product.getSeller();

			// 장바구니에 주문에 상품 마스터 정보를 등록한다.
			ShoppingCart shoppingCart = new ShoppingCart(customerId, sellerId);
			shoppingCart.setAmount(Integer.parseInt(amount));
			shoppingCart.setProdId(Integer.parseInt(prodId));
			int insertedCartId = shoppingCartService.addProductOnCart(shoppingCart);

			// 만일 주문상품에 옵션 선택사항이 존재한다면, 옵션을 구성(목록형태로)하여 주문된 상품의 옵션 테이블(order_product_option)에 추가해준다.
			if (!StringUtils.isBlank(productOptions)) {
				prodOptions = StringUtils.split(productOptions, "^");
				if (prodOptions.length > 0) {

					List<OrderProductOption> optionList = new ArrayList<OrderProductOption>();
					for (int i = 0; i < prodOptions.length; i++) {
						OrderProductOption obj = new OrderProductOption(insertedCartId, StringUtils.substringBefore(prodOptions[i], "="), StringUtils.substringAfter(prodOptions[i], "="));
						obj.setSellerId(sellerId);
						obj.setCustomerId(customerId);
						obj.setProdId(Integer.parseInt(prodId));
						obj.setCartOrOrder("CART");

						optionList.add(obj);
					}

					// 옵션항목을 등록하기전에 순번 설정해준다.
					int nextOptionSeq = orderProductOptionService.nextSeq(insertedCartId);
					for (int i = 0; i < optionList.size(); i++) {
						optionList.get(i).setSeq((nextOptionSeq + i));
					}
					
					// 구성된 주문상품의 옵션정보를 등록한다.
					orderProductOptionService.insertOrderProductOption(optionList);
				}
			}

			model.put("resultCode", "0");
			model.put("message", "정상적으로 " + product.getName() + "상품이 장바구니에 담겼습니다.");

		} catch (Exception e) {
			e.printStackTrace();

			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	/**
	 * 선택한 항목을 장바구니에서 제거한다.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removeItem", produces = "application/json")
	@ResponseBody
	public Map<String, Object> removeItem(HttpServletRequest request) throws Exception {
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		Map<String, Object> model = new HashMap<String, Object>();

		String customerId = sessionUser.getUser().getUserId();
		String itemId = request.getParameter("itemId");

		try {

			int productId = shoppingCartService.getShoppingCartProduct(new ShoppingCart(itemId)).getProdId();
			Product product = productService.getProduct(new Product(productId));

			if (StringUtils.isBlank(customerId) || StringUtils.isBlank(itemId)) {
				throw new Exception("요청하신 주문항목은 삭제할 수 없습니다.");
			}

			// 장바구니로부터 주문된 항목을 지운다.
			ShoppingCart shoppingCart = new ShoppingCart(Integer.parseInt(itemId), customerId);
			int deleteCnt = shoppingCartService.deleteShoppingCart(shoppingCart);

			// 주문된 항목의 옵션항목들이다.
			OrderProductOption productOption = new OrderProductOption(Integer.parseInt(itemId));
			productOption.setCustomerId(customerId);
			productOption.setCartOrOrder("CART");
			int deleteOptionCnt = orderProductOptionService.deleteOrderProductOption(productOption);

			model.put("resultCode", "0");
			model.put("message", "정상적으로 [" + product.getName() + "] 상품이 장바구니에서 제거되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();

			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/modifyDeliveryAddressForm")
	public ModelAndView modifyDeliveryAddressForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/cart/modifyDeliveryAddressForm");
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		String sessionUserId = sessionUser.getUser().getUserId();
		// User user = sessionUser.getUser();
		User user = userService.getUserInfo(sessionUserId);

		Properties htmlProperty = new Properties();
		List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressState());
		htmlProperty = new Properties("addressState");
		htmlProperty.setCssClass("form-control");
		mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

		mav.addObject("customer", user);

		return mav;
	}

	@RequestMapping(value = "/modifyDeliveryAddress", produces = "application/json")
	@ResponseBody
	public Map<String, Object> modifyDeliveryAddress(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		String userId = sessionUser.getUser().getUserId();
		String addressState = request.getParameter("addressState");
		String addressPostcode = request.getParameter("addressPostcode");
		String addressSuburb = request.getParameter("addressSuburb");
		String addressStreet = request.getParameter("addressStreet");

		String updateUserId = "";
		try {

			if (StringUtils.isBlank(userId)) throw new Exception("[사용자 ID]  이항목(들)은 빈 값이 될 수 없습니다.");

			User user = new User(userId);
			if (!StringUtils.isBlank(addressPostcode)) user.setAddressPostcode(addressPostcode);
			if (!StringUtils.isBlank(addressState)) user.setAddressState(addressState);
			if (!StringUtils.isBlank(addressSuburb)) user.setAddressSuburb(addressSuburb);
			if (!StringUtils.isBlank(addressStreet)) user.setAddressStreet(addressStreet);

			updateUserId = userService.modifyUserForNotNull(user);

			model.put("resultCode", "0");
			model.put("message", "고객님의 주소(상품수령지)가 변경 되었습니다.");

		} catch (Exception e) {
			logger.info(e.getMessage());
			model.put("resultCode", "-1");
			model.put("message", e.getMessage());
		}

		return model;
	}

	@RequestMapping("/chooseDeliveryDateForm")
	public ModelAndView chooseDeliveryDateForm(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/cart/chooseDeliveryDateForm");
		String sellerId = request.getParameter("sellerId");
		mav.addObject("sellerId", sellerId);

		DeliveryCalendar deliveryCalendar = new DeliveryCalendar();

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		String sessionUserId = sessionUser.getUser().getUserId();
		// User user = sessionUser.getUser(); // 주문 도중에 고객의 주소지를 변경할수 있으므로 계속해서 세션의 사용자 정보를 사용하면 잘못될수도 있다.
		User user = userService.getUserInfo(sessionUserId);
		User seller = userService.getUserInfo(sellerId);
		mav.addObject("seller", seller);

		// For Pagination
		deliveryCalendar.setPagenationPage(0);
		deliveryCalendar.setPagenationPageSize(99999);

		String addressPostcode = user.getAddressPostcode();
		String addressSuburb = user.getAddressSuburb();

		// 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		deliveryCalendar.setSearchDateFrom(df.format(cal.getTime()));

		deliveryCalendar.setSellerId(sellerId);

		Integer totalCountForPickup = 0;
		Integer totalCountForDelivery = 0;

		// 고객이 상품을 픽업해서 찾아갈수있는 레코드 건수 체크
		deliveryCalendar.setIsPickup("Y");
		deliveryCalendar.setAddressPostcode(null);
		deliveryCalendar.setAddressSuburb(null);
		totalCountForPickup = deliveryCalendarService.getTotalCntForDeliveryCalendars(deliveryCalendar);
		mav.addObject("totalCountForPickup", totalCountForPickup);

		// 고객의 지역에 배달할수있는 레코드 건수 체크
		deliveryCalendar.setIsPickup("N");
		deliveryCalendar.setAddressPostcode(addressPostcode);
		deliveryCalendar.setAddressSuburb(addressSuburb);
		totalCountForDelivery = deliveryCalendarService.getTotalCntForDeliveryCalendars(deliveryCalendar);
		mav.addObject("totalCountForDelivery", totalCountForDelivery);

		return mav;
	}

	@RequestMapping(value = "/getDeliveryCalendars", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getDeliveryCalendars(HttpServletRequest request) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		DeliveryCalendar deliveryCalendar = new DeliveryCalendar();

		SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
		String sessionUserId = sessionUser.getUser().getUserId();
		// User user = sessionUser.getUser(); // 주문 도중에 고객의 주소지를 변경할수 있으므로 계속해서 세션의 사용자 정보를 사용하면 잘못될수도 있다.
		User user = userService.getUserInfo(sessionUserId);

		// For Pagination
		deliveryCalendar.setPagenationPage(getPage(request));
		deliveryCalendar.setPagenationPageSize(getPageSize(request));

		String sellerId = request.getParameter("sellerId");
		String addressPostcode = user.getAddressPostcode();
		String addressSuburb = user.getAddressSuburb();

		// 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		deliveryCalendar.setSearchDateFrom(df.format(cal.getTime()));

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

	@RequestMapping("/showSellerInfoPopup")
	public ModelAndView showSellerInfoPopup(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/cart/showSellerInfoPopup");
		String sellerId = request.getParameter("sellerId");
		String isPopup = request.getParameter("isPopup");

		User seller = userService.getUserInfo(sellerId);

		if (StringUtils.equalsIgnoreCase(isPopup, "y") || StringUtils.equalsIgnoreCase(isPopup, "yes") || StringUtils.equalsIgnoreCase(isPopup, "true")) {
			mav.addObject("isPopup", "y");
		} else {
			mav.addObject("isPopup", "n");
		}
		mav.addObject("seller", seller);

		return mav;
	}

	/**
	 * 테스트<br>
	 * 테스트<br>
	 * 테스트<br>
	 * 테스트<br>
	 * 
	 * 결재처리후 되돌아올 페이지
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/paymentresult")
	public ModelAndView paymentresult(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("tiles/cart/paymentresult");
		// User user = sessionUser.getUser();

		String paymentresult = request.getParameter("stripeToken");

		mav.addObject("paymentresult", paymentresult);

		return mav;
	}

}
