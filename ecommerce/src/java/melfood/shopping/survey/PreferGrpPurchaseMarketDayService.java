package melfood.shopping.survey;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Steven on 14/7/17.
 */
public interface PreferGrpPurchaseMarketDayService {

    public Integer insertPreferGrpPurchaseMarketDay(String answerValue, String answerType, HttpServletRequest request) throws Exception;

}
