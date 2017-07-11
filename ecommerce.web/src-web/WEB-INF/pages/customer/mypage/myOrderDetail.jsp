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
            $("#fileUpload").kendoUpload({
                async: {
                    saveUrl: "/framework/usermanager/profileImageUpload.~~~~yum?userId=${user.userId}",
                    removeUrl: "/framework/usermanager/removeFile.~~~~~yum",
                    removeField: "removeFile",
                    autoUpload: true,
                    batch: true,
                },
                showFileList: false,
                localization: {
                    statusFailed: "Failed to file upload. Please check it again and retry.",
                    uploadSelectedFiles: "Upload file(s)"
                },
                multiple: false,
                complete: onComplete,
                error: onError,
                success: onSuccess
            });

            function onComplete(e) {
                //search();
            }

            function onSuccess(e) {
                var data = e.response;
                if (data.resultCode != '0') {
                    warningPopup("<b>프로파일 이미지 갱신 실패 : </b>" + data.message);
                    $("#profilePhotoId", parent.document).attr("src", "/resources/image/profile_photo.png");
                } else {
                    infoPopup("정상적으로 프로파일 이미지가 갱신되었습니다. ");
                    $("#profilePhotoId", parent.document).attr("src", "/img/?f=" + data.user.profilePhotoId);
                }
            }

            function onError(e) {
                var files = e.files;
                if (e.operation == "upload") {
                    warningPopup("Failed to upload : " + files[0].name);
                }
            }

        }); // END of document.ready() ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    </script>
    <script type="text/javascript">
        function askQuestion(receiverUserId) {

            $("#askQuestionPopup").kendoWindow({
                content: "/framework/communicationmanager/sendMessageFormWIthHistory.yum?receiverUserId=" + receiverUserId,
                actions: ["Minimize", "Maximize", "Close"],
                title: "SEND MESSAGE",
                modal: true,
                iframe: true
            });

            var popup_dialog = $("#askQuestionPopup").data("kendoWindow");
            popup_dialog.setOptions({
                pinned: true,
                width: 650, height: 500,
                open: function (e) {
                    this.wrapper.css({top: 100});
                }
            });
            popup_dialog.center().open();

        }
        function closeAskQuestionPopupPopup() {
            var win_dialog = $("#askQuestionPopup").data("kendoWindow");
            win_dialog.close();
        }
        function goList() {
            document.location.href = "/customer/mypage/myorder/Main.yum";
        }
    </script>
</head>

