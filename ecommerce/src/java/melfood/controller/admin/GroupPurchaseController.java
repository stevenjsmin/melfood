package melfood.controller.admin;

import melfood.framework.Ctx;
import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.HtmlCodeGenerator;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.shopping.grouppurchase.GroupPurchaseProductService;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import melfood.shopping.product.ProductImage;
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
 * Created by Steven on 15/6/17.
 */
@RequestMapping("/admin/grouppurchase")
@Controller
public class GroupPurchaseController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupPurchaseController.class);


    @Autowired
    GroupPurchaseService groupPurchaseService;

    @Autowired
    GroupPurchaseProductService groupPurchaseProductService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/grouppurchase/main");

        // 공동구매 주관자 설정
        List<Option> purchaseOrganizerOptions = groupPurchaseService.getOrganizers();
        String htmlForOrganizerCbx = HtmlCodeGenerator.generateComboboxForOptions("purchaseOrganizer", purchaseOrganizerOptions);
        mav.addObject("cbxPurchaseOrganizer", htmlForOrganizerCbx);

        // 공동구매 정지여부 : 기본값 : N
//        List<Option> stopSellingOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "IS_STOP_SELLING", "Y");
//        String htmlForStopSellingCbx = HtmlCodeGenerator.generateComboboxForOptions("stopSelling", stopSellingOptions);
//        mav.addObject("cbxStopSelling", htmlForStopSellingCbx);

        // 공동구매 장소를 위한 State 설정 콤보박스 : 기본값-빅토리아
        List<Option> marketAddressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
        String htmlForMarketAddressStateCbx = HtmlCodeGenerator.generateComboboxForOptions("marketAddressState", marketAddressStateOptions);
        mav.addObject("cbxAddressState", htmlForMarketAddressStateCbx);

        // 할인방법 콤보박스 설정 : 기본값 : %
        List<Option> discountMethodOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DISCOUNT_METHOD");
        String htmlForDiscountMethodCbx = HtmlCodeGenerator.generateComboboxForOptions("discountMethod", discountMethodOptions);
        mav.addObject("cbxDiscountMethod", htmlForDiscountMethodCbx);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String to_yyyymmdd = df.format(cal.getTime());
        mav.addObject("orderStartDt", (to_yyyymmdd + " 12:00 AM"));

        return mav;
    }

    @RequestMapping(value = "/getGroupPurchases", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getDeliveryCalendars(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        GroupPurchase groupPurchase = new GroupPurchase();

        // For Pagination
        groupPurchase.setPagenationPage(getPage(request));
        groupPurchase.setPagenationPageSize(getPageSize(request));

        String purchaseOrganizer = request.getParameter("purchaseOrganizer");
        String orderStartDt = request.getParameter("orderStartDt");
        String orderEndDt = request.getParameter("orderEndDt");
        String stopSelling = request.getParameter("stopSelling");
        String marketAddressSuburb = request.getParameter("marketAddressSuburb");

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        if (StringUtils.isBlank(orderStartDt)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            orderStartDt = df.format(cal.getTime());
        }
        groupPurchase.setOrderStartDt(orderStartDt);
        if (StringUtils.isNotBlank(orderEndDt)) groupPurchase.setOrderEndDt(orderEndDt);

        if (StringUtils.isNotBlank(purchaseOrganizer)) groupPurchase.setPurchaseOrganizer(purchaseOrganizer);
        if (StringUtils.isNotBlank(stopSelling)) groupPurchase.setStopSelling(stopSelling);
        if (StringUtils.isNotBlank(marketAddressSuburb)) groupPurchase.setMarketAddressSuburb(marketAddressSuburb);

        Integer totalCount = 0;
        List<GroupPurchase> list = groupPurchaseService.getGroupPurchases(groupPurchase);
        totalCount = groupPurchaseService.getTotalCntForGroupPurchases(groupPurchase);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }

    @RequestMapping("/registGroupPurchaseForm")
    public ModelAndView registGroupPurchaseForm() throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/grouppurchase/regist");

        // 공동구매 주관자 설정
        List<Option> purchaseOrganizerOptions = groupPurchaseService.getOrganizers();
        String htmlForOrganizerCbx = HtmlCodeGenerator.generateComboboxForOptions("purchaseOrganizer", purchaseOrganizerOptions);
        mav.addObject("cbxPurchaseOrganizer", htmlForOrganizerCbx);

        // 공동구매 정지여부 : 기본값 : N
        List<Option> stopSellingOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "IS_STOP_SELLING", "Y");
        String htmlForStopSellingCbx = HtmlCodeGenerator.generateComboboxForOptions("stopSelling", stopSellingOptions);
        mav.addObject("cbxStopSelling", htmlForStopSellingCbx);

        // 공동구매 장소를 위한 State 설정 콤보박스 : 기본값-빅토리아
        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", "VIC");
        String htmlForMarketAddressStateCbx = HtmlCodeGenerator.generateComboboxForOptions("marketAddressState", addressStateOptions);
        mav.addObject("cbxAddressState", htmlForMarketAddressStateCbx);

        // 할인방법 콤보박스 설정 : 기본값 : %
        List<Option> discountMethodOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DISCOUNT_METHOD", "RATE");
        Properties htmlProperty = new Properties();
        htmlProperty = new Properties("discountMethod");
        htmlProperty.setCssClass("form-control");
        htmlProperty.setOnchange("changeDiscountMethod(this)");
        mav.addObject("cbxDiscountMethod", codeService.generateCmbx(discountMethodOptions, htmlProperty));

        return mav;
    }

    @RequestMapping("/updateGroupPurchaseForm")
    public ModelAndView updateGroupPurchaseForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/grouppurchase/update");

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        GroupPurchase groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));

        // 공동구매 주관자 설정
        List<Option> purchaseOrganizerOptions = groupPurchaseService.getOrganizers(groupPurchase.getPurchaseOrganizer());
        String htmlForOrganizerCbx = HtmlCodeGenerator.generateComboboxForOptions("purchaseOrganizer", purchaseOrganizerOptions);
        mav.addObject("cbxPurchaseOrganizer", htmlForOrganizerCbx);

        // 공동구매 정지여부 : 기본값 : N
        List<Option> stopSellingOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "IS_STOP_SELLING", groupPurchase.getStopSelling());
        String htmlForStopSellingCbx = HtmlCodeGenerator.generateComboboxForOptions("stopSelling", stopSellingOptions);
        mav.addObject("cbxStopSelling", htmlForStopSellingCbx);

        // 공동구매 장소를 위한 State 설정 콤보박스 : 기본값-빅토리아
        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", groupPurchase.getMarketAddressState());
        String htmlForMarketAddressStateCbx = HtmlCodeGenerator.generateComboboxForOptions("marketAddressState", addressStateOptions);
        mav.addObject("cbxAddressState", htmlForMarketAddressStateCbx);

        // 할인방법 콤보박스 설정 : 기본값 : %
        List<Option> discountMethodOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DISCOUNT_METHOD", groupPurchase.getDiscountMethod());
        Properties htmlProperty = new Properties();
        htmlProperty = new Properties("discountMethod");
        htmlProperty.setCssClass("form-control");
        htmlProperty.setOnchange("changeDiscountMethod(this)");
        mav.addObject("cbxDiscountMethod", codeService.generateCmbx(discountMethodOptions, htmlProperty));

        mav.addObject("groupPurchase", groupPurchase);

        return mav;
    }

    @RequestMapping("/groupPurchaseDetailInfo")
    public ModelAndView groupPurchaseDetailInfo(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/grouppurchase/detail");

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        GroupPurchase groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));

        // 공동구매 주관자 설정
        List<Option> purchaseOrganizerOptions = groupPurchaseService.getOrganizers(groupPurchase.getPurchaseOrganizer());
        String htmlForOrganizerCbx = HtmlCodeGenerator.generateComboboxForOptions("purchaseOrganizer", purchaseOrganizerOptions);
        mav.addObject("cbxPurchaseOrganizer", htmlForOrganizerCbx);

        // 공동구매 정지여부 : 기본값 : N
        List<Option> contractStatusOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "IS_STOP_SELLING", groupPurchase.getStopSelling());
        String htmlForStopSellingCbx = HtmlCodeGenerator.generateComboboxForOptions("stopSelling", purchaseOrganizerOptions);
        mav.addObject("cbxStopSelling", htmlForStopSellingCbx);

        // 공동구매 장소를 위한 State 설정 콤보박스 : 기본값-빅토리아
        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", groupPurchase.getMarketAddressState());
        String htmlForMarketAddressStateCbx = HtmlCodeGenerator.generateComboboxForOptions("marketAddressState", addressStateOptions);
        mav.addObject("cbxAddressState", htmlForMarketAddressStateCbx);

        // 할인방법 콤보박스 설정 : 기본값 : %
        List<Option> discountMethodOptions = codeService.getValueCmbxOptions("GRP_PURCHASE", "DISCOUNT_METHOD", groupPurchase.getDiscountMethod());
        String htmlForDiscountMethodCbx = HtmlCodeGenerator.generateComboboxForOptions("marketAddressState", addressStateOptions);
        mav.addObject("cbxDiscountMethod", htmlForDiscountMethodCbx);

        mav.addObject("groupPurchase", groupPurchase);

        return mav;
    }

    @RequestMapping(value = "/saveGroupPurchase", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveGroupPurchase(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String actionMode = request.getParameter("actionMode");
        if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        String groupPurchaseTitle = request.getParameter("groupPurchaseTitle");
        String groupPurchaseSubtitle = request.getParameter("groupPurchaseSubtitle");
        String purchaseOrganizer = request.getParameter("purchaseOrganizer");
        String orderStartDt = request.getParameter("orderStartDt");
        String orderEndDt = request.getParameter("orderEndDt");
        String stopSelling = request.getParameter("stopSelling");
        String stopSellingReason = request.getParameter("stopSellingReason");
        String marketAddressStreet = request.getParameter("marketAddressStreet");
        String marketAddressSuburb = request.getParameter("marketAddressSuburb");
        String marketAddressPostcode = request.getParameter("marketAddressPostcode");
        String marketAddressState = request.getParameter("marketAddressState");
        String marketAddressComment = request.getParameter("marketAddressComment");
        String marketOpenStartDt = request.getParameter("marketOpenStartDt");
        String marketOpenEndDt = request.getParameter("marketOpenEndDt");
        String minimumPurchaseAmount = request.getParameter("minimumPurchaseAmount");
        String discountMethod = request.getParameter("discountMethod");
        String discountFixedAmount = request.getParameter("discountFixedAmount");
        String discountRateValue = request.getParameter("discountRateValue");
        String groupPurchaseNotice = request.getParameter("groupPurchaseNotice");
        String creator = sessionUser.getUser().getUserId();

        // 특별히 지정된 값이 없는 경우 "공동구매 개시" 상태로 처리
        if (StringUtils.isBlank(stopSelling)) {
            stopSelling = "Y";
            stopSellingReason = null;
        }

        try {

            GroupPurchase groupPurchase = null;
            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_MODIFY)) {
                if (StringUtils.isBlank(groupPurchaseId)) {
                    throw new Exception("[groupPurchaseId]  이항목(들)은 빈 값이 될 수 없습니다.");
                } else {
                    groupPurchase = new GroupPurchase(groupPurchaseId);
                }
            } else {
                groupPurchase = new GroupPurchase();
            }

            if (StringUtils.isNotBlank(groupPurchaseTitle)) groupPurchase.setGroupPurchaseTitle(groupPurchaseTitle);
            if (StringUtils.isNotBlank(groupPurchaseSubtitle)) groupPurchase.setGroupPurchaseSubtitle(groupPurchaseSubtitle);
            if (StringUtils.isNotBlank(purchaseOrganizer)) groupPurchase.setPurchaseOrganizer(purchaseOrganizer);
            if (StringUtils.isNotBlank(orderStartDt)) groupPurchase.setOrderStartDt(orderStartDt);
            if (StringUtils.isNotBlank(orderEndDt)) groupPurchase.setOrderEndDt(orderEndDt);
            if (StringUtils.isNotBlank(stopSelling)) groupPurchase.setStopSelling(stopSelling);
            if (StringUtils.isNotBlank(stopSellingReason)) groupPurchase.setStopSellingReason(stopSellingReason);
            if (StringUtils.isNotBlank(marketAddressStreet)) groupPurchase.setMarketAddressStreet(marketAddressStreet);
            if (StringUtils.isNotBlank(marketAddressSuburb)) groupPurchase.setMarketAddressSuburb(marketAddressSuburb);
            if (StringUtils.isNotBlank(marketAddressPostcode)) groupPurchase.setMarketAddressPostcode(marketAddressPostcode);
            if (StringUtils.isNotBlank(marketAddressState)) groupPurchase.setMarketAddressState(marketAddressState);
            if (StringUtils.isNotBlank(marketAddressComment)) groupPurchase.setMarketAddressComment(marketAddressComment);
            if (StringUtils.isNotBlank(marketOpenStartDt)) groupPurchase.setMarketOpenStartDt(marketOpenStartDt);
            if (StringUtils.isNotBlank(marketOpenEndDt)) groupPurchase.setMarketOpenEndDt(marketOpenEndDt);
            if (StringUtils.isNotBlank(minimumPurchaseAmount)) groupPurchase.setMinimumPurchaseAmount(minimumPurchaseAmount);
            if (StringUtils.isNotBlank(discountMethod)) groupPurchase.setDiscountMethod(discountMethod);

            if (StringUtils.isNotBlank(discountMethod)) {
                if (StringUtils.equalsIgnoreCase(discountMethod, "FIXED")) {
                    groupPurchase.setDiscountMethod("FIXED");
                    groupPurchase.setDiscountRateValue(0.0f);
                    if (StringUtils.isNotBlank(discountFixedAmount))
                        groupPurchase.setDiscountFixedAmount(Float.parseFloat(discountFixedAmount));
                } else {
                    groupPurchase.setDiscountMethod("RATE");
                    groupPurchase.setDiscountRateValue(0.0f);
                    if (StringUtils.isNotBlank(discountRateValue))
                        groupPurchase.setDiscountRateValue(Float.parseFloat(discountRateValue));
                }
            } else {
                discountMethod = "RATE";
                discountRateValue = "0.00";
                discountFixedAmount = "0.00";
            }


            if (StringUtils.isNotBlank(groupPurchaseNotice)) groupPurchase.setGroupPurchaseNotice(groupPurchaseNotice);
            if (StringUtils.isNotBlank(creator)) groupPurchase.setCreator(creator);


            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
                updateCnt = groupPurchaseService.insertGroupPurchase(groupPurchase);

            } else {
                updateCnt = groupPurchaseService.modifyGroupPurchase(groupPurchase);
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

    @RequestMapping(value = "/deleteGroupPurchase", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteGroupPurchase(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        if (StringUtils.isBlank(groupPurchaseId)) {
            throw new Exception("[groupPurchaseId]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        try {
            // 공동구매 마스터정보, 등록된 상품정보, 등록된 이미지정보(물리적파일포함을 모두 삭제한다.
            ProductImage productImage = new ProductImage(groupPurchaseId);
            productImage.setPagenationPage(0);
            productImage.setPagenationPageSize(99999);
            List<ProductImage> imageList = groupPurchaseService.getProductImages(productImage);

            for (ProductImage image : imageList) {
                groupPurchaseService.deleteProductImage(Integer.parseInt(groupPurchaseId), image.getImageSeq());
            }
        } catch (Exception e) {
            logger.warn("공동구매에 소속된 이미지를 삭제하는데 문제가 발생하였습니다[groupPurchaseId:" + groupPurchaseId + "]" + e.getMessage());
            e.printStackTrace();
        }

        groupPurchaseService.deleteGroupPurchase(new GroupPurchase(groupPurchaseId));

        return model;
    }

    @RequestMapping(value = "/image/uploadFile", produces = "application/json")
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        String subDirectory = Ctx.getVar("GRPPURCHASE.PRODUCTIMAGE.TEMP.UPLOAD.DIR");
        String groupPurchaseId = request.getParameter("groupPurchaseId");

        try {
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 지워준다.
            attachmentFileService.uploadFile(request, subDirectory);

            List<ProductImage> productImages = new ArrayList<ProductImage>();
            productImages = groupPurchaseService.transferFileToAttachementFileDb(Integer.parseInt(groupPurchaseId), sessionUser.getUser().getUserId());

            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 (다시한번)지워준다.

            model.put("productImages", productImages);
            model.put("resultCode", "0");
            model.put("message", "File uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            model.put("productImages", null);
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }
        return model;

    }

    /**
     * 파일을 업로드후 임시디렉토리의 파일을 삭제하는데 사용된다
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/image/removeFile", produces = "application/json")
    @ResponseBody
    public Map<String, Object> removeFile(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String subDirectory = Ctx.getVar("GRPPURCHASE.PRODUCTIMAGE.TEMP.UPLOAD.DIR");

        try {
            attachmentFileService.removeFile(request, subDirectory);

            model.put("resultCode", "0");
            model.put("message", "File removed successfully");

        } catch (Exception e) {
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }
        return model;
    }

    @RequestMapping(value = "/image/productImages", produces = "application/json")
    @ResponseBody
    public Map<String, Object> productImages(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        String groupPurchaseId = request.getParameter("groupPurchaseId");

        ProductImage productImage = new ProductImage(groupPurchaseId);

        // For Pagination
        productImage.setPagenationPage(0);
        productImage.setPagenationPageSize(99999);

        Integer totalCount = 0;
        List<ProductImage> list = groupPurchaseService.getProductImages(productImage);
        totalCount = groupPurchaseService.getTotalCntForProductImages(productImage);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }

    @RequestMapping("/image/productImageViewer")
    public ModelAndView productImageViewer(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/product/productmgt/image/imageViewer");
        String groupPurchaseId = request.getParameter("groupPurchaseId");

        if (StringUtils.isBlank(groupPurchaseId)) {
            throw new Exception("[groupPurchaseId]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        ProductImage productImage = new ProductImage(groupPurchaseId);

        // For Pagination
        productImage.setPagenationPage(0);
        productImage.setPagenationPageSize(99999);

        List<ProductImage> list = groupPurchaseService.getProductImages(productImage);

        mav.addObject("imageList", list);

        return mav;
    }


    @RequestMapping(value = "/image/deleteProductImage", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteProductImage(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        int updateCnt = 0;

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        String imageSeq = request.getParameter("imageSeq");

        if (StringUtils.isBlank(groupPurchaseId) || StringUtils.isBlank(imageSeq)) {
            throw new Exception("[groupPurchaseId | 이미지 SEQ]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        try {
            updateCnt = groupPurchaseService.deleteProductImage(Integer.parseInt(groupPurchaseId), Integer.parseInt(imageSeq));

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/product/addProduct", produces = "application/json")
    @ResponseBody
    public Map<String, Object> addGroupPurchaseProduct(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        String productId = request.getParameter("productId");

        if (StringUtils.isBlank(groupPurchaseId) || StringUtils.isBlank(productId)) {
            throw new Exception("[groupPurchaseId || productId ]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        int updateCnt = 0;
        updateCnt = groupPurchaseProductService.deleteGroupPurchaseProduct(Integer.parseInt(groupPurchaseId), Integer.parseInt(productId));

        return model;
    }


    @RequestMapping(value = "/product/getProducts", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getGroupPurchaseProducts(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        GroupPurchase groupPurchase = new GroupPurchase();

        // For Pagination
        groupPurchase.setPagenationPage(getPage(request));
        groupPurchase.setPagenationPageSize(getPageSize(request));

        String groupPurchaseId = request.getParameter("groupPurchaseId");

        if (StringUtils.isBlank(groupPurchaseId)) {
            throw new Exception("[groupPurchaseId]  이항목(들)은 빈 값이 될 수 없습니다.");
        }
        GroupPurchaseProduct purchaseProduct = new GroupPurchaseProduct(groupPurchaseId);

        Integer totalCount = 0;
        List<GroupPurchaseProduct> list = groupPurchaseProductService.getGroupPurchaseProducts(purchaseProduct);
        totalCount = groupPurchaseProductService.getTotalCntForGroupPurchaseProducts(purchaseProduct);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }

    @RequestMapping(value = "/product/deleteProduct", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteGroupPurchaseProduct(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        String productId = request.getParameter("productId");

        if (StringUtils.isBlank(groupPurchaseId) || StringUtils.isBlank(productId)) {
            throw new Exception("[groupPurchaseId || productIdproductId ]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        int updateCnt = 0;
        updateCnt = groupPurchaseProductService.deleteGroupPurchaseProduct(Integer.parseInt(groupPurchaseId), Integer.parseInt(productId));

        return model;
    }

    @RequestMapping(value = "/product/deleteProducts", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteGroupPurchaseProducts(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

        String groupPurchaseId = request.getParameter("groupPurchaseId");
        if (StringUtils.isBlank(groupPurchaseId)) {
            throw new Exception("[groupPurchaseId]  이항목(들)은 빈 값이 될 수 없습니다.");
        }

        String[] productIds = request.getParameterValues("productId");
        List<GroupPurchaseProduct> purchaseProducts = new ArrayList<GroupPurchaseProduct>();
        for (String prodId : productIds) {
            purchaseProducts.add(new GroupPurchaseProduct(groupPurchaseId, prodId));
        }

        if (purchaseProducts.size() > 0) {
            groupPurchaseProductService.deleteGroupPurchaseProducts(purchaseProducts);
        }

        return model;
    }

}
