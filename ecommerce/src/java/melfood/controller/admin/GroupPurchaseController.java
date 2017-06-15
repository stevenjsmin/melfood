package melfood.controller.admin;

import melfood.framework.system.BaseController;
import melfood.shopping.grouppurchase.GroupPurchaseProductService;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    GroupPurchaseService groupPurchaseService;

    @Autowired
    GroupPurchaseProductService groupPurchaseProductService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/grouppurchase/main");

// TODO : 검색조건에선택
//        오가나이저
//        서버브선택 (주 + 서버브)
//        할일방법선택
//        마스터가 체크한여부
//        판매정지여부
//        공동구매시작일
//        공동구매종료일

//        User seller = new User();
//        seller.setUseYn("Y");
//
//        Properties htmlProperty = new Properties();
//        List<Option> contractorOptions = contractInfoService.getSellers(seller);
//        htmlProperty = new Properties("sellerId");
//        htmlProperty.setCssClass("form-control");
//        mav.addObject("cbxSeller", contractInfoService.generateCmbx(contractorOptions, htmlProperty, true));
//
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        String searchDateFrom = df.format(cal.getTime());
//        mav.addObject("defaultSearchDateFrom", searchDateFrom);
//
//        List<Option> isPickupOptions = codeService.getValueCmbxOptions("DELIVER_MGT", "DELIVER_METHOD_ISPICKUP");
//        htmlProperty = new Properties("isPickup");
//        htmlProperty.setCssClass("form-control");
//        mav.addObject("cbxIsPickup", codeService.generateCmbx(isPickupOptions, htmlProperty));

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
        String orderingStartDt = request.getParameter("orderingStartDt");
        String orderingEndDt = request.getParameter("orderingEndDt");
        String stopSelling = request.getParameter("stopSelling");
        String marketAddressSuburb = request.getParameter("marketAddressSuburb");

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        if (StringUtils.isBlank(orderingStartDt)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            orderingStartDt = df.format(cal.getTime());
        }
        groupPurchase.setOrderingStartDt(orderingStartDt);
        if (!StringUtils.isBlank(orderingStartDt)) groupPurchase.setOrderingEndDt(orderingEndDt);

        groupPurchase.setPurchaseOrganizer(purchaseOrganizer);
        groupPurchase.setStopSelling(stopSelling);
        groupPurchase.setMarketAddressSuburb(marketAddressSuburb);

        Integer totalCount = 0;
        List<GroupPurchase> list = groupPurchaseService.getGroupPurchases(groupPurchase);
        totalCount = groupPurchaseService.getTotalCntForGroupPurchases(groupPurchase);

        model.put("totalCount", totalCount);
        model.put("list", list);

        return model;
    }
}
