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
            if('${pageType}' == 'group') {
                document.location.href = "/admin/ordermgt/grouppurchase/Main.yum";

            } else if('${pageType}' == 'all') {
                document.location.href = "/admin/ordermgt/Main.yum";
            }
        }
        function getInvoice() {
            underDevelopment();
        }
    </script>
    <script type="text/javascript">
        function sendMessagePopup(receiverUserId, type){

            $("#sendMessagePopup").kendoWindow({
                content: "/admin/ordermgt/sendMessageForm.yum?receiverUserId=" + receiverUserId + "&type=" + type ,
                actions: [ "Minimize", "Maximize","Close" ],
                title: "SEND MESSAGE",
                modal: true,
                iframe: true
            });

            var popup_dialog = $("#sendMessagePopup").data("kendoWindow");
            popup_dialog.setOptions({
                pinned: true,
                width: 650,height: 300,
                open: function (e) {
                    this.wrapper.css({ top: 300 });
                }
            });
            popup_dialog.center().open();

        }
        function closeSendMessagePopup() {
            var win_dialog = $("#sendMessagePopup").data("kendoWindow");
            win_dialog.close();
        }
    </script>

    <script type="text/javascript">
        function deleteOrderMaster(){

            BootstrapDialog.confirm({
                title: 'WARNING  :: 호주가 즐거운 이유, 멜푸드!!',
                message: '정말 주문정보를 영구히 삭제하시겠습니까?',
                type: BootstrapDialog.TYPE_WARNING, // [TYPE_DEFAULT | TYPE_INFO | TYPE_PRIMARY | TYPE_SUCCESS | TYPE_WARNING | TYPE_DANGER]
                closable: true, // Default value is false
                draggable: true, // Default value is false
                btnCancelLabel: 'Cancel', // Default value is 'Cancel',
                btnOKLabel: 'OK', // Default value is 'OK',
                btnOKClass: 'btn-warning', // If you didn't specify it, dialog type will be used,
                callback: function(result) {
                    if(result) {
                        $.ajax({
                            url  : "/admin/ordermgt/deleteOrderMaster.yum",
                            data      : {
                                orderMasterId : "${orderMaster.orderMasterId}"
                            },
                            success : callbackDeleteOrderMaster
                        });
                    }
                }
            });
        }

        function callbackDeleteOrderMaster(data) {
            var message = data.message;
            var resultCode = data.resultCode;

            if (resultCode != "0") {
                warningPopup(data.message);
            } else {
                goList();
            }
        }

    </script>
</head>

