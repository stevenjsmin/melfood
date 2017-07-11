package melfood.shopping.order;

import melfood.framework.common.dto.BaseDto;

import java.util.List;

/**
 * Created by Steven on 4/7/17.
 */
public class OrderMaster extends BaseDto {
    private int orderMasterId;
    private String sellerId;
    private String customerId;
    private String orderDatetime;
    private String invoiceIssue;
    private String invoiceIssueDatetime;
    private String customerName;
    private String customerMobile;
    private String customerPhone;
    private String customerEmail;
    private String customerAddressStreet;
    private String customerAddressSuburb;
    private String customerAddressPostcode;
    private String customerAddressState;
    private String customerOrderNote;
    private String sellerName;
    private String sellerMobile;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerAddressStreet;
    private String sellerAddressSuburb;
    private String sellerAddressPostcode;
    private String sellerAddressState;
    private String pickupAddressStreet;
    private String pickupAddressSuburb;
    private String pickupAddressPostcode;
    private String pickupAddressState;
    private String pickupAddressNote;
    private String isPickupOrDelivery;
    private String normalOrGroupOrder;
    private String groupPurchaseId;
    private String isRefund;
    private String refundDatetime;
    private String refundServiceCharge;
    private Float refundAmount;
    private String refundReason;
    private String statusPayment;
    private String statusDelivery;
    private String deliveryFromAddrStreet;
    private String deliveryFromAddrSuburb;
    private String deliveryFromAddrState;
    private String deliveryFromAddrPostcode;
    private String deliveryToAddrStreet;
    private String deliveryToAddrSuburb;
    private String deliveryToAddrState;
    private String deliveryToAddrPostcode;
    private Float deliveryDistance;
    private Float deliveryFeePerKm;
    private Float deliveryBaseCharge;
    private String paymentMethod;
    private String paymentBankName;
    private String paymentBankBsb;
    private String paymentAccountNo;
    private String paymentAccountHolderName;
    private String paymentDatetime;
    private Integer paymentAccTransferReceipt;
    private Float amountTotalProduct;
    private Float amountTotalProductOption;
    private Float amountTotalDelivery;
    private Float amountTotalDiscount;
    private Float amountTotalPaymentServiceCharge;
    private Float amountTotalCommission;
    private Float amountTotalExtra1;
    private Float amountTotalExtra2;
    private Float amountTotalExtra3;
    private Float amountTotalExtra4;
    private Float amountTotal;

    private List<OrderMasterProduct> orderMasterProduct;

    public OrderMaster() {
    }

    public OrderMaster(int orderMasterId) {
        this.orderMasterId = orderMasterId;
    }

    public OrderMaster(String orderMasterId) {
        this.orderMasterId = Integer.parseInt(orderMasterId);
    }


    public int getOrderMasterId() {
        return orderMasterId;
    }

