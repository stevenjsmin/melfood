package melfood.framework.qna;

import melfood.framework.Ctx;
import melfood.framework.email.EmailServices;
import melfood.framework.system.AwsSNSUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Steven on 26/6/17.
 */
@Service
public class QnAServiceImpl implements QnAService {


    private static final Logger logger = LoggerFactory.getLogger(QnAServiceImpl.class);

    @Autowired
    private QnADAO qnADAO;

    @Override
    public List<QnA> getQnAList(QnA qnA) throws Exception {
        return qnADAO.getQnAList(qnA);
    }

    @Override
    public Integer getTotalCntForQnAList(QnA qnA) throws Exception {
        return qnADAO.getTotalCntForQnAList(qnA);
    }

    @Override
    public Integer deleteQnA(QnA qnA) throws Exception {
        return qnADAO.deleteQnA(qnA);
    }

    @Override
    public Integer registQnA(QnA qnA) throws Exception {
        // 1. 데이터베이스에 기록한다
        // 2. 관리자에게 SMS로 문의사항이 도착했음을 알린다
        int cntUpdate = qnADAO.registQnA(qnA);

        if (cntUpdate > 0) {

            String customerSupportMobile = Ctx.xmlConfig.getString("contact-info/default-customer-service/mobile");
            String customerSupportEmail = Ctx.xmlConfig.getString("contact-info/default-customer-service/email");

            StringBuffer message = new StringBuffer("");


            String nitifyResult = null;
            if (StringUtils.isNotBlank(customerSupportEmail)) {
                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("customerMobile=" + qnA.getCustomerMobile() + "^");
                emailContents.append("customerEmail=" + qnA.getCustomerEmail() + "^");
                emailContents.append("customerQuestion=" + qnA.getCustomerQuestion() + "^");

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                emailContents.append("createDatetime=" + df.format(cal.getTime()) + "^");

                EmailServices emailSvc = new EmailServices();
                emailSvc.sendEmailUsingHtmlTemplate(customerSupportEmail, "[고객문의 사항] 고객의 문의사항이 접수되었습니다.", emailContents.toString(), "4");

                nitifyResult = "이메일 발송완료";

            } else if (StringUtils.isNotBlank(customerSupportMobile)) {
                message.append("[");
                if (StringUtils.isNotBlank(qnA.getCustomerMobile())) message.append(qnA.getCustomerMobile());
                if (StringUtils.isNotBlank(qnA.getCustomerEmail())) {
                    if (StringUtils.isNotBlank(qnA.getCustomerMobile())) {
                        message.append("/" + qnA.getCustomerEmail());
                    } else {
                        message.append(qnA.getCustomerEmail());
                    }
                }

                message.append("] 문의사항 : " + StringUtils.abbreviate(qnA.getCustomerQuestion(), 80));
                nitifyResult = AwsSNSUtils.sendMessage(message.toString(), customerSupportMobile);
                nitifyResult = "SMS of AWS SNS 발송 :" + nitifyResult;
            } else {
                nitifyResult = "모바일번호도 없고, 이메일도 없기때문에 알림메시지를 관리자에게 보내지 못하였습니다.";
            }

            logger.info("SMS Send result :" + nitifyResult);
        }

        return cntUpdate;
    }

    @Override
    public Integer modifyQnA(QnA qnA) throws Exception {
        return qnADAO.modifyQnA(qnA);
    }

    @Override
    public Integer modifyQnAForNotNull(QnA qnA) throws Exception {
        return qnADAO.modifyQnAForNotNull(qnA);
    }

    @Override
    public QnA getQnA(String id) throws Exception {
        return this.getQnA(Integer.parseInt(id));
    }

    @Override
    public QnA getQnA(int id) throws Exception {
        List<QnA> qnAs = this.getQnAList(new QnA(id));
        if (qnAs.size() > 0) {
            return qnAs.get(0);
        } else {
            return null;
        }
    }
}
