package melfood.controller.admin;

import melfood.framework.attachement.AttachmentFile;
import melfood.framework.auth.SessionUserInfo;
import melfood.framework.communication.Communication;
import melfood.framework.communication.CommunicationService;
import melfood.framework.system.BaseController;
import melfood.framework.uitl.HtmlCodeGenerator;
import melfood.framework.uitl.html.Option;
import melfood.framework.user.User;
import melfood.shopping.order.OrderMaster;
import melfood.shopping.order.OrderMasterProductService;
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
 * Created by Steven on 12/7/17.
 */
@RequestMapping("/admin/ordermgt")
@Controller
public class OrderMgtController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderMgtController.class);

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    OrderMasterProductService orderMasterProductService;

    @Autowired
    CommunicationService communicationService;

    @RequestMapping("/Main")
    public ModelAndView main(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/ordermgt/main");

        // 인보이스 발행 여부
        List<Option> invoiceIssueOptions = codeService.getValueCmbxOptions("ORDER", "INVOICE_ISSUE");
        String htmlForInvoiceIssueOptionsCbx = HtmlCodeGenerator.generateComboboxForOptions("invoiceIssue", invoiceIssueOptions);
        mav.addObject("cbxInvoiceIssue", htmlForInvoiceIssueOptionsCbx);

        // [일반|공.구]
        List<Option> normalOrGroupOrderOptions = codeService.getValueCmbxOptions("ORDER", "NORMAL_GROUP");
        String htmlForNormalOrGroupOrderOptionsCbx = HtmlCodeGenerator.generateComboboxForOptions("normalOrGroupOrder", normalOrGroupOrderOptions);
        mav.addObject("cbxNormalOrGroupOrder", htmlForNormalOrGroupOrderOptionsCbx);

        // is_pickup_or_delivery
        List<Option> isPickupOrDeliveryOptions = codeService.getValueCmbxOptions("ORDER", "PICKUP_DELIVERY");
        String htmlForIsPickupOrDeliveryOptionsCbx = HtmlCodeGenerator.generateComboboxForOptions("isPickupOrDelivery", isPickupOrDeliveryOptions);
        mav.addObject("cbxIsPickupOrDelivery", htmlForIsPickupOrDeliveryOptionsCbx);

        // is_refund
        List<Option> isRefundOptions = codeService.getValueCmbxOptions("ORDER", "IS_REFUND");
        String htmlForIsRefundCbx = HtmlCodeGenerator.generateComboboxForOptions("isRefund", isRefundOptions);
        mav.addObject("cbxIsRefund", htmlForIsRefundCbx);

        // status_delivery
        List<Option> statusDeliveryOptions = codeService.getValueCmbxOptions("ORDER", "STATUS_DELIVERY");
        String htmlForStatusDeliveryCbx = HtmlCodeGenerator.generateComboboxForOptions("statusDelivery", statusDeliveryOptions);
        mav.addObject("cbxStatusDelivery", htmlForStatusDeliveryCbx);

        // status_payment
        List<Option> statusPaymentOptions = codeService.getValueCmbxOptions("ORDER", "STATUS_PAYMENT");
        String htmlForStatusPaymentCbx = HtmlCodeGenerator.generateComboboxForOptions("statusPayment", statusPaymentOptions);
        mav.addObject("cbxStatusPayment", htmlForStatusPaymentCbx);

        // 결재방법
        List<Option> paymentMethodOptions = codeService.getValueCmbxOptions("COMM", "PAYMENT_METHOD");
        String htmlForPaymentMethodCbx = HtmlCodeGenerator.generateComboboxForOptions("paymentMethod", paymentMethodOptions);
        mav.addObject("cbxPaymentMethod", htmlForPaymentMethodCbx);

        // 은행
        List<Option> paymentBankNameOptions = codeService.getValueCmbxOptions("COMM", "BANK");
        String htmlForPaymentBankNameCbx = HtmlCodeGenerator.generateComboboxForOptions("paymentBankName", paymentBankNameOptions);
        mav.addObject("cbxPaymentBankName", htmlForPaymentBankNameCbx);


        // 검색시작년월일이 존재하지 않을경우 현재날짜 기준으로 앞으로 예정된 일짜에 해당하는 목록만 가저오게한다.
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        mav.addObject("searchDateTo", df.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_MONTH, -30);
        mav.addObject("searchDateFrom", df.format(cal.getTime()));

        return mav;
    }

    @RequestMapping(value = "/orders", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getOrders(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);

        Map<String, Object> model = new HashMap<String, Object>();
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setLazyLoad(true); // 해당 제품 목록을 가저올 필요없다

        String userId = sessionUser.getUser().getUserId();
        String sellerName = request.getParameter("sellerName");
        String customerName = request.getParameter("customerName");
        String invoiceIssue = request.getParameter("invoiceIssue");
        String normalOrGroupOrder = request.getParameter("normalOrGroupOrder");
        String isPickupOrDelivery = request.getParameter("isPickupOrDelivery");
        String isRefund = request.getParameter("isRefund");
        String statusDelivery = request.getParameter("statusDelivery");
        String statusPayment = request.getParameter("statusPayment");
        String paymentMethod = request.getParameter("paymentMethod");
        String paymentBankName = request.getParameter("paymentBankName");

        String searchDateFrom = request.getParameter("searchDateFrom");
        String searchDateTo = request.getParameter("searchDateTo");

        if (StringUtils.isNotBlank(sellerName)) orderMaster.setSellerName(sellerName);
        if (StringUtils.isNotBlank(customerName)) orderMaster.setCustomerName(customerName);
        if (StringUtils.isNotBlank(invoiceIssue)) orderMaster.setInvoiceIssue(invoiceIssue);
        if (StringUtils.isNotBlank(normalOrGroupOrder)) orderMaster.setNormalOrGroupOrder(normalOrGroupOrder);
        if (StringUtils.isNotBlank(isPickupOrDelivery)) orderMaster.setIsPickupOrDelivery(isPickupOrDelivery);
        if (StringUtils.isNotBlank(isRefund)) orderMaster.setIsRefund(isRefund);
        if (StringUtils.isNotBlank(statusDelivery)) orderMaster.setStatusDelivery(statusDelivery);
        if (StringUtils.isNotBlank(statusPayment)) orderMaster.setStatusPayment(statusPayment);
        if (StringUtils.isNotBlank(paymentMethod)) orderMaster.setPaymentMethod(paymentMethod);
        if (StringUtils.isNotBlank(paymentBankName)) orderMaster.setPaymentBankName(paymentBankName);

        if (StringUtils.isBlank(searchDateFrom)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -30);
            orderMaster.setSearchDateFrom(df.format(cal.getTime()));
        } else {
            orderMaster.setSearchDateFrom(searchDateFrom);
        }
        if (StringUtils.isNotBlank(searchDateTo)) orderMaster.setSearchDateTo(searchDateTo);

        List<OrderMaster> orderList = null;

        // For Pagination
        orderMaster.setPagenationPage(getPage(request));
        orderMaster.setPagenationPageSize(getPageSize(request));

        try {
            Integer totalCount = 0;
            totalCount = orderMasterService.getTotalCntForGetOrderMasters(orderMaster);
            orderList = orderMasterService.getOrderMasters(orderMaster);

            model.put("list", orderList);
            model.put("totalCount", totalCount);

        } catch (Exception e) {
            e.printStackTrace();

            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/deliveryStatusToggle", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deliveryStatusToggle(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        String orderMasterId = request.getParameter("orderMasterId");

        try {
            OrderMaster orderMaster = new OrderMaster(orderMasterId);
            orderMaster.setLazyLoad(true); // 해당 제품 목록을 가저올 필요없다

            OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);

            String statusDelivery = newOrderMaster.getStatusDelivery();
            if (StringUtils.equalsIgnoreCase(statusDelivery, "BEFORE")) {
                orderMaster.setStatusDelivery("COMPLETE");
                orderMasterService.modifyStatusDelivery(orderMaster);

            } else if (StringUtils.equalsIgnoreCase(statusDelivery, "COMPLETE")) {
                orderMaster.setStatusDelivery("BEFORE");
                orderMasterService.modifyStatusDelivery(orderMaster);
            } else {

            }

            model.put("resultCode", "0");
            model.put("message", "1개 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping(value = "/paymentStatusToggle", produces = "application/json")
    @ResponseBody
    public Map<String, Object> paymentStatusToggle(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        Map<String, Object> model = new HashMap<String, Object>();

        String orderMasterId = request.getParameter("orderMasterId");

        try {
            OrderMaster orderMaster = new OrderMaster(orderMasterId);
            orderMaster.setLazyLoad(true); // 해당 제품 목록을 가저올 필요없다

            OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);

            String statusDelivery = newOrderMaster.getStatusPayment();
            if (StringUtils.equalsIgnoreCase(statusDelivery, "BEFORE_PAYMENT")) {
                orderMaster.setStatusPayment("ON_PROCESSING");
                orderMasterService.modifyStatusPayment(orderMaster);

            } else if (StringUtils.equalsIgnoreCase(statusDelivery, "ON_PROCESSING")) {
                orderMaster.setStatusPayment("COMPLETED");
                orderMasterService.modifyStatusPayment(orderMaster);

            } else if (StringUtils.equalsIgnoreCase(statusDelivery, "COMPLETED")) {
                orderMaster.setStatusPayment("BEFORE_PAYMENT");
                orderMasterService.modifyStatusPayment(orderMaster);
            } else {

            }

            model.put("resultCode", "0");
            model.put("message", "1개 의 정보가 반영되었습니다.");

        } catch (Exception e) {
            logger.info(e.getMessage());
            model.put("resultCode", "-1");
            model.put("message", e.getMessage());
        }

        return model;
    }

    @RequestMapping("/orderdetail")
    public ModelAndView orderdetail(HttpServletRequest request) throws Exception {
        SessionUserInfo sessionUser = authService.getSessionUserInfo(request);
        ModelAndView mav = new ModelAndView("tiles/admin/ordermgt/orderdetail");

        String userId = sessionUser.getUser().getUserId();
        String orderMasterId = request.getParameter("orderMasterId"); // Order Id

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setCreator(userId);
        orderMaster.setOrderMasterId(Integer.parseInt(orderMasterId));
        orderMaster.setLazyLoad(false);

        OrderMaster newOrderMaster = orderMasterService.getOrderMaster(orderMaster);

        mav.addObject("orderMaster", newOrderMaster);

        if (newOrderMaster.getPaymentAccTransferReceipt() != null) {
            AttachmentFile receiptFile = attachmentFileService.getAttachmentFile(new AttachmentFile(newOrderMaster.getPaymentAccTransferReceipt()));
            if (receiptFile == null) {
                orderMasterService.removePaymentReceiptFileInfo(Integer.parseInt(orderMasterId));
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

    @RequestMapping("/sendMessageForm")
    public ModelAndView sendMessageForm(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("tiles/admin/ordermgt/sendSMSForm");

        String type = request.getParameter("type");
        String receiverUserId = request.getParameter("receiverUserId");
        User receiverUser = userService.getUserInfo(receiverUserId);

        if (StringUtils.equalsIgnoreCase(type, "email")) {
            mav.setViewName("tiles/admin/ordermgt/sendEmailForm");

        } else if (StringUtils.equalsIgnoreCase(type, "sms")) {
            mav.setViewName("tiles/admin/ordermgt/sendSMSForm");
        } else {
            throw new Exception("Email 또는 SMS를 선택해주세요.");
        }

        if (StringUtils.isBlank(receiverUserId) || receiverUserId == null) throw new Exception("메시지의 수신자가 지정되어 있지 않습니다.");

        mav.addObject("type", type);
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
     * <p>
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
}
