package melfood.framework.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //if (cntUpdate > 0) {
        //    String customerSupportMobile = Ctx.xmlConfig.getString("contact-info/default-customer-service/mobile");
        //    String smsResult = AwsSNSUtils.sendMessage("고객문의사항 : " + StringUtils.abbreviate(qnA.getCustomerQuestion(), 50), customerSupportMobile);
        //    logger.info("SMS Send result :" + smsResult);
        //}

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
}
