/**
 * 2016 ProductOrderController.java
 * Created by steven.min
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
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendar;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.grouppurchase.GroupPurchaseProductService;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import melfood.shopping.payment.PaymentMethod;
import melfood.shopping.payment.PaymentMethodService;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steven.min
 */
@Controller
@RequestMapping("/grouppurchase")
public class GroupPurchaseOrderMainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupPurchaseOrderMainController.class);

    @Autowired
    private MelfoodGoogleMapService melfoodGoogleMapService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private GroupPurchaseService groupPurchaseService;

    @Autowired
    private GroupPurchaseProductService groupPurchaseProductService;

    @Autowired
    private DeliveryCalendarService deliveryCalendarService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @RequestMapping("/Main")
    public ModelAndView orderProduct(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        ModelAndView mav = new ModelAndView("tiles/customer/grouppurchase/main");
        String groupPurchaseId = request.getParameter("groupPurchaseId");
        if (StringUtils.isBlank(groupPurchaseId)) throw new Exception("조회하고자하는 공동구매ID 값이 없습니다. :");

        GroupPurchase groupPurchase = null;
        List<GroupPurchaseProduct> groupPurchaseProducts = null;

        try {
            // GroupPurchase --> GroupPurchaseProduct/(s) --> Product/(s) --> ProductImage/(s)
            // 공동구매 기본정보
            groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));
            // 공동구매를 진행하는 사람의 상세정보
            // organizer = userService.getUserInfo(groupPurchase.getPurchaseOrganizer());
            // 공동구매 상품정보(목록)
            groupPurchaseProducts = groupPurchaseProductService.getGroupPurchaseProducts(groupPurchaseId);

            // GroupPurchase --> GroupPurchaseProduct/(s) --> Product/(s) --> ProductImage/(s)
            List<ProductImage> productImages = null;
            List<ProductOptionGroup> productOptionGroups = null;
            int prodId = 0;
            Product product = null;
            for (int i = 0; i < groupPurchaseProducts.size(); i++) {

                // 제품으 상세정보를 설정한다.
                prodId = groupPurchaseProducts.get(i).getProductId();
                product = productService.getProduct(new Product(prodId));

                // 상품에 소속된 이미지를 가저와서 설정한다.
                productImages = productImageService.getProductImages(new ProductImage(product.getProdId()));
                product.setProductionImages(productImages);

                // 상품에 소속된 옵션정보를 가저오서 설정한다.
                Properties htmlProperty = new Properties();
                productOptionGroups = productOptionService.generateCmbxForOptionAndValue(htmlProperty, prodId, false);
                product.setProductOptionGroups(productOptionGroups);

                // 첫번째 이미지를 대표이미지로 설정
                if (productImages.size() > 0) product.setProductImage(productImages.get(0));
                groupPurchaseProducts.get(i).setProduct(product);
            }

            List<ProductImage> groupPurchaseImages = groupPurchaseService.getProductImages(new ProductImage(groupPurchase.getGroupPurchaseId()));
            groupPurchase.setGroupPurchaseImages(groupPurchaseImages);
            if (groupPurchaseImages.size() > 0 && groupPurchaseImages.get(0).getImageFileId() != 0) {
                // 등록된 첫번째 이미지를 대표이미지로 사용한다.
                mav.addObject("firstImageId", Integer.toString(groupPurchaseImages.get(0).getImageFileId()));
            } else {
                mav.addObject("firstImageId", null);
            }

            // 결재방식 콤보박스 구성
            Properties htmlProperty = new Properties();
            List<Option> paymentMethods = paymentMethodService.getCmbxOptions(groupPurchase.getPurchaseOrganizer(), true, "CASH");
            htmlProperty = new Properties("paymentMethod");
            htmlProperty.setCssClass("form-control");
            htmlProperty.setOnchange("getPaymentMethodInfo(this)");
            mav.addObject("cbxPaymentMethod", paymentMethodService.generateCmbx(paymentMethods, htmlProperty));


            mav.addObject("organizer", userService.getUserInfo(groupPurchase.getPurchaseOrganizer()));
            mav.addObject("groupPurchase", groupPurchase);
            mav.addObject("groupPurchaseProducts", groupPurchaseProducts);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping("/purchaseOrganizerInfo")
    public ModelAndView detailUserForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/grouppurchase/purchaseOrganizerInfo");

        String purchaseOrganizer = request.getParameter("purchaseOrganizer");
        User user = userService.getUserInfo(purchaseOrganizer);
        mav.addObject("purchaseOrganizer", user);

        return mav;
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

        GroupPurchase groupPurchase = null;
        String groupPurchaseId = request.getParameter("groupPurchaseId");

        String customerUserId = sessionUser.getUser().getUserId();
        User customerUser = userService.getUserInfo(customerUserId);

        String resultCode = "OK";

        StringBuffer marketAddress = new StringBuffer();
        StringBuffer cutomerAddress = new StringBuffer();
        GMapResult mapResult = null;
        String mapResultCode = "";
        String mapResultMessage = "";
        String estimatedDeliveryTime = "";

        try {
            // 공동구매 기본정보를 얻어온다.
            groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));

            if (StringUtils.isNotBlank(groupPurchase.getMarketAddressStreet())) marketAddress.append(groupPurchase.getMarketAddressStreet() + " ");
            if (StringUtils.isNotBlank(groupPurchase.getMarketAddressSuburb())) marketAddress.append(groupPurchase.getMarketAddressSuburb() + " ");
            if (StringUtils.isNotBlank(groupPurchase.getMarketAddressState())) marketAddress.append(groupPurchase.getMarketAddressState() + " ");
            if (StringUtils.isNotBlank(groupPurchase.getMarketAddressPostcode())) marketAddress.append(groupPurchase.getMarketAddressPostcode());

            if (StringUtils.isNotBlank(customerUser.getAddressStreet())) cutomerAddress.append(customerUser.getAddressStreet() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressSuburb())) cutomerAddress.append(customerUser.getAddressSuburb() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressState())) cutomerAddress.append(customerUser.getAddressState() + " ");
            if (StringUtils.isNotBlank(customerUser.getAddressPostcode())) cutomerAddress.append(customerUser.getAddressPostcode());

            if (StringUtils.equals(groupPurchase.getDeliverable(), "Y")) {
                // 배송가능인 경우

                if (marketAddress == null
                        || StringUtils.isBlank(groupPurchase.getMarketAddressStreet())
                        || StringUtils.isBlank(groupPurchase.getMarketAddressSuburb())
                        || StringUtils.isBlank(groupPurchase.getMarketAddressState())
                        || StringUtils.isBlank(groupPurchase.getMarketAddressPostcode())) {
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

                // 고객의 지역(Suburb)가 배송 가능지역인지 체크한다
                if (!StringUtils.equals(resultCode, "MARKET_ADDR_INVALID") && !StringUtils.equals(resultCode, "CUSTOMER_ADDR_INVALID")) {
                    // DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    // Calendar cal = Calendar.getInstance();
                    // cal.setTime(new Date());
                    // String deliveryDate = df.format(cal.getTime());
                    String deliveryDate = groupPurchase.getMarketOpenStartDate();
                    List<DeliveryCalendar> deliverySchedule = deliveryCalendarService.getDeliveryCalendars(new DeliveryCalendar(groupPurchase.getPurchaseOrganizer(), deliveryDate));

                    boolean deliverable = false;
                    for (DeliveryCalendar calendar : deliverySchedule) {
                        if (StringUtils.equalsIgnoreCase(customerUser.getAddressSuburb(), calendar.getAddressSuburb())) {
                            deliverable = true;
                            estimatedDeliveryTime = calendar.getBtwnFromHhmm() + " ~ " + calendar.getBtwnToHhmm();
                            model.put("estimatedDeliveryTime", estimatedDeliveryTime);
                            break;
                        }
                    }
                    if (deliverable == false) {
                        resultCode = "NO_DELIVERABLE_SERVICE_AREA";
                        mapResultMessage = "죄송합니다. 현재 고객님의 지역 '<b>" + customerUser.getAddressSuburb() + " " + customerUser.getAddressPostcode() + "</b>' 에는 배송일정 스케줄이 잡혀있지 않습니다.";
                    }
                }

                DecimalFormat df = new DecimalFormat("0.00");
                float deliveruFee = 0.0f;
                int distance = 0;
                String duration = "0";
                if (!StringUtils.equals(resultCode, "MARKET_ADDR_INVALID") && !StringUtils.equals(resultCode, "CUSTOMER_ADDR_INVALID") && !StringUtils.equals(resultCode, "NO_DELIVERABLE_SERVICE_AREA")) {
                    // 마켓주소와 고객의 주소에 일단 내용이 존재한다면 지도정보를 조회한다.
                    mapResult = melfoodGoogleMapService.getLookupGmapDistance(marketAddress.toString(), cutomerAddress.toString());
                    mapResultCode = mapResult.getRows()[0].getElements()[0].getStatus();

                    if (!StringUtils.equalsIgnoreCase(mapResultCode, "OK")) {
                        resultCode = "CUSTOMER_ADDR_INVALID";
                        mapResultMessage = "고객님의 주소가 유효하지 않습니다. 배달서비스를 이용하기기 위해서 'My푸드 -> 개인정보변경' 에서 주소를 확인해 주세요.";
                    } else {
                        // 모든게 정상이면 거리와, 배달비를 계산한다
                        distance = Math.round(Integer.parseInt(mapResult.getRows()[0].getElements()[0].getDistance().getValue()) / 1000);
                        if (groupPurchase.getDeliveryFeePerKm() != 0 && distance != 0) {
                            deliveruFee = groupPurchase.getDeliveryBasicFee() + (distance * groupPurchase.getDeliveryFeePerKm());
                        } else {
                            deliveruFee = groupPurchase.getDeliveryBasicFee();
                        }
                        duration = mapResult.getRows()[0].getElements()[0].getDuration().getText();
                    }
                }

                model.put("resultCode", resultCode);
                model.put("mapResultCode", mapResultCode);
                model.put("mapResultMessage", mapResultMessage);
                model.put("deliveryFee", df.format(deliveruFee));
                model.put("cutomerAddress", cutomerAddress.toString());
                model.put("distance", Integer.toString(distance));
                model.put("duration", duration);

            } else {
                model.put("resultCode", "OK");
                model.put("mapResultCode", "NO_DELIVERY");
                model.put("mapResultMessage", "본 공동구매는 배달서비스를 제공하지 않습니다.");
                model.put("deliveryFee", "0.00");
                model.put("cutomerAddress", cutomerAddress.toString());
                model.put("estimatedDeliveryTime", estimatedDeliveryTime);
                model.put("distance", "0");
                model.put("duration", "0");
            }

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "UNKNOWN_ERROR");
            model.put("mapResultCode", "UNKNOWN_ERROR");
            model.put("mapResultMessage", e.getMessage() == null ? "UNKNOWN_ERROR" : e.getMessage());
            model.put("estimatedDeliveryTime", estimatedDeliveryTime);

            model.put("deliveryFee", "0.00");
            model.put("cutomerAddress", cutomerAddress.toString());
            model.put("distance", "0");
            model.put("duration", "0");
        }

        return model;
    }

    @RequestMapping("/deliverySchedule")
    public ModelAndView deliverySchedule(HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("tiles/customer/grouppurchase/deliverySchedule");
        Properties htmlProperty = new Properties();

        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        String groupPurchaseId = request.getParameter("groupPurchaseId");

        GroupPurchase groupPurchase = null;
        List<DeliveryCalendar> deliverySchedule = null;
        String deliveryDate = null;

        try {
            // 공동구매 기본정보를 얻어온다.
            groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));

            deliveryDate = groupPurchase.getMarketOpenStartDate();
            deliverySchedule = deliveryCalendarService.getDeliveryCalendars(new DeliveryCalendar(groupPurchase.getPurchaseOrganizer(), deliveryDate));

            mav.addObject("deliveryDate", deliveryDate);
            mav.addObject("deliverySchedules", deliverySchedule);

        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("deliveryDate", null);
            mav.addObject("deliverySchedules", null);
        }

        return mav;
    }


    @RequestMapping(value = "/getPaymentMethodInfo", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getPaymentMethodInfo(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        PaymentMethod paymentMethod = null;
        String methodSeq = request.getParameter("methodSeq");
        String purchaseOrganizer = request.getParameter("purchaseOrganizer");

        try {
            // 공동구매 기본정보를 얻어온다.
            paymentMethod = paymentMethodService.getPaymentMethod(new PaymentMethod(purchaseOrganizer, methodSeq));

            model.put("paymentMethod", paymentMethod);

            model.put("resultCode", "0");
            model.put("message", "");

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());

        }

        return model;
    }


}
