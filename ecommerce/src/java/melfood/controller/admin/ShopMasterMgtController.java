/**
 * 2016 ContractMgtController.java
 * Created by steven.min
 * <p>
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.admin;

import com.google.maps.model.GeocodingResult;
import melfood.framework.Ctx;
import melfood.framework.MelfoodConstants;
import melfood.framework.attachement.AttachmentFile;
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

    @RequestMapping("/detailInfo")
    public ModelAndView shopMaster(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/shopmaster/detailInfo");

        String shopId = request.getParameter("shopId");
        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
        if(shopMaster.getShopGateImageFileId() != null) {

        }
        if (shopMaster.getShopGateImageFileId() != null) {
            AttachmentFile receiptFile = attachmentFileService.getAttachmentFile(new AttachmentFile(shopMaster.getShopGateImageFileId()));
            if (receiptFile == null) {
                shopMasterService.removeShopGateImageFileInfo(Integer.parseInt(shopId));
                mav.addObject("attachedFileNo", null);
                mav.addObject("attachedFileName", null);
                mav.addObject("attachedFileCreateDate", null);
            } else {
                mav.addObject("attachedFileNo", receiptFile.getFileId());
                mav.addObject("attachedFileName", receiptFile.getFileName());
                mav.addObject("attachedFileCreateDate", receiptFile.getCreateDatetime());
            }
        } else {
            mav.addObject("attachedFileNo", null);
            mav.addObject("attachedFileName", null);
            mav.addObject("attachedFileCreateDate", null);
        }

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
        mav.addObject("shopMaster", shopMaster);

        List<Option> templateOptions = shopMasterService.getShopTemplateOptions(new ShopTemplate(), shopMaster.getTemplateId());
        htmlProperty = new Properties("templateId");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxTemplateId", codeService.generateCmbx(templateOptions, htmlProperty, true));

        List<Option> shopOwnerOptions = contractInfoService.getAllSellers(shopMaster.getShopOwner());
        htmlProperty = new Properties("shopOwner");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxShopOwner", contractInfoService.generateCmbx(shopOwnerOptions, htmlProperty, true));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", shopMaster.getAddressState());
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        List<Option> forDeliverCalcAddressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", shopMaster.getForDeliverCalcAddressState());
        htmlProperty = new Properties("forDeliverCalcAddressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxForDeliverCalcAddressState", codeService.generateCmbx(forDeliverCalcAddressStateOptions, htmlProperty));

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
            } else {
                shopMaster.setForDeliverCalcAddressStreet(forDeliverCalcAddressStreet);
                shopMaster.setForDeliverCalcAddressSuburb(forDeliverCalcAddressSuburb);
                shopMaster.setForDeliverCalcAddressState(forDeliverCalcAddressState);
                shopMaster.setForDeliverCalcAddressPostcode(forDeliverCalcAddressPostcode);
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
                updateCnt = Integer.parseInt(shopId);
            }

            model.put("shopId", updateCnt); // updateCnt --> 추가된 Shop ID

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/deleteShopMaster", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteShopMaster(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String shopId = request.getParameter("shopId");
        if (StringUtils.isBlank(shopId)) {
            throw new Exception("[shopId]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        try {
            shopMasterService.deleteShopMaster(new ShopMaster(shopId));

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return model;

    }


    @RequestMapping(value = "/shopGateImageUpload", produces = "application/json")
    @ResponseBody
    public Map<String, Object> shopGateImageUpload(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Integer uploadedFileNo = 0;

        String subDirectory = Ctx.getVar("SHOP.GATEIMAGE.TEMP.UPLOAD.DIR");

        try {
            String userId = sessionUser.getUser().getUserId();
            String shopId = request.getParameter("shopId"); // Order Id

            ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
            if (shopMaster == null) throw new Exception("샵정보가 존재하지 않아, 이미지를 첨부 할 수 없습니다.");

            Integer imageFileId = shopMaster.getShopGateImageFileId();

            // 이미 등록된 파일이 있다면 삭제해준다.
            if (imageFileId != null) {
                attachmentFileService.deleteAttachmentFile(new AttachmentFile(imageFileId));
            }
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 지워준다.

            // 파일을 임시 업로드 위치에 놓는다.
            attachmentFileService.uploadFile(request, subDirectory);
            Integer insertedFileId = shopMasterService.transferToAttachementFileDb(Integer.parseInt(shopId));

            // 기존의 파일들을 (다시한번)지워준다.
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);

            AttachmentFile attachedFile = attachmentFileService.getAttachmentFile(new AttachmentFile(insertedFileId));

            model.put("attachedFileNo", insertedFileId);
            model.put("attachedFileName", attachedFile.getFileName());
            model.put("attachedFileCreateDate", attachedFile.getCreateDatetime());
            model.put("resultCode", "0");
            model.put("message", "File uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);

            model.put("receiptFileNo", null);
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }
        return model;

    }

    @RequestMapping(value = "/shopGateImageRemove", produces = "application/json")
    @ResponseBody
    public Map<String, Object> shopGateImageRemove(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        String userId = sessionUser.getUser().getUserId();
        String shopId = request.getParameter("shopId"); // Order Id

        ShopMaster shopMaster = shopMasterService.getShopMaster(new ShopMaster(shopId));
        if (shopMaster == null) throw new Exception("샵정보가 존재하지 않아, 영수증 첨부를 삭제 할 수 없습니다.");

        try {
            // 물리적인 위치의 파일을 삭제하고, 첨부파일을 관리하는 테이블에서 또한 삭제해 준다.
            attachmentFileService.deleteAttachmentFile(new AttachmentFile(shopMaster.getShopGateImageFileId()));

            // 주문테이블의 영수증파일 아이디컬럼을 Null 처리해준다.
            shopMasterService.removeShopGateImageFileInfo(Integer.parseInt(shopId));

            model.put("resultCode", "0");
            model.put("message", "1개 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


}