    public void setOrderMasterId(int orderMasterId) {
        this.orderMasterId = orderMasterId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getInvoiceIssue() {
        return invoiceIssue;
    }

    public void setInvoiceIssue(String invoiceIssue) {
        this.invoiceIssue = invoiceIssue;
    }

    public String getInvoiceIssueDatetime() {
        return invoiceIssueDatetime;
    }

    public void setInvoiceIssueDatetime(String invoiceIssueDatetime) {
        this.invoiceIssueDatetime = invoiceIssueDatetime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddressStreet() {
        return customerAddressStreet;
    }

    public void setCustomerAddressStreet(String customerAddressStreet) {
        this.customerAddressStreet = customerAddressStreet;
    }

    public String getCustomerAddressSuburb() {
        return customerAddressSuburb;
    }

    public void setCustomerAddressSuburb(String customerAddressSuburb) {
        this.customerAddressSuburb = customerAddressSuburb;
    }

    public String getCustomerAddressPostcode() {
        return customerAddressPostcode;
    }

    public void setCustomerAddressPostcode(String customerAddressPostcode) {
        this.customerAddressPostcode = customerAddressPostcode;
    }

    public String getCustomerAddressState() {
        return customerAddressState;
    }

    public void setCustomerAddressState(String customerAddressState) {
        this.customerAddressState = customerAddressState;
    }

    public String getCustomerOrderNote() {
        return customerOrderNote;
    }

    public void setCustomerOrderNote(String customerOrderNote) {
        this.customerOrderNote = customerOrderNote;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerMobile() {
        return sellerMobile;
    }

    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerAddressStreet() {
        return sellerAddressStreet;
    }

    public void setSellerAddressStreet(String sellerAddressStreet) {
        this.sellerAddressStreet = sellerAddressStreet;
    }

    public String getSellerAddressSuburb() {
        return sellerAddressSuburb;
    }

    public void setSellerAddressSuburb(String sellerAddressSuburb) {
        this.sellerAddressSuburb = sellerAddressSuburb;
    }

    public String getSellerAddressPostcode() {
        return sellerAddressPostcode;
    }

    public void setSellerAddressPostcode(String sellerAddressPostcode) {
        this.sellerAddressPostcode = sellerAddressPostcode;
    }

    public String getSellerAddressState() {
        return sellerAddressState;
    }

    public void setSellerAddressState(String sellerAddressState) {
        this.sellerAddressState = sellerAddressState;
    }

    public String getPickupAddressStreet() {
        return pickupAddressStreet;
    }

    public void setPickupAddressStreet(String pickupAddressStreet) {
        this.pickupAddressStreet = pickupAddressStreet;
    }

    public String getPickupAddressSuburb() {
        return pickupAddressSuburb;
    }

    public void setPickupAddressSuburb(String pickupAddressSuburb) {
        this.pickupAddressSuburb = pickupAddressSuburb;
    }

    public String getPickupAddressPostcode() {
        return pickupAddressPostcode;
    }

    public void setPickupAddressPostcode(String pickupAddressPostcode) {
        this.pickupAddressPostcode = pickupAddressPostcode;
    }

    public String getPickupAddressState() {
        return pickupAddressState;
    }

    public void setPickupAddressState(String pickupAddressState) {
        this.pickupAddressState = pickupAddressState;
    }

    public String getPickupAddressNote() {
        return pickupAddressNote;
    }

    public void setPickupAddressNote(String pickupAddressNote) {
        this.pickupAddressNote = pickupAddressNote;
    }

    public String getIsPickupOrDelivery() {
        return isPickupOrDelivery;
    }

    public void setIsPickupOrDelivery(String isPickupOrDelivery) {
        this.isPickupOrDelivery = isPickupOrDelivery;
    }

    public String getNormalOrGroupOrder() {
        return normalOrGroupOrder;
    }

    public void setNormalOrGroupOrder(String normalOrGroupOrder) {
        this.normalOrGroupOrder = normalOrGroupOrder;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }

    public String getRefundDatetime() {
        return refundDatetime;
    }

    public void setRefundDatetime(String refundDatetime) {
        this.refundDatetime = refundDatetime;
    }

    public String getRefundServiceCharge() {
        return refundServiceCharge;
    }

    public void setRefundServiceCharge(String refundServiceCharge) {
        this.refundServiceCharge = refundServiceCharge;
    }

    public Float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getStatusDelivery() {
        return statusDelivery;
    }

    public void setStatusDelivery(String statusDelivery) {
        this.statusDelivery = statusDelivery;
    }

    public String getDeliveryFromAddrStreet() {
        return deliveryFromAddrStreet;
    }

    public void setDeliveryFromAddrStreet(String deliveryFromAddrStreet) {
        this.deliveryFromAddrStreet = deliveryFromAddrStreet;
    }

    public String getDeliveryFromAddrSuburb() {
        return deliveryFromAddrSuburb;
    }

    public void setDeliveryFromAddrSuburb(String deliveryFromAddrSuburb) {
        this.deliveryFromAddrSuburb = deliveryFromAddrSuburb;
    }

    public String getDeliveryFromAddrState() {
        return deliveryFromAddrState;
    }

    public void setDeliveryFromAddrState(String deliveryFromAddrState) {
        this.deliveryFromAddrState = deliveryFromAddrState;
    }

    public String getDeliveryFromAddrPostcode() {
        return deliveryFromAddrPostcode;
    }

    public void setDeliveryFromAddrPostcode(String deliveryFromAddrPostcode) {
        this.deliveryFromAddrPostcode = deliveryFromAddrPostcode;
    }

    public String getDeliveryToAddrStreet() {
        return deliveryToAddrStreet;
    }

    public void setDeliveryToAddrStreet(String deliveryToAddrStreet) {
        this.deliveryToAddrStreet = deliveryToAddrStreet;
    }

    public String getDeliveryToAddrSuburb() {
        return deliveryToAddrSuburb;
    }

    public void setDeliveryToAddrSuburb(String deliveryToAddrSuburb) {
        this.deliveryToAddrSuburb = deliveryToAddrSuburb;
    }

    public String getDeliveryToAddrState() {
        return deliveryToAddrState;
    }

    public void setDeliveryToAddrState(String deliveryToAddrState) {
        this.deliveryToAddrState = deliveryToAddrState;
    }

    public String getDeliveryToAddrPostcode() {
        return deliveryToAddrPostcode;
    }

    public void setDeliveryToAddrPostcode(String deliveryToAddrPostcode) {
        this.deliveryToAddrPostcode = deliveryToAddrPostcode;
    }

    public Float getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(Float deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public Float getDeliveryFeePerKm() {
        return deliveryFeePerKm;
    }

    public void setDeliveryFeePerKm(Float deliveryFeePerKm) {
        this.deliveryFeePerKm = deliveryFeePerKm;
    }

    public Float getDeliveryBaseCharge() {
        return deliveryBaseCharge;
    }

    public void setDeliveryBaseCharge(Float deliveryBaseCharge) {
        this.deliveryBaseCharge = deliveryBaseCharge;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentBankName() {
        return paymentBankName;
    }

    public void setPaymentBankName(String paymentBankName) {
        this.paymentBankName = paymentBankName;
    }

    public String getPaymentBankBsb() {
        return paymentBankBsb;
    }

    public void setPaymentBankBsb(String paymentBankBsb) {
        this.paymentBankBsb = paymentBankBsb;
    }

    public String getPaymentAccountNo() {
        return paymentAccountNo;
    }

    public void setPaymentAccountNo(String paymentAccountNo) {
        this.paymentAccountNo = paymentAccountNo;
    }

    public String getPaymentAccountHolderName() {
        return paymentAccountHolderName;
    }

    public void setPaymentAccountHolderName(String paymentAccountHolderName) {
        this.paymentAccountHolderName = paymentAccountHolderName;
    }

    public String getPaymentDatetime() {
        return paymentDatetime;
    }

    public void setPaymentDatetime(String paymentDatetime) {
        this.paymentDatetime = paymentDatetime;
    }

    public Float getAmountTotalProduct() {
        return amountTotalProduct;
    }

    public void setAmountTotalProduct(Float amountTotalProduct) {
        this.amountTotalProduct = amountTotalProduct;
    }

    public Float getAmountTotalProductOption() {
        return amountTotalProductOption;
    }

    public void setAmountTotalProductOption(Float amountTotalProductOption) {
        this.amountTotalProductOption = amountTotalProductOption;
    }

    public Float getAmountTotalDelivery() {
        return amountTotalDelivery;
    }

    public void setAmountTotalDelivery(Float amountTotalDelivery) {
        this.amountTotalDelivery = amountTotalDelivery;
    }

    public Float getAmountTotalDiscount() {
        return amountTotalDiscount;
    }

    public void setAmountTotalDiscount(Float amountTotalDiscount) {
        this.amountTotalDiscount = amountTotalDiscount;
    }

    public Float getAmountTotalPaymentServiceCharge() {
        return amountTotalPaymentServiceCharge;
    }

    public void setAmountTotalPaymentServiceCharge(Float amountTotalPaymentServiceCharge) {
        this.amountTotalPaymentServiceCharge = amountTotalPaymentServiceCharge;
    }

    public Float getAmountTotalCommission() {
        return amountTotalCommission;
    }

    public void setAmountTotalCommission(Float amountTotalCommission) {
        this.amountTotalCommission = amountTotalCommission;
    }

    public Float getAmountTotalExtra1() {
        return amountTotalExtra1;
    }

    public void setAmountTotalExtra1(Float amountTotalExtra1) {
        this.amountTotalExtra1 = amountTotalExtra1;
    }

    public Float getAmountTotalExtra2() {
        return amountTotalExtra2;
    }

    public void setAmountTotalExtra2(Float amountTotalExtra2) {
        this.amountTotalExtra2 = amountTotalExtra2;
    }

    public Float getAmountTotalExtra3() {
        return amountTotalExtra3;
    }

    public void setAmountTotalExtra3(Float amountTotalExtra3) {
        this.amountTotalExtra3 = amountTotalExtra3;
    }

    public Float getAmountTotalExtra4() {
        return amountTotalExtra4;
    }

    public void setAmountTotalExtra4(Float amountTotalExtra4) {
        this.amountTotalExtra4 = amountTotalExtra4;
    }

    public Float getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Float amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Integer getPaymentAccTransferReceipt() {
        return paymentAccTransferReceipt;
    }

    public void setPaymentAccTransferReceipt(Integer paymentAccTransferReceipt) {
        this.paymentAccTransferReceipt = paymentAccTransferReceipt;
    }

    public List<OrderMasterProduct> getOrderMasterProduct() {
        return orderMasterProduct;
    }

    public void setOrderMasterProduct(List<OrderMasterProduct> orderMasterProduct) {
        this.orderMasterProduct = orderMasterProduct;
    }

    public String getGroupPurchaseId() {
        return groupPurchaseId;
    }

    public void setGroupPurchaseId(String groupPurchaseId) {
        this.groupPurchaseId = groupPurchaseId;
    }

}


