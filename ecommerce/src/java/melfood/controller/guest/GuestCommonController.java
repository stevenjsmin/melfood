/**
 * 2016 GuestLoginController.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import melfood.framework.qna.QnA;
import melfood.framework.qna.QnAService;
import melfood.framework.system.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 회원가입에 필요한 콘트롤러들을 정의한다.
 *
 * @author steven.min
 */
@Controller
@RequestMapping("/guest/common")
public class GuestCommonController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GuestCommonController.class);

    @Autowired
    private QnAService qnAServie;

    @RequestMapping(value = "/sendQuestionMessage", produces = "application/json")
    @ResponseBody
    public Map<String, Object> sendQuestionMessage(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String customerMobile = request.getParameter("customerMobile");
        String customerEmail = request.getParameter("customerEmail");
        String customerQuestion = request.getParameter("customerQuestion");

        try {
            if (StringUtils.isBlank(customerMobile) && StringUtils.isBlank(customerEmail)) {
                throw new Exception("고객의 모바일번호 또는 Email주소 중 한가지는 입력해주셔야합니다.");
            }
            if (StringUtils.isBlank(customerQuestion)) {
                throw new Exception("고객의 문의내용이 비어있습니다. 문의내용을 다시 확인해주세요.");
            }
            QnA qnA = new QnA(customerMobile, customerEmail, customerQuestion, "NOT_OPEN");

            updateCnt = qnAServie.registQnA(qnA);

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
