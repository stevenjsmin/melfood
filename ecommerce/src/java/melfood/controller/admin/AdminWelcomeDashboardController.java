package melfood.controller.admin;

import melfood.framework.system.BaseController;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steven on 14/7/17.
 */
@Controller
@RequestMapping("/admin/dashboard")
public class AdminWelcomeDashboardController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AdminWelcomeDashboardController.class);

    @Autowired
    GroupPurchaseService groupPurchaseService;

    @RequestMapping(value = "/getHeatmapData", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getHeatmapData(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        GroupPurchase groupPurchase = new GroupPurchase();

        // For Pagination
        groupPurchase.setPagenationPage(0);
        groupPurchase.setPagenationPageSize(55555);

        List<GroupPurchase> groupPurchaseList = groupPurchaseService.getGroupPurchases(groupPurchase);

        List<GroupPurchase> list = new ArrayList<GroupPurchase>();
        for (GroupPurchase purchase : groupPurchaseList) {
            if (StringUtils.isNotBlank(purchase.getMarketGmapLatitude()) && StringUtils.isNotBlank(purchase.getMarketGmapLongitude())) {
                list.add(purchase);
            }
        }

        model.put("list", list);

        return model;
    }
}
