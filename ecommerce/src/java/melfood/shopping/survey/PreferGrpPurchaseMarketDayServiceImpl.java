package melfood.shopping.survey;

import melfood.framework.auth.AuthService;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.user.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Steven on 14/7/17.
 */
@Service
public class PreferGrpPurchaseMarketDayServiceImpl implements PreferGrpPurchaseMarketDayService {

    private static final Logger logger = LoggerFactory.getLogger(PreferGrpPurchaseMarketDayServiceImpl.class);

    @Autowired
    protected AuthService authService;

    @Autowired
    PreferGrpPurchaseMarketDayDAO preferGrpPurchaseMarketDayDAO;

    @Override
    public Integer insertPreferGrpPurchaseMarketDay(String answerValue, String answerType, HttpServletRequest request) throws Exception {

        int updateCnt = 0;

        try {
            SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
            User user = sessionUser.getUser();

            String respondent = user.getUserId();
            String respondentLatitude = user.getAddressHomeGmapLatitude();
            String respondentLongitude = user.getAddressHomeGmapLongitude();
            String respondentSuburb = user.getAddressSuburb();
            String respondentPostcode = user.getAddressPostcode();

            PreferGrpPurchaseMarketDay marketDay = new PreferGrpPurchaseMarketDay();
            if (StringUtils.isNotBlank(respondent)) marketDay.setRespondent(respondent);

            if (StringUtils.isNotBlank(respondentLatitude) &&
                    StringUtils.isNotBlank(respondentLongitude) &&
                    StringUtils.isNotBlank(respondentSuburb) &&
                    StringUtils.isNotBlank(respondentPostcode)) {

                marketDay.setRespondentLongitude(respondentLongitude);
                marketDay.setRespondentLatitude(respondentLatitude);
                marketDay.setRespondentSuburb(respondentSuburb);
                marketDay.setRespondentPostcode(respondentPostcode);

            }
            marketDay.setAnswerType(answerType);
            marketDay.setAnswerValue(answerValue);
            marketDay.setCreator(user.getUserId());

            updateCnt = preferGrpPurchaseMarketDayDAO.insertPreferGrpPurchaseMarketDay(marketDay);

        } catch (Exception e) {
            logger.info("다음 선호 공구장소 설문자료 저장 실패 : " + e.getMessage());
        }
        return updateCnt;
    }
}
