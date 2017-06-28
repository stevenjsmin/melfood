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
import melfood.framework.system.BaseController;
import melfood.framework.user.User;
import melfood.shopping.delivery.DeliveryCalendarService;
import melfood.shopping.grouppurchase.GroupPurchaseProductService;
import melfood.shopping.grouppurchase.GroupPurchaseService;
import melfood.shopping.grouppurchase.dto.GroupPurchase;
import melfood.shopping.grouppurchase.dto.GroupPurchaseProduct;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author steven.min
 */
@Controller
@RequestMapping("/grouppurchase")
public class GroupPurchaseOrderMainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupPurchaseOrderMainController.class);

    @Autowired
    private DeliveryCalendarService deliveryCalendarService;

    @Autowired
    private GroupPurchaseService groupPurchaseService;

    @Autowired
    private GroupPurchaseProductService groupPurchaseProductService;

    @RequestMapping("/Main")
    public ModelAndView orderProduct(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        ModelAndView mav = new ModelAndView("tiles/customer/grouppurchase/main");
        String groupPurchaseId = request.getParameter("groupPurchaseId");
        if (StringUtils.isBlank(groupPurchaseId)) throw new Exception("조회하고자하는 공동구매ID 값이 없습니다. :");

        GroupPurchase groupPurchase = null;
        User organizer = null;
        List<GroupPurchaseProduct> groupPurchaseProducts = null;

        try {
            // 공동구매 기본정보
            groupPurchase = groupPurchaseService.getGroupPurchase(Integer.parseInt(groupPurchaseId));
            // 공동구매를 진행하는 사람의 상세정보
            organizer = userService.getUserInfo(groupPurchase.getPurchaseOrganizer());
            // 공동구매 상품정보(목록)
            groupPurchaseProducts = groupPurchaseProductService.getGroupPurchaseProducts(groupPurchaseId);

            // TODO : 배송이 가능한 경우, 배송가능 목록 조회
            String customerUserId = sessionUser.getUser().getUserId();
            User customerUser = userService.getUserInfo(customerUserId);
            // getSuburbs using customerUser;

            mav.addObject("purchaseOrganizer", organizer);
            mav.addObject("groupPurchase", groupPurchase);
            mav.addObject("groupPurchaseProducts", groupPurchaseProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mav;
    }

}
