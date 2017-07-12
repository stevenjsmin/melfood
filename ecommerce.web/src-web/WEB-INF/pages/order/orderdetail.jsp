<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="melfood.framework.Ctx" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="/resources/js/melfood/customer/customer.mypage.myinfo.js?ver=<%=Ctx.releaseVersion%>"></script>
    <script type="text/javascript">
        $(document).ready(function () {
        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <script type="text/javascript">
        function goList() {
            document.location.href = "/customer/mypage/myorder/Main.yum";
        }
        function getInvoice() {
            underDevelopment();
        }
    </script>
</head>

<body>
<div id="askQuestionPopup"></div>
<div id="purchaseOrganizerPopup"></div>
<div class="row">
    <div class="col-sm-9" style="padding-bottom: 10px;padding-right: 40px;">
        <table style="width: 100%;">
            <tr>
                <td style="text-align: right;"><h3 style="color: #6F0E06;padding-top: 0px;margin-bottom: 0px;margin-top: 0px;">${fn:substring(orderMaster.createDatetime, 0, 16)} </h3></td>
            </tr>
        </table>
    </div>
</div>

<div class="row">
    <div class="col-sm-9">

        <!-- 결재정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                     <span style="font-size: 15px;font-weight: bold;color: #575758;">
                                        <c:choose>
                                            <c:when test="${orderMaster.statusPayment == 'BEFORE_PAYMENT'}"> 결재 전</c:when>
                                            <c:when test="${orderMaster.statusPayment == 'ON_PROCESSING'}"> 결재 처리중</c:when>
                                            <c:when test="${orderMaster.statusPayment == 'COMPLETED'}"> 결재 완료</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                     </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 20px;padding-top: 20px;">

                        <c:choose>
                            <c:when test="${orderMaster.paymentMethod == 'ACCOUNT_TRANSFER'}">
                                <!-- 계좌이체 결재인경우 수금받을 계좌정보 -->
                                <div id="detailinfo_for_acc_transfer" style="padding-bottom: 10px;">
                                    <table class="detail_table">
                                        <tr>
                                            <td colspan="3" style="text-align: right;">
                                                <span class="subtitle" style="color: #737273;text-align: right;">송금 계좌정보</span>
                                                <hr class="subtitle"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px; text-align: right;color: #8D9999;">은행명</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #514747;" id="payment_bankName">${orderMaster.paymentBankName}</td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px; text-align: right;color: #8D9999;">BSB - Account no</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #514747;" id="payment_bankBsbBankAccountNo">${orderMaster.paymentBankBsb} - ${orderMaster.paymentAccountNo}8</td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px; text-align: right;color: #8D9999;">Account Holder</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #8D9999;" id="payment_bankAccountOwnerName">${orderMaster.paymentAccountHolderName}</td>
                                        </tr>
                                    </table>
                                </div>


                                <!-- 영수증 업로드 -->
                                <div id="acc_transfer_receipt_upload" style="margin-bottom: 10px;">
                                    <table style="width: 600px;">
                                        <tr>
                                            <td style="color: #6F0E06;padding-top: 0px;padding-left: 20px;">
                                                <div id="paymentAccTransferReceipt">
                                                    <c:choose>
                                                        <c:when test="${orderMaster.paymentAccTransferReceipt != null && receiptFileNo != null}">
                                                            첨부해주신 영수증 파일이 있습니다 : ${receiptFileName} <a href="javascript:downloadFile('${receiptFileNo}');"><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"/></a>
                                                        </c:when>
                                                        <c:otherwise> *** 현재 첨부된 영수증이 없습니다.</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${orderMaster.paymentMethod == 'CASH'}">
                                <div style="padding-bottom: 20px;">
                                    <table width="100%;" style="font-size: 5px;">
                                        <tr>
                                            <td style="text-align: right;">
                                                <span class="subtitle" style="color: #737273;text-align: right;">만나서결재</span>
                                                <hr class="subtitle"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="text-align: center;font-size: 14px;color: #C5C6C9;">만나서 CASH 지불</td>
                                        </tr>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${orderMaster.paymentMethod == 'CREDIT_CARD'}"></c:when>
                            <c:when test="${orderMaster.paymentMethod == 'PAYPAL'}"></c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>

                    </div>

                </div>
            </div>
        </div>

        <!-- 판매자 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;">판매자 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">판매자</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.sellerName}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">Mobile</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.sellerMobile}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">Email</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #8D9999;">${orderMaster.sellerEmail}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">Address</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #8D9999;">${orderMaster.sellerAddressStreet} ${orderMaster.sellerAddressSuburb} ${orderMaster.sellerAddressPostcode} ${orderMaster.sellerAddressState}</td>
                                </tr>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>

        <!-- 주문내용 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">배송서비스</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <!-- 주문상품 목록 -->
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="color: #575758;padding-left: 20px;">주문 상품</th>
                                <th style="color: #575758;">옵션</th>
                                <th style="width: 200px;text-align: right;color: #575758;">단가</th>
                                <th style="width: 130px;text-align: right;color: #575758;">수량</th>
                                <th style="width: 130px;text-align: right;color: #575758;">소계</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="orderMasterProduct" items="${orderMaster.orderMasterProduct}" varStatus="count" begin="0">
                                <tr>
                                    <td style="color: #002F00;padding-left: 20px;">${orderMasterProduct.productName}</td>
                                    <td style="color: #797979;">
                                        <c:forEach var="productOption" items="${orderMasterProduct.orderMasterProductOptionList}" varStatus="count1" begin="0">
                                            <span style="color: #b3b3b3;">${productOption.optionName}</span> : ${productOption.optionValue} &nbsp;&nbsp;&nbsp;
                                        </c:forEach>
                                    </td>
                                    <td style="color: #797979;text-align: right;">${orderMasterProduct.unitPrice} $</td>
                                    <td style="color: #797979;text-align: right;">${orderMasterProduct.numberOfOrder}</td>
                                    <td style="color: #797979;text-align: right;">${orderMasterProduct.numberOfOrder * orderMasterProduct.unitPrice} $</td>
                                </tr>
                            </c:forEach>

                            <tr>
                                <td style="color: #575758; text-align: right;font-weight: bold;" colspan="4">상품주문 합</td>
                                <td style="color: #575758;text-align: right;font-weight: bold;">${orderMaster.amountTotalProduct} $</td>
                            </tr>
                            </tbody>
                        </table>


                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="color: #575758;padding-left: 20px;">배송서비스 - 거리(Km)</th>
                                <th style="width: 170px;text-align: right;color: #575758;">기본서비스 Charge</th>
                                <th style="width: 120px;text-align: right;color: #575758;">요금/Km</th>
                                <th style="width: 200px;text-align: right;color: #575758;">소계</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${orderMaster.isPickupOrDelivery == 'D'}">
                                    <tr>
                                        <td style="color: #002F00;padding-left: 20px;">${orderMaster.deliveryDistance} KM <span style="font-size: 10px;color: #b3b3b3;">(공.구 장소로부터의 거리입니다 : Avoid toll drive way)</span></td>
                                        <td style="color: #797979;text-align: right;">${orderMaster.deliveryBaseCharge} $</td>
                                        <td style="color: #797979;text-align: right;">${orderMaster.deliveryFeePerKm} $</td>
                                        <td style="color: #797979;text-align: right;">${orderMaster.amountTotalDelivery} $</td>
                                    </tr>
                                </c:when>
                                <c:when test="${orderMaster.isPickupOrDelivery == 'P'}">
                                    <tr>
                                        <td style="color: #8B8A8A;text-align: right;" colspan="4">배송서비스 이용 않함</td>
                                    </tr>
                                </c:when>
                            </c:choose>
                            </tbody>
                        </table>

                        <!-- 할인내역 -->
                        <table class="table table-striped" border="0">
                            <thead>
                            <tr>
                                <th style="text-align: right;color: #575758;">할인 (<i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i><i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i>)</th>
                                <th style="width: 130px;text-align: right;color: #575758;">할인금액</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="color: #797979;text-align: right;"> -</td>
                                <td style="color: #797979;text-align: right;width: 90px;"> - ${orderMaster.amountTotalDiscount} $</td>
                            </tr>
                            </tbody>
                        </table>

                        <!-- Total -->
                        <table class="table table-striped">
                            <tbody>
                            <tr>
                                <td></td>
                                <td style="color: #737273;text-align: right;font-weight: bold;font-size: 15px;">Total</td>
                                <td style="color: #737273;text-align: right;font-weight: bold;font-size: 15px;width: 100px;text-decoration: underline;">${orderMaster.amountTotal} $</td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>

        <!-- 주문메모 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">주문메모</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">
                        ${orderMaster.customerOrderNote}
                    </div>
                </div>
            </div>
        </div>

        <!-- 상품 배송지정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">상품 배송지정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">배송 출발지 주소</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">
                                        ${orderMaster.deliveryFromAddrStreet} ${orderMaster.deliveryFromAddrSuburb} ${orderMaster.deliveryFromAddrState} ${orderMaster.deliveryFromAddrPostcode}
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">배송 도착지 주소</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">
                                        ${orderMaster.deliveryToAddrStreet} ${orderMaster.deliveryToAddrSuburb} ${orderMaster.deliveryToAddrState} ${orderMaster.deliveryToAddrPostcode}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 상품 픽업지정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">상품픽업지 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">상품픽업지 주소</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">
                                        ${orderMaster.pickupAddressStreet} ${orderMaster.pickupAddressSuburb} ${orderMaster.pickupAddressPostcode} ${orderMaster.pickupAddressState}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 인보이스 발행정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">인보이스 발행정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">발행여부</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.invoiceIssue}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">발행일자</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.invoiceIssueDatetime}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 주문자 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">주문자 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">이름</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.customerName}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">모바일</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.customerMobile}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">Email</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.customerEmail}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">주소</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.customerAddressStreet}, ${orderMaster.customerAddressSuburb}, ${orderMaster.customerAddressPostcode} ${orderMaster.customerAddressState}</td>
                                </tr>

                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 환불처리 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">환불처리 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">환불여부</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.isRefund}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">환불처일시</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.refundDatetime}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">환불이유</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.refundReason}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">환불 서비스</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.refundAmount}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">환불이유</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.refundReason}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 기타 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;padding-left: 20px;">기타 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">픽업/배달서비스</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.isPickupOrDelivery}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">일반/공구</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.normalOrGroupOrder}</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px; text-align: right;color: #8D9999;">공구인경우 공구번호</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.groupPurchaseId}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>



<!-- 화면 하단 버튼 -->
<div class="row">
    <div class="col-sm-9" style="padding-bottom: 30px;padding-top: 20px;">
        <table class="action_button_table">
            <tr>
                <td style="text-align: right;padding-right: 15px;">
                    <button type="button" class="btn btn-warning btn-sm" onclick="getInvoice()"><i class="fa fa-file-o" aria-hidden="true"></i> 인보이스발행</button>
                    &nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success btn-sm" onclick="goList()"><i class="fa fa-list" aria-hidden="true"></i> 이전화면</button>
                </td>
            </tr>
        </table>
    </div>
</div>


</body>
</html>