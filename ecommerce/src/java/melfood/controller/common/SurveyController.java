package melfood.controller.common;

import melfood.framework.system.BaseController;
import melfood.shopping.survey.PreferGrpPurchaseMarketDayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steven on 11/7/17.
 */
@Controller
@RequestMapping("/survey")
public class SurveyController extends BaseController {

    @Autowired
    PreferGrpPurchaseMarketDayService preferGrpPurchaseMarketDayService;

    @RequestMapping(value = "/preferNextGroupPurchaseDay", produces = "application/json")
    @ResponseBody
    public Map<String, Object> suburbCmbx(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String whatday = request.getParameter("whatday");
        String ampm = request.getParameter("ampm");

        try {
            if (StringUtils.isNotBlank(whatday)
                    && (StringUtils.equals(whatday, "월")
                    || StringUtils.equals(whatday, "화")
                    || StringUtils.equals(whatday, "수")
                    || StringUtils.equals(whatday, "목")
                    || StringUtils.equals(whatday, "금")
                    || StringUtils.equals(whatday, "토")
                    || StringUtils.equals(whatday, "일"))
                    ) {
                preferGrpPurchaseMarketDayService.insertPreferGrpPurchaseMarketDay(whatday, "요일", request);
            }
            if (StringUtils.isNotBlank(ampm)
                    && (StringUtils.equals(ampm, "오전")
                    || StringUtils.equals(ampm, "오후"))
                    ) {
                preferGrpPurchaseMarketDayService.insertPreferGrpPurchaseMarketDay(ampm, "오전.오후", request);
            }

            model.put("resultCode", "0");
            model.put("message", "");

        } catch (Exception e) {
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


}