<body>
<div id="sendMessagePopup"></div>
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
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                     <span style="font-size: 15px;font-weight: bold;color: #FFFFFF;">
                                        결재정보 :
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
                                                            첨부된 영수증 파일이 있습니다 : ${receiptFileName} <a href="javascript:downloadFile('${receiptFileNo}');"><img src="/resources/css/images/gic/ic_file_download_black_18dp_1x.png"/></a>
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

        <!-- 주문내용 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #FFFFFF;padding-left: 20px;">주요 주문</span></a>
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

                        <!-- 배송서비스 내역 -->
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
                                <th style="text-align: left;color: #575758;padding-left: 20px;">할인 (<i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i><i class="fa fa-arrow-down" aria-hidden="true" style="color: #575758;"></i>)</th>
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
                            <thead>
                            <tr>
                                <th style="text-align: left;color: #575758;padding-left: 20px;">Total</th>
                                <th style="width: 130px;text-align: right;color: #575758;"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td></td>
                                <td style="color: #737273;text-align: right;font-weight: bold;font-size: 15px;width: 100px;text-decoration: underline;">${orderMaster.amountTotal} $</td>
                            </tr>
                            </tbody>
                        </table>

                        <!-- 주문메모 -->
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="width: 200px;text-align:left;color: #575758;padding-left: 20px;">주문메모</th>
                                <th style="text-align: right;color: #575758;"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td></td>
                                <td style="color: #737273;font-weight: bold;font-size: 13px;">${orderMaster.customerOrderNote}</td>
                            </tr>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>

        <!-- 판매자 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                    <span style="font-size: 15px;font-weight: bold;color: #FFFFFF;">판매자 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 10px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <colgroup>
                                    <col width="200px" />
                                    <col width="300px" />
                                    <col width="200px" />
                                    <col width="300px" />
                                </colgroup>
                                <tr>
                                    <td class="label">판매자 </td>
                                    <td class="value" colspan="3"><span style="color: #dc0a5e;font-weight: bold;font-size: 15px;">${orderMaster.sellerName}</span></td>
                                </tr>
                                <tr>
                                    <td class="label">Mobile</td>
                                    <td class="value">
                                        ${orderMaster.sellerMobile}
                                        <c:choose>
                                            <c:when test="${orderMaster.sellerMobile != null}">
                                                <a href="javascript:sendMessagePopup('${orderMaster.sellerMobile}', 'sms');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                            </c:when>
                                            <c:otherwise>${orderMaster.sellerMobile}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="label">Email</td>
                                    <td class="value">
                                        ${orderMaster.sellerEmail}
                                        <c:choose>
                                            <c:when test="${orderMaster.sellerEmail != null}">
                                                <a href="javascript:sendMessagePopup('${orderMaster.sellerMobile}', 'email');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                            </c:when>
                                            <c:otherwise>${orderMaster.sellerEmail}</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">Address</td>
                                    <td class="value" colspan="3">${orderMaster.sellerAddressStreet} ${orderMaster.sellerAddressSuburb} ${orderMaster.sellerAddressPostcode} ${orderMaster.sellerAddressState}</td>
                                </tr>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>


        <!-- 주문자 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                    <span style="font-size: 15px;font-weight: bold;color: #FFFFFF;">주문자 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 10px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <colgroup>
                                    <col width="200px" />
                                    <col width="300px" />
                                    <col width="200px" />
                                    <col width="300px" />
                                </colgroup>
                                <tr>
                                    <td class="label">이름</td>
                                    <td class="value">${orderMaster.customerName}</td>
                                    <td class="label">모바일</td>
                                    <td class="value">${orderMaster.customerMobile}
                                        <c:choose>
                                            <c:when test="${orderMaster.customerMobile != null}">
                                                <a href="javascript:sendMessagePopup('${orderMaster.customerId}', 'sms');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                            </c:when>
                                            <c:otherwise><b>${orderMaster.customerMobile}</b></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">Email</td>
                                    <td class="value">${orderMaster.customerEmail}
                                        <c:choose>
                                            <c:when test="${orderMaster.customerEmail != null}">
                                                <a href="javascript:sendMessagePopup('${orderMaster.customerId}', 'email');"><i class="fa fa-commenting" aria-hidden="true"></i></a>
                                            </c:when>
                                            <c:otherwise>${orderMaster.customerEmail}</c:otherwise>
                                        </c:choose>

                                    </td>
                                    <td class="label">주소</td>
                                    <td class="value">${orderMaster.customerAddressStreet} ${orderMaster.customerAddressSuburb} ${orderMaster.customerAddressPostcode} ${orderMaster.customerAddressState}</td>
                                </tr>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>

        <!-- 기타 정보 -->
        <div class="row">
            <div class="col-sm-12" style="padding-right: 20px;padding-left: 10px;">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <table style="width: 100%;">
                            <tr>
                                <td style="text-align: left;padding-left: 20px;">
                                    <span style="font-size: 15px;font-weight: bold;color: #FFFFFF;">기타 정보</span></a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 10px;">

                        <div style="padding-bottom: 10px;">
                            <table class="detail_table">
                                <colgroup>
                                    <col width="200px" />
                                    <col width="300px" />
                                    <col width="200px" />
                                    <col width="300px" />
                                </colgroup>
                                <tr>
                                    <td colspan="4" style="text-align: left;">
                                        <span class="subtitle" style="color: #737273;text-align: left;">상품 배송지정보</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">배송 출발지 주소</td>
                                    <td class="value" colspan="3">${orderMaster.deliveryFromAddrStreet} ${orderMaster.deliveryFromAddrSuburb} ${orderMaster.deliveryFromAddrState} ${orderMaster.deliveryFromAddrPostcode}</td>
                                </tr>
                                <tr>
                                    <td class="label">배송 도착지 주소</td>
                                    <td class="value" colspan="3">${orderMaster.deliveryToAddrStreet} ${orderMaster.deliveryToAddrSuburb} ${orderMaster.deliveryToAddrState} ${orderMaster.deliveryToAddrPostcode}</td>
                                </tr>

                                <tr>
                                    <td colspan="4" style="text-align: left;">
                                        <br/><br/>
                                        <span class="subtitle" style="color: #737273;text-align: left;">상품 픽업 장소 정보</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">상품픽업 주소</td>
                                    <td class="value" colspan="3">${orderMaster.pickupAddressStreet} ${orderMaster.pickupAddressSuburb} ${orderMaster.pickupAddressPostcode} ${orderMaster.pickupAddressState}</td>
                                </tr>

                                <tr>
                                    <td colspan="4" style="text-align: left;">
                                        <br/><br/>
                                        <span class="subtitle" style="color: #737273;text-align: left;">인보이스 정보</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">발행여부</td>
                                    <td class="value">
                                        <c:choose>
                                            <c:when test="${orderMaster.invoiceIssue == 'Y'}">인보이스 발행됨</c:when>
                                            <c:when test="${orderMaster.invoiceIssue == 'N'}">인보이스 미발행</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="label">발행일자</td>
                                    <td class="value">${orderMaster.invoiceIssueDatetime}</td>
                                </tr>

                                <tr>
                                    <td colspan="4" style="text-align: left;">
                                        <br/><br/>
                                        <span class="subtitle" style="color: #737273;text-align: left;">환불처리 정보</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">환불여부</td>
                                    <td class="value"><b>
                                        <c:choose>
                                            <c:when test="${orderMaster.isRefund == 'Y'}">환불처리</c:when>
                                            <c:when test="${orderMaster.isRefund == 'N'}">미 환불</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                        </b>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="label">환불처일시</td>
                                    <td class="value">${orderMaster.refundDatetime}</td>
                                    <td class="label">환불 서비스비(패널티)</td>
                                    <td class="value"><b>${orderMaster.refundAmount}</b></td>
                                </tr>
                                <tr>
                                    <td class="label">환불이유</td>
                                    <td class="value" colspan="3">${orderMaster.refundReason}</td>
                                </tr>

                                <tr>
                                    <td colspan="4" style="text-align: left;">
                                        <br/><br/>
                                        <span class="subtitle" style="color: #737273;text-align: left;">기타 정보</span>
                                        <hr class="subtitle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">픽업/배달서비스</td>
                                    <td class="value">
                                        <c:choose>
                                            <c:when test="${orderMaster.isPickupOrDelivery == 'D'}">배송서비스 이용</c:when>
                                            <c:when test="${orderMaster.isPickupOrDelivery == 'P'}">픽업</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="label">일반/공구</td>
                                    <td class="value">
                                        <c:choose>
                                            <c:when test="${orderMaster.normalOrGroupOrder == 'G'}">공동구매</c:when>
                                            <c:when test="${orderMaster.normalOrGroupOrder == 'P'}">일반</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">공구인경우 공구번호</td>
                                    <td class="value">${orderMaster.groupPurchaseId}</td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>


<!-- 화면 하단 버튼 -->
<div class="row">
    <div class="col-sm-12" style="padding-bottom: 30px;padding-top: 10px;padding-right: 20px;">
        <table class="action_button_table">
            <tr>
                <td style="text-align: right;">
                    <button type="button" class="btn btn-danger btn-sm" onclick="deleteOrderMaster()"><i class="fa fa-trash-o" aria-hidden="true"></i> 삭제</button> &nbsp;&nbsp;
                    <button type="button" class="btn btn-success btn-sm" onclick="goList()"><i class="fa fa-list" aria-hidden="true"></i> 이전화면</button>
                </td>
            </tr>
        </table>
    </div>
</div>


</body>
</html>