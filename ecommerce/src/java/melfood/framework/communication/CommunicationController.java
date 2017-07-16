/**
 * 2016 NoticeController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.communication;

import melfood.framework.MelfoodConstants;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
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
@RequestMapping("/framework/communicationmanager")
public class CommunicationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationController.class);

    @Autowired
    private CommunicationService communicationService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/main");
        Properties htmlProperty = new Properties();

        String category = request.getParameter("category");
        if (StringUtils.equalsIgnoreCase(category, "QNA")) {
            mav.setViewName("tiles/framework/communicationmanager/qna/main");
        }
        category = StringUtils.isBlank(category) ? "QNA" : category.toUpperCase();

        List<Option> progressStatusOptions = codeService.getValueCmbxOptions("COMM", "QNA");
        htmlProperty = new Properties("progressStatus");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxProgressStatus", codeService.generateCmbx(progressStatusOptions, htmlProperty));

        List<Option> categoryOptions = codeService.getValueCmbxOptions("COMM", "COMMUNICATION_CATEGORY", category.toUpperCase());
        htmlProperty = new Properties("category");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxCategory", codeService.generateCmbx(categoryOptions, htmlProperty));

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

    @RequestMapping(value = "/getCommunications", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getCommunications(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        Communication communication = new Communication();

        // For Pagination
        communication.setPagenationPage(getPage(request));
        communication.setPagenationPageSize(getPageSize(request));

        String category = request.getParameter("category");
        if (StringUtils.isBlank(category)) {
            category = "NOTICE";
            // throw new Exception("[category] 값은 필수 입니다.");
        }

        String writer = request.getParameter("writer");
        String writerMobile = request.getParameter("writerMobile");
        String writerEmail = request.getParameter("writerEmail");
        String writeFrom = request.getParameter("writeFrom");
        String writeTo = request.getParameter("writeTo");
        String contents = request.getParameter("contents");
        String progressStatus = request.getParameter("progressStatus");
        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        if (StringUtils.isNotBlank(writer)) communication.setWriter(writer);
        if (StringUtils.isNotBlank(writerMobile)) communication.setWriterMobile(writerMobile);
        if (StringUtils.isNotBlank(writerEmail)) communication.setWriterEmail(writerEmail);
        if (StringUtils.isNotBlank(writeFrom)) communication.setWriteFrom(writeFrom);
        if (StringUtils.isNotBlank(writeTo)) communication.setWriteTo(writeTo);
        if (StringUtils.isNotBlank(contents)) communication.setContents(contents);
        if (StringUtils.isNotBlank(category)) communication.setCategory(category);
        if (StringUtils.isNotBlank(progressStatus)) communication.setProgressStatus(progressStatus);


        if (StringUtils.isNotBlank(searchDateFrom)) communication.setSearchDateFrom(searchDateFrom);
        if (StringUtils.isNotBlank(searchDateTo)) communication.setSearchDateTo(searchDateTo);

        List<Communication> communicationList = communicationService.getCommunicationList(communication);

        Integer totalCount = 0;
        totalCount = communicationService.getTotalCntForCommunicationList(communication);
        model.put("totalCount", totalCount);
        model.put("list", communicationList);

        return model;
    }

    @RequestMapping("/getCommunication")
    public ModelAndView getCommunication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/getCommunication");

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

        Communication communication = communicationService.getCommunication(id);
        if (StringUtils.equalsIgnoreCase(communication.getCategory(), "QNA")) mav.setViewName("tiles/framework/communicationmanager/qna/qnADetailInfo");

        mav.addObject("communication", communication);

        return mav;
    }

    @RequestMapping(value = "/updateProgressStatus", produces = "application/json")
    @ResponseBody
    public Map<String, Object> updateProgressStatus(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

        String progressStatus = request.getParameter("progressStatus");
        if (StringUtils.isBlank(progressStatus)) throw new Exception("Can not be null : progressStatus");

        Communication communication = new Communication(id);

        try {
            if (StringUtils.equalsIgnoreCase(progressStatus, "NOT_OPEN")) {
                communication.setProgressStatus("NOT_OPEN");
            } else if (StringUtils.equalsIgnoreCase(progressStatus, "OPEN")) {
                communication.setProgressStatus("OPEN");
            } else if (StringUtils.equalsIgnoreCase(progressStatus, "COMPLETE")) {
                communication.setProgressStatus("COMPLETE");
            }

            updateCnt = communicationService.modifyCommunicationForNotNull(communication);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


    @RequestMapping("/regist")
    public ModelAndView noticeDiscussRegistForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/regist");
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        mav.addObject("creator", sessionUser.getUser().getUserName());

        Properties htmlProperty = new Properties();

        List<Option> categoryOptions = codeService.getValueCmbxOptions("COMM", "COMMUNICATION_CATEGORY");
        htmlProperty = new Properties("category");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxCategory", codeService.generateCmbx(categoryOptions, htmlProperty));

        List<Option> isForAllSellerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", "Y");
        htmlProperty = new Properties("isForAllSeller");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxIsForAllSeller", codeService.generateCmbx(isForAllSellerOptions, htmlProperty));

        List<Option> isForAllCustomerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", "Y");
        htmlProperty = new Properties("isForAllCustomer");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxIsForAllCustomer", codeService.generateCmbx(isForAllCustomerOptions, htmlProperty));

        mav.addObject("writeFrom", sessionUser.getUser().getUserId());
        mav.addObject("writeFromName", sessionUser.getUser().getUserName());

        return mav;
    }

    @RequestMapping("/modify")
    public ModelAndView noticeDiscussModifyForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/modify");

        String id = request.getParameter("id");
        Communication communication = communicationService.getCommunication(id);

        Properties htmlProperty = new Properties();

        List<Option> categoryOptions = codeService.getValueCmbxOptions("COMM", "COMMUNICATION_CATEGORY", communication.getCategory());
        htmlProperty = new Properties("category");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxCategory", codeService.generateCmbx(categoryOptions, htmlProperty));

        List<Option> isForAllSellerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", communication.getIsForAllSeller());
        htmlProperty = new Properties("isForAllSeller");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxIsForAllSeller", codeService.generateCmbx(isForAllSellerOptions, htmlProperty));

        List<Option> isForAllCustomerOptions = codeService.getValueCmbxOptions("COMM", "YES_NO", communication.getIsForAllCustomer());
        htmlProperty = new Properties("isForAllCustomer");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxIsForAllCustomer", codeService.generateCmbx(isForAllCustomerOptions, htmlProperty));

        mav.addObject("noticeDiscuss", communication);

        return mav;
    }

    @RequestMapping(value = "/saveModify", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveModify(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        int updateCnt = 0;

        String actionMode = request.getParameter("actionMode");
        if (StringUtils.isBlank(actionMode)) actionMode = MelfoodConstants.ACTION_MODE_MODIFY;

        String id = request.getParameter("id");
        String category = request.getParameter("category");
        String subject = request.getParameter("subject");
        String contents = request.getParameter("contents");
        String writer = request.getParameter("writer");
        String writerMobile = request.getParameter("writerMobile");
        String writerEmail = request.getParameter("writerEmail");
        String writeFrom = request.getParameter("writeFrom");
        String writeTo = request.getParameter("writeTo");
        String isForAllSeller = request.getParameter("isForAllSeller");
        String isForAllCustomer = request.getParameter("isForAllCustomer");
        String progressStatus = request.getParameter("progressStatus");
        String notifySmsNo = request.getParameter("notifySmsNo");
        String notifyEmail = request.getParameter("notifyEmail");
        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");
        String creator = sessionUser.getUser().getUserId();

        if (StringUtils.isBlank(writeFrom)) {
            writer = sessionUser.getUser().getUserId();
        } else {
            writer = writeFrom;
        }
        if (StringUtils.isBlank(writeFrom)) writeFrom = creator;
        if (StringUtils.isBlank(writerMobile)) writerMobile = sessionUser.getUser().getMobile() == null ? sessionUser.getUser().getUserId() : sessionUser.getUser().getMobile();
        if (StringUtils.isBlank(writerEmail)) writerEmail = sessionUser.getUser().getEmail();

        // 누구에게 특정지어서 보내는 경우 isForAllSeller 와 isForAllCustomer는 필요없다.
        if (!StringUtils.isBlank(writeTo)) {
            isForAllSeller = "N";
            isForAllCustomer = "N";
        }
        // isForAllSeller 또는 isForAllCustomer가 Y 인경우 특정인의 컬럼이 필요없다
        if (StringUtils.equalsIgnoreCase(isForAllSeller, "Y") || StringUtils.equalsIgnoreCase(isForAllCustomer, "Y")) {
            writeTo = null;
        }

        Communication communication = null;

        try {

            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_MODIFY)) {
                if (StringUtils.isBlank(id) || StringUtils.isBlank(category) || StringUtils.isBlank(writer) || StringUtils.isBlank(contents)) {
                    throw new Exception("[ID | CATEGORY | WRITER | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
                }
                communication = new Communication(id);
            } else {
                if (StringUtils.isBlank(category) || StringUtils.isBlank(writer) || StringUtils.isBlank(contents)) {
                    throw new Exception("[CATEGORY | WRITER | CONTENTS]  이항목(들)은 빈 값이 될 수 없습니다.");
                }
                communication = new Communication();
                communication.setCreator(creator);
            }

            if (StringUtils.isNotBlank(category)) communication.setCategory(category);
            if (StringUtils.isNotBlank(subject)) communication.setSubject(subject);
            if (StringUtils.isNotBlank(contents)) communication.setContents(contents);
            if (StringUtils.isNotBlank(writer)) communication.setWriter(writer);
            if (StringUtils.isNotBlank(writerMobile)) communication.setWriterMobile(writerMobile);
            if (StringUtils.isNotBlank(writerEmail)) communication.setWriterEmail(writerEmail);
            if (StringUtils.isNotBlank(writeFrom)) communication.setWriteFrom(writeFrom);
            if (StringUtils.isNotBlank(writeTo)) communication.setWriteTo(writeTo);
            if (StringUtils.isNotBlank(isForAllSeller)) communication.setIsForAllSeller(isForAllSeller);
            if (StringUtils.isNotBlank(isForAllCustomer)) communication.setIsForAllCustomer(isForAllCustomer);
            if (StringUtils.isNotBlank(progressStatus)) communication.setProgressStatus(progressStatus);
            if (StringUtils.isNotBlank(notifySmsNo)) communication.setNotifySmsNo(notifySmsNo);
            if (StringUtils.isNotBlank(notifyEmail)) communication.setNotifyEmail(notifyEmail);
            if (StringUtils.isNotBlank(searchDateFrom)) communication.setSearchDateFrom(searchDateFrom);
            if (StringUtils.isNotBlank(searchDateTo)) communication.setSearchDateTo(searchDateTo);

            if (StringUtils.equalsIgnoreCase(actionMode, MelfoodConstants.ACTION_MODE_ADD)) {
                updateCnt = communicationService.registCommunication(communication);
            } else {
                updateCnt = communicationService.modifyCommunication(communication);
            }

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");
            model.put("category", category);

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/deleteCommunication", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteCommunication(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String id = request.getParameter("id");

        try {
            if (StringUtils.isBlank(id)) throw new Exception("Can not be null : id");

            int updateCnt = 0;
            updateCnt = communicationService.deleteCommunication(new Communication(id));

            model.put("resultCode", "0");
            model.put("message", updateCnt + " record deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


    @RequestMapping("/findUserForm")
    public ModelAndView addCalendarForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/findUserForm");
        Properties htmlProperty = new Properties();

        String objectName = request.getParameter("objectName");
        mav.addObject("objectName", objectName);

        List<Option> userTypeOptions = codeService.getValueCmbxOptions("USER_MGT", "USER_TYPE");
        htmlProperty = new Properties("userType");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxUserType", codeService.generateCmbx(userTypeOptions, htmlProperty));

        return mav;
    }

    @RequestMapping(value = "/findUser", produces = "application/json")
    @ResponseBody
    public Map<String, Object> listUsers(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        List<User> list = null;

        String userName = request.getParameter("userName");
        String userType = request.getParameter("userType");

        User user = new User();

        // For Pagination
        user.setPagenationPage(getPage(request));
        user.setPagenationPageSize(getPageSize(request));

        if (StringUtils.isNotBlank(userName)) user.setUserName(userName);
        if (StringUtils.isNotBlank(userType)) user.setRoleId(userType);

        try {
            Integer totalCount = 0;

            list = userService.getUsers(user);
            totalCount = userService.getTotalCntForUsers(user);

            model.put("totalCount", totalCount);
            model.put("list", list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping("/sendMessageForm")
    public ModelAndView sendMessageForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/sendMessageForm");

        String receiverUserId = request.getParameter("receiverUserId");
        User receiverUser = userService.getUserInfo(receiverUserId);
        mav.addObject("receiverUser", receiverUser);

        return mav;
    }

    /**
     * 메시지(Email, SMS)를 발송한다<br/>
     * <p>
     * 1. 발송하려는 메시지는 "contents" 파라미터로 전달되어야 한다.<br/>
     * 2. Email로 메시지를 발송하고자하는 경우 "sendEmail" 파라미터에 true로 전달되어야한다<br/>
     * 3. SMS로 메시지를 발송하고자하는 경우 "sendSMS" 파라미터에 true로 전달되어야한다<br/>
     * <p>
     * 4. 메시지를 발송자 정보는 세션사용자의 정보가 자동 설정된다.<br/>
     * 5. 메시지를 수신자 정보는 "receiverUserId"로 전달된 사용자의 정보가 설정된다.<br/>
     *
     * 6. 메시지의 종류는 "CAHT"으로 설정된다.
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendMessage", produces = "application/json")
    @ResponseBody
    public Map<String, Object> sendMessage(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();

        String receiverUserId = request.getParameter("receiverUserId");
        String contents = request.getParameter("contents");
        String sendSMS = request.getParameter("sendSMS");
        String sendEmail = request.getParameter("sendEmail");

        Communication communication = new Communication();

        try {
            User receiverUser = userService.getUserInfo(receiverUserId);

            String receiverUserEmail = receiverUser.getEmail();
            String receiverUserMobile = receiverUser.getUserId();

            communication.setCategory("CHAT");
            if (StringUtils.isNotBlank(contents)) communication.setContents(contents);
            communication.setWriter(sessionUser.getUser().getUserId());
            communication.setWriterMobile(sessionUser.getUser().getUserId());
            communication.setWriterEmail(sessionUser.getUser().getEmail());
            communication.setWriteFrom(sessionUser.getUser().getUserId());

            communication.setWriteTo(receiverUser.getUserId());

            communication.setIsForAllSeller("N");
            communication.setIsForAllCustomer("N");
            communication.setProgressStatus(null);

            // Notifiy 설정
            if (StringUtils.isNotBlank(receiverUserMobile) && StringUtils.equalsIgnoreCase(sendSMS, "true")) {
                communication.setNotifySmsNo(receiverUserMobile);
            } else {
                communication.setNotifySmsNo(null);
            }

            if (StringUtils.isNotBlank(receiverUserEmail) && StringUtils.equalsIgnoreCase(sendEmail, "true")) {
                communication.setNotifyEmail(receiverUserEmail);
            } else {
                communication.setNotifyEmail(null);
            }

            int updateCnt = communicationService.registCommunication(communication);

            model.put("resultCode", "0");
            model.put("message", updateCnt + " 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }


        return model;
    }

    /**
     * 한달치의 대화기록을 가져온다.
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendMessageFormWIthHistory")
    public ModelAndView sendMessageFormWIthHistory(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/framework/communicationmanager/sendMessageFormWIthHistory");

        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        String userId = sessionUser.getUser().getUserId();
        mav.addObject("sessionUserId", userId);

        String receiverUserId = request.getParameter("receiverUserId");
        User receiverUser = userService.getUserInfo(receiverUserId);
        mav.addObject("receiverUser", receiverUser);

        Communication communication = new Communication();
        communication.setWriteFrom(userId);
        communication.setWriteTo(receiverUser.getUserId());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // cal.add(Calendar.MONTH, -1); // One month ago
        cal.add(Calendar.DAY_OF_MONTH, -7); // 7 Days ago
        communication.setSearchDateFrom(df.format(cal.getTime()));

        // For Pagination
        communication.setPagenationPage(0);
        communication.setPagenationPageSize(99999);

        List<Communication> list = communicationService.getMyCommunicationListWithPerson(communication);
        mav.addObject("communicationList", list);


        return mav;
    }

}
