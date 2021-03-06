/**
 * 2015 UserController.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.controller.customer;

import melfood.framework.Ctx;
import melfood.framework.attachement.AttachmentFile;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.communication.Communication;
import melfood.framework.communication.CommunicationService;
import melfood.framework.email.EmailServices;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.html.Option;
import melfood.framework.uitl.html.Properties;
import melfood.framework.user.User;
import melfood.shopping.order.OrderMaster;
import melfood.shopping.order.OrderMasterService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 마이페이지 <br>
 */
@Controller
@RequestMapping("/customer/mypage")
public class MyPageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MyPageController.class);

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private OrderMasterService orderMasterService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/main");
        return mav;
    }

    @RequestMapping("/passwordChangeForm")
    public ModelAndView passwordChangeForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/passwordChangeForm");
        return mav;
    }

    @RequestMapping(value = "/passwordChange", produces = "application/json")
    @ResponseBody
    public Map<String, Object> passwordChange(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        String userId = sessionUser.getUser().getUserId();
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String passwordCurrent = request.getParameter("passwordCurrent");

        try {

            User checkUser = userService.getUserInfo(userId);

            if (!StringUtils.equals(passwordCurrent, checkUser.getPassword())) throw new Exception("입력하신 현재비밀번호가 일치하지않습니다.");
            if (!StringUtils.equals(password1, password2)) throw new Exception("입력하신 비밀번호와 재입력하신 비밀번호가 일치하지않습니다.");

            User user = new User(userId);
            user.setPassword(password1);

            String updateUserId = userService.passwordChange(userId, password1);

            // 사용자 정보가 변경되었다는 이메일 발송
            String userEmail = checkUser.getEmail();
            if (!StringUtils.isBlank(userEmail) && StringUtils.equalsIgnoreCase(Ctx.getVar("EMAIL.AFTR.CHANGE.PWD"), "Y")) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());

                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("dearWhom=" + checkUser.getUserName() + "^");
                emailContents.append("changedDatetime=" + df.format(cal.getTime()) + "^");
                emailContents.append("whatChanged=개인정보^");

                if (!StringUtils.isBlank(checkUser.getEmail())) {
                    EmailServices emailSvc = new EmailServices();
                    emailSvc.sendEmailUsingHtmlTemplate(checkUser.getEmail(), "[멜푸드] 고객님의 비밀번호가 변경되었습니다.", emailContents.toString(), "3");
                }
            }

            model.put("resultCode", "0");
            model.put("message", "정상적으로 사용자 비밀번호를 변경하였습니다.");

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/modifyMyDetailInfo")
    public ModelAndView modifyMyDetailInfo(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/modifyMyDetailInfo");

        String userId = sessionUser.getUser().getUserId();

        User user = userService.getUserInfo(userId);

        Properties htmlProperty = new Properties();

        List<Option> sexOptions = codeService.getValueCmbxOptions("USER_MGT", "SEX", user.getSex());
        htmlProperty = new Properties("sex");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxSex", codeService.generateCmbx(sexOptions, htmlProperty));

        List<Option> addressStateOptions = codeService.getValueCmbxOptions("COMM", "ADDR_STATE", user.getAddressState());
        htmlProperty = new Properties("addressState");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxAddressState", codeService.generateCmbx(addressStateOptions, htmlProperty));

        List<Option> useSocialMessengerOptions = codeService.getValueCmbxOptions("COMM", "SOCIAL_MESSENGER", user.getUseSocialMessenger());
        htmlProperty = new Properties("useSocialMessenger");
        htmlProperty.setCssClass("form-control");
        mav.addObject("cbxUseSocialMessenger", codeService.generateCmbx(useSocialMessengerOptions, htmlProperty));

        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping("/myDetailInfo")
    public ModelAndView myDetailInfo(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/myDetailInfo");

        User user = new User();

        String userId = sessionUser.getUser().getUserId();
        user = userService.getUserInfo(userId);

        String birthDt = user.getDob();
        if (!StringUtils.isEmpty(birthDt) && StringUtils.length(birthDt) == 8) {
            birthDt = StringUtils.substring(birthDt, 0, 2) + "/" + StringUtils.substring(birthDt, 2, 4) + "/" + StringUtils.substring(birthDt, 4);
        }
        user.setDob(birthDt);

        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(value = "/saveUser", produces = "application/json")
    @ResponseBody
    public Map<String, Object> saveUser(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();
        String updateUserId = "";

        String userId = sessionUser.getUser().getUserId();
        String userName = request.getParameter("userName");
        String userNameReal = request.getParameter("userNameReal");
        String sex = request.getParameter("sex");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        // String mobile = request.getParameter("mobile");
        String telephone = request.getParameter("telephone");
        String useSocialMessenger = request.getParameter("useSocialMessenger");
        String useSocialMessengerId = request.getParameter("useSocialMessengerId");
        String addressState = request.getParameter("addressState");
        String addressPostcode = request.getParameter("addressPostcode");
        String addressSuburb = request.getParameter("addressSuburb");
        String addressStreet = request.getParameter("addressStreet");

        try {

            if (StringUtils.isBlank(userId)) throw new Exception("[사용자 ID]  이항목(들)은 빈 값이 될 수 없습니다.");
            User user = userService.getUserInfo(userId);

            if (StringUtils.isBlank(userName)) {
                user.setUserName(userId.substring(userId.length() - 3));
                // user.setUserName(joinMemberService.getDefaultUserName());
            } else {
                user.setUserName(userName);
            }

            if (StringUtils.isBlank(userNameReal)) {
                user.setUserNameReal(userId.substring(userId.length() - 3));
                // user.setUserNameReal(joinMemberService.getDefaultUserName());
            } else {
                user.setUserNameReal(userNameReal);
            }

            if (!StringUtils.isBlank(sex)) {
                user.setSex(sex);
            } else {
                user.setSex(null);
            }
            if (!StringUtils.isBlank(dob)) {
                user.setDob(dob);
            } else {
                user.setDob(null);
            }
            // if (!StringUtils.isBlank(mobile)) user.setMobile(mobile);
            if (!StringUtils.isBlank(telephone)) {
                user.setTelephone(telephone);
            } else {
                user.setTelephone(null);
            }
            if (!StringUtils.isBlank(email)) {
                user.setEmail(email);
            } else {
                user.setEmail(null);
            }
            if (!StringUtils.isBlank(useSocialMessenger)) {
                user.setUseSocialMessenger(useSocialMessenger);
            } else {
                user.setUseSocialMessenger(null);
            }
            if (!StringUtils.isBlank(useSocialMessengerId)) {
                user.setUseSocialMessengerId(useSocialMessengerId);
            } else {
                user.setUseSocialMessengerId(null);
            }
            if (!StringUtils.isBlank(addressPostcode)) {
                user.setAddressPostcode(addressPostcode);
            } else {
                user.setAddressPostcode(null);
            }
            if (!StringUtils.isBlank(addressState)) {
                user.setAddressState(addressState);
            } else {
                user.setAddressState(null);
            }
            if (!StringUtils.isBlank(addressSuburb)) {
                user.setAddressSuburb(addressSuburb);
            } else {
                user.setAddressSuburb(null);
            }
            if (!StringUtils.isBlank(addressStreet)) {
                user.setAddressStreet(addressStreet);
            } else {
                user.setAddressStreet(null);
            }

            updateUserId = userService.modifyUser(user);
            userService.updateHomeAddressGmapCoordinate(user); // 사용자의 집주소 좌표를 개신한다.

            if (!StringUtils.isBlank(user.getEmail()) && StringUtils.equalsIgnoreCase(Ctx.getVar("EMAIL.AFTR.CHANGE.MYINFO"), "Y")) {

                // 사용자 정보가 변경되었다는 이메일 발송
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());

                StringBuffer emailContents = new StringBuffer("");
                emailContents.append("dearWhom=" + user.getUserName() + "^");
                emailContents.append("changedDatetime=" + df.format(cal.getTime()) + "^");
                emailContents.append("whatChanged=개인정보^");

                if (!StringUtils.isBlank(user.getEmail())) {
                    EmailServices emailSvc = new EmailServices();
                    emailSvc.sendEmailUsingHtmlTemplate(user.getEmail(), "[멜푸드] 고객님의 개인정보가 변경되었습니다.", emailContents.toString(), "3");
                }
            }

            // 현재 세션의 이름 설정
            sessionUser.getUser().setUserName(user.getUserName());

            model.put("resultCode", "0");
            model.put("message", updateUserId + " 의 정보가 저장/변경 되었습니다.");
            model.put("userId", updateUserId);

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
            model.put("userId", updateUserId);
        }

        return model;
    }

    @RequestMapping("/myorder/Main")
    public ModelAndView myOrderMain(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/myorder/main");
        String userId = sessionUser.getUser().getUserId();

        String searchDateFrom = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        searchDateFrom = df.format(cal.getTime());

        mav.addObject("searchDateFrom", searchDateFrom);

        return mav;
    }

    @RequestMapping(value = "/myorder/myorders", produces = "application/json")
    @ResponseBody
    public Map<String, Object> myOrderList(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        String userId = sessionUser.getUser().getUserId();
        String searchDateFrom = request.getParameter("searchDateFrom");
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setCreator(userId);
        orderMaster.setSearchDateFrom(searchDateFrom);
        orderMaster.setLazyLoad(true); // 해당 제품 목록을 가저올 필요없다

        List<OrderMaster> myOrderList = null;

        // For Pagination
        orderMaster.setPagenationPage(getPage(request));
        orderMaster.setPagenationPageSize(getPageSize(request));

        try {
            Integer totalCount = 0;
            totalCount = orderMasterService.getTotalCntForGetOrderMasters(orderMaster);

            myOrderList = orderMasterService.getOrderMasters(orderMaster);

            model.put("list", myOrderList);
            model.put("totalCount", totalCount);

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/myorder/myorderdetail")
    public ModelAndView myorderdetail(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/myorder/myorderdetail");

        String userId = sessionUser.getUser().getUserId();
        String thanks = request.getParameter("thanks"); // Order Id

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setCreator(userId);
        orderMaster.setOrderMasterId(Integer.parseInt(thanks));
        orderMaster.setLazyLoad(false);

        OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);

        mav.addObject("orderMaster", newOrderMaster);

        if (newOrderMaster.getPaymentAccTransferReceipt() != null) {
            AttachmentFile receiptFile = attachmentFileService.getAttachmentFile(new AttachmentFile(newOrderMaster.getPaymentAccTransferReceipt()));
            if (receiptFile == null) {
                orderMasterService.removePaymentReceiptFileInfo(Integer.parseInt(thanks));
                mav.addObject("receiptFileNo", null);
                mav.addObject("receiptFileName", null);
                mav.addObject("receiptFileCreateDate", null);
            } else {
                mav.addObject("receiptFileNo", receiptFile.getFileId());
                mav.addObject("receiptFileName", receiptFile.getFileName());
                mav.addObject("receiptFileCreateDate", receiptFile.getCreateDatetime());
            }
        } else {
            mav.addObject("receiptFileNo", null);
            mav.addObject("receiptFileName", null);
            mav.addObject("receiptFileCreateDate", null);
        }

        return mav;
    }


    @RequestMapping(value = "/myorder/acctransferreceiptUpload", produces = "application/json")
    @ResponseBody
    public Map<String, Object> acctransferreceiptUpload(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Integer uploadedFileNo = 0;

        String subDirectory = Ctx.getVar("PAYMENT.RECEIPT.TEMP.UPLOAD.DIR");

        try {
            String userId = sessionUser.getUser().getUserId();
            String thanks = request.getParameter("thanks"); // Order Id

            OrderMaster orderMaster = new OrderMaster();
            orderMaster.setCreator(userId);
            orderMaster.setOrderMasterId(Integer.parseInt(thanks));
            orderMaster.setLazyLoad(false);

            OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);
            if (newOrderMaster == null) throw new Exception("주문정보가 존재하지 않아, 영수증 첨부를 할 수 없습니다.");

            Integer receiptFileNo = newOrderMaster.getPaymentAccTransferReceipt();

            // 이미 등록된 파일이 있다면 삭제해준다.
            if (receiptFileNo != null) {
                attachmentFileService.deleteAttachmentFile(new AttachmentFile(receiptFileNo));
                attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory); // 기존의 파일들을 지워준다.
            }

            // 파일을 임시 업로드 위치에 놓는다.
            attachmentFileService.uploadFile(request, subDirectory);
            Integer insertedFileId = orderMasterService.transferPaymentReceiptToAttachementFileDb(Integer.parseInt(thanks));

            // 기존의 파일들을 (다시한번)지워준다.
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);

            AttachmentFile receiptFile = attachmentFileService.getAttachmentFile(new AttachmentFile(insertedFileId));

            model.put("receiptFileNo", insertedFileId);
            model.put("receiptFileName", receiptFile.getFileName());
            model.put("receiptFileCreateDate", receiptFile.getCreateDatetime());
            model.put("resultCode", "0");
            model.put("message", "File uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            attachmentFileService.deleteAllfilesForTempUploadDir(subDirectory);

            model.put("receiptFileNo", null);
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }
        return model;

    }

    @RequestMapping(value = "/myorder/acctransferreceiptRemove", produces = "application/json")
    @ResponseBody
    public Map<String, Object> acctransferreceiptRemove(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        String userId = sessionUser.getUser().getUserId();
        String thanks = request.getParameter("thanks"); // Order Id

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setCreator(userId);
        orderMaster.setOrderMasterId(Integer.parseInt(thanks));
        orderMaster.setLazyLoad(false);

        OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);
        if (newOrderMaster == null) throw new Exception("주문정보가 존재하지 않아, 영수증 첨부를 할 수 없습니다.");

        try {
            // 물리적인 위치의 파일을 삭제하고, 첨부파일을 관리하는 테이블에서 또한 삭제해 준다.
            attachmentFileService.deleteAttachmentFile(new AttachmentFile(newOrderMaster.getPaymentAccTransferReceipt()));

            // 주문테이블의 영수증파일 아이디컬럼을 Null 처리해준다.
            orderMasterService.removePaymentReceiptFileInfo(Integer.parseInt(thanks));

            model.put("resultCode", "0");
            model.put("message", "1개 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }


    @RequestMapping("/myCommunication")
    public ModelAndView myCommunication(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/customer/mypage/customer/myCommunication");
        String userId = sessionUser.getUser().getUserId();

        Communication communication = new Communication();
        communication.setWriteFrom(userId);
        communication.setWriteTo(userId);

        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        if (StringUtils.isBlank(searchDateFrom)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -1);
            searchDateFrom = df.format(cal.getTime());
        }

        if (StringUtils.isNotBlank(searchDateFrom)) {
            communication.setSearchDateFrom(searchDateFrom);
            mav.addObject("searchDateFrom", searchDateFrom);
        } else {
            mav.addObject("setSearchDateFrom", "");
        }
        if (StringUtils.isNotBlank(searchDateTo)) {
            communication.setSearchDateTo(searchDateTo);
            mav.addObject("searchDateTo", searchDateTo);
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            searchDateTo = df.format(cal.getTime());
            mav.addObject("searchDateTo", searchDateTo);
        }

        // For Pagination
        communication.setPagenationPage(0);
        communication.setPagenationPageSize(99999);

        List<Communication> list = communicationService.getMyCommunicationList(communication);

        mav.addObject("communicationList", list);
        mav.addObject("sessionUserId", userId);

        return mav;
    }

}
