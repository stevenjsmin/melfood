/**
 * 2016 GuestLoginController.java
 * Created by steven.min
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.guest;

import melfood.framework.Ctx;
import melfood.framework.communication.Communication;
import melfood.framework.communication.CommunicationService;
import melfood.framework.system.BaseController;
import melfood.framework.user.User;
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
    private CommunicationService communicationService;

    @RequestMapping(value = "/sendQuestionMessage", produces = "application/json")
    @ResponseBody
    public Map<String, Object> sendQuestionMessage(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String customerMobile = request.getParameter("customerMobile");
        String customerEmail = request.getParameter("customerEmail");
        String customerQuestion = request.getParameter("customerQuestion");

        String writeTo = request.getParameter("writeTo");

        try {
            if (StringUtils.isBlank(customerMobile) && StringUtils.isBlank(customerEmail)) {
                throw new Exception("고객의 모바일번호 또는 Email주소 중 한가지는 입력해주셔야합니다.");
            }
            if (StringUtils.isBlank(customerQuestion)) {
                throw new Exception("고객의 문의내용이 비어있습니다. 문의내용을 다시 확인해주세요.");
            }
            //QnA qnA = new QnA(customerMobile, customerEmail, customerQuestion, "NOT_OPEN");
            Communication communication = new Communication();
            communication.setProgressStatus("NOT_OPEN");


            // Notify User 설정
            // SMS 또는 Email 수신자 설정 :  QnA등록 사항을 알려준다.
            User writeToUser = StringUtils.isBlank(writeTo) ? null : userService.getUserInfo(writeTo);

            if (writeToUser == null) {
                String customerSupportMobile = Ctx.xmlConfig.getString("contact-info/default-customer-service/mobile");
                String customerSupportEmail = Ctx.xmlConfig.getString("contact-info/default-customer-service/email");

                communication.setWriteTo(customerSupportMobile); // 고객관리자를 수신자로 설정한다.

                if (!StringUtils.isBlank(customerSupportEmail)) {
                    communication.setNotifyEmail(customerSupportEmail);
                }
                if (!StringUtils.isBlank(customerSupportMobile)) {
                    communication.setNotifySmsNo(customerSupportMobile);
                }

            } else {

                communication.setWriteTo(writeToUser.getUserId());

                if (!StringUtils.isBlank(writeToUser.getEmail())) {
                    communication.setNotifyEmail(writeToUser.getEmail());
                }
                if (!StringUtils.isBlank(writeToUser.getMobile())) {
                    communication.setNotifySmsNo(writeToUser.getMobile());
                }
            }

            communication.setCategory("QNA");
            communication.setContents(customerQuestion);

            if (!StringUtils.isBlank(customerEmail)) {
                communication.setWriter(customerEmail);
                communication.setWriteFrom(customerEmail);
                communication.setWriterEmail(customerEmail);
            }

            if (!StringUtils.isBlank(customerMobile)) {
                communication.setWriter(customerMobile);
                communication.setWriteFrom(customerMobile);
                communication.setWriterMobile(customerMobile);
            }

            updateCnt = communicationService.registCommunication(communication);

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
