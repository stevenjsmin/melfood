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
import melfood.framework.system.BaseController;
import melfood.shopping.product.Product;
import melfood.shopping.product.ProductService;
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
 * 전문샵 메인 컨트롤러 <br>
 */
@Controller
@RequestMapping("/shop")
public class ShopMainController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ShopMainController.class);

    @Autowired
    ProductService productService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/shop/main");
        return mav;
    }


    @RequestMapping(value = "/products", produces = "application/json")
    @ResponseBody
    public Map<String, Object> products(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        // String userId = sessionUser.getUser().getUserId();
        String seller = request.getParameter("seller");

        Product product = new Product();
        product.setSeller(seller);

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

}
