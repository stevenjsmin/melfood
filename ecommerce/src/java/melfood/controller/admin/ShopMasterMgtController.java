/**
 * 2016 ContractMgtController.java
 * Created by steven.min
 * <p>
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import com.google.maps.model.GeocodingResult;
import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.gmap.MelfoodGoogleMapService;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.HtmlCodeGenerator;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.shopping.contract.ContractInfoService;
import melfood.shopping.shop.ShopMaster;
import melfood.shopping.shop.ShopMasterService;
import melfood.shopping.shop.ShopTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Class description
 *
 * @author steven.min
 */
@Controller
@RequestMapping("/admin/shopmastermgt")
public class ShopMasterMgtController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShopMasterMgtController.class);

    @Autowired
    private ShopMasterService shopMasterService;

    @Autowired
    private ContractInfoService contractInfoService;

    @Autowired
    private MelfoodGoogleMapService melfoodGoogleMapService;

    @RequestMapping("/Main")
    public ModelAndView main() throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/shopmaster/main");
        return mav;
    }

    @RequestMapping(value = "/shopMasters", produces = "application/json")
    @ResponseBody
    public Map<String, Object> shopMasters(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        ShopMaster shopMaster = new ShopMaster();

        shopMaster.setPagenationPage(getPage(request));
        shopMaster.setPagenationPageSize(getPageSize(request));

        Integer totalCount = 0;
        List<ShopMaster> list = shopMasterService.getShopMasters(shopMaster);
        totalCount = shopMasterService.getTotalCntForGetShopMasters(shopMaster);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }

    @RequestMapping("/shopMaster")
    public ModelAndView shopMaster(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/shopmaster/shopMaster");

        String shopId = request.getParameter("shopId");
        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
        mav.addObject("shopMaster", shopMaster);

        return mav;
    }

    @RequestMapping("/registShopForm")
    public ModelAndView registShopForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/shopmaster/regist");
        Properties htmlProperty = new Properties();

        List<Option> templateOptions = shopMasterService.getShopTemplateOptions(new ShopTemplate());
        htmlProperty = new Properties("templateId");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxTemplateId", codeService.generateCmbx(templateOptions, htmlProperty, true));

        List<Option> shopOwnerOptions = contractInfoService.getAllSellers();
        htmlProperty = new Properties("shopOwner");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxShopOwner", contractInfoService.generateCmbx(shopOwnerOptions, htmlProperty, true));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE");
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        htmlProperty = new Properties("forDeliverCalcAddressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxForDeliverCalcAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        // 배달가능여부 : 기본값 : N
        List<Option> deliverableOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DELIVERABLE", "N");
        String htmlForDeliverableCbx = HtmlCodeGenerator.generateComboboxForOptions("deliveryService", deliverableOptions);
        mav.addObject("cbxDeliveryService", htmlForDeliverableCbx);

        return mav;
    }

    @RequestMapping("/modifyShopForm")
    public ModelAndView modifyShopForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/shopmaster/modify");
        Properties htmlProperty = new Properties();

        String shopId = request.getParameter("shopId");

        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));

        List<Option> templateOptions = shopMasterService.getShopTemplateOptions(new ShopTemplate(), shopMaster.getShopId());
        htmlProperty = new Properties("templateId");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxTemplateId", codeService.generateCmbx(templateOptions, htmlProperty, true));

        List<Option> shopOwnerOptions = contractInfoService.getAllSellers(shopMaster.getShopOwner());
        htmlProperty = new Properties("shopOwner");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxShopOwner", contractInfoService.generateCmbx(shopOwnerOptions, htmlProperty, true));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE");
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        htmlProperty = new Properties("forDeliverCalcAddressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxForDeliverCalcAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        // 배달가능여부 : 기본값 : N
        List<Option> deliverableOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DELIVERABLE", "N");
        String htmlForDeliverableCbx = HtmlCodeGenerator.generateComboboxForOptions("deliveryService", deliverableOptions);
        mav.addObject("cbxDeliveryService", htmlForDeliverableCbx);


        return mav;
    }


    @RequestMapping(value = "/saveShopMaster", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveShopMaster(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String actionMode = request.getParameter("actionMode");
        if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

        String shopId = request.getParameter("shopId");
        String shopCredit = request.getParameter("shopCredit");
        String shopName = request.getParameter("shopName");
        String shopOwner = request.getParameter("shopOwner");
        String notice = request.getParameter("notice");
        String templateId = request.getParameter("templateId");
        String addressStreet = request.getParameter("addressStreet");
        String addressSuburb = request.getParameter("addressSuburb");
        String addressState = request.getParameter("addressState");
        String addressPostcode = request.getParameter("addressPostcode");
        String forDeliverCalcAddressStreet = request.getParameter("forDeliverCalcAddressStreet");
        String forDeliverCalcAddressSuburb = request.getParameter("forDeliverCalcAddressSuburb");
        String forDeliverCalcAddressState = request.getParameter("forDeliverCalcAddressState");
        String forDeliverCalcAddressPostcode = request.getParameter("forDeliverCalcAddressPostcode");


        String deliveryService = request.getParameter("deliveryService");
        String deliveryFeePerKm = request.getParameter("deliveryFeePerKm");
        String deliveryBaseCharge = request.getParameter("deliveryBaseCharge");
        String minimumPurchaseAmount = request.getParameter("minimumPurchaseAmount");
        String maximumPurchaseAmount = request.getParameter("maximumPurchaseAmount");
        String discountRateValue = request.getParameter("discountRateValue");

        ShopMaster shopMaster = null;

        try {

            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_MODIFY)) {
                if (StringUtils.isBlank(shopId)) {
                    throw new Exception("[shopId]  이항목(들)은 빈 값이 될 수 없습니다.");
                } else {
                    shopMaster = new ShopMaster(shopId);
                }
            } else {
                shopMaster = new ShopMaster();
            }

            if (StringUtils.isNotBlank(shopCredit)) shopMaster.setShopCredit(Integer.parseInt(shopCredit));
            if (StringUtils.isNotBlank(shopName)) shopMaster.setShopName(shopName);
            if (StringUtils.isNotBlank(shopOwner)) shopMaster.setShopOwner(shopOwner);
            if (StringUtils.isNotBlank(notice)) shopMaster.setNotice(notice);
            if (StringUtils.isNotBlank(templateId)) shopMaster.setTemplateId(Integer.parseInt(templateId));
            if (StringUtils.isNotBlank(addressStreet)) shopMaster.setAddressStreet(addressStreet);
            if (StringUtils.isNotBlank(addressSuburb)) shopMaster.setAddressSuburb(addressSuburb);
            if (StringUtils.isNotBlank(addressState)) shopMaster.setAddressState(addressState);
            if (StringUtils.isNotBlank(addressPostcode)) shopMaster.setAddressPostcode(addressPostcode);


            // 배달비 계산을 위한 주소가 빈경우 기본 비지니스 주소를 넣는다
            if (StringUtils.isBlank(forDeliverCalcAddressStreet)
                    || StringUtils.isBlank(forDeliverCalcAddressSuburb)
                    || StringUtils.isBlank(forDeliverCalcAddressState)
                    || StringUtils.isBlank(forDeliverCalcAddressPostcode)
                    ) {
                shopMaster.setForDeliverCalcAddressStreet(shopMaster.getAddressStreet());
                shopMaster.setForDeliverCalcAddressSuburb(shopMaster.getAddressSuburb());
                shopMaster.setForDeliverCalcAddressState(shopMaster.getAddressState());
                shopMaster.setForDeliverCalcAddressPostcode(shopMaster.getAddressPostcode());
            }

            if (StringUtils.isNotBlank(deliveryService)) shopMaster.setDeliveryService(deliveryService);
            if (StringUtils.isNotBlank(deliveryFeePerKm)) shopMaster.setDeliveryFeePerKm(deliveryFeePerKm);
            if (StringUtils.isNotBlank(deliveryBaseCharge)) shopMaster.setDeliveryBaseCharge(Float.parseFloat(deliveryBaseCharge));
            if (StringUtils.isNotBlank(minimumPurchaseAmount)) shopMaster.setMinimumPurchaseAmount(Float.parseFloat(minimumPurchaseAmount));
            if (StringUtils.isNotBlank(maximumPurchaseAmount)) shopMaster.setMaximumPurchaseAmount(Float.parseFloat(maximumPurchaseAmount));
            if (StringUtils.isNotBlank(discountRateValue)) shopMaster.setDiscountRateValue(Float.parseFloat(discountRateValue));
            shopMaster.setDiscountMethod("RATE");
            shopMaster.setDiscountFixedAmount(0.00f);

            GeocodingResult geoResult = null;
            // 20-24 Ellingworth Parade, Box Hill VIC 3128
            String marketGmapLatitude = "-37.820836";
            String marketGmapLongitude = "145.125625";


            try {
                // 기본 비지니스 장소 위치
                geoResult = melfoodGoogleMapService.lookupGMap(shopMaster.getAddressStreet() + " " + shopMaster.getAddressSuburb() + " " + shopMaster.getAddressState() + " " + shopMaster.getAddressPostcode());
                if (geoResult != null) {
                    marketGmapLatitude = Double.toString(geoResult.geometry.location.lat);
                    marketGmapLongitude = Double.toString(geoResult.geometry.location.lng);

                    shopMaster.setAddressLatitude(marketGmapLatitude);
                    shopMaster.setAddressLongitude(marketGmapLongitude);
                    shopMaster.setAddressFormattedAddress(geoResult.formattedAddress);
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("[" + shopMaster.getAddressStreet() + " " + shopMaster.getAddressSuburb() + " " + shopMaster.getAddressState() + " " + shopMaster.getAddressPostcode() + "] 에대한 죄표를 구하는데 실패하였습니다. :" + e.getMessage());
            }

            try {
                // 배송비 위치
                geoResult = melfoodGoogleMapService.lookupGMap(shopMaster.getForDeliverCalcAddressStreet() + " " + shopMaster.getForDeliverCalcAddressSuburb() + " " + shopMaster.getForDeliverCalcAddressState() + " " + shopMaster.getForDeliverCalcAddressPostcode());
                if (geoResult != null) {
                    marketGmapLatitude = Double.toString(geoResult.geometry.location.lat);
                    marketGmapLongitude = Double.toString(geoResult.geometry.location.lng);

                    shopMaster.setForDeliverCalcAddressLatitude(marketGmapLatitude);
                    shopMaster.setForDeliverCalcAddressLongitude(marketGmapLongitude);
                    shopMaster.setForDeliverCalcAddressFormattedAddress(geoResult.formattedAddress);
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("[" + shopMaster.getForDeliverCalcAddressStreet() + " " + shopMaster.getForDeliverCalcAddressSuburb() + " " + shopMaster.getForDeliverCalcAddressState() + " " + shopMaster.getForDeliverCalcAddressPostcode() + "] 에대한 죄표를 구하는데 실패하였습니다. :" + e.getMessage());
            }


            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
                updateCnt = shopMasterService.insertShopMaster(shopMaster);

            } else {
                updateCnt = shopMasterService.modifyShopMaster(shopMaster);
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


}