<body>
<div id="askQuestionPopup"></div>
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
                                <td style="text-align: left;">
                                     <span style="font-size: 15px;font-weight: bold;color: #575758;">
                                        <c:choose>
                                            <c:when test="${orderMaster.statusPayment == 'BEFORE_PAYMENT'}"> 결재 전</c:when>
                                            <c:when test="${orderMaster.statusPayment == 'BEFORE_PAYMENT'}"> 결재 처리중</c:when>
                                            <c:when test="${orderMaster.statusPayment == 'BEFORE_PAYMENT'}"> 결재 완료</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                     </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 20px;padding-top: 20px;">

                        <c:choose>
                            <c:when test="${orderMaster.paymentMethod == '계좌이체'}">
                                <!-- 계좌이체 결재인경우 수금받을 계좌정보 -->
                                <div id="detailinfo_for_acc_transfer" style="padding-bottom: 20px;">
                                    <table width="100%;" style="font-size: 5px;">
                                        <tr>
                                            <td colspan="3" style="text-align: right;">
                                                <span class="subtitle" style="color: #737273;text-align: right;">송금 계좌정보</span>
                                                <hr class="subtitle"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">은행명</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #514747;" id="payment_bankName">${orderMaster.paymentBankName}</td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">BSB - Account no</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #514747;" id="payment_bankBsbBankAccountNo">${orderMaster.paymentBankBsb} - ${orderMaster.paymentAccountNo}8</td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Account Holder</td>
                                            <td style="width: 20px;text-align: center">:</td>
                                            <td style="color: #8D9999;" id="payment_bankAccountOwnerName">${orderMaster.paymentAccountHolderName}</td>
                                        </tr>
                                    </table>
                                </div>

                                <!-- 계좌이체 결재인경우 영수증 첨부 안내 -->
                                <div id="notice_for_acc_transfer">
                                    <div class="alert alert-warning" style="border-left: 6px solid #F15F4C;padding-left: 5px;">송금하신 후 <span style="text-decoration: underline;">영수증 이미지를 업로드</span>해 주시면 빠르게 처리해 드리겠습니다.</div>
                                </div>

                                <!-- 영수증 업로드 -->
                                <div id="acc_transfer_receipt_upload" style="margin-bottom: 10px;">
                                    <table style="width: 600px;">
                                        <tr>
                                            <td>
                                                <table class="detail_table">
                                                    <tr>
                                                        <td class="label">계좌이체 영수증 첨부하기</td>
                                                        <td class="value" style="padding-right: 0px;"><input type="file" name="files" id="fileUpload"/></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${orderMaster.paymentMethod == '만나서결재'}">
                                <div style="padding-bottom: 20px;">
                                    <table width="100%;" style="font-size: 5px;">
                                        <tr>
                                            <td style="text-align: right;">
                                                <span class="subtitle" style="color: #737273;text-align: right;">만나서결재</span>
                                                <hr class="subtitle"/>
                                            </td>
                                        </tr>
                                        </tr>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${orderMaster.paymentMethod == '신용카드결재'}"></c:when>
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
                                <td style="text-align: left;">
                                    <span style="font-size: 15px;font-weight: bold;color: #575758;">판매자 정보</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-body" style="padding-left: 10px;padding-right: 10px;padding-bottom: 10px;padding-top: 20px;">

                        <div style="padding-bottom: 10px;">
                            <table width="100%;" style="font-size: 5px;">
                                <tr>
                                    <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">판매자</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">
                                        ${orderMaster.sellerName} &nbsp; &nbsp;
                                        <i class="fa fa-comment" aria-hidden="true"></i>
                                        <a href="javascript:askQuestion('${orderMaster.sellerId}')" style="color: #69B7F5;"> ?!! 물어보세요</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Mobile</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #514747;">${orderMaster.sellerMobile}</td>
                                </tr>
                                <tr>
                                    <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Email</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #8D9999;">${orderMaster.sellerEmail}</td>
                                </tr>
                                <tr>
                                    <td style="width: 100px; text-align: right;font-size: 11px;color: #8D9999;">Address</td>
                                    <td style="width: 20px;text-align: center">:</td>
                                    <td style="color: #8D9999;">${orderMaster.sellerAddressStreet} ${orderMaster.sellerAddressSuburb} ${orderMaster.sellerAddressPostcode} ${orderMaster.sellerAddressState}</td>
                                </tr>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>

        <!-- 주문상품 목록 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; ">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th style="color: #575758;">주문 상품</th>
                        <th style="color: #575758;">옵션</th>
                        <th style="width: 100px;text-align: right;color: #575758;">단가</th>
                        <th style="width: 130px;text-align: right;color: #575758;">수량</th>
                        <th style="width: 130px;text-align: right;color: #575758;">소계</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="orderMasterProduct" items="${orderMaster.orderMasterProduct}" varStatus="count" begin="0">
                        <tr>
                            <td style="color: #002F00;">${orderMasterProduct.productName}</td>
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
            </div>
        </div>

        <!-- 배송서비스 -->
        <c:choose>
            <c:when test="${orderMaster.isPickupOrDelivery == 'D'}">
                <div class="row">
                    <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px; ">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="color: #575758;">배송서비스 - 거리(Km)</th>
                                <th style="width: 170px;text-align: right;color: #575758;">기본서비스 Charge</th>
                                <th style="width: 120px;text-align: right;color: #575758;">요금/Km</th>
                                <th style="width: 100px;text-align: right;color: #575758;">소계</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${orderMaster.isPickupOrDelivery == 'D'}">
                                    <tr>
                                        <td style="color: #002F00;">${orderMaster.deliveryDistance} KM <span style="font-size: 10px;color: #b3b3b3;">(공.구 장소로부터의 거리입니다 : Avoid toll drive way)</span></td>
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
                    </div>
                </div>
            </c:when>
        </c:choose>

        <!-- 할인내역 -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px; ">
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
            </div>
        </div>


        <!-- Total -->
        <div class="row">
            <div class="col-sm-12" style="padding-left: 10px; padding-top: 20px;padding-bottom: 20px;">
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


<div class="row">
    <div class="col-sm-9" style="padding-bottom: 30px;">
        <table class="action_button_table">
            <tr>
                <td style="text-align: right;padding-right: 15px;">
                    <button type="button" class="btn btn-success btn-sm" onclick="goList()"><i class="fa fa-list" aria-hidden="true"></i> 이전화면</button>
                </td>
            </tr>
        </table>
    </div>
</div>


</body>
</html>