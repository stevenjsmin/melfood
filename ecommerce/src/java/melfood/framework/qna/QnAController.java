/**
 * 2016 NoticeController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.qna;

import melfood.framework.auth.SessionUserInfo;
import melfood.framework.communication.CommunicationService;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for Admin main entry management
 *
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 */
@Controller
@RequestMapping("/framework/qnamanager")
public class QnAController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(QnAController.class);

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private QnAService qnAService;

    @RequestMapping("/Main")
    public ModelAndView main() throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/qnamanager/main");
        Properties htmlProperty = new Properties();

        List<Option> qnaStatusOptions = codeService.getValueCmbxOptions("COMM", "QNA");
        htmlProperty = new Properties("qnaStatus");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxQnaStatus", codeService.generateCmbx(qnaStatusOptions, htmlProperty));


        String searchDateTo = "";

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        mav.addObject("searchDateTo", df.format(cal.getTime()));

        cal.add(Calendar.MONTH, -1);
        mav.addObject("searchDateFrom", df.format(cal.getTime()));

        return mav;
    }

    @RequestMapping(value = "/getQnAs", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getQnAs(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        QnA qnA = new QnA();

        // For Pagination
        qnA.setPagenationPage(getPage(request));
        qnA.setPagenationPageSize(getPageSize(request));

        String customerEmail = request.getParameter("customerEmail");
        String customerMobile = request.getParameter("customerMobile");
        String customerQuestion = request.getParameter("customerQuestion");
        String qnaStatus = request.getParameter("qnaStatus");
        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        if (StringUtils.isNotBlank(customerEmail)) qnA.setCustomerEmail(customerEmail);
        if (StringUtils.isNotBlank(customerMobile)) qnA.setCustomerMobile(customerMobile);
        if (StringUtils.isNotBlank(customerQuestion)) qnA.setCustomerQuestion(customerQuestion);
        if (StringUtils.isNotBlank(qnaStatus)) qnA.setQnaStatus(qnaStatus);
        if (StringUtils.isNotBlank(searchDateFrom)) qnA.setSearchDateFrom(searchDateFrom);
        if (StringUtils.isNotBlank(searchDateTo)) qnA.setSearchDateTo(searchDateTo);

        List<QnA> noticeDiscussList = qnAService.getQnAList(qnA);

        Integer totalCount = 0;
        totalCount = qnAService.getTotalCntForQnAList(qnA);
        model.put("totalCount", totalCount);
        model.put("list", noticeDiscussList);

        return model;
    }

    @RequestMapping("/getQnA")
    public ModelAndView getQnA(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/qnamanager/detailInfo");

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

        QnA qnA = qnAService.getQnA(id);

        mav.addObject("qnA", qnA);

        return mav;
    }

    @RequestMapping(value = "/updateStatus", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveModify(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

        String qnaStatus = request.getParameter("qnaStatus");
        if (StringUtils.isBlank(id)) throw new Exception("Can not be null : qnaStatus");

        QnA qnA = new QnA(id);

        try {
            if (StringUtils.equalsIgnoreCase(qnaStatus, "NOT_OPEN")) {
                qnA.setQnaStatus("NOT_OPEN");
            } else if (StringUtils.equalsIgnoreCase(qnaStatus, "OPEN")) {
                qnA.setQnaStatus("OPEN");
            } else if (StringUtils.equalsIgnoreCase(qnaStatus, "COMPLETE")) {
                qnA.setQnaStatus("COMPLETE");
            }

            updateCnt = qnAService.modifyQnAForNotNull(qnA);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/deleteQnA", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteQnA(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String id = request.getParameter("id");

        try {
            if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

            QnA qnA = new QnA(id);

            int updateCnt = 0;
            updateCnt = qnAService.deleteQnA(qnA);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " record deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


}
