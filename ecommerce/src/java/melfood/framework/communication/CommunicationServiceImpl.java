package melfood.framework.communication;

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
 * Created by Steven on 8/7/17.
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

    @Autowired
    private CommunicationDAO communicationDAO;

    @Override
    public List<Communication> getCommunicationList(Communication communication) throws Exception {
        return communicationDAO.getCommunicationList(communication);
    }

    @Override
    public Integer getTotalCntForCommunicationList(Communication communication) throws Exception {
        return communicationDAO.getTotalCntForCommunicationList(communication);
    }

    @Override
    public Integer deleteCommunication(Communication communication) throws Exception {
        return communicationDAO.deleteCommunication(communication);
    }

    /**
     * Notice, Discussion 또는 QnA글을 등록한다<br/>
     * SMS에 통지를 하기위해서는 notifySmsNo를 설정해준다.(한개이상의 SMS로 보내기위해서는 콤마(,)로 구분하여 설정한다.) <br/>
     * Email로 통지하기 위해서는 notifyEmail를 설정해준다.(한개이상의 Email로 보내기위해서는 콤마(,)로 구분하여 설정한다.)
     *
     * @param communication
     * @return
     * @throws Exception
     */
    @Override
    public Integer registCommunication(Communication communication) throws Exception {

        int cntUpdate = communicationDAO.registCommunication(communication);

        String notifyResult = null;
        StringBuffer message = null;

        // 1. SMS로 보내야하는 경우 SMS로 발송한다
        if (cntUpdate > 0 && StringUtils.isNotBlank(communication.getNotifySmsNo())) {

            String[] mobileNumbers = StringUtils.split(communication.getNotifySmsNo(), ",");

            for (String mobileNumber : mobileNumbers) {
                message = new StringBuffer();
                message.append("[" + communication.getWriter() + "] ");

                if (StringUtils.equalsIgnoreCase(communication.getCategory(), "QNA")) {
                    message.append("문의사항 : ");
                } else if (StringUtils.equalsIgnoreCase(communication.getCategory(), "NOTICE")) {
                    message.append("공지사항 : ");
                } else if (StringUtils.equalsIgnoreCase(communication.getCategory(), "CHAT")) {
                    message.append("일반대화 : ");
                } else {
                    message.append(" : ");
                }
                message.append(StringUtils.abbreviate(communication.getContents(), 200));

                notifyResult = AwsSNSUtils.sendMessage(message.toString(), mobileNumber);
                logger.info("SMS of AWS SNS 발송 :" + notifyResult);
            }

        }

        // 2. Email로 보내야하는 경우 이메일로 발송한다
        if (cntUpdate > 0 && StringUtils.isNotBlank(communication.getNotifyEmail())) {

            String[] emails = StringUtils.split(communication.getNotifyEmail(), ",");

            for (String email : emails) {
                message = new StringBuffer("");
                message.append("customerMobile=" + communication.getWriterMobile() + "^");
                message.append("customerEmail=" + communication.getWriterEmail() + "^");
                message.append("customerQuestion=" + communication.getContents() + "^");

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                message.append("createDatetime=" + df.format(cal.getTime()) + "^");

                EmailServices emailSvc = new EmailServices();
                emailSvc.sendEmailUsingHtmlTemplate(communication.getNotifyEmail(), "[고객문의 사항] 고객의 문의사항이 접수되었습니다.", message.toString(), "4");

                logger.info("이메일 발송완료 :" + communication.getNotifyEmail());
            }

        }
        logger.info(notifyResult);

        return cntUpdate;
    }

    @Override
    public Integer modifyCommunication(Communication communication) throws Exception {
        return communicationDAO.modifyCommunication(communication);
    }

    @Override
    public Integer modifyCommunicationForNotNull(Communication communication) throws Exception {
        return communicationDAO.modifyCommunicationForNotNull(communication);
    }

    @Override
    public Communication getCommunication(String id) throws Exception {
        return this.getCommunication(Integer.parseInt(id));
    }

    @Override
    public Communication getCommunication(int id) throws Exception {
        List<Communication> communications = this.getCommunicationList(new Communication(id));
        if (communications.size() > 0) {
            return communications.get(0);
        } else {
            return null;
        }
    }
}
