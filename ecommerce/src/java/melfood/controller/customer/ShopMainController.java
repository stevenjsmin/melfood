/**
 * 2015 ShoppingCartController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.customer;

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.gmap.MelfoodGoogleMapService;
import melfood.framework.gmap.gson.dto.GMapResult;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.product.*;
import melfood.shopping.shop.ShopMaster;
import melfood.shopping.shop.ShopMasterService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 전문샵 메인 컨트롤러 <br>
 */
@Controller
@RequestMapping("/shop")
public class ShopMainController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ShopMainController.class);

    @Autowired
    ProductService productService;

    @Autowired
    ShopMasterService shopMasterService;

    @Autowired
    DeliveryCalendarService deliveryCalendarService;

    @Autowired
    MelfoodGoogleMapService melfoodGoogleMapService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductOptionService productOptionService;


    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/shop/main");
        String shopId = request.getParameter("shopId");

        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
        User shopOwner = userService.getUserInfo(shopMaster.getShopOwner());
        User customerUser = null;
        if (sessionUser != null) customerUser = userService.getUserInfo(sessionUser.getUser().getUserId());

        mav.addObject("shopMaster", shopMaster);
        mav.addObject("owner", shopOwner);
        mav.addObject("customerUser", customerUser);

        return mav;
    }


    @RequestMapping(value = "/products", produces = "application/json")
    @ResponseBody
    public Map<String, Object> products(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        // String userId = sessionUser.getUser().getUserId();
        String shopOwner = request.getParameter("shopOwner");
        String searchWord = request.getParameter("searchWord");

        Product product = new Product();
        product.setSeller(shopOwner);
        product.setName(searchWord);

        List<Product> productList = null;

        // For Pagination
        product.setPagenationPage(getPage(request));
        product.setPagenationPageSize(getPageSize(request));

        try {
            Integer totalCount = 0;
            productList = productService.getProducts(product);
            totalCount = productService.getTotalCntForProducts(product);

            model.put("list", productList);
            model.put("totalCount", totalCount);

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    /**
     * 배송이 가능한 경우, 배송가능 목록 조회
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkDeliverable", produces = "application/json")
    @ResponseBody
    public Map<String, Object> checkDeliverable(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        ShopMaster shopMaster = null;
        String shopId = request.getParameter("shopId");

        if (sessionUser == null) {
            model.put("resultCode", "UNKNOWN_ERROR");
            model.put("mapResultCode", "UNKNOWN_ERROR");
            model.put("mapResultMessage", "로그인을 하셔야 고객님위치에 따라 배송비 정보가 제공됩니다. :" + "&nbsp;&nbsp;&nbsp;[ <a href=\"javascript:doLoginPopup();\" style=\"font-size: 9px;\">로그인</a> ]");

            model.put("deliveryFee", "0.00");
            model.put("cutomerAddress", "");
            model.put("distance", "0");
            model.put("duration", "0");
            model.put("shopMaster", null);

            return model;
        }

        String customerUserId = sessionUser.getUser().getUserId();
        User customerUser = userService.getUserInfo(customerUserId);

        String resultCode = "OK";

        StringBuffer shopAddress = new StringBuffer();
        StringBuffer cutomerAddress = new StringBuffer();
        GMapResult mapResult = null;
        String mapResultCode = "";
        String mapResultMessage = "";
        String estimatedDeliveryTime = "";

        try {
            // 샵기본정보를 얻어온다.
            shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
            model.put("shopMaster", shopMaster);

            if (StringUtils.isNotBlank(shopMaster.getAddressStreet())) shopAddress.append(shopMaster.getAddressStreet() + " ");
            if (StringUtils.isNotBlank(shopMaster.getAddressSuburb())) shopAddress.append(shopMaster.getAddressSuburb() + " ");
            if (StringUtils.isNotBlank(shopMaster.getAddressState())) shopAddress.append(shopMaster.getAddressSuburb() + " ");
            if (StringUtils.isNotBlank(shopMaster.getAddressPostcode())) shopAddress.append(shopMaster.getAddressPostcode());

            if (StringUtils.isNotBlank(customerUser.getAddressStreet())) cutomerAddress.append(customerUser.getAddressStreet() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressSuburb())) cutomerAddress.append(customerUser.getAddressSuburb() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressState())) cutomerAddress.append(customerUser.getAddressState() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressPostcode())) cutomerAddress.append(customerUser.getAddressPostcode());

            if (StringUtils.equals(shopMaster.getDeliveryService(), "Y")) {
                // 배송가능인 경우

                if (shopAddress == null
                        || StringUtils.isBlank(shopMaster.getAddressStreet())
                        || StringUtils.isBlank(shopMaster.getAddressSuburb())
                        || StringUtils.isBlank(shopMaster.getAddressState())
                        || StringUtils.isBlank(shopMaster.getAddressPostcode())) {
                    resultCode = "MARKET_ADDR_INVALID";
                    mapResultMessage = "공동구매 하는곳의 주소가 유효하지 않습니다.";

                }

                if (customerUser == null
                        || StringUtils.isBlank(customerUser.getAddressStreet())
                        || StringUtils.isBlank(customerUser.getAddressSuburb())
                        || StringUtils.isBlank(customerUser.getAddressState())
                        || StringUtils.isBlank(customerUser.getAddressPostcode())) {
                    resultCode = "CUSTOMER_ADDR_INVALID";
                    mapResultMessage = "고객님의 주소가 유효하지 않습니다. 'My푸드 -> 개인정보변' 에서 주소를 확인해 주세요.";
                }

//                // 고객의 지역(Suburb)가 배송 가능지역인지 체크한다
//                if (!StringUtils.equals(resultCode, "MARKET_ADDR_INVALID") && !StringUtils.equals(resultCode, "CUSTOMER_ADDR_INVALID")) {
//                    // DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                    // Calendar cal = Calendar.getInstance();
//                    // cal.setTime(new Date());
//                    // String deliveryDate = df.format(cal.getTime());
//                    String deliveryDate = groupPurchase.getMarketOpenStartDate();
//                    List<DeliveryCalendar> deliverySchedule = deliveryCalendarService.getDeliveryCalendars(new DeliveryCalendar(groupPurchase.getPurchaseOrganizer(), deliveryDate));
//
//                    boolean deliverable = false;
//                    for (DeliveryCalendar calendar : deliverySchedule) {
//                        if (StringUtils.equalsIgnoreCase(customerUser.getAddressSuburb(), calendar.getAddressSuburb())) {
//                            deliverable = true;
//                            estimatedDeliveryTime = calendar.getBtwnFromHhmm() + " ~ " + calendar.getBtwnToHhmm();
//                            model.put("estimatedDeliveryTime", estimatedDeliveryTime);
//                            break;
//                        }
//                    }
//                    if (deliverable == false) {
//                        resultCode = "NO_DELIVERABLE_SERVICE_AREA";
//                        mapResultMessage = "죄송합니다. 현재 고객님의 지역 '<b>" + customerUser.getAddressSuburb() + " " + customerUser.getAddressPostcode() + "</b>' 에는 배송일정 스케줄이 잡혀있지 않습니다.";
//                    }
//                }

                DecimalFormat df = new DecimalFormat("0.00");
                float deliveruFee = 0.0f;
                int distance = 0;
                String duration = "0";
                if (!StringUtils.equals(resultCode, "MARKET_ADDR_INVALID") && !StringUtils.equals(resultCode, "CUSTOMER_ADDR_INVALID") && !StringUtils.equals(resultCode, "NO_DELIVERABLE_SERVICE_AREA")) {
                    // 마켓주소와 고객의 주소에 일단 내용이 존재한다면 지도정보를 조회한다.
                    mapResult = melfoodGoogleMapService.getLookupGmapDistance(shopAddress.toString(), cutomerAddress.toString());
                    if (mapResult == null) throw new Exception("거리를 계산하는 도중 문제가 발생하여 배송서비스를 이용하실 수 없습니다.");
                    mapResultCode = mapResult.getRows()[0].getElements()[0].getStatus();

                    if (!StringUtils.equalsIgnoreCase(mapResultCode, "OK")) {
                        resultCode = "CUSTOMER_ADDR_INVALID";
                        mapResultMessage = "고객님의 주소가 유효하지 않습니다. 배달서비스를 이용하기기 위해서 'My푸드 -> 개인정보변경' 에서 주소를 확인해 주세요.";
                    } else {
                        // 모든게 정상이면 거리와, 배달비를 계산한다
                        distance = Math.round(Integer.parseInt(mapResult.getRows()[0].getElements()[0].getDistance().getValue()) / 1000);
                        if (Float.parseFloat(shopMaster.getDeliveryFeePerKm()) != 0 && distance != 0) {
                            deliveruFee = shopMaster.getDeliveryBaseCharge() + (distance * Float.parseFloat(shopMaster.getDeliveryFeePerKm()));
                        } else {
                            deliveruFee = shopMaster.getDeliveryBaseCharge();
                        }
                        duration = mapResult.getRows()[0].getElements()[0].getDuration().getText();
                    }
                }

                model.put("resultCode", resultCode);
                model.put("mapResultCode", mapResultCode);
                model.put("mapResultMessage", mapResultMessage);
                model.put("deliveryFee", df.format(deliveruFee));
                model.put("cutomerAddress", customerUser.getAddressHomeGmapFormattedAddress());
                model.put("distance", Integer.toString(distance));
                model.put("duration", duration);

            } else {
                model.put("resultCode", "OK");
                model.put("mapResultCode", "NO_DELIVERY");
                model.put("mapResultMessage", "본 공동구매는 배달서비스를 제공하지 않습니다.");
                model.put("deliveryFee", "0.00");
                model.put("cutomerAddress", customerUser.getAddressHomeGmapFormattedAddress());
                model.put("estimatedDeliveryTime", estimatedDeliveryTime);
                model.put("distance", "0");
                model.put("duration", "0");
            }

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "UNKNOWN_ERROR");
            model.put("mapResultCode", "UNKNOWN_ERROR");
            model.put("mapResultMessage", e.getMessage() == null ? "UNKNOWN_ERROR" : (e.getMessage() + "&nbsp;&nbsp;&nbsp;[ <a href=\"javascript:checkDeliverable();\" style=\"font-size: 9px;\">다시시도</a> ]"));
            model.put("estimatedDeliveryTime", estimatedDeliveryTime);

            model.put("deliveryFee", "0.00");
            model.put("cutomerAddress", cutomerAddress.toString());
            model.put("distance", "0");
            model.put("duration", "0");
        }

        return model;
    }

//    @RequestMapping("/deliverySchedule")
//    public ModelAndView deliverySchedule(HttpServletRequest request) throws Exception {
//
//        ModelAndView mav = new ModelAndView("tiles/customer/grouppurchase/deliverySchedule");
//        Properties htmlProperty = new Properties();
//
//        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
//        String groupPurchaseId = request.getParameter("groupPurchaseId");
//
//        GroupPurchase groupPurchase = null;
//        List<DeliveryCalendar> deliverySchedule = null;
//        String deliveryDate = null;
//
//        try {
//            // 공동구매 기본정보를 얻어온다.
//            groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));
//
//            deliveryDate = groupPurchase.getMarketOpenStartDate();
//            deliverySchedule = deliveryCalendarService.getDeliveryCalendars(new DeliveryCalendar(groupPurchase.getPurchaseOrganizer(), deliveryDate));
//
//            mav.addObject("deliveryDate", deliveryDate);
//            mav.addObject("deliverySchedules", deliverySchedule);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            mav.addObject("deliveryDate", null);
//            mav.addObject("deliverySchedules", null);
//        }
//
//        return mav;
//    }


    @RequestMapping("/productOrderPopup")
    public ModelAndView productOrderPopup(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/shop/productOrderPopup");
        String shopId = request.getParameter("shopId");
        String prodId = request.getParameter("prodId");

        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
        User shopOwner = userService.getUserInfo(shopMaster.getShopOwner());
        Product product = productService.getProduct(new Product(prodId));

        List<ProductImage> productImages = null;
        List<ProductOptionGroup> productOptionGroups = null;

        // 상품에 소속된 이미지를 가저와서 설정한다.
        productImages = productImageService.getProductImages(new ProductImage(product.getProdId()));
        product.setProductionImages(productImages);

        // 상품에 소속된 옵션정보를 가저오서 설정한다.
        Properties htmlProperty = new Properties();
        productOptionGroups = productOptionService.generateCmbxForOptionAndValue(htmlProperty, Integer.parseInt(prodId), false);
        product.setProductOptionGroups(productOptionGroups);

        if (productImages.size() > 0 && productImages.get(0).getImageFileId() != 0) {
            // 등록된 첫번째 이미지를 대표이미지로 사용한다.
            mav.addObject("firstImageId", Integer.toString(productImages.get(0).getImageFileId()));
        } else {
            mav.addObject("firstImageId", null);
        }

        mav.addObject("shopMaster", shopMaster);
        mav.addObject("shopOwner", shopOwner);
        mav.addObject("product", product);

        return mav;
    }
}
